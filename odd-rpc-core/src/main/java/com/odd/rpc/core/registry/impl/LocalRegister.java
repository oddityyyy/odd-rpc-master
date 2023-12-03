package com.odd.rpc.core.registry.impl;

import com.odd.rpc.core.registry.Register;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * application registry for "local"
 *
 * @author oddity
 * @create 2023-11-26 0:14
 */
public class LocalRegister extends Register {

    /**
     * registry data
     */
    private Map<String, TreeSet<String>> registryData;

    /**
     * @param param ignore, not use
     */
    @Override
    public void start(Map<String, String> param) {
        registryData = new HashMap<String, TreeSet<String>>();
    }

    @Override
    public void stop() {
        registryData.clear();
    }

    @Override
    public boolean registry(Set<String> keys, String value) {
        if (keys==null || keys.size()==0 || value==null || value.trim().length()==0) {
            return false;
        }
        for (String key : keys) {
            TreeSet<String> values = registryData.get(key);
            if (values == null) {
                values = new TreeSet<>();
                registryData.put(key, values);
            }
            values.add(value);
        }
        return true;
    }

    @Override
    public boolean remove(Set<String> keys, String value) {
        if (keys==null || keys.size()==0 || value==null || value.trim().length()==0) {
            return false;
        }
        for (String key : keys) {
            TreeSet<String> values = registryData.get(key);
            if (values != null) {
                values.remove(value);
            }
        }
        return true;
    }

    @Override
    public Map<String, TreeSet<String>> discovery(Set<String> keys) {
        if (keys==null || keys.size()==0) {
            return null;
        }
        Map<String, TreeSet<String>> registryDataTmp = new HashMap<String, TreeSet<String>>();
        for (String key : keys) {
            TreeSet<String> valueSetTmp = discovery(key);
            if (valueSetTmp != null) {
                registryDataTmp.put(key, valueSetTmp);
            }
        }
        return registryDataTmp;
    }

    @Override
    public TreeSet<String> discovery(String key) {
        return registryData.get(key);
    }
}
