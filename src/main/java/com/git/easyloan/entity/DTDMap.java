package com.git.easyloan.entity;

import java.util.*;
import java.util.Map.Entry;

public class DTDMap<V> extends LinkedHashMap<String, V> {
    private final Map<String, String> caseInsensitiveKeys;
    private final Locale locale;

    public DTDMap() {
        this(null);
    }

    public DTDMap(Locale locale) {
        this.caseInsensitiveKeys = new HashMap();
        this.locale = locale != null ? locale : Locale.getDefault();
    }

    public DTDMap(int initialCapacity) {
        this(initialCapacity, null);
    }

    public DTDMap(int initialCapacity, Locale locale) {
        super(initialCapacity);
        this.caseInsensitiveKeys = new HashMap(initialCapacity);
        this.locale = locale != null ? locale : Locale.getDefault();
    }

    public V put(String key, V value) {
        String oldKey = this.caseInsensitiveKeys.put(this.convertKey(key), key);
        if (oldKey != null && !oldKey.equals(key)) {
            super.remove(oldKey);
        }

        return super.put(key, value);
    }

    public void putAll(Map<? extends String, ? extends V> map) {
        if (!map.isEmpty()) {
            Iterator i$ = map.entrySet().iterator();

            while(i$.hasNext()) {
                Entry<? extends String, ? extends V> entry = (Entry)i$.next();
                this.put(entry.getKey(), entry.getValue());
            }

        }
    }

    public boolean containsKey(Object key) {
        return key instanceof String && this.caseInsensitiveKeys.containsKey(this.convertKey((String)key));
    }

    public V get(Object key) {
        return key instanceof String ? super.get(this.caseInsensitiveKeys.get(this.convertKey((String)key))) : null;
    }

    public V remove(Object key) {
        return key instanceof String ? super.remove(this.caseInsensitiveKeys.remove(this.convertKey((String)key))) : null;
    }

    public void clear() {
        this.caseInsensitiveKeys.clear();
        super.clear();
    }

    protected String convertKey(String key) {
        return key.toLowerCase(this.locale);
    }
}
