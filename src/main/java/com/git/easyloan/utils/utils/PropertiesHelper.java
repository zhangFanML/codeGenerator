package com.git.easyloan.utils.utils;

import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class PropertiesHelper {

    static boolean isSearchSystemProperty = false;
    static Properties p;

    public PropertiesHelper(Properties p) {
        this.p = p;
    }

    public PropertiesHelper(Properties p, boolean isSearchSystemProperty) {
        this.p = p;
        this.isSearchSystemProperty = isSearchSystemProperty;
    }

    public static Properties getProperties() {
        return p;
    }

    public static String getProperty(String key, String defaultValue) {
        String value = null;
        if (isSearchSystemProperty) {
            value = System.getProperty(key);
        }

        if (value == null || "".equals(value.trim())) {
            value = getProperties().getProperty(key);
        }

        return value != null && !"".equals(value.trim()) ? value : defaultValue;
    }

    public String getProperty(String key) {
        return this.getProperty(key, (String) null);
    }

    public String getRequiredProperty(String key) {
        String value = this.getProperty(key);
        if (value != null && !"".equals(value.trim())) {
            return value;
        } else {
            throw new IllegalStateException("required property is blank by key=" + key);
        }
    }

    public Integer getInt(String key) {
        return this.getProperty(key) == null ? null : Integer.parseInt(this.getRequiredProperty(key));
    }

    public int getInt(String key, int defaultValue) {
        return this.getProperty(key) == null ? defaultValue : Integer.parseInt(this.getRequiredProperty(key));
    }

    public int getRequiredInt(String key) {
        return Integer.parseInt(this.getRequiredProperty(key));
    }

    public Boolean getBoolean(String key) {
        return this.getProperty(key) == null ? null : Boolean.parseBoolean(this.getRequiredProperty(key));
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return this.getProperty(key) == null ? defaultValue : Boolean.parseBoolean(this.getRequiredProperty(key));
    }

    public boolean getRequiredBoolean(String key) {
        return Boolean.parseBoolean(this.getRequiredProperty(key));
    }

    public String getNullIfBlank(String key) {
        String value = this.getProperty(key);
        return value != null && !"".equals(value.trim()) ? value : null;
    }

    public PropertiesHelper setProperty(String key, String value) {
        this.p.setProperty(key, value);
        return this;
    }

    public void clear() {
        this.p.clear();
    }

    public Set<Entry<Object, Object>> entrySet() {
        return this.p.entrySet();
    }

    public Enumeration<?> propertyNames() {
        return this.p.propertyNames();
    }

//    public static String[] loadAllPropertiesFromClassLoader(Properties properties, String... resourceNames) throws IOException {
//        List successLoadProperties = new ArrayList();
//        String[] arr$ = resourceNames;
//        int len$ = resourceNames.length;
//
//        for (int i$ = 0; i$ < len$; ++i$) {
//            String resourceName = arr$[i$];
//            Enumeration urls = GeneratorProperties.class.getClassLoader().getResources(resourceName);
//
//            while (urls.hasMoreElements()) {
//                URL url = (URL) urls.nextElement();
//                successLoadProperties.add(url.getFile());
//                InputStream input = null;
//
//                try {
//                    URLConnection con = url.openConnection();
//                    con.setUseCaches(false);
//                    input = con.getInputStream();
//                    if (resourceName.endsWith(".xml")) {
//                        properties.loadFromXML(input);
//                    } else {
//                        properties.load(input);
//                    }
//                } finally {
//                    if (input != null) {
//                        input.close();
//                    }
//
//                }
//            }
//        }
//
//        return (String[]) ((String[]) successLoadProperties.toArray(new String[0]));
//    }
}
