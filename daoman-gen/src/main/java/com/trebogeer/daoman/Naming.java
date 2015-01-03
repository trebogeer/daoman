package com.trebogeer.daoman;

/**
 * @author dimav
 *         Date: 1/27/12
 *         Time: 10:01 AM
 */
final class Naming {

    public static final String GETTER_PREFIX = System.getProperty("daoman.get.prefix","get");
    public static final String BY = "_by_";
    public static final String DAO = System.getProperty("daoman.dao.class.postfix","DAO");
    public static final String PACKAGE = System.getProperty("daoman.tld","com.trebogeer.daoman.api.gen.");

    private Naming() {
    }

    public static String getMapperName(String packageName, String spName, String schema) {

        String modelClassName;
        if (spName.startsWith(GETTER_PREFIX)) {
            modelClassName = spName.substring(GETTER_PREFIX.length() + 1);
        } else {
            modelClassName = spName;
        }
//        if (spName.contains(BY)) {
//            modelClassName = modelClassName.substring(0, modelClassName.indexOf(BY));
//        }

        return packageName + schema.replaceAll("_", ".") + ".mappers." + camelizedName(modelClassName, true) + "Mapper";
    }

    public static String getModelName(String packageName, String spName, String schema) {
        String modelClassName = spName.substring(GETTER_PREFIX.length() + 1);
//        if (spName.contains(BY)) {
//            modelClassName = modelClassName.substring(0, modelClassName.indexOf(BY));
//        }
        return packageName + schema.replaceAll("_", ".") + ".model." + camelizedName(modelClassName, true);
    }

    public static String getDaoName(String packageName, String schema) {
        return packageName + camelizedName(schema, true) + DAO;
    }

    public static String camelizedName(String str, boolean firstToo) {
        String[] tokens = str.split("_");
        StringBuilder builder = new StringBuilder();
        for (final String token : tokens) {
            if (token != null && token.length() > 0) {
                char capLetter = firstToo ? Character.toUpperCase(token.charAt(0)) : token.charAt(0);
                firstToo = true;
                builder.append(capLetter).append(token.substring(1, token.length()));
            }
        }
        return builder.toString();
    }

    public static String getSPName(String schemaDotProcedure) {
        return schemaDotProcedure.substring(schemaDotProcedure.indexOf(".") + 1);
    }

    public static String getSchemaName(String schemaDotProcedure) {
        return schemaDotProcedure.substring(0, schemaDotProcedure.indexOf("."));
    }

}
