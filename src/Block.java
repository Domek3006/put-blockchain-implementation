import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Block implements Serializable {
    private int index;
    private long timestamp;
    private int numOfTransactions;
    private String senderId;
    private String receiverId;
    private double amount;
    private String previousHash;
    private String hash;
    private int nonce = 0;

    // Constructor
    public Block(int index, String senderId, String receiverId, double amount, String previousHash) {
        this.index = index;
        this.timestamp = new Date().getTime();
        this.numOfTransactions = 1;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.previousHash = previousHash;
    }

    // Getters and Setters

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getNumOfTransactions() {
        return numOfTransactions;
    }

    public void setNumOfTransactions(int numOfTransactions) {
        this.numOfTransactions = numOfTransactions;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    // Calculate the hash of the block using SHA-256
    public String calculateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String dataToHash = index + timestamp + numOfTransactions + senderId + receiverId + amount + previousHash + nonce;
            byte[] hashBytes = digest.digest(dataToHash.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Increment the number of transactions in the block
    public void incrementNumOfTransactions() {
        numOfTransactions++;
    }

    public void mineBlock(int difficulty) {
        hash = StringUtils.leftPad("-", difficulty, "-");
        String target = StringUtils.leftPad("0", difficulty, "0");
        while(!hash.substring(0, difficulty).equals(target)) {
            ++nonce;
            hash = calculateHash();
            System.out.println(hash + " " + target);
        }
    }

}