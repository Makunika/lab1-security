package security.crypto.base;

import security.exceptions.CryptoException;

public interface Table<K, V> {
    void setKey(K key);
    K getKey();
    void init() throws CryptoException;
    boolean isInit();
    V encrypt(V value);
    V decrypt(V value);
}
