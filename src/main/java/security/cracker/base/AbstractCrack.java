package security.cracker.base;

import security.crypto.base.Crypto;

/**
 * @author Maxim Pshiblo
 */
public abstract class AbstractCrack<T extends Crypto<?>> implements Cracker<T> {

    protected T crypto;

    public AbstractCrack(T crypto) {
        this.crypto = crypto;
    }

    @Override
    public void setCrypto(T crypto) {
        this.crypto = crypto;
    }
}
