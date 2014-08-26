package com.trebogeer.daoman;

import org.kohsuke.args4j.Option;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author dimav
 *         Date: 8/25/14
 *         Time: 5:18 PM
 */
public class Config {
    @Option(name = "--generated-source-dir", aliases = {"-gsd"})
    String generatedSourceDir = "/tmp/generated-source-dir";

    @Option(name = "--database-host", aliases = {"-h"})
    String dbHost = "localhost";

    @Option(name = "--database-port", aliases = {"-P"})
    String dbPort = "3306";

    @Option(name = "--user", aliases = {"-u"})
    String userName = "daoman";

    @Option(name = "--password", aliases = {"-p"})
    String password = "daoman";

    @Option(name = "--schema", aliases = {"-s"}/*, multiValued = true*/)
    List<String> schemaName = newArrayList();

    @Option(name = "--meta-config-file", aliases = {"-mcf"})
    String config = null;

    @Option(name = "--application", aliases = {"-a"})
    String app = "app";

    @Option(name = "--data-source-provider", aliases = {"-dsp"})
    String dataSourceProvider = "com.trebogeer.datasource.%sDataSource();";

    @Option(name = "--debug", aliases = "-d")
    String debug = "n";//"y";

    @Option(name = "--stored-procedures", aliases = {"-sp"}/*, multiValued = true*/)
    List<String> storedProcs = newArrayList();

    @Option(name = "--out-parameter-size", aliases = "-ops")
    String outParamSize = "n";

    @Option(name = "--error-code-name", aliases = "-ecn")
    String errorCodeName = "error_code";

}
