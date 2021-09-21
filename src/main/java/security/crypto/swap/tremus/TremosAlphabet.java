package security.crypto.swap.tremus;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import security.crypto.swap.SwapAlphabet;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TremosAlphabet extends SwapAlphabet {

    private static final TremosAlphabet instance = new TremosAlphabet();

    public static TremosAlphabet getInstance() {
        return instance;
    }

    @Override
    protected void fillAlphabet() {
        for (int i = 'A'; i <= (int) 'Z'; i++) {
            alphabet.add((char) i);
        }
        alphabet.add('.');
        alphabet.add(' ');
    }
}
