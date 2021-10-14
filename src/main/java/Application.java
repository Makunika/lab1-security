import security.cracker.CrackResult;
import security.cracker.tremus.TremusCracker;
import security.cracker.tremus.TremusKnownInfo;
import security.crypto.enigma.EnigmaKey;
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
import security.gamma.enigma.EnigmaGammaGenerator;

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
        EnigmaGammaGenerator gammaGenerator = new EnigmaGammaGenerator(new EnigmaKey(new File("keyEnigma.txt")));
        while (true) {
            System.out.print("Напишите текст (0 - закончить): ");
            String origin = scanner.nextLine();
            if (origin.equals("0")) {
                break;
            }
            String result = gammaGenerator.gamma(origin);
            System.out.println(result);
        }
    }
}
