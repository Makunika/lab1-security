import security.cracker.CrackResult;
import security.cracker.tremus.TremusCracker;
import security.cracker.tremus.TremusKnownInfo;
import security.crypto.swap.bi.BiCrypto;
import security.crypto.swap.bi.BiKey;
import security.exceptions.CrackException;
import security.exceptions.CryptoException;
import security.crypto.tab.TabCrypto;
import security.crypto.tab.TabKey;
import security.crypto.swap.tremus.TremosCrypto;
import security.crypto.swap.tremus.TremosKey;
import security.crypto.vernom.VernomCrypto;
import security.crypto.vernom.VernomKey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        BiCrypto encryption = new BiCrypto(new BiKey(new File("keyBi.txt")));
        TabCrypto tabEncryption = new TabCrypto(new TabKey(new File("keyTab.txt")));
        VernomCrypto vernomCrypto = new VernomCrypto(new VernomKey(new File("keyVernom.txt")));
        TremosCrypto tremosCrypto = new TremosCrypto(new TremosKey(new File("keyTremos.txt")));
        TremusCracker tremusCracker = new TremusCracker(tremosCrypto);
        while (true) {
            try {

                System.out.print("Напишите текст, который необходимо зашифровать (0 - закончить): ");
                String origin = scanner.nextLine();
                if (origin.equals("0")) {
                    break;
                }
                origin = new BufferedReader(new InputStreamReader(new FileInputStream("text.txt"))).lines().collect(Collectors.joining("\n"));

                System.out.println(origin);

                String result = tremosCrypto.encryption(origin);
                System.out.println("Результат шифрации: " + result);

                TremusKnownInfo tremusKnownInfo = new TremusKnownInfo();
                tremusKnownInfo.setLetter(tremosCrypto.getKey().getLetter());
                tremusKnownInfo.setRowCount(tremosCrypto.getKey().getRowCount());

                CrackResult<TremosKey> crackResult = tremusCracker.crack(result, tremusKnownInfo);

                System.out.println(crackResult);
            } catch (CryptoException | CrackException e) {
                System.out.println("Ошибка! " + e.getMessage());
            }
        }
    }
}
