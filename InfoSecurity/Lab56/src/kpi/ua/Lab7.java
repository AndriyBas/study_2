package kpi.ua;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by andriybas on 3/28/16.
 */
public class Lab7 {

    private BigInteger n, d, e;

    private int bitlen = 1024;

    /**
     * Create an instance that can encrypt using someone elses public key.
     */
    public Lab7(BigInteger newn, BigInteger newe) {
        n = newn;
        e = newe;
    }

    /**
     * Create an instance that can both encrypt and decrypt.
     */
    public Lab7(int bits) {
        bitlen = bits;
        SecureRandom r = new SecureRandom();
        BigInteger p = new BigInteger(bitlen / 2, 100, r);
        BigInteger q = new BigInteger(bitlen / 2, 100, r);
        n = p.multiply(q);
        BigInteger m = (p.subtract(BigInteger.ONE))
                .multiply(q.subtract(BigInteger.ONE));
        e = new BigInteger("3");
        while (m.gcd(e).intValue() > 1) {
            e = e.add(new BigInteger("2"));
        }
        d = e.modInverse(m);

        System.out.println(n.toString());
        System.out.println(d.toString());
        System.out.println(e.toString());
    }

    /**
     * Encrypt the given plaintext message.
     */
    public synchronized String encrypt(String message) {
        return (new BigInteger(message.getBytes())).modPow(e, n).toString();
    }

    /**
     * Encrypt the given plaintext message.
     */
    public synchronized BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }

    /**
     * Decrypt the given ciphertext message.
     */
    public synchronized String decrypt(String message) {
        return new String((new BigInteger(message)).modPow(d, n).toByteArray());
    }

    /**
     * Decrypt the given ciphertext message.
     */
    public synchronized BigInteger decrypt(BigInteger message) {
        return message.modPow(d, n);
    }

    /**
     * Generate a new public and private key set.
     */
    public synchronized void generateKeys() {
        SecureRandom r = new SecureRandom();
        BigInteger p = new BigInteger(bitlen / 2, 100, r);
        BigInteger q = new BigInteger(bitlen / 2, 100, r);
        n = p.multiply(q);
        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q
                .subtract(BigInteger.ONE));
        e = new BigInteger("3");
        while (m.gcd(e).intValue() > 1) {
            e = e.add(new BigInteger("2"));
        }
        d = e.modInverse(m);
    }

    /**
     * Return the modulus.
     */
    public synchronized BigInteger getN() {
        return n;
    }

    /**
     * Return the public key.
     */
    public synchronized BigInteger getE() {
        return e;
    }

    /**
     * Trivial test program.
     */
    public static void main(String[] args) {

        Lab7 rsa = new Lab7(1024);

        String text1 = "Super secret text ...)))";
        System.out.println("Plaintext: " + text1);
        BigInteger plaintext = new BigInteger(text1.getBytes());

        BigInteger ciphertext = rsa.encrypt(plaintext);
        System.out.println("Ciphertext: " + ciphertext);
        plaintext = rsa.decrypt(ciphertext);

        String text2 = new String(plaintext.toByteArray());
        System.out.println("Plaintext: " + text2);


    }
}
