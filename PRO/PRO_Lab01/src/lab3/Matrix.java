package lab3;

public class Matrix {

    private static int N;
    private Vector[] data;

    public Matrix() {
        data = new Vector[N];
        for (int i = 0; i < N; i++) {
            data[i] = new Vector();
        }
    }

    public Matrix(int value) {
        data = new Vector[N];
        for (int i = 0; i < N; i++) {
            data[i] = new Vector(value);
        }
    }

    private Matrix(Vector[] data) {
        this.data = data;
    }

    public static int getN() {
        return N;
    }

    public static void setN(int N) {
        Matrix.N = N;
    }

    public int get(int row, int column) {
        return data[row].get(column);
    }

    public Vector get(int row) {
        return data[row];
    }

    public void set(int row, int column, int value) {
        data[row].set(column, value);
    }

    public Matrix mul(Matrix m) {
        Vector[] result = new Vector[N];
        for (int i = 0; i < N; i++) {
            result[i] = data[i].mul(m);
        }
        return new Matrix(result);
    }

    public Matrix add(Matrix m) {
        Vector[] result = new Vector[N];
        for (int i = 0; i < N; i++) {
            result[i] = data[i].add(m.get(i));
        }
        return new Matrix(result);
    }

    public int max() {
        int max = data[0].max();
        int temp;
        for (int i = 1; i < N; i++) {
            temp = data[i].max();
            if (max < temp)
                max = temp;
        }
        return max;
    }
}
