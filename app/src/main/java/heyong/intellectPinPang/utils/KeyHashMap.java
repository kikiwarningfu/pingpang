package heyong.intellectPinPang.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class KeyHashMap<K,V> {
    private Map<K, V> dataMap = new HashMap<K, V>();
    private Map<K, K> keyMap = new HashMap<K, K>();

    /**
     * 维护key值,建议linkedKeys 带上属性名
     * 以免重复导致获取数据对不上
     *
     * @param key
     * @param value
     * @param linkedKeys
     */
    public void put(K key, V value, K... linkedKeys) {
        if (linkedKeys != null) {
            for (int i = 0, len = linkedKeys.length; i < len; i++) {
                keyMap.put(linkedKeys[i], key);
            }
        }
        dataMap.put(key, value);
    }

    /**
     * 根据key获取value
     *
     * @param key
     * @return
     */
    public V get(K key) {
        if (dataMap.containsKey(key)) {
            return dataMap.get(key);
        } else if (keyMap.containsKey(key)) {
            K k = keyMap.get(key);
            if (dataMap.containsKey(k)) {
                return dataMap.get(k);
            }
        }
        return null;
    }

    /**
     * 根据key删除 dataMap   keyMap 元素
     *
     * @param key
     * @return
     */
    public void remove(K key) {
        if (dataMap.containsKey(key)) {
            dataMap.remove(key);
        } else if (keyMap.containsKey(key)) {
            K k = keyMap.get(key);
            if (dataMap.containsKey(k)) {
                dataMap.remove(k);
            }
            Iterator<Map.Entry<K, K>> it = keyMap.entrySet().iterator();
            Map.Entry<K, K> entry = null;
            while (it.hasNext()) {
                entry = it.next();
                if (entry.getValue().equals(k)) {
                    it.remove();
                }
            }
        }
    }

    /**
     * 删除keymap的元素,dataMap不会处理
     *
     * @param keyMapKey
     * @return
     */
    public void removeKeyMapKey(K keyMapKey) {
        if (keyMap.containsKey(keyMapKey)) {
            keyMap.remove(keyMapKey);
        }
    }

    /**
     * 是否包含
     *
     * @param key
     * @return
     */
    public boolean contains(K key) {
        if (dataMap.containsKey(key) || keyMap.containsKey(key)) {
            return true;
        }
        return false;
    }

    /**
     * dataMap是否包含
     *
     * @param dataMapKey
     * @return
     */
    public boolean containsDataMap(K dataMapKey) {
        if (dataMap.containsKey(dataMapKey)) {
            return true;
        }
        return false;
    }

    /**
     * KeyMap是否包含
     *
     * @param keyMapKey
     * @return
     */
    public boolean containsKeyMap(K keyMapKey) {
        if (keyMap.containsKey(keyMapKey)) {
            return true;
        }
        return false;
    }

    /**
     * 返回dataMap集合大小
     *
     * @return
     */
    public int dataMapSize() {
        return dataMap.size();
    }

    /**
     * 返回keyMap集合大小
     *
     * @return
     */
    public int keyMapSize() {
        return keyMap.size();
    }


    /**
     * 返回dataMap指定key在keyMap中key数量
     *
     * @return
     */
    public int keyNum(K dataMapKey) {
        int i = 0;
        if (dataMap.containsKey(dataMapKey)) {
            Iterator<Map.Entry<K, K>> it = keyMap.entrySet().iterator();
            Map.Entry<K, K> entry = null;
            while (it.hasNext()) {
                entry = it.next();
                if (entry.getValue().equals(dataMapKey)) {
                    i++;
                }
            }
        }
        return i;
    }

    /**
     * 返回集合大小
     *
     * @return
     */
    public Set<Map.Entry<K, V>> dataMapEntrySet() {
        return dataMap.entrySet();
    }

    /**
     * 返回集合大小
     *
     * @return
     */
    public Set<Map.Entry<K, K>> keyMapEntrySet() {
        return keyMap.entrySet();
    }
}