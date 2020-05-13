package aufgabe1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class HashDictionary<K, V> implements Dictionary<K, V>, java.lang.Iterable<Dictionary.Entry<K, V>> {

    public HashDictionary(){
        capacity = 31;
    }

    public HashDictionary(int n){
        capacity = n;
    }

    private static int capacity;

    private static final int m = 31;

    private LinkedList<Entry<K, V>>[] tab = new LinkedList[31];

    static int hash(String key){
        int adr = 0;
        for (int i = 0; i < key.length(); i++){
            adr = m * adr + key.charAt(i);
        }
        if (adr < 0) {
            adr = -adr;
        }
        return adr % capacity;
    }

    @Override
    public V insert(K key, V value) {

        int adr = hash(key.toString());

        if(tab[adr] == null){
            tab[adr] = new LinkedList<>();
            tab[adr].add(new Entry<>(key, value));
        } else {
            if(tab[adr].isEmpty()) {
                tab[adr].add(new Entry<>(key, value));
            }
            for (var x : tab[adr]) {
                if (x.getKey().equals(key)) {
                    return x.setValue(value);
                }
            }
            tab[adr].add(new Entry<>(key, value));
        }
        return null;
    }

    @Override
    public V search(K key) {

        int adr = hash(key.toString());

        for(var x : tab[adr]){
            if(x.getKey().equals(key)){
                return x.getValue();
            }
        }

        return null;
    }

    @Override
    public V remove(K key) {

        int adr = hash(key.toString());

        V tmp;

        for(var x : tab[adr]){
            if(x.getKey().equals(key)){
                tmp = x.getValue();
                tab[adr].remove(x);
                return tmp;
            }
        }

        return null;
    }

    @Override
    public int size() {

        int size = 0;

        for(int i = 0; i < capacity; i++){
            size += tab[i].size();
        }
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {

        return null;
    }
}
