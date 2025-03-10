package deafult;

import java.awt.EventQueue;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;

public class Log extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField login;
    private JPasswordField haslo;
    private List<LoginResult> kontaList = new ArrayList<>(); // Kolekcja obiektów LoginResult

    // Main method to launch the application
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Log frame = new Log();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Constructor for setting up the frame
    public Log() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 380);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 175, 185));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Login button action
        JButton zaloguj = new JButton("Zaloguj");
        zaloguj.setFont(new Font("Tahoma", Font.BOLD, 15));
        zaloguj.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nrKarty = login.getText();
                String pin = new String(haslo.getPassword());

                LoginResult wynikLogowania = czyPoprawneLogowanie(nrKarty, pin);

                if (wynikLogowania != null) {
                    setVisible(false);
                    OknoGlowne okno = new OknoGlowne(Log.this, wynikLogowania.getStanKonta(), wynikLogowania.getNrKarty());
                    okno.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Niepoprawny numer karty lub PIN!", "Błąd logowania", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        zaloguj.setBounds(225, 206, 150, 51);
        contentPane.add(zaloguj);

        // Login input field
        login = new JTextField();
        login.setBounds(272, 65, 96, 19);
        contentPane.add(login);
        login.setColumns(10);

        // Labels for input fields
        JLabel logintxt = new JLabel("Nr. Karty:");
        logintxt.setHorizontalAlignment(SwingConstants.RIGHT);
        logintxt.setFont(new Font("Tahoma", Font.BOLD, 18));
        logintxt.setBounds(166, 61, 96, 21);
        contentPane.add(logintxt);

        JLabel haslotxt = new JLabel("Pin:");
        haslotxt.setHorizontalAlignment(SwingConstants.RIGHT);
        haslotxt.setFont(new Font("Tahoma", Font.BOLD, 18));
        haslotxt.setBounds(187, 120, 65, 16);
        contentPane.add(haslotxt);

        // Password input field
        haslo = new JPasswordField();
        haslo.setBounds(272, 122, 96, 19);
        contentPane.add(haslo);
    }

    public LoginResult czyPoprawneLogowanie(String nrKarty, String pin) {
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream("Konta.dat"))) {
            while (dataInputStream.available() > 0) {
                int nrKartyLength = dataInputStream.readInt(); // length of the string
                byte[] nrKartyBytes = new byte[nrKartyLength];
                dataInputStream.read(nrKartyBytes);
                String cardNumber = new String(nrKartyBytes, "UTF-8");

                if (cardNumber.equals(nrKarty)) {
                    int pinLength = dataInputStream.readInt(); // length of the PIN
                    byte[] pinBytes = new byte[pinLength];
                    dataInputStream.read(pinBytes);
                    String enteredPin = new String(pinBytes, "UTF-8");

                    if (enteredPin.equals(pin)) {
                        int stanKontaLength = dataInputStream.readInt(); // length of the account balance
                        byte[] stanKontaBytes = new byte[stanKontaLength];
                        dataInputStream.read(stanKontaBytes);
                        String accountBalance = new String(stanKontaBytes, "UTF-8");

                        return new LoginResult(nrKarty, pin, accountBalance);
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Błąd podczas odczytu pliku!", "Błąd", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return null; // Return null if no match is found
    }

    // Class representing a login result (card number, PIN, and account balance)
    public static class LoginResult {
        private String nrKarty;
        private String pin;
        private String stanKonta;

        public LoginResult(String nrKarty, String pin, String stanKonta) {
            this.nrKarty = nrKarty;
            this.pin = pin;
            this.stanKonta = stanKonta;
        }

        public String getNrKarty() {
            return nrKarty;
        }

        public String getPin() {
            return pin;
        }

        public String getStanKonta() {
            return stanKonta;
        }
    }
}
