public class Main {

    public static void main(String[] args) {
        KeyPair test = new KeyPair();
        Signature signature = new Signature();
        signature.signData("123123", test.getPrivateKey());
        signature.printSignature();
    }
}
