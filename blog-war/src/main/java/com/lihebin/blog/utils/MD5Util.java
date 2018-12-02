package com.lihebin.blog.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    private static final char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static char[] encode(byte[] bytes) {
        final int nBytes = bytes.length;
        char[] result = new char[2 * nBytes];
        int j = 0;
        for (byte aByte : bytes) {
            result[j++] = HEX[(0xF0 & aByte) >>> 4];
            result[j++] = HEX[(0x0F & aByte)];

        }
        return result;
    }

    private byte[] decode(CharSequence s) {
        int nChars = s.length();
        if (nChars % 2 != 0) {
            throw new IllegalArgumentException("Hex-encoded string must have an even number of characters");
        }
        byte[] result = new byte[nChars / 2];
        for (int i = 0; i < nChars; i += 2) {
            int msb = Character.digit(s.charAt(i), 16);
            int lsb = Character.digit(s.charAt(i + 1), 16);
            if (msb < 0 || lsb < 0) {
                throw new IllegalArgumentException("Non-hex character in input: " + s);
            }
            result[i / 2] = (byte) ((msb << 4) | lsb);
        }
        return result;
    }

    public static String md5(byte[] input) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            return new String(encode(digest.digest(input)));
        } catch (NoSuchAlgorithmException e) {
            throw new IOException(e);
        }
    }

    private String md5(InputStream input) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                digest.update(buffer, 0, length);
            }
            return new String(encode(digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new IOException(e);
        }
    }

    private static String encryptMd5(String string) throws IOException {
        return encryptMd5(string, "UTF-8");
    }

    private static String encryptMd5(String string, String charSet) throws IOException, IOException {
//        return DigestUtils.md5Hex(string.getBytes(charSet));
        return md5(string.getBytes(charSet));
    }

    /**
     * 计算字符串的MD5值
     * @param  signStr:签名字符串
     * @return
     */
    public static String getSign(String signStr) {
        String md5;
        try {
            md5 = encryptMd5(signStr);
            return md5;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
