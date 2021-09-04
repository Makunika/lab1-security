package security.base;

import security.exceptions.EncryptionException;

public interface Encryption<K> {
    String encryption(String origin) throws EncryptionException;
    void setKey(K key);
    K getKey();
}
