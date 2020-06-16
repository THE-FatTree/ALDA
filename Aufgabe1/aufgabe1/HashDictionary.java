package aufgabe1;

import java.util.Iterator;
import java.util.LinkedList;

public class HashDictionary<K, V> implements Dictionary<K, V> {

    private int capacity;
    public LinkedList<Entry<K, V>>[] tab;

    private static final int m = 31;

    public HashDictionary(){
        capacity = 31;
        tab = new LinkedList[capacity];
    }

    public HashDictionary(int n){
        capacity = n;
        tab = new LinkedList[capacity];
    }

    private static boolean isPrime(int n) {
        for (int i = 2; i * i < n; i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    private void ensureLoadfactor(){
        int newLoad = 2 * tab.length;

        while(!isPrime(newLoad)){
            newLoad++;
        }

        capacity = newLoad;
        LinkedList<Entry<K, V>>[] tmp = new LinkedList[newLoad];



        for(int i = 0; i < tab.length; i++){
            if(tab[i] != null){
                for(var x : tab[i]){
                    int adr = hash(x.getKey());
                    if (tmp[adr] == null) {
                        tmp[adr] = new LinkedList<>();
                    }
                    tmp[adr].add(new Entry<K, V>(x.getKey(), x.getValue()));
                }
            }
        }

        tab = tmp;

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
        return adr % capacity;
    }

    @Override
    public V insert(K key, V value) {

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
        }
        tab[adr].add(new Entry<K, V>(key, value));
        if (tab[adr].size() > 2) {
            ensureLoadfactor();

        }
        size++;
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
            if(tab[i] != null){
                size += tab[i].size();
            }
        }
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
