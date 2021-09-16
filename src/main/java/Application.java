import security.bi.BiCrypto;
import security.bi.BiKey;
import security.exceptions.CryptoException;
import security.tab.TabCrypto;
import security.tab.TabKey;
import security.vernom.VernomAlphabet;
import security.vernom.VernomCrypto;
import security.vernom.VernomKey;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        BiCrypto encryption = new BiCrypto(new BiKey(new File("keyBi.txt")));
        TabCrypto tabEncryption = new TabCrypto(new TabKey(new File("keyTab.txt")));
        VernomCrypto vernomCrypto = new VernomCrypto(new VernomKey(new File("keyVernom.txt")));
        while (true) {
            try {

                System.out.print("Напишите текст, который необходимо зашифровать (0 - закончить): ");
                String origin = scanner.nextLine();
                if (origin.equals("0")) {
                    break;
                }

                System.out.println(origin);
                String result = vernomCrypto.encryption(origin);
                System.out.println("Результат шифрации методом 'биграммный шифр Плейфейра': " + result);

//                result = tabEncryption.encryption(result);
//                System.out.println("Результат шифрации методом 'простые шифрующие таблицы': " + result);
//
//                result = tabEncryption.decryption(result);
//                System.out.println("Результат дешифрации методом 'простые шифрующие таблицы': " + result);

                result = vernomCrypto.decryption(result);
                System.out.println("Результат дешифрации методом 'биграммный шифр Плейфейра': " + result);
            } catch (CryptoException e) {
                System.out.println("Ошибка! " + e.getMessage());
            }
        }
    }
}
