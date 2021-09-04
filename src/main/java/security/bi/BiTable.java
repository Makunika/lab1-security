package security.bi;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import security.base.Table;
import security.exceptions.EncryptionException;

import java.util.*;

public class BiTable implements Table<BiKey, BiGrammarly> {

    private BiKey key;
    private char[][] table;
    private Map<Character, Coordinate> coordinateMap;
    private boolean isInit;

    public BiTable(BiKey key) {
        this.key = key;
    }

    @Override
    public void setKey(BiKey key) {
        isInit = false;
        this.key = key;
    }

    @Override
    public BiKey getKey() {
        return key;
    }

    @Override
    public void init() throws EncryptionException {
        coordinateMap = new HashMap<>();
        table = new char[key.getHeight()][key.getWidth()];
        LinkedHashSet<Character> alphabet = BiAlphabet.getInstance().getAlphabet();
        LinkedHashSet<Character> sortedTable = new LinkedHashSet<>();
        for (int i = 0; i < key.getLetter().length(); i++) {
            sortedTable.add(key.getLetter().charAt(i));
        }
        sortedTable.addAll(alphabet);
        if (key.getWidth() * key.getHeight() != alphabet.size()) {
            throw new IllegalStateException("key.getWidth() * key.getHeight() != alphabet.size()");
        }

        Iterator<Character> iterator = sortedTable.iterator();

        for (int i = 0; i < key.getHeight(); i++) {
            for (int j = 0; j < key.getWidth(); j++) {
                System.out.println("[INFO] -- i = " + i + ", j = " + j);
                table[i][j] = iterator.next();
                coordinateMap.put(table[i][j], Coordinate.builder().rowIndex(i).columnIndex(j).build());
            }
        }
        isInit = true;
    }

    @Override
    public BiGrammarly encrypt(BiGrammarly value) {
        Coordinate coordChar1 = coordinateMap.get(value.getCharacterOne());
        Coordinate coordChar2 = coordinateMap.get(value.getCharacterTwo());
        if (coordChar1.equals(coordChar2)) {
            throw new IllegalArgumentException("value equals");
        }

        if (coordChar1.getRowIndex() == coordChar2.getRowIndex()) {
            return methodTwo(coordChar1, coordChar2);
        } else if (coordChar1.getColumnIndex() == coordChar2.getColumnIndex()) {
            return methodThree(coordChar1, coordChar2);
        } else {
            return methodOne(coordChar1, coordChar2);
        }
    }

    private BiGrammarly methodOne(Coordinate coordinate1, Coordinate coordinate2) {
        BiGrammarly result = new BiGrammarly();
        result.setCharacterOne(table[coordinate2.getRowIndex()][coordinate1.getColumnIndex()]);
        result.setCharacterTwo(table[coordinate1.getRowIndex()][coordinate2.getColumnIndex()]);
        return result;
    }

    private BiGrammarly methodTwo(Coordinate coordinate1, Coordinate coordinate2) {
        BiGrammarly result = new BiGrammarly();
        int size = key.getWidth();
        int rowIndex = coordinate1.getRowIndex();
        result.setCharacterOne(table[rowIndex][(coordinate1.getColumnIndex() + 1) % size]);
        result.setCharacterTwo(table[rowIndex][(coordinate2.getColumnIndex() + 1) % size]);
        return result;
    }

    private BiGrammarly methodThree(Coordinate coordinate1, Coordinate coordinate2) {
        BiGrammarly result = new BiGrammarly();
        int size = key.getHeight();
        int columnIndex = coordinate1.getColumnIndex();
        result.setCharacterOne(table[(coordinate1.getRowIndex() + 1) % size][columnIndex]);
        result.setCharacterTwo(table[(coordinate2.getRowIndex() + 1) % size][columnIndex]);
        return result;
    }

    @Override
    public boolean isInit() {
        return isInit;
    }

    @Data
    @Builder
    private static class Coordinate {
        private int rowIndex;
        private int columnIndex;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return rowIndex == that.rowIndex && columnIndex == that.columnIndex;
        }
    }

}
