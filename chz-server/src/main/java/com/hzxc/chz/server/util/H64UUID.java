package com.hzxc.chz.server.util;

import java.text.ParseException;
import java.util.UUID;

/**
 * create by chz on 2018/3/1
 */
public class H64UUID {
    public static final char[] charMap;
    public static final char[] hex16_charMap;

    static {
        charMap = new char[64];
        for (int i = 0; i < 10; i++) {
            charMap[i] = (char) ('0' + i);
        }
        for (int i = 10; i < 36; i++) {
            charMap[i] = (char) ('a' + i - 10);
        }
        for (int i = 36; i < 62; i++) {
            charMap[i] = (char) ('A' + i - 36);
        }
        charMap[62] = '_';
        charMap[63] = '-';

        //hex16_charmap
        hex16_charMap = new char[16];
        for (int i = 0; i < 10; i++) {
            hex16_charMap[i] = (char) ('0' + i);
        }
        for (int i = 10; i < 16; i++) {
            hex16_charMap[i] = (char) ('a' + i - 10);
        }
    }

    public static String hexTo64(String hex) {
        StringBuffer r = new StringBuffer();
        int index;
        int[] buff = new int[3];
        int l = hex.length();
        for (int i = 0; i < l; i++) {
            index = i % 3;
            buff[index] = Integer.parseInt("" + hex.charAt(i), 16);
            if (index == 2) {
                r.append(charMap[buff[0] << 2 | buff[1] >>> 2]);
                r.append(charMap[(buff[1] & 3) << 4 | buff[2]]);
            }
        }
        return r.toString();
    }

    private static int indexOfChar(char[] map, char ch) {
        for (int i = 0; i < map.length; i++) {
            if (ch == map[i]) {
                return i;
            }
        }
        return -1;
    }

    public static String h64to16(String h64) {
        StringBuffer r = new StringBuffer();
        int index;
        int[] buff = new int[2];
        int l = h64.length();

        for (int i = 0; i < l; i++) {
            index = i % 2;
            buff[index] = indexOfChar(charMap, h64.charAt(i));
            if (index == 1) {
                r.append(hex16_charMap[buff[0] >> 2]);
                r.append(hex16_charMap[(buff[0] & 3) << 2 | buff[1] >> 4]);
                r.append(hex16_charMap[(buff[1] & 0xf)]);
            }
        }
        return r.toString();
    }

    public static String getUUID() {
        StringBuffer sb = new StringBuffer("0");
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "").toUpperCase();
        sb.append(uuid);
        uuid = hexTo64(sb.toString());
        return uuid;
    }

    public static String encodeUUID(String uuid) {
        uuid = uuid + "0";
        return hexTo64(uuid);
    }

    public static String decodeUUID(String encodedUuid) {
        String uuid = h64to16(encodedUuid);
        return uuid.substring(0, uuid.length() - 1);
    }


    public static void main(String[] args) throws ParseException {
        // String raw = "3058f2b1c12041ed8674d182fa64287d";
        // System.out.println("raw:" + raw);
        // String cipher = hexTo64(raw + "0");
        // System.out.println("cipher:" + cipher);
        // System.out.println("dec:" + h64to16(cipher));
        for (int i = 0; i < 1000; i++) {
            String uuid = UUID.randomUUID().toString().replace("-","");
            // System.out.println(uuid);
            String e = encodeUUID(uuid);
            // System.out.println(e);
            String d = decodeUUID(e);
            // System.out.println(d);
            if(!uuid.equals(d)){
                System.out.println("fail");
            }
        }
    }
}
