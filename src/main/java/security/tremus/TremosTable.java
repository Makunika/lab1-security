package security.tremus;

import security.base.Alphabet;
import security.swap.SwapTable;

public class TremosTable extends SwapTable<TremosKey, String> {

    public TremosTable(TremosKey key) {
        super(key);
    }

    @Override
    public String encrypt(String value) {
        StringBuilder sb = new StringBuilder();
        String upperCase = value.toUpperCase();
        for (int i = 0; i < upperCase.length(); i++) {
            char c = upperCase.charAt(i);
            Coordinate coordinate = coordinateMap.get(c);
            int row = (coordinate.getRowIndex() + 1) % key.getRowCount();
            int column = (coordinate.getColumnIndex() + 1) % key.getColumnCount();
            sb.append(table[row][column]);
        }
        return sb.toString();
    }

    @Override
    public String decrypt(String value) {
        StringBuilder sb = new StringBuilder();
        String upperCase = value.toUpperCase();
        for (int i = 0; i < upperCase.length(); i++) {
            char c = upperCase.charAt(i);
            Coordinate coordinate = coordinateMap.get(c);
            int row = (coordinate.getRowIndex() + key.getRowCount() - 1) % key.getRowCount();
            int column = (coordinate.getColumnIndex() + key.getColumnCount() - 1) % key.getColumnCount();
            sb.append(table[row][column]);
        }
        return sb.toString();
    }

    @Override
    protected int getRowCount() {
        return key.getRowCount();
    }

    @Override
    protected int getColumnCount() {
        return key.getColumnCount();
    }

    @Override
    protected String getSecret() {
        return key.getLetter();
    }

    @Override
    protected Alphabet getAlphabet() {
        return TremosAlphabet.getInstance();
    }
}
