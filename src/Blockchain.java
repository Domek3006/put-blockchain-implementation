import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Blockchain implements Serializable {
    private List<Block> chain;
    private Map<String, Double> senderBalances;
    private String name;

    private final int difficulty = 2;

    // Constructor
    public Blockchain(double startingAmount, String name) {
        this.name = name;
        this.chain = new ArrayList<>();
        this.senderBalances = new HashMap<>();

        // Create the genesis block
        createGenesisBlock();

        // Initialize sender balances with 100 units for each person
        senderBalances.put("Dominik", startingAmount);
        senderBalances.put("Marcel", startingAmount);
        senderBalances.put("Artur", startingAmount);

        //senderBalances.put("Dominik", 100.0);
        //senderBalances.put("Marcel", 100.0);
        //senderBalances.put("Artur", 100.0);
    }

    // Create the genesis block (first block)
    private void createGenesisBlock() {
        Block genesisBlock = new Block(0, "Genesis Sender", "Genesis Receiver", 0.0, "0");
        this.chain.add(genesisBlock);
    }

    // Get the latest block in the blockchain
    private Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    // Add a new block to the blockchain
    public void addBlock(String senderId, String receiverId, double amount) {
        Block latestBlock = getLatestBlock();
        int newIndex = latestBlock.getIndex() + 1;
        String newPreviousHash = latestBlock.getHash();
        Block newBlock = new Block(newIndex, senderId, receiverId, amount, newPreviousHash);
        newBlock.mineBlock(difficulty);
        this.chain.add(newBlock);

        // Update sender balances
        //senderBalances.put(senderId, senderBalances.get(senderId) - amount);
        //senderBalances.put(receiverId, senderBalances.get(receiverId) + amount);
    }

    // Validate the integrity of the blockchain
    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            // Validate the hash of the current block
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }

            // Validate the previous hash
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }

        return true;
    }

    // Get the blockchain size
    public int getChainSize() {
        return chain.size();
    }

    // Get a specific block in the blockchain by its index
    public Block getBlockByIndex(int index) {
        if (index >= 0 && index < chain.size()) {
            return chain.get(index);
        }
        return null;
    }

    // Get the total currency balance for a sender
    public double getSenderBalance(String senderId) {
        return senderBalances.getOrDefault(senderId, 0.0);
    }

    public String getName() {
        return name;
    }
    public List<Block> getChain() {
        return chain;
    }
}