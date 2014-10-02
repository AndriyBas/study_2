package lab3;

/**
 * Created by andriybas on 10/3/14.
 */
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

    public Matrix mul(int s) {
        Vector[] result = new Vector[N];
        for (int i = 0; i < N; i++) {
            result[i] = data[i].mul(s);
        }
        return new Matrix(result);
    }

    public Vector mul(Vector v) {
        Vector result = new Vector();
        int temp;
        for (int i = 0; i < N; i++) {
            temp = 0;
            for (int j = 0; j < N; j++) {
                temp += data[i].get(j)*v.get(j);
            }
            result.set(i, temp);
        }
        return result;
    }

    public Matrix mul(Matrix m) {
        Vector[] result = new Vector[N];
        for (int i = 0; i < N; i++) {
            result[i] = data[i].mul(m);
        }
        return new Matrix(result);
    }

    public int min() {
        int min = data[0].min();
        int temp;
        for (int i = 1; i < N; i++) {
            temp = data[i].min();
            if(min > temp)
                min = temp;
        }
        return min;
    }

    public String toString() {
        StringBuilder str = new StringBuilder("{\n");
        for (int i = 0; i < N; i++) {
            str.append("\t");
            str.append(data[i].toString());
            str.append("\n");
        }
        str.append("}");
        return str.toString();
    }


}
