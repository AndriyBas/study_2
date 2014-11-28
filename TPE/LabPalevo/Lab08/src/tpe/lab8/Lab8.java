package tpe.lab8;

import java.util.Locale;

public class Lab8 {
    public static int w = 10;
    public static int c = 2;
    public static boolean plus = false;

    public static String f(double arg) {
        if (plus) return String.format(Locale.US, "%+" + w + "." + c + "f", arg);
        else return String.format(Locale.US, "% " + w + "." + c + "f", arg);
    }

    public static String v(String arg) {
        return String.format(Locale.US, "%" + w + "s", arg);
    }

    public static void print(double arg) {
        System.out.print(f(arg));
    }

    public static void print(String arg) {
        System.out.print(v(arg));
    }

    public static void println(double arg) {
        System.out.println(f(arg));
    }

    public static void println(String arg) {
        System.out.println(v(arg));
    }

    public static void main(String[] args) {
        // ПУНКТ 1
        // Коефіцієнти рівняння регресії
        // Підпункт 1
        double b0 = 7;
        double b1 = 25;
        double b2 = 67;
        double b3 = 77;
        // Підпункт 2
        double p = 0.95;
        // Підпункт 3
        // Кодовані значення факторів при проведенні ПФЕ (X0 = 1)
        double[][] mx = {
                {1, -1, -1, -1},
                {1, -1, 1, 1},
                {1, 1, -1, 1},
                {1, 1, 1, -1},
                {1, -1, -1, 1},
                {1, -1, 1, -1},
                {1, 1, -1, -1},
                {1, 1, 1, 1}};
        // Заповнення матриці значень функції відгуку при проведенні експерименту
        // Підпункт 4
        double dy = 0.05;
        // Підпункт 5
        int m = 3;
        int dm = 2;
        // Підпункт 6
        int n = 8;
        boolean f = true;
        double[][] y = new double[n][m];
        double[][] yy = new double[n][m];
        // Пошук m при якому дисперсія однорідна
        while (f) {
            y = new double[n][m];
            yy = new double[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    yy[i][j] = Math.random();
                }
            }
            f = false;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    y[i][j] = (mx[i][0] * b0 + mx[i][1] * b1 + mx[i][2] * b2 + mx[i][3] * b3) * (1 + (2 * yy[i][j] * 10000 / 10000 - 1) * dy);
                }
            }
            for (int i = 0; i <= 3; i++)
                print("X" + i);
            for (int i = 1; i <= 3; i++)
                print("Y" + i);
            print("Ym");
            println("D");
            // Виведення матриці планування
            for (int i = 0; i < n; i++) {
                System.out.print(f(mx[i][0]) + f(mx[i][1]) + f(mx[i][2]) + f(mx[i][3]));
                for (int j = 0; j < m; j++) {
                    print(y[i][j]);
                }
                System.out.println();
            }
            // Середні значення функції відгуку по рядку
            double[] yr = new double[n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    yr[i] = yr[i] + y[i][j];
                }
                yr[i] = yr[i] / m;
            }
            System.out.println("Середні значення функції відгуку по рядку - <Y>:");
            for (int i = 0; i < yr.length; i++) {
                println(yr[i]);
            }
            // Дисперсія
            double[] disp = new double[n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    disp[i] = disp[i] + (yr[i] - y[i][j]) * (yr[i] - y[i][j]);
                }
                disp[i] = disp[i] / m;
            }
            System.out.println("Дисперсія Y:");
            for (int i = 0; i < n; i++) {
                println(disp[i]);
            }
            //Сума дисперсій
            double dsum = 0;
            //Максимальна дисперсія
            double dmax = 0;
            for (int i = 0; i < disp.length; i++) {
                if (disp[i] > dmax) dmax = disp[i];
                dsum = dsum + disp[i];
            }
            double Gp = dmax / dsum;
            double Gt = 0;
            if (m == 2)
                Gt = 0.6798;
            if (m == 3)
                Gt = 0.5157;
            if (m == 4) Gt = 0.4377;
            if (m == 5) Gt = 0.3910;
            if (m == 6) Gt = 0.3595;
            //Перевірка на однорідність
            if (Gp < Gt)
                System.out.println("Дисперсія однорідна " + Gp + " < " + Gt);
            else {
                System.out.println("Дисперсія неоднорідна " + Gp + " > " + Gt);
                f = true;
                m = m + dm;
            }
        }
        // ПУНКТ 2
        //Погрішності
        double[] ddy = new double[5];
        ddy[0] = 0.1;
        ddy[1] = 0.05;
        ddy[2] = 0.02;
        ddy[3] = 0.01;
        ddy[4] = 0.001;
        for (int k = 0; k < 5; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    y[i][j] = (mx[i][0] * b0 + mx[i][1] * b1 + mx[i][2] * b2 + mx[i][3] * b3) * (1 + (2 * yy[i][j] * 10000 / 10000 - 1) * ddy[k]);
                }
            }
            for (int i = 0; i <= 3; i++)
                print("X" + i);
            for (int i = 1; i <= 3; i++)
                print("Y" + i);
            print("Ym");
            println("D");
            for (int i = 0; i < n; i++) {
                System.out.print(f(mx[i][0]) + f(mx[i][1]) + f(mx[i][2]) + f(mx[i][3]));
                for (int j = 0; j < m; j++) {
                    print(y[i][j]);
                }
                System.out.println();
            }
            // Среднє значення функції відгуку по рядку
            double[] yr = new double[n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    yr[i] = yr[i] + y[i][j];
                }
                yr[i] = yr[i] / m;
            }
            System.out.println("Середні значення функції відгуку по рядку - <Y>:");
            for (int i = 0; i < yr.length; i++) {
                println(yr[i]);
            }
            // Дисперсія
            double[] disp = new double[n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    disp[i] = disp[i] + (yr[i] - y[i][j]) * (yr[i] - y[i][j]);
                }
                disp[i] = disp[i] / m;
            }
            System.out.println("Дисперсія Y:");
            for (int i = 0; i < n; i++) {
                println(disp[i]);
            }
            double[] B = new double[5];
            double[] sigma = new double[5];
            double a11 = 0;
            double a22 = 0;
            double a33 = 0;
            double a1 = 0;
            double a2 = 0;
            double a3 = 0;
            double a4 = 0;
            double a5 = 0;
            double a6 = 0;
            double my = 0;
            for (int i = 0; i < n; i++) {
                my = my + yr[i];
                a1 = a1 + mx[i][1] * mx[i][1];
                a4 = a4 + mx[i][2] * mx[i][2];
                a6 = a6 + mx[i][3] * mx[i][3];
                a2 = a2 + mx[i][1] * mx[i][2];
                a3 = a3 + mx[i][1] * mx[i][3];
                a5 = a5 + mx[i][2] * mx[i][3];
                a11 = a11 + mx[i][1] * yr[i];
                a22 = a22 + mx[i][2] * yr[i];
                a33 = a33 + mx[i][3] * yr[i];
            }
            my = my / n;
            a1 = a1 / n;
            a4 = a4 / n;
            a6 = a6 / n;
            a2 = a2 / n;
            a3 = a3 / n;
            a5 = a5 / n;
            a11 = a11 / n;
            a22 = a22 / n;
            a33 = a33 / n;
            // Обчислення визначників
            double d0 = my * (a1 * a4 * a6 + a2 * a5 * a3 + a2 * a5 * a3 - a3 * a4 * a3 - a2 * a2 * a6 - a1 * a5 * a5);
            double d1 = 1 * (a11 * a4 * a6 + a2 * a5 * a33 + a22 * a5 * a3 - a3 * a4 * a33 - a22 * a2 * a6 - a11 * a5 * a5);
            double d2 = 1 * (a1 * a22 * a6 + a11 * a5 * a3 + a2 * a33 * a3 - a3 * a22 * a3 - a2 * a11 * a6 - a1 * a33 * a5);
            double d3 = 1 * (a1 * a4 * a33 + a2 * a22 * a3 + a2 * a5 * a11 - a3 * a4 * a11 - a2 * a2 * a33 - a1 * a5 * a22);
            double d = (a1 * a4 * a6 + a2 * a5 * a3 + a2 * a5 * a3 - a3 * a4 * a3 - a2 * a2 * a6 - a1 * a5 * a5);
            // Знаходження коефіцієнтів регресії
            B[0] = d0 / d;
            B[1] = d1 / d;
            B[2] = d2 / d;
            B[3] = d3 / d;
            System.out.println("dy[ " + k + " ] = " + f(ddy[k]));
            w = 6;
            plus = true;
            System.out.println("Рiвняння регресії:");
            System.out.println(f(B[0]) + " " + f(B[1]) + "*x1 " + f(B[2]) + "*x2 " + f(B[3]) + "*x3");
            // Погрішності
            sigma[0] = Math.abs(B[0] - b0) / B[0];
            sigma[1] = Math.abs(B[1] - b1) / B[1];
            sigma[2] = Math.abs(B[2] - b2) / B[2];
            sigma[3] = Math.abs(B[3] - b3) / B[3];
            System.out.println("Відносні погрішності:");
            w = 18;
            c = 16;
            plus = false;
            for (int i = 0; i < 4; i++)
                println("sigma" + i + " = " + f(sigma[i]));
            w = 10;
            c = 2;
            System.out.println();
        }
    }
}
