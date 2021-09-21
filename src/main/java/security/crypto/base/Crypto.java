package security.crypto.base;

import security.exceptions.CryptoException;

public interface Crypto<K> {
    String encryption(String origin) throws CryptoException;
    String decryption(String crypto) throws CryptoException;
    void setKey(K key);
    K getKey();
}
