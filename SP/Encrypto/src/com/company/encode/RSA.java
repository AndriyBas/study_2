package com.company.encode;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;

public class RSA {

    private final Cipher cipherEncrypt;
    private final Cipher cipherDecrypt;

    private KeyPair keyPair;

    public RSA() throws Exception {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");
        kpg.initialize(1024);

        keyPair = kpg.genKeyPair();

        cipherEncrypt = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");

        cipherDecrypt = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");


        cipherEncrypt.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());

        cipherDecrypt.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
    }

    public RSA(String secretKeyHexString) throws Exception {

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(DatatypeConverter.parseHexBinary(secretKeyHexString));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(keySpec);
        
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
//
//        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//
//        SecretKeySpec secretKeySpec = new SecretKeySpec(DatatypeConverter.parseHexBinary(secretKeyHexString), "RSA");
//        PrivateKey privateKey = keyFactory.generatePrivate(secretKeySpec);
//


        cipherEncrypt = null;

        cipherDecrypt = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");

        cipherDecrypt.init(Cipher.DECRYPT_MODE, privateKey);
    }

//    public String getKeyHex(Key secretKey) {
//        if (secretKey != null) {
//            return bytesToHex(secretKey.getEncoded());
//        } else {
//            return bytesToHex(secretKeySpec.getEncoded());
//        }
//    }

    public String getPublicKeyHex() {
        return bytesToHex(keyPair.getPublic().getEncoded());
    }

    public String getPrivateKeyHex() {
        return bytesToHex(keyPair.getPrivate().getEncoded());
    }

    public FileOutputStream encrypt(File file) throws IOException {

        if (cipherEncrypt == null) {
            throw new IllegalStateException("cipher is not initialized :((");
        }

        FileInputStream is = new FileInputStream(file);

//        File out = new File("temp.txt");

        FileOutputStream os = new FileOutputStream("temp_en_rsa_out.txt");

        int dataSize = is.available();

        byte[] inbytes = new byte[dataSize];

        is.read(inbytes);

        String str2 = new String(inbytes);

        System.out.println("Input file contentn" + str2 + "n");

        writeEncode(inbytes, os);

        os.flush();

        is.close();

        os.close();

        return os;
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public FileOutputStream decrypt(File file) throws IOException {

        FileInputStream is = new FileInputStream(file);

//        File out = new File("temp.txt");

        FileOutputStream os = new FileOutputStream("temp_de_rsa_out.txt");

        int dataSize = is.available();

        byte[] decBytes = new byte[dataSize];

        readDecode(decBytes, is);

        is.close();

        os.write(decBytes);

//        String str = new String(decBytes);
//        System.out.println("Decrypted file contents:n" + str);

        os.flush();

        is.close();

        os.close();

        return os;

    }

    public void writeEncode(byte[] bytes, OutputStream output) throws IOException {

        CipherOutputStream cOutputStream = new CipherOutputStream(output, cipherEncrypt);

        cOutputStream.write(bytes, 0, bytes.length);

        cOutputStream.close();
    }

    public void readDecode(byte[] bytes, InputStream input) throws IOException {

        CipherInputStream cInputStream = new CipherInputStream(input, cipherDecrypt);

        int position = 0, i;

        while ((i = cInputStream.read()) != -1) {

            bytes[position] = (byte) i;

            position++;

        }
    }

}
