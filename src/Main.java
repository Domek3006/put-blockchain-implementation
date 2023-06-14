import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        LoginFrame frame = new LoginFrame();
        frame.setTitle("Blockchain");
        frame.setVisible(true);
        frame.setBounds(10,10,370,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
}