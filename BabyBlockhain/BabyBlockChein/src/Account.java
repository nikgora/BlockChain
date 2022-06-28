import java.math.BigInteger;
import java.util.ArrayList;

public class Account {
    private String accountId;
    private ArrayList<KeyPair> wallet;
    private int balance;

    public Account() {
        KeyPair firstKeyPair = new KeyPair();
        wallet.add(0, firstKeyPair);
    }

    public Account(String accountId, ArrayList<KeyPair> wallet, int balance) {
        this.accountId = accountId;
        this.wallet = wallet;
        this.balance = balance;
    }

    public Account genAccount() {
        return new Account();
    }

    public void addKeyPairToWallet(KeyPair keyPair) {
        wallet.add(keyPair);
    }

    public void updateBalance(int balance) {
        this.balance += balance;
    }

    public int getBalance() {
        return balance;
    }

    public BigInteger signData(String message, int index) {
        BigInteger messageBI;
        messageBI = new BigInteger(message);
        BigInteger signature = messageBI.modPow(wallet.get(index).getPrivateKey().getKey(), wallet.get(index).getPrivateKey().getValue());
        return signature;
    }

    public void printBalance() {
        System.out.println(balance);
    }

    public Operation createOperation(Account recipient, int amount, int index) {
        return new Operation(new Account(this.accountId, this.wallet, this.balance), recipient, amount, signData(accountId, index));
    }
}
