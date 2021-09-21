package security.exceptions;

/**
 * @author Maxim Pshiblo
 */
public class CrackException extends Exception {
    public CrackException() {
    }

    public CrackException(String message) {
        super(message);
    }

    public CrackException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrackException(Throwable cause) {
        super(cause);
    }

    public CrackException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
