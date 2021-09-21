package security.cracker;

import lombok.Getter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Maxim Pshiblo
 */
@Getter
public class CrackInfo {

    private static final CrackInfo instance = new CrackInfo();

    public static CrackInfo getInstance() {
        return instance;
    }

    private final HashMap<Character, Double> chanceMap;
    private final HashMap<Character, Double> chanceLowerCaseMap;
    private final HashMap<String, Double> stringsChanceMap;

    private CrackInfo() {
        chanceMap = new HashMap<>();
        stringsChanceMap = new HashMap<>();
        chanceLowerCaseMap = new HashMap<>();
        fill();
    }

    private void fill() {
        try {
            Scanner scannerFile = new Scanner(new FileInputStream("crackinfo.txt"));
            while (scannerFile.hasNextLine()) {
                String line = scannerFile.nextLine();
                if (line.isBlank()) {
                    break;
                }
                String[] data = line.split(" ");
                String upperCaseString = data[0].toUpperCase();
                String lowerCaseString = data[0].toLowerCase();
                Character upperCharacter = upperCaseString.charAt(0);
                Character lowerCharacter = lowerCaseString.charAt(0);
                Double chance = Double.parseDouble(data[1]);
                stringsChanceMap.put(upperCaseString, chance);
                stringsChanceMap.put(lowerCaseString, chance);
                chanceMap.put(upperCharacter, chance);
                chanceMap.put(lowerCharacter, chance);
                chanceLowerCaseMap.put(lowerCharacter, chance);
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
