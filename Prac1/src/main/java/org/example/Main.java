package org.example;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        BigInteger numbersOfBits ,base = new BigInteger("2");
        Scanner in = new Scanner(System.in);
        var input = in.next();
        numbersOfBits = new BigInteger(input);
        varlogarithm =Math.log(numbersOfBits.intValue())/Math.log(2);
        if(logarithm==Math.round(logarithm)){

            BigInteger rand = MyRandom(numbersOfBits.intValue());
            System.out.println(MyPow(base, numbersOfBits));
            System.out.println(rand);
            System.out.print(MyTime(rand));
        }
        else {
            System.out.println("Incorrect input");
        }

    }
    public static BigInteger MyRandom(int numbersOfBits) {
        return new BigInteger(numbersOfBits, new Random());
    }

    public static BigInteger MyPow( BigInteger base,BigInteger numbersOfBits)
    {
        return base.pow(numbersOfBits.intValue());
    }

    public static long MyTime(BigInteger random) {
        long time = System.currentTimeMillis();
        BigInteger brutForce = new BigInteger("0");
        while (!brutForce.equals(random))
        {
            brutForce=brutForce.add(new BigInteger("1"));
        }
        return System.currentTimeMillis()-time;
    }

}
