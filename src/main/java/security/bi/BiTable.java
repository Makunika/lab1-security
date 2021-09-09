package security.bi;

import lombok.Builder;
import lombok.Data;
import security.base.Table;

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
    public void init() {
        coordinateMap = new HashMap<>();
        table = new char[key.getRowCount()][key.getColumnCount()];
        LinkedHashSet<Character> alphabet = BiAlphabet.getInstance().getAlphabet();
        LinkedHashSet<Character> sortedTable = new LinkedHashSet<>();
        String keyUpperCase = key.getLetter().trim().toUpperCase();
        for (int i = 0; i < keyUpperCase.length(); i++) {
            sortedTable.add(keyUpperCase.charAt(i));
        }
        sortedTable.addAll(alphabet);
        if (key.getColumnCount() * key.getRowCount() != alphabet.size()) {
            throw new IllegalStateException("key.getWidth() * key.getHeight() != alphabet.size()");
        }

        Iterator<Character> iterator = sortedTable.iterator();

        System.out.print("[INFO] --  j =   | ");
        for (int i = 0; i < key.getColumnCount(); i++) {
            System.out.print("   " + i + "   | ");
        }
        System.out.println();
        for (int i = 0; i < key.getRowCount(); i++) {
            System.out.print("[INFO] -- i = " + i + " >| ");
            for (int j = 0; j < key.getColumnCount(); j++) {
                table[i][j] = iterator.next();
                System.out.print(" <<" + String.valueOf(table[i][j]).toUpperCase() + ">> | ");
                coordinateMap.put(table[i][j], Coordinate.builder().rowIndex(i).columnIndex(j).build());
            }
            System.out.println();
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
