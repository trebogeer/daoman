package com.trebogeer.daoman;


import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JType;
import com.sun.codemodel.writer.SingleStreamCodeWriter;
import com.trebogeer.daoman.util.JDBCUtil;
import org.javatuples.Pair;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import static com.trebogeer.daoman.GenUtils.collapseModel;
import static com.trebogeer.daoman.GenUtils.collectionResultModelMapper;
import static com.trebogeer.daoman.GenUtils.getDaoClass;
import static com.trebogeer.daoman.GenUtils.getDaoMethod;
import static com.trebogeer.daoman.GenUtils.notCollectionReturnType;
import static com.trebogeer.daoman.Naming.GETTER_PREFIX;
import static com.trebogeer.daoman.Naming.PACKAGE;
import static com.trebogeer.daoman.Naming.getSPName;
import static com.trebogeer.daoman.Naming.getSchemaName;

/**
 * @author dimav
 *         Date: 1/23/12
 *         Time: 5:05 PM
 */
public class DaoMan {

    private Config cfg;

    private DaoMan(Config cfg) {
        this.cfg = cfg;
    }

    public static void main(String... args) {

        Config cfg = new Config();
        CmdLineParser parser = new CmdLineParser(cfg);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            throw new RuntimeException(e);
        }
        DaoMan daoMan = new DaoMan(cfg);
        daoMan.exec();

        try{
            AbandonedConnectionCleanupThread.shutdown();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void exec() {

        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://" + cfg.dbHost + ":" + cfg.dbPort + "/",
                                              cfg.userName, cfg.password);
            LinkedList<String> schemas = new LinkedList<String>();
            DatabaseMetaData aBaseM = con.getMetaData();
            ResultSet rs = null;

            if (cfg.schemaName.isEmpty() || cfg.schemaName.contains("all")) {
                rs = aBaseM.getCatalogs();
                while (rs.next()) {
                    schemas.add(rs.getString(1));
                }
                JDBCUtil.close(rs);
            } else {
                schemas.addAll(new HashSet<String>(cfg.schemaName));
            }

            //SqlUtil.close(con);
            Multimap<String, String> procs = HashMultimap.create();
            for (String schema : schemas) {
                if (cfg.storedProcs == null || cfg.storedProcs.isEmpty()) {
                    rs = con.getMetaData().getProcedures(schema, null, null);
                    while (rs.next()) {
                        // skipping functions for now ,
                        // need to process them separately
                        if (rs.getInt(8) != 2)
                            procs.put(rs.getString(1), rs.getString(3));
                    }
//                    ResultSetMetaData rsmd = rs.getMetaData();
//                    int rowcount = 0;
//                    while (rs.next ()) {
//                        System.out.println ("----------- Row " + rowcount++ + " -----------");
//                        // Retrieve each row in the ResultSet
//                        for (int i = 1; i <= rsmd.getColumnCount (); i++) {
//                            System.out.println (i + ": " + rsmd.getColumnName (i) + " = " +
//                                                        rs.getString (i));
//                        }
//                    }

                    JDBCUtil.close(rs);
                } else {
                    for (String sp : cfg.storedProcs) {
                        procs.put(schema, sp);
                    }
                }
            }

            JDBCUtil.close(con);
            System.out.println(procs.values().size());
            Multimap<String, SQLParam> sprocParams = LinkedListMultimap.create();
            for (String schema : procs.keySet()) {

                con = getConnection(schema);
                try {
                    for (String sproc : procs.get(schema)) {
                        rs = con.getMetaData().getProcedureColumns(schema, null, sproc, "%");
                        while (rs.next()) {
                            sprocParams.put(schema + '.' + sproc, new SQLParam(
                                    rs.getString(4),
                                    rs.getShort(5),
                                    rs.getInt(6),
                                    rs.getShort(12)));
                        }
//                    ResultSetMetaData rsmd = rs.getMetaData();
//                    int rowcount = 0;
//                    while (rs.next ()) {
//                        System.out.println ("----------- Row " + rowcount++ + " -----------");
//                        // Retrieve each row in the ResultSet
//                        for (int i = 1; i <= rsmd.getColumnCount (); i++) {
//                            System.out.println (i + ": " + rsmd.getColumnName (i) + " = " +
//                                                        rs.getString (i));
//                        }
//                    }

                        JDBCUtil.close(rs);
                    }
                } finally {
                    if (con != null)
                        JDBCUtil.close(con);
                }


            }

            Multimap<String, SQLParam> resultSets = LinkedListMultimap.create();
            Multimap<String, String> tableToColumns = LinkedListMultimap.create();
            Map<String, String> columnLabelToColumnName = new HashMap<String, String>();
            for (String key : sprocParams.keySet()) {
                try {
                    String schema = getSchemaName(key);
                    String storedProc = getSPName(key);
                    // TODO May be should hit all stored procedures and ignore exceptions from modifiers
                    if (storedProc.startsWith(GETTER_PREFIX)) {
                        Connection connection = getConnection(schema);
                        CallableStatement stmt = null;
                        ResultSet resultSet = null;
                        try {
                            Collection<SQLParam> parameters = sprocParams.get(key);
                            stmt = connection.prepareCall(JDBCUtil.generateSqlCallString(storedProc, parameters.size()));
                            int i = 1;
                            for (SQLParam param : parameters) {
                                if (param.isOut()) {
                                    stmt.registerOutParameter(i, param.getType());
                                } else if (param.isInOut()) {
                                    param.setFakeParam(stmt, i);
                                    stmt.registerOutParameter(i, param.getType());
                                } else {
                                    param.setFakeParam(stmt, i);
                                }
                                i++;
                            }
                            stmt.execute();
                            resultSet = stmt.getResultSet();
                            if (resultSet != null) {
                                ResultSetMetaData meta = resultSet.getMetaData();
                                int columnCount = meta.getColumnCount();
                                if (columnCount > 0) {
                                    for (int c = 1; c <= columnCount; c++) {
                                        com.mysql.jdbc.ResultSetMetaData mySqlMeta = (com.mysql.jdbc.ResultSetMetaData) meta;
                                        Field metaFields = com.mysql.jdbc.ResultSetMetaData.class.getDeclaredField("fields");
                                        // metaFields.
                                        metaFields.setAccessible(true);
                                        com.mysql.jdbc.Field[] fields = (com.mysql.jdbc.Field[]) metaFields.get(mySqlMeta);
                                        com.mysql.jdbc.Field field = fields[c - 1];

                                        resultSets.put(key, new SQLParam(
                                                meta.getColumnLabel(c),
                                                (short) 0,
                                                meta.getColumnType(c),
                                                (short) meta.isNullable(c),
                                                meta.getTableName(c),
                                                field.getOriginalTableName(),
                                                field.getNameNoAliases(),
                                                meta.getColumnTypeName(c),
                                                meta.getColumnDisplaySize(c)));
                                        //System.out.println(c + ", display_size: " + meta.getColumnDisplaySize(c) + " , dbType: " + meta.getColumnTypeName(c) + " , column_name: " + storedProc + "." + meta.getColumnName(c));

                                        tableToColumns.put(field.getOriginalTableName(), meta.getCatalogName(c));
                                        columnLabelToColumnName.put(meta.getColumnName(c), meta.getColumnLabel(c));
                                    }
                                }
                            }else {
//                                switch (outParamPos.size()){
//                                    case 0: System.out.println(key + " stored procedure has not out params"); break;
//                                    case 1: Unit a = new Unit<Object>(stmt.getInt(outParamPos.get(0)));
//                                        System.out.println(key + " stored procedure returned error code : 1: " + a.getValue0().toString());  break;
//                                    case 2: Pair b = new Pair<Object, Object>(stmt.getInt(outParamPos.get(0)), stmt.getInt(outParamPos.get(1)));
//                                        System.out.println(key + " stored procedure returned error code : 1: " + b.getValue0().toString() + " 2: " + b.getValue1().toString()); break;
//                                    case 3: Triplet c = new Triplet<Object, Object, Object>(stmt.getInt(outParamPos.get(0)), stmt.getInt(outParamPos.get(1)), stmt.getInt(outParamPos.get(2)));
//                                        System.out.println(key + " stored procedure returned 1: " + c.getValue0().toString() + " 2: " + c.getValue1().toString() + " 3: " + c.getValue2().toString()); break;
//                                }
 //                               System.out.println(key + " stored procedure returned error code : " +stmt.getInt(1));
                            }

                        } catch (Exception e){
                            System.out.println("Broken stored procedure: " + storedProc);
                            e.printStackTrace();
                        }finally {
                            JDBCUtil.close(rs, stmt);
                            JDBCUtil.close(connection);
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Broken stored procedure: " + key);
                    e.printStackTrace(System.out);
                }/*finally {
                    if (con != null)
                    JDBCUtil.close(con);
                }*/
            }

            generateCode(procs.keySet(), sprocParams, resultSets);
            generateUnitTests(procs.keySet(), sprocParams, resultSets);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null)
            JDBCUtil.close(con);
        }
    }

    private void generateUnitTests(Set<String> schemas, Multimap<String, SQLParam> storedProcedures, Multimap<String, SQLParam> resultSets) {
        final JCodeModel codeModel = new JCodeModel();
    }

    private Connection getConnection(String schema) throws Exception {
        final StringBuilder bldr = new StringBuilder();
        bldr.append("jdbc:mysql://")
                .append(cfg.dbHost).append(":")
                .append(cfg.dbPort).append("/").append(schema)
                .append("?zeroDateTimeBehavior=convertToNull&cachePrepStmts=true&cacheCallableStmts=true&prepStmtCacheSize=100&maintainTimeStats=false");

        return DriverManager.
                getConnection(
                        bldr.toString(),
                        cfg.userName, cfg.password);
    }


    private void generateCode(Set<String> schemas, Multimap<String, SQLParam> storedProcedures, Multimap<String, SQLParam> resultSets) throws Exception {
        final JCodeModel codeModel = new JCodeModel();

        final Map<Collection<SQLParam>, JClass> mappers = new HashMap<Collection<SQLParam>, JClass>();
        for (final String schema : schemas) {

            Multimap<String, SQLParam> filteredBySchema = Multimaps.filterKeys(storedProcedures, new Predicate<String>() {
                @Override
                public boolean apply(final String input) {
                    return input.startsWith(schema);
                }
            });

            if (filteredBySchema != null && !filteredBySchema.isEmpty()) {
                final Map<String, JClass> model = collapseModel(resultSets, PACKAGE + cfg.app + ".", schema, codeModel);
                JDefinedClass daoClass = getDaoClass(PACKAGE + cfg.app + ".", schema, codeModel, cfg.dataSourceProvider);
                JClass paramFactory = codeModel.directClass("com.trebogeer.daoman.jdbc.SPParams");
                JClass storedProcExecutor = codeModel.directClass("com.trebogeer.daoman.jdbc.SPExec");
                JClass mapperFactory = codeModel.directClass("com.trebogeer.daoman.jdbc.SimpleMapperFactory");
                JClass rsUtils = codeModel.directClass("com.trebogeer.daoman.jdbc.RSUtils");
                for (String schemaDotProcedure : filteredBySchema.keySet()) {
                    String sp = getSPName(schemaDotProcedure);
                    JType returnType = null;
                    JClass clazzResult = null;
                    // all stored procedure input parameters
                    Collection<SQLParam> inputParameters = filteredBySchema.get(schemaDotProcedure);
                    // all output parameters java is interested in
                    Collection<SQLParam> outParams = Collections2.filter(inputParameters, new Predicate<SQLParam>() {
                        @Override
                        public boolean apply(final SQLParam input) {
                            return input.isInOut() || (input.isOut() && !input.getName().equals(cfg.errorCodeName));
                        }
                    });
                    // assuming that if a stored procedure has a list of output parameters it won't have a resultset
                    if (outParams.size() > 0) {
                        returnType = notCollectionReturnType(outParams, codeModel, sp, PACKAGE + app + ".", schema);

                    } else
                        // TODO better condition ?
                        if (sp.startsWith(GETTER_PREFIX) && resultSets.containsKey(schemaDotProcedure)) {
                            Pair<JType, JClass> typeAndClass = collectionResultModelMapper(
                                    schemaDotProcedure, resultSets, PACKAGE + cfg.app + ".", model, codeModel
                            );
                            returnType = typeAndClass.getValue0();
                            clazzResult = typeAndClass.getValue1();
                        }

                    // in or inout parameters, those that are actually passed to dao from java
                    Collection<SQLParam> inputParams = Collections2.filter(inputParameters, new Predicate<SQLParam>() {
                        @Override
                        public boolean apply(final SQLParam input) {
                            return input.isIn() || input.isInOut();
                        }
                    });

                    JBlock daoMethodBody = getDaoMethod(
                            codeModel, returnType, sp, daoClass, inputParams, outParams,
                            paramFactory, storedProcExecutor, mapperFactory,
                            PACKAGE + cfg.app + ".", schema, clazzResult, mappers, model, resultSets, inputParameters, rsUtils);

                }


            }
        }
        if ("y".equalsIgnoreCase(cfg.debug)) {
            codeModel.build(new SingleStreamCodeWriter(System.out));
        } else {
            File source = new File(cfg.generatedSourceDir);
            if (!source.exists()) {
                source.mkdirs();
            }
            codeModel.build(new File(cfg.generatedSourceDir));
        }

    }
}
