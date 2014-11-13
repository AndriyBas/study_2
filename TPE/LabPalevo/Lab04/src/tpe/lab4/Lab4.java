package tpe.lab4;

public class Lab4 {
    static int minX1 = -40;
    static int maxX1 = 20;

    static int minX2 = 10;
    static int maxX2 = 60;

    static int minX3 = -20;
    static int maxX3 = 20;

    static int minY = (minX1 + minX2 + minX3) / 3 + 200;
    static int maxY = (maxX1 + maxX2 + maxX3) / 3 + 200;

    public static void main(String[] args) {
        Manager m = new Manager(minX1, maxX1, minX2, maxX2, minX3, maxX3, minY, maxY);
        m.main();
    }
}
