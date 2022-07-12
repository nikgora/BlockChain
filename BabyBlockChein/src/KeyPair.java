import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.Random;

public class KeyPair {
    public AbstractMap.SimpleEntry<BigInteger, BigInteger> publicKey;
    private AbstractMap.SimpleEntry<BigInteger, BigInteger> privateKey;

    public KeyPair() {
        BigInteger d;
        BigInteger e = BigInteger.ONE;
        BigInteger n;
        BigInteger p = new BigInteger(200, new Random());
        BigInteger q = new BigInteger(200, new Random());
        p = p.nextProbablePrime();
        q = q.nextProbablePrime();
        n = p.multiply(q);
        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        boolean w = true;
        for (var i = BigInteger.valueOf(3); i.compareTo(m) < 0 && w; i = i.add(BigInteger.TWO)) {
            if (i.gcd(m).equals(BigInteger.ONE)) {
                w = false;
                e = i;
            }
        }
        BigInteger k = BigInteger.ZERO;
        do {
            k = k.add(BigInteger.ONE);
            d = (k.multiply(m).add(BigInteger.ONE)).divide(e);
        } while (!(k.multiply(m).add(BigInteger.ONE)).mod(e).equals(BigInteger.ZERO));
        privateKey = new AbstractMap.SimpleEntry<>(d, n);
        publicKey = new AbstractMap.SimpleEntry<>(e, n);
    }

    public AbstractMap.SimpleEntry<BigInteger, BigInteger> getPrivateKey() {
        return privateKey;
    }

    public void printKeyPair() {
        System.out.println("KeyPair {\n" +
                "privateKey=" + privateKey.getKey() + ",   " + privateKey.getValue() +
                "\npublicKey=" + publicKey.getKey() + ",  " + publicKey.getValue() +
                "\n}");
    }

    public KeyPair genKeyPar() {
        return new KeyPair();
    }
}
