package security.cracker.base;

import security.cracker.CrackResult;
import security.crypto.base.Crypto;
import security.exceptions.CrackException;

/**
 * @author Maxim Pshiblo
 */
public interface Cracker<T extends Crypto<?>> {
    void setCrypto(T crypto);
    CrackResult<?> crack(String crypto, KnownInfo knownInfo) throws CrackException;
}
