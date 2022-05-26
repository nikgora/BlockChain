import java.math.BigInteger;
import java.util.Objects;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        System.out.println("print 1 you want to enter vector or 2 if you want enter Big endian numbers,or 3 if you want enter Little endian number");
        Scanner in = new Scanner(System.in);
        var vector = in.next();
        int n = Integer.parseInt(vector);


        switch (n) {
            case (1) -> {
                vector = in.next();
                vector = vector.replace("0x", "");
                var logarithm = Math.log(vector.length() >> 1) / Math.log(2);
                if (logarithm == Math.round(logarithm)) {
                    System.out.println("Value: " + vector);
                    if (!Objects.equals(HexToLittleEndian(vector), BigInteger.valueOf(-1)))
                        System.out.println("Number of bytes: " + vector.length() / 2);
                    if (!Objects.equals(HexToLittleEndian(vector), BigInteger.valueOf(-1)))
                        System.out.println("Little-endian: " + HexToLittleEndian(vector));
                    if (!Objects.equals(HexToBigEndian(vector), BigInteger.valueOf(-1)))
                        System.out.println("Big-endian: " + HexToBigEndian(vector));
                } else {
                    System.out.println("Incorrect input");
                }
            }
            case (2) -> {
                vector = in.next();
                System.out.println("Value: " + vector);
                System.out.println("Hex: 0x" + BigEndianToHex(new BigInteger(vector)));
            }
            case (3) -> {
                vector = in.next();
                System.out.println("Value: " + vector);
                System.out.println("Hex: 0x" + LittleEndianToHex(new BigInteger(vector)));

            }
            default -> {
                System.out.println("Incorrect input number");
            }
        }
    }

    public static BigInteger HexToBigEndian(String vector) {
        BigInteger result = BigInteger.ZERO;
        BigInteger base = BigInteger.valueOf(16);
        boolean q = false;
        for (int i = 0; i < vector.length() && !q; i++) {
            char symbol = vector.charAt(i);
            int symbolInInt = 0;
            q = false;
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