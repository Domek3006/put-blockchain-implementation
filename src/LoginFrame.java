import com.github.cliftonlabs.json_simple.Jsoner;
import netscape.javascript.JSObject;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class LoginFrame extends JFrame implements ActionListener, ListSelectionListener {

    private final Container container = getContentPane();
    private final JLabel userLabel = new JLabel("Username");
    private final JLabel passwdLabel = new JLabel("Password");
    private final JTextField userTextField = new JTextField();
    private final JPasswordField passwdField = new JPasswordField();
    private final JButton loginButton = new JButton("Login");

    private final JLabel chainsLabel = new JLabel("Available chains");
    private JList<String> chainList = new JList<>();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JButton addButton = new JButton("New");
    private JTextField newText = new JTextField();
    private ArrayList<Blockchain> chains;

    private JButton mineButton = new JButton("Add block");
    private JTextField recipientText = new JTextField();
    private JTextField senderText = new JTextField();
    private JTextField amountText = new JTextField();
    private JList<String> blockList = new JList<>();
    private DefaultListModel<String> blockModel = new DefaultListModel<>();
    private JLabel blocksLabel = new JLabel("Available blocks");
    private JLabel senderLabel = new JLabel("");
    private JLabel recipientLabel = new JLabel("");
    private JLabel amountLabel = new JLabel("");
    private JLabel senderTag = new JLabel("Sender");
    private JLabel rcvTag = new JLabel("Recipient");
    private JLabel amountTag = new JLabel("Amount");
    private boolean firstChains = true;
    private boolean firstBlocks = true;

    private Blockchain currentChain;
    private Block currentBlock;
    public LoginFrame() {
        container.setLayout(null);
        initializeLogin();
        //addUserButton.addActionListener(this);
        //backButton.addActionListener(this);
        loginButton.addActionListener(this);
        //regButton.addActionListener(this);
    }

    public void removeInfo() {
        container.remove(senderLabel);
        container.remove(recipientLabel);
        container.remove(amountLabel);
    }

    public void initializeChains() {
        chains = new ArrayList<>();
        chains.add(new Blockchain(100, "new"));
        listModel.clear();
        for(Blockchain b : chains) {
            listModel.addElement(b.getName());
        }
        chainList.setModel(listModel);
        chainsLabel.setBounds(50,150,100,30);
        chainList.setBounds(150,150,150,200);
        newText.setBounds(50,500,100,30);
        addButton.setBounds(200,500,100,30);
        chainList.addListSelectionListener(this);
        blockList.addListSelectionListener(this);
        chainList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        blockList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        if(firstChains) {
            container.add(chainList);
            container.add(chainsLabel);
            container.add(newText);
            container.add(addButton);
            addButton.addActionListener(this);
            firstChains = false;
        }
        this.setBounds(10,10,1000,1000);
        container.revalidate();
        container.repaint();
    }
    public void initializeLogin(){
        userLabel.setBounds(50,150,100,30);
        passwdLabel.setBounds(50,220,100,30);
        userTextField.setBounds(150,150,150,30);
        passwdField.setBounds(150,220,150,30);
        loginButton.setBounds(50,300,150,30);
        //regButton.setBounds(200,300,100,30);
        container.add(userLabel);
        container.add(passwdLabel);
        container.add(userTextField);
        container.add(passwdField);
        container.add(loginButton);
        //container.add(regButton);
        //container.revalidate();
        //container.repaint();
    }

    public void initializeBlocks() {
        blockList.setModel(blockModel);
        blockList.setBounds(450,150,150,200);
        blocksLabel.setBounds(330,150,100,30);
        blockModel.clear();
        for(Block b : currentChain.getChain()) {
            blockModel.addElement(Long.toString(b.getTimestamp()));
        }
        if(firstBlocks) {
            container.add(blocksLabel);
            container.add(blockList);
            firstBlocks = false;
        }
        container.revalidate();
        container.repaint();
    }

    public void initializeInfo() {
        senderLabel.setBounds(650,150,300,30);
        recipientLabel.setBounds(650,200,300,30);
        amountLabel.setBounds(650,250,300,30);
        senderLabel.setText("Sender: " + currentBlock.getSenderId());
        recipientLabel.setText("Recipient: " + currentBlock.getReceiverId());
        amountLabel.setText("Amount: " + currentBlock.getAmount());
        container.add(senderLabel);
        container.add(recipientLabel);
        container.add(amountLabel);
        container.revalidate();
        container.repaint();
    }

    public void initializeMine() {
        mineButton.setBounds(650,750,100,30);
        senderText.setBounds(650,600,100,30);
        recipientText.setBounds(650,650,100,30);
        amountText.setBounds(650,700,100,30);
        senderTag.setBounds(550,600,100,30);
        rcvTag.setBounds(550,650,100,30);
        amountTag.setBounds(550,700,100,30);
        container.add(mineButton);
        container.add(senderText);
        container.add(senderTag);
        container.add(recipientText);
        container.add(rcvTag);
        container.add(amountText);
        container.add(amountTag);
        mineButton.addActionListener(this);
    }

    /*public void initializeRegister() {
        addUserButton.setBounds(50,350,100,30);
        backButton.setBounds(200,350,100,30);
        container.add(userLabel);
        container.add(passwdLabel);
        container.add(userTextField);
        container.add(passwdField);
        container.add(backButton);
        container.add(addUserButton);
        container.revalidate();
        container.repaint();
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginButton) {
            String userText;
            String passwdText;
            userText = userTextField.getText();
            passwdText = new String(passwdField.getPassword());
            if(userText.equals("Steve") && passwdText.equals("Harvey")) {
                container.removeAll();
                initializeChains();
                initializeMine();
            }
        }
        if(e.getSource() == addButton) {
            String newName = newText.getText();
            if(!newName.isBlank()) {
                Blockchain newChain = new Blockchain(100, newName);
                chains.add(newChain);
                listModel.addElement(newChain.getName());
            }
            newText.setText("");
        }
        if(e.getSource() == mineButton) {
            currentChain.addBlock(senderText.getText(), recipientText.getText(), Double.parseDouble(amountText.getText()));
            initializeBlocks();
        }

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == chainList) {
            currentChain = chains.get(chainList.getSelectedIndex());
            currentBlock = currentChain.getBlockByIndex(0);
            if(currentChain.isChainValid()) {
                System.out.println("Chain valid!");
            } else {
                System.out.println("Chain invalid!");
            }
            removeInfo();
            initializeBlocks();
        }
        if (e.getSource() == blockList) {
            if(currentChain.getBlockByIndex(blockList.getSelectedIndex()) != null){
                currentBlock = currentChain.getBlockByIndex(blockList.getSelectedIndex());
                initializeInfo();
            }
        }
    }
}
