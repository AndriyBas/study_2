package lab3;

/*	Лабораторна робота №3
--------------------------------
	Основна програма
--------------------------------
	1.22: F1 : d = (B*C) + (A*B) +(C*(B*(MA*MZ)))
	2.19: F2 : v = MAX(MA + MB*MC)
    3.21: F3 : W = SORT(B*MD)*(MA *MB)
--------------------------------
	Виконав:
	Бас Андрій, гр. ІО-22
	3.10.2014
---------------------------------*/
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Data.setN(1000);

        // java -Xss128m Main.java
        Thread f1 = new Thread(null, new F1Task(), "F1", 100000);
        Thread f2 = new Thread(null, new F2Task(), "F2", 100000);
        Thread f3 = new Thread(null, new F3Task(), "F3", 100000);

        f1.setPriority(Thread.NORM_PRIORITY);
        f2.setPriority(Thread.MIN_PRIORITY);
        f3.setPriority(10);

        f1.start();
        f2.start();
        f2.join();
        f3.start();
    }
}
