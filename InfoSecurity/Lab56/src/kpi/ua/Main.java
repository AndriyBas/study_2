package kpi.ua;

import java.math.BigInteger;

public class Main {

    private static final BigInteger FLOOR = new BigInteger("10123400123400000001234000123400123401234");

    private static BigInteger nextPrime(BigInteger floor) {
        return floor.nextProbablePrime();
    }

    private static boolean isPrime(BigInteger prime) {
        return prime.isProbablePrime(100);
    }

    public static void main(String[] args) {
        BigInteger prime = nextPrime(FLOOR);
        System.out.println("Probably prime: " + prime);
        System.out.println("Does " + prime + " prime?: " + isPrime(prime));

        System.out.println();

        BigInteger nextPrime = nextPrime(prime);
        System.out.println("Multiply:     " + nextPrime + " * " + prime + "  = " + nextPrime.multiply(prime));
        System.out.println("Divide(mod):  " + nextPrime + " / " + prime + " ~= " + nextPrime.divide(prime));
        System.out.println("Add:          " + nextPrime + " + " + prime + "  = " + nextPrime.add(prime));
        System.out.println("Subtract:     " + nextPrime + " - " + prime + "  = " + nextPrime.subtract(prime));
    }

}
