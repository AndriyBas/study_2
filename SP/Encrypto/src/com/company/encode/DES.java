package com.company.encode;


import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;

public class DES {

    private static final byte[] initializationVector = new byte[]{
            (byte) 0x10, (byte) 0x10, (byte) 0x01, (byte) 0x04,
            (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x02
    };

    private final Cipher cipherEncrypt;
    private final Cipher cipherDecrypt;

    private SecretKey secretKey;
    private SecretKeySpec secretKeySpec;

    public DES() throws Exception {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        secretKey = KeyGenerator.getInstance("DES").generateKey();

        final AlgorithmParameterSpec algParameters = new IvParameterSpec(initializationVector);

        cipherEncrypt = Cipher.getInstance("DES/CBC/PKCS5Padding", "BC");

        cipherDecrypt = Cipher.getInstance("DES/CBC/PKCS5Padding", "BC");

        cipherEncrypt.init(Cipher.ENCRYPT_MODE, secretKey, algParameters);

        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKey, algParameters);
    }

    public DES(String secretKeyHexString) throws Exception {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        secretKeySpec = new SecretKeySpec(DatatypeConverter.parseHexBinary(secretKeyHexString), "DES");

        final AlgorithmParameterSpec algParameters = new IvParameterSpec(initializationVector);

        cipherEncrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");

        cipherDecrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");

        cipherEncrypt.init(Cipher.ENCRYPT_MODE, secretKeySpec, algParameters);

        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec, algParameters);
    }

    public String getKeyHex() {
        if (secretKey != null) {
            return bytesToHex(secretKey.getEncoded());
        } else {
            return bytesToHex(secretKeySpec.getEncoded());
        }
    }

    public FileOutputStream encrypt(File file) throws IOException {

        FileInputStream is = new FileInputStream(file);

//        File out = new File("temp.txt");

        FileOutputStream os = new FileOutputStream("temp_en_des_out.txt");

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

        FileOutputStream os = new FileOutputStream("temp_de_des_out.txt");

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
