import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Blockchain {
    private HashMap<Account, Integer> coinDatabase;
    private ArrayList<Block> blockHistory;
    private ArrayList<Transaction> txDatabase;
    private int faucetCoins = 1000000000;

    public Blockchain() {
        Block block = new Block(new ArrayList<Transaction>(), "");
        blockHistory.add(block);
    }

    public void validateBlock(Block block) {
        if (!Objects.equals(block.getPrevHash(), blockHistory.get(blockHistory.size() - 2).getPrevHash())) return;
        for (int i = 0; i < txDatabase.size(); i++) {
            for (int j = 0; j < block.getSetOfTransaction().size(); j++) {
                if (block.getSetOfTransaction().get(j) == txDatabase.get(i)) return;
                for (int k = 0; k < block.getSetOfTransaction().get(j).getSetOfOperations().size(); k++) {
                    if (!block.getSetOfTransaction().get(j).getSetOfOperations().get(k).verifyOperation(block.getSetOfTransaction().get(j).getSetOfOperations().get(k)))
                        return;
                }
            }
        }
    }

    public void getTokenFromFaucet(Account account, int amount) {
        int balance = coinDatabase.get(account);
        coinDatabase.replace(account, balance + amount);
        faucetCoins = faucetCoins - amount;
        account.updateBalance(balance + amount);
    }

    public void showCoinDatabase() {
        for (Account account :
                coinDatabase.keySet()) {
            System.out.println(account + "   " + coinDatabase.get(account));
        }
    }
}
