//SHA-1

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        System.out.println(GetSHA1Hash("hjd"));
    }

    public static String LeftRotate(String string, int rotate) {
        String res = "";
        for (int i = 0; i < string.length(); i++) {
            res += string.charAt((i + rotate) % string.length());
        }
        return res;
    }

    public static String GetSHA1Hash(String message) {
        String h0 = "67452301";
        String h1 = "EFCDAB89";
        String h2 = "98BADCFE";
        String h3 = "10325476";
        String h4 = "C3D2E1F0";
        String messageInHex = "";
        for (int i = 0; i < message.length(); i++) {
            String hexString = Integer.toHexString(message.charAt(i));
            messageInHex += hexString;
        }
        int ml = (messageInHex.length()) / 2;
        List<String> chunks = new ArrayList<>();
        for (int i = 0; i < messageInHex.length(); i += 1024) {
            String chunk = "";
            for (int j = i; j < messageInHex.length(); j++) {
                chunk += messageInHex.charAt(j);
            }
            chunks.add(i / 1024, chunk);
        }
        for (String chunk :
                chunks) {
            List<String> w = new ArrayList<>(80);
            for (int i = 0; i <= 15; i++) {
                String temp = "";
                for (int j = i * 64; j < (i + 1) * 64 && j < chunk.length(); j++) {
                    temp += chunk.charAt(j);
                }
                w.add(i, temp);
            }
            for (int i = 16; i <= 79; i++) {
                w.add(i, LeftRotate(BigEndianToHex(HexToBigEndian(w.get(i - 3)).xor(HexToBigEndian(w.get(i - 8))).xor(HexToBigEndian(w.get(i - 14))).xor(HexToBigEndian(w.get(i - 16)))), 1));
            }
            var a = h0;
            var b = h1;
            var c = h2;
            var d = h3;
            var e = h4;
            for (int i = 0; i <= 79; i++) {
                String k = "";
                String f = "";
                if (i >= 0 && i <= 19) {
                    k = "5A827999";
                    f = BigEndianToHex((HexToBigEndian(b).and(HexToBigEndian(c))).or((HexToBigEndian(b).not()).and(HexToBigEndian(d))));
                } else if (i >= 20 && i <= 39) {
                    f = BigEndianToHex(HexToBigEndian(b).xor(HexToBigEndian(c).xor(HexToBigEndian(d))));
                    k = "6ED9EBA1";
                } else if (i >= 40 && i <= 59) {
                    k = "8F1BBCDC";
                    f = BigEndianToHex((HexToBigEndian(b).and(HexToBigEndian(c))).or(HexToBigEndian(b).and(HexToBigEndian(d))).or(HexToBigEndian(c).and(HexToBigEndian(d))));
                } else if (i >= 60 && i <= 79) {
                    f = BigEndianToHex(HexToBigEndian(b).xor(HexToBigEndian(c).xor(HexToBigEndian(d))));
                    k = "CA62C1D6";
                }
                String temp = BigEndianToHex(HexToBigEndian(LeftRotate(a, 5)).add(HexToBigEndian(f)).add(HexToBigEndian(e)).add(HexToBigEndian(k)).add(HexToBigEndian(w.get(i))));
                e = d;
                d = c;
                c = LeftRotate(b, 30);
                b = a;
                a = temp;
            }
            h0 = BigEndianToHex(HexToBigEndian(h0).add(HexToBigEndian(a)));
            h1 = BigEndianToHex(HexToBigEndian(h1).add(HexToBigEndian(b)));
            h2 = BigEndianToHex(HexToBigEndian(h2).add(HexToBigEndian(c)));
            h3 = BigEndianToHex(HexToBigEndian(h3).add(HexToBigEndian(d)));
            h4 = BigEndianToHex(HexToBigEndian(h4).add(HexToBigEndian(e)));
        }
        return BigEndianToHex((HexToBigEndian(h0).shiftLeft(128)).or(HexToBigEndian(h1).shiftLeft(96)).or(HexToBigEndian(h2).shiftLeft(64)).or((HexToBigEndian(h3).shiftLeft(32))).or(HexToBigEndian(h4)));
    }

    public static BigInteger HexToBigEndian(String vector) {
        BigInteger result = BigInteger.ZERO;
        BigInteger base = BigInteger.valueOf(16);
        boolean q = false;
        for (int i = 0; i < vector.length() && !q; i++) {
            char symbol = vector.charAt(i);
            q = false;
            int symbolInInt = 0;
            if (symbol >= 48 && symbol <= 57) {
                symbolInInt = symbol - 48;
                q = true;
            }
            if (symbol >= 65 && symbol <= 70) {
                symbolInInt = symbol - 55;
                q = true;
            }
            if (symbol >= 97 && symbol <= 102) {
                symbolInInt = symbol - 87;
                q = true;
            }
            if (!q) {
                System.out.println("vector has incorrect symbol");
                return BigInteger.valueOf(-1);
            }
            q = false;

            BigInteger integer = new BigInteger(Integer.toString(symbolInInt));
            result = result.add(integer.multiply(base.pow(vector.length() - i - 1)));
        }
        return result;
    }

    public static String ReversMemory(String string) {
        String vector = "";
        for (int i = 0; i < string.length(); i += 2) {
            vector += (Character.toString(string.charAt(string.length() - 2 - i)) + string.charAt(string.length() - 1 - i));
        }
        return vector;
    }

    public static BigInteger HexToLittleEndian(String string) {
        return HexToBigEndian(ReversMemory(string));
    }

    public static String LittleEndianToHex(BigInteger bigInteger) {
        String res = "";
        while (!bigInteger.equals(BigInteger.ZERO)) {
            BigInteger i = bigInteger.mod(BigInteger.valueOf(16));
            if (i.intValue() < 10 && i.intValue() >= 0) {
                res += i.toString();
            } else {
                res += Character.toString('A' + (i.intValue() - 10));
            }
            bigInteger = bigInteger.divide(BigInteger.valueOf(16));
        }
        return ReverseString(res);
    }

    public static String BigEndianToHex(BigInteger bigInteger) {
        return LittleEndianToHex(bigInteger);
    }

    public static String ReverseString(String string) {
        String res = "";
        for (int i = 0; i < string.length(); i++) {
            res += string.charAt(string.length() - 1 - i);
        }
        return res;
    }
}