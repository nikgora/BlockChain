import java.math.BigInteger;
import java.util.AbstractMap;

public class Signature {

    public BigInteger signData(String message, AbstractMap.SimpleEntry<BigInteger, BigInteger> privateKey) {
        BigInteger messageBI;
        messageBI = new BigInteger(message);
        return messageBI.modPow(privateKey.getKey(), privateKey.getValue());
    }

    public boolean verifySignature(BigInteger signature, String message, AbstractMap.SimpleEntry<BigInteger, BigInteger> privateKey) {
        return signature.equals(signData(message, privateKey));
    }
}
