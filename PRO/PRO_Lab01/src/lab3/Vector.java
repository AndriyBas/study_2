package lab3;

import java.util.Arrays;

/**
 * Created by andriybas on 10/3/14.
 */
public class Vector {
    private static int N;
    private int[] data;
    public Vector() {
        data = new int[N];
    }

    public Vector(int value) {
        data = new int[N];
        Arrays.fill(data, value);
    }

    private Vector(Vector v) {
        data = new int[N];
        for (int i = 0; i < N; i++) {
            data[i] = v.data[i];
        }
    }

    public static int getN() {
        return N;
    }

    public static void setN(int N) {
        Vector.N = N;
    }

    public int get(int index) {
        return data[index];
    }

    public void set(int index, int value) {
        data[index] = value;
    }

    public Vector add(Vector v) {
        Vector result = new Vector(this);
        for (int i = 0; i < N; i++) {
            result.data[i] += v.data[i];
        }
        return result;
    }

    public Vector mul(int s) {
        Vector result = new Vector(this);
        for (int i = 0; i < N; i++) {
            result.data[i] *= s;
        }
        return result;
    }

    public int mul(Vector v) {
        int result = 0;
        for (int i = 0; i < N; i++) {
            result += this.data[i] * v.data[i];
        }
        return result;
    }

    public Vector mul(Matrix m) {
        Vector result = new Vector();
        int temp;
        for (int i = 0; i < N; i++) {
            temp = 0;
            for (int j = 0; j < N; j++) {
                temp += this.data[j]*m.get(j, i);
            }
            result.set(i, temp);
        }
        return result;
    }

    public int min() {
        int min = data[0];
        for (int i = 1; i < N; i++) {
            if(min > data[i])
                min = data[i];
        }
        return min;
    }

    public String toString() {
        StringBuilder str = new StringBuilder("[");
        for (int i = 0; i < N - 1; i++) {
            str.append(data[i]);
            str.append(",\t");
        }
        str.append(data[N-1]);
        str.append("]");
        return str.toString();
    }

}
