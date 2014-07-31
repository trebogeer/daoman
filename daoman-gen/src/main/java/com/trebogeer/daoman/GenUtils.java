package com.trebogeer.daoman;

import com.google.common.base.Strings;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;
import com.trebogeer.daoman.jdbc.RowMapper;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author dimav
 *         Date: 1/17/12
 *         Time: 4:08 PM
 */
public final class GenUtils {

    public static final String GET_VALUE_DAO_MAN= "getValueOrNull";

    private static final int MAX_ALLOWED_RS_DIFF = 5;


    public static JDefinedClass getDaoClass(String packageName, String schema, JCodeModel codeModel, String dataSourceProvider) throws JClassAlreadyExistsException {
        JDefinedClass clazz = codeModel
                ._class(JMod.PUBLIC | JMod.FINAL, Naming.getDaoName(packageName, schema), ClassType.CLASS);
        clazz.constructor(JMod.PRIVATE);
        // private final DataSource dataSource = SPINDataSourceLocator.getInstance().accountDataSource();
        JVar dao = clazz.field(JMod.PRIVATE | JMod.STATIC | JMod.FINAL, DataSource.class, "dataSource");
        clazz.init().directStatement(String.format("dataSource = " + dataSourceProvider, schema));
        return clazz;
    }

    public static JBlock getDaoMethod(
            final JCodeModel codeModel, final JType returnType,
            final String storedProcedureName, final JDefinedClass daoClass,
            final Collection<SQLParam> inputParams, final Collection<SQLParam> outParams,
            final JClass paramFactory, final JClass storedProcExecutor, final JClass mapperFactory,
            final String packageName, final String schema,
            final JClass clazzResult, final Map<Collection<SQLParam>, JClass> mappers,
            final Map<String, JClass> models, final Multimap<String, SQLParam> resultSets,
            final Collection<SQLParam> allParameters) throws JClassAlreadyExistsException {
        JMethod daoMethod;
        if (returnType == null)
            daoMethod = daoClass.method(JMod.PUBLIC | JMod.STATIC, Void.TYPE, Naming.camelizedName(storedProcedureName, false));
        else
            daoMethod = daoClass.method(JMod.PUBLIC | JMod.STATIC, returnType, Naming.camelizedName(storedProcedureName, false));

        for (SQLParam input : inputParams) {
            if (!Strings.isNullOrEmpty(input.getName())) {
                JVar jvar = daoMethod.param(JMod.FINAL, input.getJavaType(), Naming.camelizedName(input.getName(), false));

//                if (input.isNullable())
//                    jvar.annotate(Nullable.class);

            }
        }
        JBlock daoMethodBody = daoMethod.body();
        List<JVar> outs = declareOutParameters(outParams, paramFactory, codeModel, daoMethodBody);
        if (returnType == null || outParams.size() > 0) {
            JInvocation call = storedProcExecutor.staticInvoke("call").arg(JExpr.ref("dataSource")).arg(JExpr.lit(storedProcedureName));
            writeExecutorParameters(outs, allParameters, call, paramFactory);
            daoMethodBody.add(call);
        }

        if (returnType != null) {
            if (outParams.size() == 1) {
                daoMethodBody._return(outs.get(0).invoke(GET_VALUE_DAO_MAN));
            } else if (outParams.size() == 2) {
                daoMethodBody._return(JExpr._new(returnType).arg(
                        outs.get(0).invoke(GET_VALUE_DAO_MAN)).arg(
                        outs.get(1).invoke(GET_VALUE_DAO_MAN)));
            } else if (outParams.size() == 3) {
                daoMethodBody._return(JExpr._new(returnType).arg(
                        outs.get(0).invoke(GET_VALUE_DAO_MAN)).arg(
                        outs.get(1).invoke(GET_VALUE_DAO_MAN)).arg(
                        outs.get(2).invoke(GET_VALUE_DAO_MAN)));
            } else if (outParams.size() > 3) {
                // Generate model bean
                daoMethodBody._return(JExpr._null());
            } else if (outParams.size() == 0) {
                Collection<SQLParam> resultSet = resultSets.get(schema + "." + storedProcedureName);
                if (resultSet != null) {
                    JClass mapper;
                    JInvocation call = storedProcExecutor.staticInvoke("queryList").arg(JExpr.ref("dataSource")).arg(JExpr.lit(storedProcedureName));
                    if (resultSet.size() == 1) {
                        call = call.arg(mapperFactory.staticInvoke("create" + resultSet.iterator().next().getJavaType().getSimpleName() + "Mapper"));
                    } else if (resultSet.size() == 2) {
                        Iterator<SQLParam> iterator = resultSet.iterator();
                        call = call.arg(mapperFactory.staticInvoke("create" + iterator.next().getJavaType().getSimpleName() + iterator.next().getJavaType().getSimpleName() + "Mapper"));
                    } else {

                        String mapperName = Naming.getMapperName(packageName, storedProcedureName, schema);

                        if (mappers.containsKey(resultSet)) {
                            mapper = mappers.get(resultSet);
                            System.out.println("Found the same resultset, reusing mapper. " + mapperName);
                        } else {
                            try {
                                JDefinedClass mapper1 = codeModel._class(JMod.FINAL | JMod.PUBLIC, Naming.getMapperName(packageName, storedProcedureName, schema), ClassType.CLASS);
                                JClass rowMapper = codeModel.ref(RowMapper.class);
                                rowMapper = rowMapper.narrow(clazzResult);
                                mapper1._extends(rowMapper);
                                mapperBody(models, mapper1, packageName, schema, storedProcedureName, resultSet);
                                mappers.put(resultSet, mapper = mapper1);
                            } catch (Exception e) {
                                System.out.println(Naming.getMapperName(packageName, storedProcedureName, schema));
                                System.out.println(storedProcedureName);
                                // System.out.println(getMapperName(packageName, storedProcedureName, schema));
                                // System.out.println(getMapperName(packageName, storedProcedureName, schema));
                                throw new RuntimeException(e);
                            }
                        }
                        call = call.arg(JExpr._new(mapper));
                    }
                    writeExecutorParameters(null, allParameters, call, paramFactory);
                    daoMethodBody._return(call);
                }
            }
        }


        return daoMethod.body();
    }


    private static List<JVar> declareOutParameters(
            final Collection<SQLParam> outParams, final JClass paramFactory,
            final JCodeModel codeModel, final JBlock daoMethodBody) {
        List<JVar> outs = new ArrayList<JVar>();
        if (outParams.size() > 0) {
            for (SQLParam sqlParam : outParams) {
                JInvocation invoke = paramFactory.staticInvoke(sqlParam.getParamMethodName());
                if (sqlParam.isInOut()) {
                    invoke = invoke.arg(JExpr.ref(Naming.camelizedName(sqlParam.getName(), false)));
                }
                outs.add(daoMethodBody.decl(JMod.FINAL, codeModel.ref(sqlParam.getDaoManType()),
                                            "ref" + Naming.camelizedName(sqlParam.getName(), true),
                                            invoke));
            }
        }
        return outs;
    }

    public static JType notCollectionReturnType(final Collection<SQLParam> outParams, final JCodeModel codeModel) {
        JType returnType = null;
//        List<Class<?> > params = getSQLParams(outParams.iterator());
//        JClass ret = codeModel.ref(Decade.class);
//        returnType = ret.narrow((JClass) params);


        if (outParams.size() == 1) {
            SQLParam retParam = outParams.iterator().next();
            returnType = codeModel.ref(retParam.getJavaType());
        } else if (outParams.size() == 2) {
            Iterator<SQLParam> it = outParams.iterator();
            SQLParam first = it.next();
            SQLParam second = it.next();

            JClass ret = codeModel.ref(Pair.class);
            returnType = ret.narrow(codeModel.ref(first.getJavaType()), codeModel.ref(second.getJavaType()));
        } else if (outParams.size() == 3) {
            Iterator<SQLParam> it = outParams.iterator();
            SQLParam first = it.next();
            SQLParam second = it.next();
            SQLParam third = it.next();

            JClass ret = codeModel.ref(Triplet.class);
            returnType = ret.narrow(first.getJavaType(), second.getJavaType(), third.getJavaType());
        } //else if (outParams.size() == 4) {
//            Iterator<SQLParam> it = outParams.iterator();
//            SQLParam first = it.next();
//            SQLParam second = it.next();
//            SQLParam third = it.next();
//            SQLParam forth = it.next();
//
//            JClass ret = codeModel.ref(Quartet.class);
//            returnType = ret.narrow(first.getJavaType(), second.getJavaType(), third.getJavaType(), forth.getJavaType());
//        } else if (outParams.size() == 5) {
//            Iterator<SQLParam> it = outParams.iterator();
//            SQLParam first = it.next();
//            SQLParam second = it.next();
//            SQLParam third = it.next();
//            SQLParam forth = it.next();
//            SQLParam fifth = it.next();
//
//            JClass ret = codeModel.ref(Quintet.class);
//            returnType = ret.narrow(first.getJavaType(), second.getJavaType(), third.getJavaType(), forth.getJavaType(), fifth.getJavaType());
//        } else if (outParams.size() == 6) {
//            Iterator<SQLParam> it = outParams.iterator();
//            SQLParam first = it.next();
//            SQLParam second = it.next();
//            SQLParam third = it.next();
//            SQLParam forth = it.next();
//            SQLParam fifth = it.next();
//            SQLParam sixth = it.next();
//
//            JClass ret = codeModel.ref(Sextet.class);
//            returnType = ret.narrow(first.getJavaType(), second.getJavaType(), third.getJavaType(), forth.getJavaType(), fifth.getJavaType(),
//                    sixth.getJavaType());
//        } else if (outParams.size() == 7) {
//            Iterator<SQLParam> it = outParams.iterator();
//            SQLParam first = it.next();
//            SQLParam second = it.next();
//            SQLParam third = it.next();
//            SQLParam forth = it.next();
//            SQLParam fifth = it.next();
//            SQLParam sixth = it.next();
//            SQLParam seventh = it.next();
//
//            JClass ret = codeModel.ref(Septet.class);
//            returnType = ret.narrow(first.getJavaType(), second.getJavaType(), third.getJavaType(), forth.getJavaType(), fifth.getJavaType(),
//                    sixth.getJavaType(), seventh.getJavaType());
//        } else if (outParams.size() == 8) {
//            Iterator<SQLParam> it = outParams.iterator();
//            SQLParam first = it.next();
//            SQLParam second = it.next();
//            SQLParam third = it.next();
//            SQLParam forth = it.next();
//            SQLParam fifth = it.next();
//            SQLParam sixth = it.next();
//            SQLParam seventh = it.next();
//            SQLParam eighth = it.next();
//
//            JClass ret = codeModel.ref(Octet.class);
//            returnType = ret.narrow(first.getJavaType(), second.getJavaType(), third.getJavaType(), forth.getJavaType(), fifth.getJavaType(),
//                    sixth.getJavaType(), seventh.getJavaType(),eighth.getJavaType());
//        } else if (outParams.size() == 9) {
//            Iterator<SQLParam> it = outParams.iterator();
//            SQLParam first = it.next();
//            SQLParam second = it.next();
//            SQLParam third = it.next();
//            SQLParam forth = it.next();
//            SQLParam fifth = it.next();
//            SQLParam sixth = it.next();
//            SQLParam seventh = it.next();
//            SQLParam eighth = it.next();
//            SQLParam ninth = it.next();
//
//            JClass ret = codeModel.ref(Ennead.class);
//            returnType = ret.narrow(first.getJavaType(), second.getJavaType(), third.getJavaType(), forth.getJavaType(), fifth.getJavaType(),
//                    sixth.getJavaType(), seventh.getJavaType(), eighth.getJavaType(),ninth.getJavaType());
//        } else if (outParams.size() == 10) {
//            Iterator<SQLParam> it = outParams.iterator();
//            SQLParam first = it.next();
//            SQLParam second = it.next();
//            SQLParam third = it.next();
//            SQLParam forth = it.next();
//            SQLParam fifth = it.next();
//            SQLParam sixth = it.next();
//            SQLParam seventh = it.next();
//            SQLParam eighth = it.next();
//            SQLParam ninth = it.next();
//            SQLParam tenth = it.next();
//
//            JClass ret = codeModel.ref(Decade.class);
//            returnType = ret.narrow(first.getJavaType(), second.getJavaType(), third.getJavaType(), forth.getJavaType(), fifth.getJavaType(),
//                    sixth.getJavaType(), seventh.getJavaType(), eighth.getJavaType(), ninth.getJavaType(),tenth.getJavaType());
            // TODO produce return bean
            // continue;
            // Current gen.a implementation does not support more than 3 out/inout parameters
            // throw new XRuntime("Current gen.a implementation does not support more than 3 out/inout parameters");
  //      }
        return returnType;
    }

    private static List<Class<?> > getSQLParams(Iterator<SQLParam> it){
        List<Class<?> > result = new LinkedList<Class<?>>();
        while (it.hasNext()){
            result.add(it.next().getJavaType());
        }
        return result;
    }

    public static Pair<JType, JClass> collectionResultModelMapper(
            final String schemaDotProcedure, final Multimap<String, SQLParam> resultSets,
            final String packageName, final Map<String, JClass> models,
            final JCodeModel codeModel) throws JClassAlreadyExistsException {
        JType returnType = null;
        JClass clazzResult = null;
        String storedProcedureName = Naming.getSPName(schemaDotProcedure);
        String schema = Naming.getSchemaName(schemaDotProcedure);
        if (storedProcedureName.startsWith(Naming.GETTER_PREFIX) && resultSets.containsKey(schemaDotProcedure)) {

            // TODO need better mechanism to verify if the same resultset already exists
            Collection<SQLParam> resultSetCollection = resultSets.get(schemaDotProcedure);
            if (resultSetCollection != null) {
                if (resultSetCollection.size() == 1) {
                    SQLParam param = resultSetCollection.iterator().next();
                    clazzResult = codeModel.ref(param.getJavaType());
                } else if (resultSetCollection.size() == 2) {
                    Iterator<SQLParam> iterator = resultSetCollection.iterator();
                    SQLParam paramFirst = iterator.next();
                    SQLParam paramSecond = iterator.next();
                    clazzResult = codeModel.ref(Pair.class);
                    clazzResult = clazzResult.narrow(paramFirst.getJavaType());
                    clazzResult = clazzResult.narrow(paramSecond.getJavaType());
                } else {
                    clazzResult = models.get(schemaDotProcedure);
                }
            }
            JClass ret = codeModel.ref(List.class);
            returnType = ret.narrow(clazzResult);
        }
        return new Pair<JType, JClass>(returnType, clazzResult);

    }

    // BeanT mapRow(final int rowIndex, final RSWrapperT rsw) throws SQLException;
    public static void mapperBody(final Map<String, JClass> models, JDefinedClass mapper, String packageName, String schema, String proc, Collection<SQLParam> resultSet) {
        String modelName = Naming.getModelName(packageName, proc, schema);
        JClass model = models.get(schema + "." + proc);
        if (model == null) {
            throw new NullPointerException("Model [" + modelName + "] is null");
        }
        JMethod mapRow = mapper.method(JMod.PUBLIC, model, "mapRow");
        JVar index = mapRow.param(JMod.FINAL, Integer.TYPE, "rowIndex");
        JVar rsw = mapRow.param(JMod.FINAL, /*ResultSetWrapper.class*/Object.class, "rsw");
        mapRow._throws(SQLException.class);

        JBlock body = mapRow.body();

        JInvocation returnExpr = JExpr._new(model);
        int i = 1;
        for (SQLParam res : resultSet) {
            returnExpr.arg(rsw.invoke(res.getResultSetWrapperMethodName()).arg(JExpr.lit(i)));
            i++;
        }

        body._return(returnExpr);
    }

    public static JInvocation writeExecutorParameters(List<JVar> inOuts, Collection<SQLParam> inputParameters, JInvocation call, JClass paramFactory) {
        Iterator<JVar> inOutIterator = null;
        if (inOuts != null) {
            inOutIterator = inOuts.iterator();
        }
        for (SQLParam param : inputParameters) {
            if (param.isOut() && param.getName().equals("error_code")) {
                call = call.arg(paramFactory.staticInvoke("outStatus"));
            } else if ((param.isInOut() || param.isOut()) && inOutIterator != null && inOutIterator.hasNext()) {
                call = call.arg(inOutIterator.next());
            } else {
                call = call.arg(paramFactory.staticInvoke(param.getParamMethodName()).arg(JExpr.ref(Naming.camelizedName(param.getName(), false))));
            }

        }
        return call;
    }

    public static Map<String, JClass> collapseModel(
            final Multimap<String, SQLParam> resultSets, final String packageName,
            final String schema, final JCodeModel codeModel) {
        Map<String, JClass> storedProcToModel = new HashMap<String, JClass>();
        Multimap<String, String> storedProcedureToTableOriginalName = LinkedHashMultimap.create();
        for (String procedure : resultSets.keySet()) {

            Collection<SQLParam> resultSet = resultSets.get(procedure);
            if (resultSet.size() > 2) {
                for (SQLParam column : resultSet) {
                    storedProcedureToTableOriginalName.put(procedure, column.getTableOriginalName());
                }
            }
        }

        Set<String> storedProcedures = new LinkedHashSet<String>(storedProcedureToTableOriginalName.keySet());
        for (String storedProcedure : storedProcedures) {
            if (storedProcedureToTableOriginalName.containsKey(storedProcedure)) {
                Collection<String> tables = new LinkedHashSet<String>(storedProcedureToTableOriginalName.get(storedProcedure));
                Collection<String> similarResultSetStoredProcedures = new LinkedHashSet<String>();
                Collection<SQLParam> finalResultSet = new LinkedHashSet<SQLParam>();
                similarResultSetStoredProcedures.add(storedProcedure);
                Collection<SQLParam> storedProcedureResultSet = resultSets.get(storedProcedure);
                finalResultSet.addAll(storedProcedureResultSet);
                storedProcedureToTableOriginalName.removeAll(storedProcedure);
                for (String otherStoredProcedure : new HashSet<String>(storedProcedureToTableOriginalName.keySet())) {
                    if (!otherStoredProcedure.equals(storedProcedure) && storedProcedureToTableOriginalName.containsKey(otherStoredProcedure)) {
                        Collection<String> otherTables = storedProcedureToTableOriginalName.get(otherStoredProcedure);
                        Collection<String> testRetain = new HashSet<String>(tables);
                        testRetain.retainAll(otherTables);
                        if (!testRetain.isEmpty()) {
                            Collection<SQLParam> otherStoredProcedureResultSet = resultSets.get(otherStoredProcedure);
                            if (storedProcedureResultSet.equals(otherStoredProcedureResultSet)
                                    || matchByNameOrderType(storedProcedureResultSet, otherStoredProcedureResultSet, storedProcedure, otherStoredProcedure, finalResultSet)) {
                                similarResultSetStoredProcedures.add(otherStoredProcedure);
                                storedProcedureToTableOriginalName.removeAll(otherStoredProcedure);
                            }
                        }
                    }
                }

                String modelClassName = Naming.getModelName(packageName, Naming.getSPName(shortestName(similarResultSetStoredProcedures)), schema);

                JDefinedClass classResultDefinition = null;
                try {
                    classResultDefinition = codeModel._class(JMod.PUBLIC, modelClassName, ClassType.CLASS);
                } catch (JClassAlreadyExistsException e) {
                    System.out.println(modelClassName);
                    e.printStackTrace();
                }
                for (String storedProc : similarResultSetStoredProcedures)
                    storedProcToModel.put(storedProc, classResultDefinition);

                JMethod constructor = classResultDefinition.constructor(JMod.PUBLIC);
                JBlock constructorBody = constructor.body();
                try {
                    for (SQLParam column : finalResultSet) {
                        String colName = column.getTableName() != null ? column.getTableName() + "_" + column.getName() : column.getName();
                        String fieldName = Naming.camelizedName(colName, false);
                        String method = Naming.camelizedName(column.getName(), true);
                        constructor.param(column.getJavaType(), fieldName);
                        JFieldVar fld = classResultDefinition.field(JMod.PRIVATE, column.getJavaType(), fieldName);
                        constructorBody.assign(JExpr._this().ref(fld), JExpr.ref(fieldName));
                        // getter
                        JMethod getter = classResultDefinition.method(JMod.PUBLIC, column.getJavaType(), Naming.GETTER_PREFIX + method);

                        getter.body()._return(JExpr._this().ref(fieldName));
                        // setter 
                        JMethod setter = classResultDefinition.method(JMod.PUBLIC, Void.TYPE, "set" + method);
                        setter.param(column.getJavaType(), fieldName);
                        setter.body().assign(JExpr._this().ref(fld), JExpr.ref(fieldName));
                    }
                } catch (IllegalArgumentException ex) {
                    System.out.println(similarResultSetStoredProcedures);
                    ex.printStackTrace();
                    throw new RuntimeException(ex);
                }


            }
        }


        return storedProcToModel;
    }


    private static String shortestName(Collection<String> names) {
        int min = Integer.MAX_VALUE;
        String shortest = null;
        for (String name : names) {
            if (name != null && name.length() < min) {
                min = name.length();
                shortest = name;
            }
        }
        return shortest;
    }


    private static int diffResultSets(Collection<SQLParam> source, Collection<SQLParam> target) {

        int absDiff = Math.abs(source.size() - target.size());
        if (absDiff < MAX_ALLOWED_RS_DIFF) {
            int sourceSize = source.size();
            Set<SQLParam> total = new HashSet<SQLParam>(source);
            total.addAll(target);
            int totalDeDuped = total.size();
            return totalDeDuped - sourceSize;
        } else {
            return absDiff;
        }
    }

    private static boolean matchByNameOrderType(Collection<SQLParam> source, Collection<SQLParam> target, String sourceStoredProcedure, String targetStoredProcedure, Collection<SQLParam> finalResultSet) {
//        if (sourceStoredProcedure.contains(targetStoredProcedure) || targetStoredProcedure.contains(sourceStoredProcedure)) {
//
//        }
        Iterator<SQLParam> sourceIterator = source.iterator();
        Iterator<SQLParam> targetIterator = target.iterator();
        while (sourceIterator.hasNext() && targetIterator.hasNext()) {
            SQLParam sourceParam = sourceIterator.next();
            SQLParam targetParam = targetIterator.next();
            if (!(sourceParam.getColumnName().equals(targetParam.getColumnName()) || sourceParam.getName().equals(targetParam.getName())
                    && sourceParam.getDbTypeName().equals(targetParam.getDbTypeName()))) {
                return false;
            }
        }
        LinkedHashSet<SQLParam> leftOverParams = new LinkedHashSet<SQLParam>();
        int sourceLeftOverCounter = 0;
        while (sourceIterator.hasNext()) {
            leftOverParams.add(sourceIterator.next());
            sourceLeftOverCounter++;
        }
        int targetLeftOverCounter = 0;
        if (targetIterator.hasNext()) {
            while (targetIterator.hasNext()) {
                leftOverParams.add(targetIterator.next());
                targetLeftOverCounter++;
            }
        }
        int leftOver = Math.max(sourceLeftOverCounter, targetLeftOverCounter);

        boolean matches = leftOver <= MAX_ALLOWED_RS_DIFF;
        if (matches) {
            finalResultSet.addAll(leftOverParams);
        }
        return matches;
    }
}
