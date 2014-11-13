import java.util.Random;

public class Lab6 {

    public static void main(String[] args) {
        Lab6 lab = new Lab6();
        int step = 1;
        do {
            ++step;
            lab.setY(step);
            m = 1;
            do {
                ++m;
                lab.MPE();
                lab.Reg();
            }
            while ((!lab.Cohren()) && (m < 5));
            lab.Student();
        }
        while ((!lab.Fisher()) && (step < 2));
        lab.show();
    }

    private void MPE() {
        l = getL(p);
        refreshMatrix();

        xMatrix = new double[N][nk - 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < k; j++)
                xMatrix[i][j] = x0[j] + plan_matrix[i][j] * xDel[j];

            for (int j = 0; j < k - 1; j++)
                for (int q = j + 1; q < k; q++)
                    xMatrix[i][j + q + k - 1] = xMatrix[i][j] * xMatrix[i][q];

            xMatrix[i][6] = xMatrix[i][0] * xMatrix[i][1] * xMatrix[i][2];

            for (int j = 7; j < 10; j++)
                xMatrix[i][j] = Math.pow(xMatrix[i][j - 7], 2);
        }

        f = new int[4];
        f[0] = m - 1;
        f[1] = N;
        f[2] = N * (m - 1);
        x2 = new double[k];

        for (int i = 0; i < k; i++)
            x2[i] = (Math.pow(2, k) + 2 * Math.pow(l, 2)) / N;
        for (int i = 0; i < N - 1; i++)
            for (int j = 7; j < 10; j++)
                plan_matrix[i][j] -= x2[j - 7];

        y = new double[N][m];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < m; j++)
                y[i][j] = f(xMatrix[i][0], xMatrix[i][1], xMatrix[i][2]) + 10 * Math.random() - 5;
    }

    private void Reg() {
        srY = new double[N];
        b = new double[nk];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < m; j++)
                srY[i] += y[i][j];
            srY[i] /= m;
        }

        my = 0;

        for (int i = 0; i < srY.length; i++)
            my += srY[i];

        my /= srY.length;

        if (N <= 8) {
            double[][] a = new double[N - 1][N - 1];
            double[] A = new double[N - 1];
            double[] mx = new double[N - 1];

            for (int i = 0; i < N - 1; i++) {
                for (int j = 0; j < nk; j++) {
                    mx[i] += xMatrix[j][i];
                    A[i] += xMatrix[j][i] * srY[j];
                }

                mx[i] /= nk;
                A[i] /= nk;

                for (int q = 0; q < nk - 1; q++) {
                    for (int j = 0; j < N; j++)
                        a[i][q] += xMatrix[j][i] * xMatrix[j][q];
                    a[i][q] /= N;
                }
            }

            double[][] t1 = new double[nk][nk];
            double[][] t2 = new double[nk][nk];
            double[][] columns = new double[nk][nk];

            for (int i = 0; i < nk - 1; i++) {
                t1[i + 1][0] = mx[i];
                t2[i + 1][0] = mx[i];
                columns[0][i + 1] = mx[i];
                t1[0][i + 1] = mx[i];
                t2[0][i + 1] = mx[i];

                columns[i + 1][0] = mx[i];
                for (int j = 0; j < nk - 1; j++) {
                    t1[i + 1][j + 1] = a[i][j];
                    t2[i + 1][j + 1] = a[i][j];
                    columns[j + 1][i + 1] = a[i][j];
                }
            }

            t1[0][0] = 1;
            t2[0][0] = 1;
            columns[0][0] = 1;
            double t2det = determinant(t2);
            double[] row1 = new double[N];
            row1[0] = my;
            for (int i = 1; i < N; i++)
                row1[i] = A[i - 1];
            replaceColumn(0, t1, row1);
            b[0] = determinant(t1) / t2det;
            for (int i = 1; i < nk; i++) {
                replaceColumn(i - 1, t1, columns[i - 1]);
                replaceColumn(i, t1, row1);
                b[i] = determinant(t1) / t2det;
            }
        } else {
            for (int i = 0; i < nk; i++)
                b[i] = 0;

            b[0] = my;

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < k; j++)
                    b[j + 1] += plan_matrix[i][j] * srY[i];

                for (int j = 0; j < k - 1; j++)
                    for (int q = j + 1; q < k; q++)
                        b[j + q + k] += plan_matrix[i][k - 1 + q + j] * srY[i];

                b[7] += plan_matrix[i][6] * srY[i];
                for (int j = 0; j < k; j++)
                    b[(int) Math.pow(2, k) + j] += plan_matrix[i][(int) Math.pow(2, k) + j - 1] * srY[i];
            }

            for (int i = 1; i < k + 1; i++)
                b[i] /= Math.pow(2, k - p) + 2 * l * l;

            for (int i = k + 1; i < Math.pow(2, k); i++)
                b[i] /= (int) Math.pow(2, k - p);

            for (int i = (int) Math.pow(2, k); i < nk; i++) {
                b[i] /= 2 * Math.pow(l, 4);
                b[i] = Math.sqrt(b[i]);
            }

            for (int i = 0; i < k; i++)
                b[0] -= x2[i] * b[(int) Math.pow(2, k) + i];
        }
    }

    private boolean Cohren() {
        disp = new double[N];
        double[] ts = new double[m];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < m; j++)
                ts[j] = y[i][j];
            disp[i] = getDispersion(ts);
        }

        double max = 0;
        double summ = 0;
        for (double t : disp) {
            if (max < t)
                max = t;
            summ += t;
        }
        Gp = max / summ;
        GTable = getCohrenTableValue(f[0], f[1]);
        return Gp < GTable;
    }

    private void Student() {
        t = new double[nk];
        tmod = new double[nk];
        sb = 0;
        double[] beta = new double[nk];

        for (int i = 0; i < N; i++)
            sb += disp[i];

        sb /= N;
        beta[0] = my;
        for (int i = 1; i < nk; i++) {
            for (int j = 0; j < N; j++)
                beta[i] += xMatrix[j][i - 1] * srY[j];
            beta[i] /= N;
        }

        int st3 = (m - 1) * N;
        if (st3 > 30)
            st3 = 30;

        double sbeta = Math.sqrt(sb / (N * m));
        if (N <= 8) {
            for (int i = 0; i < nk; i++) {
                tmod[i] = Math.abs(beta[i]) / sbeta;
                if (tmod[i] > tableStuident[st3])
                    t[i] = 1;
                else
                    t[i] = 0;
            }
        } else {
            double[] sB = new double[nk];
            sB[0] = sb / (m * (N + 2 * Math.pow(l, 4)));
            /*for (int i = 1; i < k + 1; i++)
                sB[i] = sb / (m * (Math.pow(2, k - p) + 2 * l * l));
            for (int i = k + 1; i < Math.pow(2, k); i++)
                sB[i] = sb / (m * Math.pow(2, k - p));
            for (int i = (int) Math.pow(2, k); i < nk; i++) 
                sB[i] = sb / (m * 2 * Math.pow(l, 4));*/
            for (int i = 0; i < sB.length; i++) {
                double z = 0;
                for (int j = 0; j < 10; j++)
                    z += this.plan_matrix[i][j];
                z = m * Math.pow(z, 2);
                sB[i] = sb / z;
            }
            STable = tableStuident[st3];
            for (int i = 0; i < nk; i++) {
                tmod[i] = Math.abs(beta[i]) / Math.sqrt(sB[i]);
                if (tmod[i] > STable)
                    t[i] = 1;
                else
                    t[i] = 0;
            }
        }
    }

    private boolean Fisher() {
        fish = new double[nk];
        for (int i = 0; i < nk; i++) {
            fish[i] += b[0] * t[0];
            for (int j = 1; j < nk; j++)
                if (!((N > 8) && ((j == 4) || (j == 6))))
                    fish[i] += b[j] * t[j] * plan_matrix[i][j - 1];
        }
        int d = 0;
        for (int i = 0; i < t.length; i++)
            if (t[i] == 1)
                ++d;
        f[3] = N - d;
        Fp = 0;
        double Sad = 0;

        if (f[3] > 0) {
            for (int i = 0; i < nk; i++)
                Sad += Math.pow((fish[i] - srY[i]), 2);
            Sad *= m;
            Sad /= f[3];
            Fp = Sad / sb;
        }

        return Fp < getFisherTableValue(f[2], f[3]);
    }

    private void show() {
        System.out.println("Xmax1 = " + xMax[0] + "\tXmax2 = " + xMax[1] + "\tXmax3 = " + xMax[2]);
        System.out.println("Xmin1 = " + xMin[0] + "\tXmin2 = " + xMin[1] + "\tXmin3 = " + xMin[2]);
        printDelimiter();

        System.out.println("Experimet marix:");
        System.out.print("N |   X1  |   X2  |  X3   | X1*X2 | X2*X3 |  X1^2 |  X2^2 |  X3^2 |");
        for (int i = 0; i < m; i++)
            System.out.print("      Y" + (i + 1) + "      |");
        System.out.println("     <Y>  ");

        for (int i = 0; i < N; i++) {
            System.out.printf("%2d|", i + 1);

            for (int j = 0; j < nk - 1; j++)
                if (!((j == 4) || (j == 6)))
                    System.out.printf("%7.3f|", plan_matrix[i][j]);

            for (int j = 0; j < m; j++)
                System.out.printf("%14.3f|", y[i][j]);
            System.out.printf("%14.4f|", srY[i]);
            System.out.println();
        }
        printDelimiter();

        System.out.println("Regression equation:");
        printEquation(b);
        printDelimiter();

        System.out.println("<Y> comparison:");
        double temp;

        for (int i = 0; i < N; i++) {
            temp = getResult(b, i);
            System.out.print("Yave[" + (i + 1) + "]=");
            System.out.printf("%7.3f   ", srY[i]);
            System.out.print("Result = ");
            System.out.printf("%7.3f  ", temp);
            System.out.print("Delta = ");
            System.out.printf("%7.3f   ", (srY[i] - temp));
            System.out.println();
        }
        printDelimiter();

        System.out.println("Student's test:");
        temp = tableStuident[(m - 1) * nk];
        System.out.println("T (in table) = " + temp);
        double ttt = temp;

        for (int i = 0; i < nk; i++) {
            if (!((N > 8) && ((i == 4) || (i == 6)))) {
                System.out.print("T" + i + " = ");
                System.out.printf("%7.3f", tmod[i]);
                if (t[i] > 0)
                    System.out.println(" -\tis significant.");
                else
                    System.out.println(" -\tis not significant.");
            }
        }
        printDelimiter();

        System.out.println("Fisher's test:");
        System.out.println("Fp = " + Fp);
        if (N > 8) {
            normalizeBKoef();
            System.out.println("Naturalizing of equation: ");
            for (int i = 0; i < k; i++) {
                System.out.print("\tdx" + (i + 1) + " = ");
                System.out.printf("%4.2f", xDel[i]);
            }
            System.out.println();

            for (int i = 0; i < k; i++) {
                System.out.print("\tx0" + (i + 1) + " = ");
                System.out.printf("%4.2f", x0[i]);
            }
            System.out.println();

            for (int i = 0; i < nk; i++)
                if (!((i == 5) || (i == 7))) {
                    System.out.print("b[" + i + "]=");
                    System.out.printf("%7.3f ", b[i]);
                    System.out.println();
                }

            System.out.println("Naturalized coefficients:");
            for (int i = 0; i < nk; i++)
                if ((i != 5) || (i != 7)) {
                    System.out.print("a[" + i + "]=");
                    System.out.printf("%7.3f ", Akoef[i]);
                    System.out.println();
                }

            System.out.println("Naturalized equation:");
            printEquation(Akoef);
            printDelimiter();

            System.out.println("<Y> comparison in naturalized equation:");
            for (int i = 0; i < N; i++) {
                temp = getResult(b, i);
                System.out.print("<Y>[" + (i + 1) + "]=");
                System.out.printf("%7.3f   ", srY[i]);
                System.out.print("Result = ");
                System.out.printf("%7.3f  ", temp);
                System.out.print("Delta = ");
                System.out.printf("%7.3f   ", (srY[i] - temp));
                System.out.println();
            }
        }
        printDelimiter();

        System.out.println("Degrees of freedom:");
        System.out.println("\tf1 = m-1 =" + f[0]);
        System.out.println("\tf2 = N = " + f[1]);
        System.out.println("\tf3 = f1*f2 = " + f[2]);
        System.out.println("\tf4 = " + f[3]);

        System.out.println("Coefficients in table:");
        System.out.print("\tCohren's coeff (q = 0.05): Ct = ");
        System.out.printf("%7.4f", GTable);
        System.out.println();
        System.out.print("\tFisher's coeff (q = 0.05): Ft = ");
        System.out.printf("%7.4f", getFisherTableValue(f[2], f[3]));
        System.out.println();

        System.out.println("Practiocal coefficients:");
        System.out.print("\tCohren's coeff:\tCp = ");
        System.out.printf("%7.4f", Gp);
        System.out.println(" < Gt -> variance is homogeneous.");
        System.out.println("\tStudent's coeff:\t" + ttt);
        System.out.printf("\tFisher's coeff:\tFp = %7.4f", Fp);
        System.out.println(" < Fт -> model is adequate.");
    }

    private void printDelimiter() {
        System.out.println("-----------------------------------------------------------------------------------------------------\n");
    }

    private void normalizeBKoef() {
        Akoef = new double[nk];
        Akoef[0] = b[0];
        for (int i = 0; i < k; i++)
            Akoef[0] += Math.sqrt(b[8 + i]) * Math.pow(x0[i] / xDel[i], 2) - b[i + 1] * x0[i] / xDel[i];

        Akoef[0] += b[4] * x0[0] * x0[1] / (xDel[0] * xDel[1]) + b[6] * x0[1] * x0[2] / (xDel[1] * xDel[2]);
        Akoef[1] = b[1] / xDel[0] - 2 * b[8] * x0[0] / Math.pow(xDel[0], 2) - b[4] * x0[1] / (xDel[0] * xDel[1]);
        Akoef[2] = b[2] / xDel[1] - 2 * b[9] * x0[1] / Math.pow(xDel[1], 2) - b[4] * x0[0] / (xDel[0] * xDel[1]) - b[6] * x0[0] / (xDel[1] * xDel[2]);
        Akoef[3] = b[3] / xDel[2] - 2 * b[10] * x0[2] / Math.pow(xDel[2], 2) - b[6] * x0[1] / (xDel[1] * xDel[2]);
        Akoef[4] = b[4] / (xDel[0] * xDel[1]);
        Akoef[6] = b[6] / (xDel[1] * xDel[2]);

        for (int i = 0; i < k; i++)
            Akoef[(int) Math.pow(2, k) + i] = Math.sqrt(b[(int) Math.pow(2, k) + i]) / (Math.pow(xDel[i], 2));
    }

    private double f(double x1, double x2, double x3) {
        return 1.5 + 1.5 * x1 + 6.8 * x2 + 3.2 * x3 + 2.7 * x1 * x1 + 0.1 * x2 * x2 + 1.2 * x3 * x3 +
                6.2 * x1 * x2 + 0.7 * x1 * x3 + 2.4 * x2 * x3 + 6.1 * x1 * x2 * x3;
//        return 200 + xMinAv + rand.nextDouble() * (xMaxAv - xMinAv);
//        return 1.4 + 2.2 * x1 + 0.9 * x2 + 4.6 * x3 + 0.0030 * x1 * x2 + 6.5 * x2 * x3 + 5.2 * x1 * x1 + 0.0067 * x2 * x2 + 0.4 * x3 * x3;
    }

    private void setY(int step) {
        switch (step) {
            case 1: {
                nk = 8;
                N = 8;
            }
            break;
            case 2: {
                nk = 11;
                N = 15;
            }
            break;
            default: {
            }
        }
    }

    private double getL(double p) {
        return Math.sqrt((Math.sqrt(N * Math.pow(2, k - p)) - Math.pow(2, k - p)) / 2);
    }

    private double getCohrenTableValue(int f1, int f2) {
        double[][] KOHRENE_TABLE = {{0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0.9985, 0.9750, 0.9392, 0.9057, 0.8772, 0.8534},
                {0.9669, 0.8709, 0.7977, 0.7457, 0.7071, 0.6771},
                {0.9065, 0.7679, 0.6841, 0.6287, 0.5891, 0.5598},
                {0.8412, 0.6838, 0.5981, 0.5440, 0.5063, 0.4783},
                {0.7808, 0.6161, 0.5321, 0.4803, 0.4447, 0.4184},
                {0.7271, 0.5612, 0.4800, 0.4307, 0.3974, 0.3726},
                {0.6798, 0.5157, 0.4377, 0.3910, 0.3595, 0.3362},
                {0.6385, 0.4775, 0.4027, 0.3584, 0.3286, 0.3067},
                {0.6020, 0.4450, 0.3733, 0.3311, 0.3029, 0.2823},
                {0, 0, 0, 0, 0, 0},
                {0.5410, 0.3924, 0.3264, 0.2880, 0.2624, 0.2439},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0.4709, 0.3346, 0.2758, 0.2419, 0.2159, 0.2034}};
        if (f1 <= 0)
            f1 = 1;
        if (f2 <= 0)
            f2 = 1;
        if (f2 >= 16)
            f2 = 15;
        if (f1 >= 8)
            f1 = 7;
        return KOHRENE_TABLE[f2][f1 - 1];
    }

    private double getFisherTableValue(int F3, int F4) {
        double[][] TableFisher = {{166.4, 199.5, 215.7, 224.6, 230.2, 234.0, 244.9, 249.0},
                {18.5, 19.2, 19.2, 19.3, 19.3, 19.3, 19.4, 19.4},
                {10.1, 8.6, 9.3, 9.1, 9.0, 8.9, 8.7, 8.6},
                {7.7, 6.9, 6.6, 6.4, 6.3, 6.2, 5.9, 5.8},
                {6.6, 5.8, 5.4, 5.2, 5.1, 5.0, 4.7, 4.5},
                {6.0, 5.1, 4.8, 4.5, 4.4, 4.3, 4.0, 3.8},
                {5.5, 4.7, 4.4, 4.1, 4.0, 3.9, 3.6, 3.4},
                {5.3, 4.5, 4.1, 3.8, 3.7, 3.6, 3.3, 3.1},
                {5.1, 4.3, 3.9, 3.6, 3.5, 3.4, 3.1, 2.9},
                {5.0, 4.1, 3.7, 3.5, 3.3, 3.2, 2.9, 2.7},
                {4.8, 4.0, 3.6, 3.4, 3.2, 3.1, 2.8, 2.6},
                {4.8, 3.9, 3.5, 3.3, 3.1, 3.0, 2.7, 2.5},
                {4.7, 3.8, 3.4, 3.2, 3.0, 2.9, 2.6, 2.4},
                {4.6, 3.7, 3.3, 3.1, 3.0, 2.9, 2.5, 2.3},
                {4.5, 3.7, 3.3, 3.1, 2.9, 2.8, 2.5, 2.3},
                {4.5, 3.6, 3.2, 3.0, 2.9, 2.7, 2.4, 2.2},
                {4.5, 3.6, 3.2, 3.0, 2.8, 2.7, 2.4, 2.2},
                {4.4, 3.6, 3.2, 2.9, 2.8, 2.7, 2.3, 2.1},
                {4.4, 3.5, 3.1, 2.9, 2.7, 2.6, 2.3, 2.1},
                {4.4, 3.5, 3.1, 2.9, 2.7, 2.6, 2.3, 2.1},
                {4.4, 3.5, 3.1, 2.9, 2.7, 2.6, 2.3, 2.1},
                {4.3, 3.4, 3.1, 2.8, 2.7, 2.6, 2.2, 2.0},
                {4.3, 3.4, 3.1, 2.8, 2.7, 2.6, 2.2, 2.0},
                {4.3, 3.4, 3.0, 2.8, 2.6, 2.5, 2.2, 2.0},
                {4.3, 3.4, 3.0, 2.8, 2.6, 2.5, 2.2, 2.0},
                {4.2, 3.4, 3.0, 2.7, 2.6, 2.5, 2.2, 2.0},
                {4.2, 3.4, 3.0, 2.7, 2.6, 2.5, 2.2, 2.0},
                {4.2, 3.3, 3.0, 2.7, 2.6, 2.4, 2.1, 1.9},
                {4.2, 3.3, 2.9, 2.7, 2.5, 2.4, 2.1, 1.9},
                {4.2, 3.3, 2.9, 2.7, 2.5, 2.4, 2.1, 1.9},
                {4.1, 3.2, 2.9, 2.6, 2.5, 2.3, 2.0, 1.8},
                {4.0, 3.2, 2.8, 2.5, 2.4, 2.3, 1.9, 1.7}};
        if (F3 <= 0)
            F3 = 1;
        if (F4 <= 0)
            F4 = 1;
        if (F4 >= 7 && F4 < 9)
            F4 = 6;
        if ((F4 >= 9 && F4 <= 12) || (F4 > 12 && F4 <= 19))
            F4 = 7;
        if (F4 > 19 && F4 <= 24)
            F4 = 8;
        if (F4 > 24)
            F4 = 9;
        if (F3 >= 29)
            F3 = 29;
        return TableFisher[F3 - 1][F4 - 1];
    }

    private void replaceColumn(int a, double[][] arr, double[] row) {
        for (int k = 0; k < arr.length; k++)
            arr[k][a] = row[k];
    }

    static void printMatrix(double a[][]) {
        int[] p = arrayBounds(a);
        System.out.println();
        for (int i = 0; i < p[0]; i++) {
            for (int j = 0; j < p[1]; j++) {
                System.out.printf("%12.2f", a[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private double getDispersion(double[] d) {
        double res = 0;
        double mz = (yimax + yimin) / 2;
        for (double t : d)
            res += Math.pow((mz - t), 2);
        res /= d.length;
        return res;
    }

    private void printEquation(double[] arr) {
        System.out.print("y = ");
        System.out.printf("%9.5f", arr[0]);
        if (arr[1] > 0)
            System.out.print(" +");
        System.out.printf("%9.5f", arr[1]);
        System.out.print("*x1");
        if (arr[2] > 0)
            System.out.print(" +");
        System.out.printf("%9.5f", arr[2]);
        System.out.print("*x2 ");
        if (arr[3] > 0)
            System.out.print(" +");
        System.out.printf("%9.5f", arr[3]);
        System.out.print("*x3 ");

        if (nk == 8) {
            if (arr[4] > 0)
                System.out.print(" +");
            System.out.printf("%9.5f", arr[4]);
            System.out.println("*x1*x2 ");
            if (arr[5] > 0)
                System.out.print(" +");
            System.out.printf("%9.5f", arr[5]);
            System.out.print("*x1*x3 +");
            System.out.printf("%9.5f", arr[6]);
            System.out.print("*x2*x3 +");
            System.out.printf("%9.5f", arr[7]);
            System.out.print("*x1*x2*x3");
        }

        if (nk > 8) {
            if (arr[4] > 0)
                System.out.print(" +");
            System.out.printf("%9.5f", arr[4]);
            System.out.print("*x1*x2 ");
            if (arr[6] > 0)
                System.out.print(" +");
            System.out.printf("%9.5f", arr[6]);
            System.out.print("*x2*x3 ");
            if (arr[8] > 0)
                System.out.print(" +");
            System.out.printf("%9.5f", arr[8]);
            System.out.print("*x1^2 ");
            if (arr[9] > 0)
                System.out.print(" +");
            System.out.printf("%9.5f", arr[9]);
            System.out.print("*x2^2 ");
            if (arr[10] > 0)
                System.out.print(" +");
            System.out.printf("%9.5f", arr[10]);
            System.out.print("*x3^2");
        }
        System.out.println();
    }

    static int[] arrayBounds(double m[][]) {
        int[] b = new int[2];
        double c;
        try {
            for (b[0] = 0; ; b[0]++)
                c = m[b[0]][0];
        } catch (Exception e) {
        }
        try {
            for (b[1] = 0; ; b[1]++)
                c = m[0][b[1]];
        } catch (Exception e) {
        }
        return b;
    }

    public double determinant(double[][] mat) {
        double result = 0;
        if (mat.length == 1) {
            result = mat[0][0];
            return result;
        }
        if (mat.length == 2) {
            result = mat[0][0] * mat[1][1] - mat[0][1] * mat[1][0];
            return result;
        }

        for (int i = 0; i < mat[0].length; i++) {
            double temp[][] = new double[mat.length - 1][mat[0].length - 1];
            for (int j = 1; j < mat.length; j++) {
                System.arraycopy(mat[j], 0, temp[j - 1], 0, i);
                System.arraycopy(mat[j], i + 1, temp[j - 1], i, mat[0].length - i - 1);
            }
            result += mat[0][i] * Math.pow(-1, i) * determinant(temp);
        }
        return result;
    }

    private double getResult(double[] koefOfEq, int row) {
        double res = koefOfEq[0];
        if (N <= 8)
            for (int i = 1; i < nk; i++)
                res += koefOfEq[i] * plan_matrix[row][i - 1];
        else
            for (int i = 1; i < nk; i++) {
                if (!((i == 5) || (i == 7)))
                    res += koefOfEq[i] * plan_matrix[row][i - 1];
            }
        return res;
    }

    private void refreshMatrix() {
        plan_matrix[8][0] = -l;
        plan_matrix[9][0] = l;
        plan_matrix[10][1] = -l;
        plan_matrix[11][1] = l;
        plan_matrix[12][2] = -l;
        plan_matrix[13][2] = l;
        plan_matrix[8][7] = l * l;
        plan_matrix[9][7] = l * l;
        plan_matrix[10][8] = l * l;
        plan_matrix[11][8] = l * l;
        plan_matrix[12][9] = l * l;
        plan_matrix[13][9] = l * l;
    }

    private double[][] y;
    private int nk = 4;
    private int k = 3;
    private int N = 4;
    private int p = 0;
    private double l = 0;
    private double[] srY;
    private double[] disp;
    private double Gp = 0;
    private double GTable;
    private double STable;
    private int[] f;
    private double Fp;
    private double[] Akoef;
    private double sb;
    private double[] b;
    private double[] t;
    private double[] fish;
    private double[] tmod;
    private double[] x2;
    private double[] xMin = {-10, 20, 50};
    private double[] xMax = {50, 60, 55};
    private Random rand = new Random();
    private double xMinAv = (xMin[0] + xMin[1] + xMin[2]) / 3;
    private double xMaxAv = (xMax[0] + xMax[1] + xMax[2]) / 3;

    private double[] x0 = {(xMax[0] + xMin[0]) / 2, (xMax[1] + xMin[1]) / 2, (xMax[2] + xMin[2]) / 2};
    private double[] xDel = {Math.abs(xMax[0] - xMin[0]) / 2, Math.abs(xMax[1] - xMin[1]) / 2, Math.abs(xMax[2] - xMin[2]) / 2};
    private double[][] xMatrix;
    private double yimax;
    private double yimin;
    private double my;
    private static int m = 2;
    private double[][] plan_matrix = {
            {-1, -1, -1, 1, 1, 1, -1, 1, 1, 1},
            {-1, 1, 1, -1, -1, 1, -1, 1, 1, 1},
            {1, -1, 1, -1, 1, -1, -1, 1, 1, 1},
            {1, 1, -1, 1, -1, -1, -1, 1, 1, 1},
            {-1, -1, 1, 1, -1, -1, 1, 1, 1, 1},
            {-1, 1, -1, -1, 1, -1, 1, 1, 1, 1},
            {1, -1, -1, -1, -1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {-l, 0, 0, 0, 0, 0, 0, l * l, 0, 0},
            {l, 0, 0, 0, 0, 0, 0, l * l, 0, 0},
            {0, -l, 0, 0, 0, 0, 0, 0, l * l, 0},
            {0, l, 0, 0, 0, 0, 0, 0, l * l, 0},
            {0, 0, -l, 0, 0, 0, 0, 0, 0, l * l},
            {0, 0, l, 0, 0, 0, 0, 0, 0, l * l},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
    private static double[] tableStuident = {0, 12.71, 4.303, 3.182, 2.776,
            2.571, 2.447, 2.365, 2.306, 2.262, 2.228, 2.201, 2.179, 2.160,
            2.145, 2.131, 2.12, 2.11, 2.101, 2.093, 2.086, 2.08, 2.074, 2.069,
            2.064, 2.06, 2.056, 2.052, 2.048, 2.045, 2.042, 1.96};
}
