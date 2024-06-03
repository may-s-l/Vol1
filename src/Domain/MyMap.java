package Domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class  MyMap<K, V> {

    private final Map<K, V> map;

    public MyMap() {
        this.map = new HashMap<>();
    }

    public void put(K key, V value) {
        map.put(key, value);
    }

    public V get(K key) {
        return map.get(key);
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public Set<K> getKeys(){return map.keySet();}

    public void remove(K key) {
        map.remove(key);
    }

    public int size() {
        return map.size();
    }

    public void clear() {
        map.clear();
    }

    @Override
    public String toString() {
        return  map.toString().replace("="," :").replace("{","").replace("}","");
    }

}
