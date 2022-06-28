import java.math.BigInteger;

public class Operation {
    private Account sender;
    private Account receiver;
    private int amount;
    private BigInteger signature;

    public Operation(Account sender, Account receiver, int amount, BigInteger signature) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.signature = signature;
    }

    public boolean verifyOperation(Operation operation) {
        if (operation.amount > sender.getBalance()) return false;
        return operation.signature.equals(this.signature);
    }
}
