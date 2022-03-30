package com.sportsdirect.lv.utils;

import org.apache.commons.configuration.*;

public class ConfigurationProvider {

    private static final String CONFIG_PROPERTIES = "config.properties";

    private static final Configuration CONFIGURATION = loadConfiguration();

    private static Configuration loadConfiguration() {
        CompositeConfiguration config = new CompositeConfiguration();
        config.addConfiguration(new SystemConfiguration());
        try {
            config.addConfiguration(new PropertiesConfiguration(CONFIG_PROPERTIES));
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
        return config;
    }

    public static String getProperty(final String key) {return CONFIGURATION.getString(key);}

    public static String getHomePageUrl() {return getProperty("home.page.url");}

    public static String getMensCategoryPath() {return getProperty("path.mens.category");}

    public static String getWomensCategoryPath() {return getProperty("path.womens.category");}

    public static String getKidsCategoryPath() {return getProperty("path.kids.category");}

    public static String getOutletCategoryPath() {return getProperty("path.outlet.category");}

    public static String getUscCategoryPath() {return getProperty("path.usc.category");}

}
