package security.base;

import security.exceptions.EncryptionException;

public interface Table<K, V> {
    void setKey(K key);
    K getKey();
    void init() throws EncryptionException;
    boolean isInit();
    V encrypt(V value);
}
