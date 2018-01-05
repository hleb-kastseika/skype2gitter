package com.skype2gitter;

import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Logger;
import org.pmw.tinylog.policies.SizePolicy;
import org.pmw.tinylog.writers.RollingFileWriter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static spark.Spark.get;
import static spark.Spark.port;

public class Skype2Gitter {
    private static final String PROPERTY_FILE = "skype2gitter.properties";
    private static final String LOG_FILE = "skype2gitter.log";
    private static final String LOGGING_FORMAT = "{message}";
    private static final String DEFAULT_SERVER_PORT = "8080";
    private static final String DEFAULT_SERVER_URL = "/";
    private static StatisticsUtil statisticsUtil = StatisticsUtil.getInstance();

    public static void main(String[] args) {
        configTinylog();
        try {
            loadProps();
        } catch (IOException e) {
            Logger.error(e);
            System.exit(0);
        }

        SkypeUtils skypeUtils = new SkypeUtils();
        GitterUtils gitterUtils = new GitterUtils();
        skypeUtils.processSkypeMessages(gitterUtils);

        startStatisticsServer();
    }

    private static void configTinylog() {
        Configurator.currentConfig()
                .addWriter(new RollingFileWriter(LOG_FILE, 4, new SizePolicy(52428800)))
                .formatPattern(LOGGING_FORMAT)
                .activate();
    }

    private static void loadProps() throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(PROPERTY_FILE);
        props.load(fis);
        for (String name : props.stringPropertyNames()) {
            String value = props.getProperty(name);
            System.setProperty(name, value);
        }
    }

    private static void startStatisticsServer() {
        String port = System.getProperty(AppProperties.STATISTICS_SERVER_PORT);
        String url = System.getProperty(AppProperties.STATISTICS_SERVER_URL);
        port(Integer.parseInt(port.isEmpty() || port == null ? DEFAULT_SERVER_PORT : port));
        get(url.isEmpty() || url == null ? DEFAULT_SERVER_URL : url,
                (req, res) -> statisticsUtil.getStatisticsAsString());
    }
}