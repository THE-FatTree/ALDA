package aufgabe1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class HashDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    public LinkedList<Entry<K, V>>[] tab;
    private int size;
    private int capacity;


    public HashDictionary(int capacity){
        this.tab = new LinkedList[capacity];
        this.capacity = capacity;
        this.size = 0;
    }

    private static boolean isPrime(int n) {
        for (int i = 2; i * i < n; i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    int hash(K k){
        String key;
        if (!(k instanceof String)) key = String.valueOf(k);
        else key = (String) k;
        int adr = 0;
        for (int i = 0; i < key.length(); i++)
            adr = 31 * adr + key.charAt(i);
        if (adr < 0)
            adr = -adr;
        return adr % (tab.length - 1);
    }


    public void ensurecapacity() {
        if (size + 1 == capacity) {

            int newLoad = 2 * tab.length;

            while (!isPrime(newLoad)) {
                ++newLoad;
            }

            HashDictionary<K, V> newDict = new HashDictionary<>(newLoad);

            for (LinkedList<Entry<K, V>> index : tab) {
                if (index == null) continue;
                for (Entry<K, V> entry : index) {
                    newDict.insert(entry.getKey(), entry.getValue());
                }
            }
            tab = new LinkedList[newLoad];
            capacity = newLoad;
            for (LinkedList<Entry<K, V>> index : newDict.tab) {
                if (index == null) continue;
                for (Entry<K, V> entry : index) {
                    this.insert(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    @Override
    public V insert(K key, V value) {
        ensurecapacity();
        int adr = hash(key);

        if (search(key) != null) {
            for (Entry<K, V> entry : tab[adr]) {
                if (entry.getKey().equals(key)) {
                    V val = entry.getValue();
                    entry.setValue(value);
                    return val;
                }
            }
        }


        if (tab[adr] == null) {
            tab[adr] = new LinkedList<>();
            tab[adr].add(new Entry<K, V>(key, value));
            ++size;
        } else {
            tab[adr].add(new Entry<K, V>(key, value));
            ++size;
        }
        return null;
    }

    @Override
    public V search(K key) {

        int adr = hash(key);
        if (tab[adr] != null)
            for(Entry<K, V> v : tab[adr]){
                if(v.getKey().equals(key)){
                    return v.getValue();
                }
            }

        return null;
    }

    @Override
    public V remove(K key) {

        int adr = hash(key);

        V tmp;

        for(Entry<K, V> v : tab[adr]){
            if(v.getKey().equals(key)){
                tmp = v.getValue();
                tab[adr].remove(v);
                size--;
                return tmp;
            }
        }

        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<Entry<K, V>>() {

            int index = -1;
            Iterator<Entry<K, V>> listIterator;

            @Override
            public boolean hasNext() {
                if (listIterator != null && listIterator.hasNext())
                    return true;
                while (++index < tab.length) {
                    if (tab[index] != null) {
                        listIterator = tab[index].iterator();
                        return listIterator.hasNext();
                    }
                }
                return false;
            }

            @Override
            public Entry<K, V> next() {
                return listIterator.next();
            }
        };
    }
}
