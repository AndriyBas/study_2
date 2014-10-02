package lab3;

/*	Лабораторна робота №2
--------------------------------
	Основна програма
--------------------------------
	F1: e = (A + B)*(C + D*(MA*MZ))
	F2: MD = MIN(MA)*MB*MC
	F3: F = MA*D + MB*C + (MX*ME)*P
--------------------------------
	Виконав:
	Бас Андрій, гр. ІО-22
	3.10.2014
---------------------------------*/
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Data.setN(1000);

        Thread f1 = new F1Task();
        Thread f2 = new Thread(new F2Task());
        Thread f3 = new Thread(new F3Task());

        f1.setName("F1");
        f2.setName("F2");
        f3.setName("F3");
        f1.setPriority(Thread.NORM_PRIORITY + 3);
        f2.setPriority(Thread.MIN_PRIORITY);
        f3.setPriority(10);

        f1.start();
        f2.start();
        f2.join();
        f3.start();
    }
}
