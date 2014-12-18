package com.company.algorithms;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.*;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

final class StringCodec {
    /**
     * @param original
     * @return null if fails
     */
    public static String urlencode(String original) {
        try {
            //return URLEncoder.encode(original, "utf-8");
            //fixed: to comply with RFC-3986
            return URLEncoder.encode(original, "utf-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            //  Logger.e(e.toString());
        }
        return null;
    }

    /**
     * @param encoded
     * @return null if fails
     */
    public static String urldecode(String encoded) {
        try {
            return URLDecoder.decode(encoded, "utf-8");
        } catch (UnsupportedEncodingException e) {
            //  Logger.e(e.toString());
        }
        return null;
    }

    /**
     * @param original
     * @param key
     * @return null if fails
     */
    public static String hmacSha1Digest(String original, String key) {
        return hmacSha1Digest(original.getBytes(), key.getBytes());
    }

    /**
     * @param original
     * @param key
     * @return null if fails
     */
    public static String hmacSha1Digest(byte[] original, byte[] key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(key, "HmacSHA1"));
            byte[] rawHmac = mac.doFinal(original);
            return new String(Base64Coder.encode(rawHmac));
        } catch (Exception e) {
            //  Logger.e(e.toString());
        }
        return null;
    }

    /**
     * @param original
     * @return null if fails
     */
    public static String md5sum(byte[] original) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(original, 0, original.length);
            StringBuffer md5sum = new StringBuffer(new BigInteger(1, md.digest()).toString(16));
            while (md5sum.length() < 32)
                md5sum.insert(0, "0");
            return md5sum.toString();
        } catch (NoSuchAlgorithmException e) {
            //Logger.e(e.toString());
        }
        return null;
    }

    /**
     * @param original
     * @return null if fails
     */
    public static String md5sum(String original) {
        return md5sum(original.getBytes());
    }

    /**
     * AES encrypt function
     *
     * @param original
     * @param key      16, 24, 32 bytes available
     * @param iv       initial vector (16 bytes) - if null: ECB mode, otherwise: CBC mode
     * @return
     */
    public static byte[] aesEncrypt(byte[] original, byte[] key, byte[] iv) {
        if (key == null || (key.length != 16 && key.length != 24 && key.length != 32)) {
            //  Logger.e("key's bit length is not 128/192/256");
            return null;
        }
        if (iv != null && iv.length != 16) {
            //  Logger.e("iv's bit length is not 16");
            return null;
        }

        try {
            SecretKeySpec keySpec = null;
            Cipher cipher = null;
            if (iv != null) {
                keySpec = new SecretKeySpec(key, "AES/CBC/PKCS7Padding");
                cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
            } else  //if(iv == null)
            {
                keySpec = new SecretKeySpec(key, "AES/ECB/PKCS7Padding");
                cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
                cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            }

            return cipher.doFinal(original);
        } catch (Exception e) {
            //  Logger.e(e.toString());
        }
        return null;
    }

    /**
     * AES decrypt function
     *
     * @param encrypted
     * @param key       16, 24, 32 bytes available
     * @param iv        initial vector (16 bytes) - if null: ECB mode, otherwise: CBC mode
     * @return
     */
    public static byte[] aesDecrypt(byte[] encrypted, byte[] key, byte[] iv) {
        if (key == null || (key.length != 16 && key.length != 24 && key.length != 32)) {
            //  Logger.e("key's bit length is not 128/192/256");
            return null;
        }
        if (iv != null && iv.length != 16) {
            //  Logger.e("iv's bit length is not 16");
            return null;
        }

        try {
            SecretKeySpec keySpec = null;
            Cipher cipher = null;
            if (iv != null) {
                keySpec = new SecretKeySpec(key, "AES/CBC/PKCS7Padding");
                cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
            } else  //if(iv == null)
            {
                keySpec = new SecretKeySpec(key, "AES/ECB/PKCS7Padding");
                cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
                cipher.init(Cipher.DECRYPT_MODE, keySpec);
            }

            return cipher.doFinal(encrypted);
        } catch (Exception e) {
            //  Logger.e(e.toString());
        }
        return null;
    }

    /**
     * generates RSA key pair
     *
     * @param keySize
     * @param publicExponent public exponent value (can be RSAKeyGenParameterSpec.F0 or F4)
     * @return
     */
    public static KeyPair generateRsaKeyPair(int keySize, BigInteger publicExponent) {
        KeyPair keys = null;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            RSAKeyGenParameterSpec spec = new RSAKeyGenParameterSpec(keySize, publicExponent);
            keyGen.initialize(spec);
            keys = keyGen.generateKeyPair();
        } catch (Exception e) {
            //  Logger.e(e.toString());
        }
        return keys;
    }

    /**
     * generates a RSA public key with given modulus and public exponent
     *
     * @param modulus        (must be positive? don't know exactly)
     * @param publicExponent
     * @return
     */
    public static PublicKey generateRsaPublicKey(BigInteger modulus, BigInteger publicExponent) {
        try {
            return KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(modulus, publicExponent));
        } catch (Exception e) {
            //  Logger.e(e.toString());
        }
        return null;
    }

    /**
     * generates a RSA private key with given modulus and private exponent
     *
     * @param modulus         (must be positive? don't know exactly)
     * @param privateExponent
     * @return
     */
    public static PrivateKey generateRsaPrivateKey(BigInteger modulus, BigInteger privateExponent) {
        try {
            return KeyFactory.getInstance("RSA").generatePrivate(new RSAPrivateKeySpec(modulus, privateExponent));
        } catch (Exception e) {
            //  Logger.e(e.toString());
        }
        return null;
    }

    /**
     * RSA encrypt function (RSA / ECB / PKCS1-Padding)
     *
     * @param original
     * @param key
     * @return
     */
    public static byte[] rsaEncrypt(byte[] original, PublicKey key) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(original);
        } catch (Exception e) {
            //  Logger.e(e.toString());
        }
        return null;
    }

    /**
     * RSA decrypt function (RSA / ECB / PKCS1-Padding)
     *
     * @param encrypted
     * @param key
     * @return
     */
    public static byte[] rsaDecrypt(byte[] encrypted, PrivateKey key) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(encrypted);
        } catch (Exception e) {
            //  Logger.e(e.toString());
        }
        return null;
    }

    /**
     * converts given byte array to a hex string
     *
     * @param bytes
     * @return
     */
    public static String byteArrayToHexString(byte[] bytes) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10)
                buffer.append("0");
            buffer.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buffer.toString();
    }

    /**
     * converts given hex string to a byte array
     * (ex: "0D0A" => {0x0D, 0x0A,})
     *
     * @param str
     * @return
     */
    public static final byte[] hexStringToByteArray(String str) {
        int i = 0;
        byte[] results = new byte[str.length() / 2];
        for (int k = 0; k < str.length(); ) {
            results[i] = (byte) (Character.digit(str.charAt(k++), 16) << 4);
            results[i] += (byte) (Character.digit(str.charAt(k++), 16));
            i++;
        }
        return results;
    }
}


//Copyright 2003-2009 Christian d'Heureuse, Inventec Informatik AG, Zurich, Switzerland
//www.source-code.biz, www.inventec.ch/chdh
//
//This module is multi-licensed and may be used under the terms
//of any of the following licenses:
//
//EPL, Eclipse Public License, http://www.eclipse.org/legal
//LGPL, GNU Lesser General Public License, http://www.gnu.org/licenses/lgpl.html
//AL, Apache License, http://www.apache.org/licenses
//BSD, BSD License, http://www.opensource.org/licenses/bsd-license.php
//
//Please contact the author if you need another license.
//This module is provided "as is", without warranties of any kind.

/**
 * A Base64 Encoder/Decoder.
 *
 * <p>
 * This class is used to encode and decode data in Base64 format as described in RFC 1521.
 *
 * <p>
 * Home page: <a href="http://www.source-code.biz">www.source-code.biz</a><br>
 * Author: Christian d'Heureuse, Inventec Informatik AG, Zurich, Switzerland<br>
 * Multi-licensed: EPL/LGPL/AL/BSD.
 *
 * <p>
 * Version history:<br>
 * 2003-07-22 Christian d'Heureuse (chdh): Module created.<br>
 * 2005-08-11 chdh: Lincense changed from GPL to LGPL.<br>
 * 2006-11-21 chdh:<br>
 * &nbsp; Method encode(String) renamed to encodeString(String).<br>
 * &nbsp; Method decode(String) renamed to decodeString(String).<br>
 * &nbsp; New method encode(byte[],int) added.<br>
 * &nbsp; New method decode(String) added.<br>
 * 2009-07-16: Additional licenses (EPL/AL) added.<br>
 * 2009-09-16: Additional license (BSD) added.<br>
 */

