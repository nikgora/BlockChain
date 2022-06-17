// RSA


import java.math.BigInteger;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        String message = "127934712348134982189431279243143123412340912349123049134091234230410912340947137";
        KeyGen();
        BigInteger encrypt = Encrypt(message,e,n);
        System.out.println(encrypt);
        String decrypt = Decrypt(encrypt,d,n);
        System.out.println(decrypt);

    }
    static BigInteger d;
    static BigInteger e;
    static BigInteger n;
    public static void KeyGen (){
        BigInteger p=new BigInteger(2000,new Random());
        BigInteger q=new BigInteger(2000,new Random());
        p=p.nextProbablePrime();
        q=q.nextProbablePrime();
        n=p.multiply(q);
        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        boolean w=true;
        for (var i = BigInteger.valueOf(3); i.compareTo(m) < 0 && w; i=i.add(BigInteger.TWO)) {
            if (i.gcd(m).equals(BigInteger.ONE)){
                w=false;
                e=i;
            }
        }
        BigInteger k = BigInteger.ZERO;
        do  {
            k=k.add(BigInteger.ONE);
            d = (k.multiply(m).add(BigInteger.ONE)).divide(e);
        }while (!(k.multiply(m).add(BigInteger.ONE)).mod(e).equals(BigInteger.ZERO));
    }

    public static BigInteger Encrypt(String message, BigInteger e, BigInteger n){
        BigInteger messageBI;
        messageBI=new BigInteger(message);
        return messageBI.modPow(e,n);
    }
    public static String Decrypt(BigInteger message, BigInteger d, BigInteger n){
        return message.modPow(d,n).toString();
    }
}