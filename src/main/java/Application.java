import security.bi.BiCrypto;
import security.bi.BiKey;
import security.exceptions.CryptoException;
import security.tab.TabCrypto;
import security.tab.TabKey;

public class Application {
    public static void main(String[] args) throws CryptoException {
        BiCrypto encryption = new BiCrypto(new BiKey(5, 7, "префектура"));
        String origin = "ВО ВРЕМЯ ПЕРВОЙ МИРОВОЙ ВОЙНЫ ИСПОЛЬЗОВАЛИСЬ БИГРАММНЫЕ ШИФРЫ";
        System.out.println(origin);
        String result = encryption.encryption(origin);
        System.out.println(result);

        TabCrypto tabEncryption = new TabCrypto(new TabKey(3, 5));
        result = tabEncryption.encryption(result);
        System.out.println(result);

        result = tabEncryption.decryption(result);
        System.out.println(result);

        result = encryption.decryption(result);
        System.out.println(result);
    }
}
