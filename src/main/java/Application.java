import security.bi.BiAlphabet;
import security.bi.BiEncryption;
import security.bi.BiKey;
import security.exceptions.EncryptionException;

public class Application {
    public static void main(String[] args) throws EncryptionException {
        BiEncryption encryption = new BiEncryption(new BiKey(5, 7, "говно"));
        String result = encryption.encryption("Привет");
        System.out.println(result);
    }
}
