public class Field {
    private char[][] values;

    public Field() {
        values = new char[7][7];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 7; j++) {
                values[i][j] = ' ';
            }
        for (int i = 0; i < 7; i += 2) {
            for (int j = 0; j < 7; j += 2)
                values[i][j] = '+';
        }
        for (int i = 1; i < 7; i += 2) {
            values[i][0] = '|';
            values[i][6] = '|';
            values[0][i] = '-';
            values[6][i] = '-';
        }
    }

    public Field(char[][] oldField) {
        values = new char[7][7];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 7; j++) {
                values[i][j] = oldField[i][j];
            }
    }

    public char getValue(int x, int y) {
        return values[x][y];
    }

    public boolean pickPosition(int x, int y) {
        if (values[y][x] != ' ')
            return false;
        if (x % 2 != 0 && y % 2 != 0)
            return false;
        if (x % 2 == 0 && y % 2 == 0)
            return false;
        if (x % 2 != 0 && y % 2 == 0)
            values[y][x] = '-';
        else if (x % 2 == 0 && y % 2 != 0)
            values[y][x] = '|';
        return true;
    }

    public boolean checkByPosition(int x, int y) {
        if (values[y][x] != ' ')
            return false;
        if (values[y - 1][x] != ' ' && values[y + 1][x] != ' ' && values[y][x - 1] != ' ' && values[y][x + 1] != ' ')
            return true;
        return false;
    }

    public void setValue(int x, int y, char c) {
        values[y][x] = c;
    }

    public char[][] getValues() {
        return values;
    }

    public void printField() {
        System.out.print("  ");
        for (int i = 0; i < 7; i++){
            System.out.print(i);
            System.out.print(' ');
        }
        System.out.println();
        for (int i = 0; i < 7; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 7; j++) {
                System.out.print(values[i][j]);
                System.out.print(' ');
            }
            System.out.println();
        }
    }
}