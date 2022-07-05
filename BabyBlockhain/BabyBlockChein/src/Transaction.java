import java.util.ArrayList;

public class Transaction {
    int nonce;
    private String transactionId;
    private ArrayList<Operation> setOfOperations;

    public Transaction(ArrayList<Operation> setOfOperations, int nonce) {
        this.nonce = nonce;
        this.setOfOperations = setOfOperations;
        Hash hash = new Hash(setOfOperations.toString());
        this.transactionId = hash.toSHA1(hash.getHash());
    }

    public Transaction createOperation(ArrayList<Operation> setOfOperations, int nonce) {
        return new Transaction(setOfOperations, nonce);
    }

}
