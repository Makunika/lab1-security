package security.crypto.swap.bi;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BiGrammarly {
    private Character characterOne;
    private Character characterTwo;

    public boolean isEquals() {
        return characterOne.equals(characterTwo);
    }

    public boolean isExistBlank() {
        return characterOne.equals(' ') || characterTwo.equals(' ');
    }

    public String getAsString() {
        return String.valueOf(characterOne) + characterTwo;
    }

    public List<Character> getAsListCharacters() {
        return List.of(characterOne, characterTwo);
    }

    public List<String> getAsListStrings() {
        return List.of(String.valueOf(characterOne), String.valueOf(characterTwo));
    }

    public void setFromString(String str, int pos1, int pos2) {
        characterOne = str.charAt(pos1);
        characterTwo = str.charAt(pos2);
    }

    public void setCharacters(Character one, Character two) {
        characterOne = one;
        characterTwo = two;
    }

    @Override
    public String toString() {
        return String.valueOf(characterOne) + characterTwo;
    }
}
