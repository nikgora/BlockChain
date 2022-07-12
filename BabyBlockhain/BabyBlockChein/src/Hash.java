import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Hash {
    private String hash;

    public Hash(String hash) {
        this.hash = hash;
    }

    public String To8Chars(String string) {
        String res = "";
        for (int i = 0; i < 8 - string.length(); i++) {
            res += "0";
        }
        return res + string;
    }

    public String toSHA1(String message) {
        String h0 = "67452301";
        String h1 = "EFCDAB89";
        String h2 = "98BADCFE";
        String h3 = "10325476";
        String h4 = "C3D2E1F0";
        String messageInHex = "";
        boolean q = false;
        int numOfChunk = -1;
        for (int i = 0; i < message.length(); i++) {
            messageInHex += Integer.toHexString(message.charAt(i));
        }
        int ml = (messageInHex.length()) * 4;
        String messageLengthInHex = BigEndianToHex(BigInteger.valueOf(ml));
        String t = "";
        for (int i = 0; i < 8 - messageLengthInHex.length(); i++) {
            t += "0";
        }
        t += messageLengthInHex;
        messageLengthInHex = t;
        List<String> chunks = new ArrayList<>();
        for (int i = 0; i < messageInHex.length(); i += 128) {
            String chunk = "";
            int j;
            for (j = i; j < (i + 1) * 128 && j < messageInHex.length(); j++) {
                chunk += messageInHex.charAt(j);
            }
            if (j < (i + 1) * 128 - 1) {
                chunk += "8";
                if (j >= 112) q = true;
                for (j = messageInHex.length() + 1; j < (i + 1) * 128 - 8 && !q; j++) {
                    chunk += BigEndianToHex(BigInteger.valueOf(0));
                }
                if (!q) chunk += messageLengthInHex;
                for (j = messageInHex.length() + 1; j < (i + 1) * 128 && q; j++) {
                    chunk += BigEndianToHex(BigInteger.valueOf(0));
                }

            }
            numOfChunk++;
            chunks.add(numOfChunk, chunk);
        }
        if (q) {
            String chunk = "";
            for (int i = 0; i < 120; i++) {
                chunk += BigEndianToHex(BigInteger.ZERO);
            }
            chunk += messageLengthInHex;
            chunks.add(numOfChunk + 1, chunk);

        }
        var a = h0;
        var b = h1;
        var c = h2;
        var d = h3;
        var e = h4;
        for (String chunk :
                chunks) {
            List<String> w = new ArrayList<>(80);
            for (int i = 0; i <= 15; i++) {
                String temp = "";
                for (int j = i * 8; j < (i + 1) * 8; j++) {
                    temp += chunk.charAt(j);
                }
                w.add(i, temp);
            }

            for (int i = 16; i <= 79; i++) {
                String temp = BigEndianToHex((HexToBigEndian(w.get(i - 3)).xor(HexToBigEndian(w.get(i - 8))).xor(HexToBigEndian(w.get(i - 14))).xor(HexToBigEndian(w.get(i - 16)))).shiftLeft(1));
                t = "";
                for (int j = 0; j < 8 - temp.length(); j++) {
                    t += "0";
                }
                t += temp;
                temp = t;
                //temp = temp.substring(temp.length() - 8);
                w.add(i, temp);
            }
            /*for (int i = 0; i < 80; i++) {
                System.out.println("i= " + i + "\t w[i].length()=" + w.get(i).length() + "\t w[i]=" + w.get(i));
            }*/
            for (int i = 0; i <= 79; i++) {
                String k = "";
                String f = "";
                if (i <= 19) {
                    k = "5A827999";
                    f = BigEndianToHex((HexToBigEndian(b).and(HexToBigEndian(c))).or((HexToBigEndian(b).not()).and(HexToBigEndian(d))));
                } else if (i <= 39) {
                    f = BigEndianToHex(HexToBigEndian(b).xor(HexToBigEndian(c).xor(HexToBigEndian(d))));
                    k = "6ED9EBA1";
                } else if (i <= 59) {
                    k = "8F1BBCDC";
                    f = BigEndianToHex((HexToBigEndian(b).and(HexToBigEndian(c))).or(HexToBigEndian(b).and(HexToBigEndian(d))).or(HexToBigEndian(c).and(HexToBigEndian(d))));
                } else {
                    f = BigEndianToHex(HexToBigEndian(b).xor(HexToBigEndian(c).xor(HexToBigEndian(d))));
                    k = "CA62C1D6";
                }
                String temp = BigEndianToHex((HexToBigEndian(a).shiftLeft(5)));
                temp = To8Chars(temp);
                temp = temp.substring(temp.length() - 8);
                temp = BigEndianToHex(HexToBigEndian(temp).add(HexToBigEndian(f)));
                temp = To8Chars(temp);
                temp = temp.substring(temp.length() - 8);
                temp = BigEndianToHex(HexToBigEndian(temp).add(HexToBigEndian(e)));
                temp = To8Chars(temp);
                temp = temp.substring(temp.length() - 8);
                temp = To8Chars(temp);
                temp = BigEndianToHex(HexToBigEndian(temp).add(HexToBigEndian(k)));
                temp = To8Chars(temp);
                temp = temp.substring(temp.length() - 8);
                temp = BigEndianToHex(HexToBigEndian(temp).add(HexToBigEndian(w.get(i))));
                temp = To8Chars(temp);
                temp = temp.substring(temp.length() - 8);

                e = d;
                d = c;
                c = BigEndianToHex(HexToBigEndian(b).shiftLeft(30));
                b = a;
                a = temp;
                a = a.substring(a.length() - 8);
                b = b.substring(b.length() - 8);
                c = c.substring(c.length() - 8);
                d = d.substring(d.length() - 8);
                e = e.substring(e.length() - 8);
                h0 = BigEndianToHex(HexToBigEndian(h0).add(HexToBigEndian(a)));
                h1 = BigEndianToHex(HexToBigEndian(h1).add(HexToBigEndian(b)));
                h2 = BigEndianToHex(HexToBigEndian(h2).add(HexToBigEndian(c)));
                h3 = BigEndianToHex(HexToBigEndian(h3).add(HexToBigEndian(d)));
                h4 = BigEndianToHex(HexToBigEndian(h4).add(HexToBigEndian(e)));
                h0 = h0.substring(h0.length() - 8);
                h1 = h1.substring(h1.length() - 8);
                h2 = h2.substring(h2.length() - 8);
                h3 = h3.substring(h3.length() - 8);
                h4 = h4.substring(h4.length() - 8);
                //System.out.println("i= " + i + ":\t a= " + a + "\tb= " + b + "\tc= " + c + "\td= " + d + "\te= " + e);
            }
        }
        hash = h0 + h1 + h2 + h3 + h4;
        return h0 + h1 + h2 + h3 + h4;
    }

    public BigInteger HexToBigEndian(String vector) {
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


    public String LittleEndianToHex(BigInteger bigInteger) {
        String res = "";
        if (bigInteger.equals(BigInteger.ZERO)) return "0";
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

    public String BigEndianToHex(BigInteger bigInteger) {
        return LittleEndianToHex(bigInteger);
    }

    public String ReverseString(String string) {
        String res = "";
        for (int i = 0; i < string.length(); i++) {
            res += string.charAt(string.length() - 1 - i);
        }
        return res;
    }

    public String getHash() {
        return hash;
    }
}


