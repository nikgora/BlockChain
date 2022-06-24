import java.math.BigInteger;
import java.util.AbstractMap;

public class Signature {
    private BigInteger signature;

    public BigInteger signData(String message, AbstractMap.SimpleEntry<BigInteger, BigInteger> privateKey) {
        BigInteger messageBI;
        messageBI = new BigInteger(message);
        signature = messageBI.modPow(privateKey.getKey(), privateKey.getValue());
        return signature;
    }

    public void printSignature() {
        System.out.println(signature);
    }

    public boolean verifySignature(BigInteger signature, String message, AbstractMap.SimpleEntry<BigInteger, BigInteger> privateKey) {
        return signature.equals(signData(message, privateKey));
    }
}
