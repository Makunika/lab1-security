package security.crypto.swap.bi;

import security.crypto.base.Alphabet;
import security.crypto.swap.SwapTable;

public class BiTable extends SwapTable<BiKey, BiGrammarly> {

    public BiTable(BiKey key) {
        super(key);
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
        return BiAlphabet.getInstance();
    }

    @Override
    public BiGrammarly encrypt(BiGrammarly value) {
        Coordinate coordChar1 = coordinateMap.get(value.getCharacterOne());
        Coordinate coordChar2 = coordinateMap.get(value.getCharacterTwo());
        if (coordChar1.equals(coordChar2)) {
            throw new IllegalArgumentException("value equals");
        }

        if (coordChar1.getRowIndex() == coordChar2.getRowIndex()) {
            return methodTwoCrypt(coordChar1, coordChar2);
        } else if (coordChar1.getColumnIndex() == coordChar2.getColumnIndex()) {
            return methodThreeCrypt(coordChar1, coordChar2);
        } else {
            return methodOneCrypt(coordChar1, coordChar2);
        }
    }

    @Override
    public BiGrammarly decrypt(BiGrammarly value) {
        Coordinate coordChar1 = coordinateMap.get(value.getCharacterOne());
        Coordinate coordChar2 = coordinateMap.get(value.getCharacterTwo());
        if (coordChar1.equals(coordChar2)) {
            throw new IllegalArgumentException("value equals");
        }

        if (coordChar1.getRowIndex() == coordChar2.getRowIndex()) {
            return methodTwoDecrypt(coordChar1, coordChar2);
        } else if (coordChar1.getColumnIndex() == coordChar2.getColumnIndex()) {
            return methodThreeDecrypt(coordChar1, coordChar2);
        } else {
            return methodOneDecrypt(coordChar1, coordChar2);
        }
    }

    private BiGrammarly methodOneCrypt(Coordinate coordinate1, Coordinate coordinate2) {
        BiGrammarly result = new BiGrammarly();
        result.setCharacterTwo(table[coordinate2.getRowIndex()][coordinate1.getColumnIndex()]);
        result.setCharacterOne(table[coordinate1.getRowIndex()][coordinate2.getColumnIndex()]);
        return result;
    }

    private BiGrammarly methodTwoCrypt(Coordinate coordinate1, Coordinate coordinate2) {
        BiGrammarly result = new BiGrammarly();
        int size = key.getColumnCount();
        int rowIndex = coordinate1.getRowIndex();
        result.setCharacterOne(table[rowIndex][(coordinate1.getColumnIndex() + 1) % size]);
        result.setCharacterTwo(table[rowIndex][(coordinate2.getColumnIndex() + 1) % size]);
        return result;
    }

    private BiGrammarly methodThreeCrypt(Coordinate coordinate1, Coordinate coordinate2) {
        BiGrammarly result = new BiGrammarly();
        int size = key.getRowCount();
        int columnIndex = coordinate1.getColumnIndex();
        result.setCharacterOne(table[(coordinate1.getRowIndex() + 1) % size][columnIndex]);
        result.setCharacterTwo(table[(coordinate2.getRowIndex() + 1) % size][columnIndex]);
        return result;
    }

    private BiGrammarly methodOneDecrypt(Coordinate coordinate1, Coordinate coordinate2) {
        BiGrammarly result = new BiGrammarly();
        result.setCharacterTwo(table[coordinate2.getRowIndex()][coordinate1.getColumnIndex()]);
        result.setCharacterOne(table[coordinate1.getRowIndex()][coordinate2.getColumnIndex()]);
        return result;
    }

    private BiGrammarly methodTwoDecrypt(Coordinate coordinate1, Coordinate coordinate2) {
        BiGrammarly result = new BiGrammarly();
        int size = key.getColumnCount();
        int rowIndex = coordinate1.getRowIndex();
        result.setCharacterOne(table[rowIndex][(coordinate1.getColumnIndex() - 1 + size) % size]);
        result.setCharacterTwo(table[rowIndex][(coordinate2.getColumnIndex() - 1 + size) % size]);
        return result;
    }

    private BiGrammarly methodThreeDecrypt(Coordinate coordinate1, Coordinate coordinate2) {
        BiGrammarly result = new BiGrammarly();
        int size = key.getRowCount();
        int columnIndex = coordinate1.getColumnIndex();
        result.setCharacterOne(table[(coordinate1.getRowIndex() - 1 + size) % size][columnIndex]);
        result.setCharacterTwo(table[(coordinate2.getRowIndex() - 1 + size) % size][columnIndex]);
        return result;
    }

}
