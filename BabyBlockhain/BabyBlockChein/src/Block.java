import java.util.ArrayList;

public class Block {
    private ArrayList<Transaction> setOfTransaction;
    private String blockID;
    private String prevHash;

    public Block(ArrayList<Transaction> setOfTransaction, String prevHash) {
        this.setOfTransaction = setOfTransaction;
        this.prevHash = prevHash;
        Hash hash = new Hash("41233123");
        blockID = hash.toSHA1(setOfTransaction.toString());
    }

    public String getPrevHash() {
        return prevHash;
    }

    public ArrayList<Transaction> getSetOfTransaction() {
        return setOfTransaction;
    }
}
