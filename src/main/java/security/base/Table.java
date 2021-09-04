package security.base;

public interface Table<K, V> {
    void setKey(K key);
    K getKey();
    void init();
    boolean isInit();
    V encrypt(V value);
    V decrypt(V value);
}
