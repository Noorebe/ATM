package deafult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class WplataBlik extends JFrame {

    private String bogactwo;
    private String nrKarty;
    private int kodBlik;
    private Log logowanie;
    private JTextField textField;
    private JTextField textField_1;

    public WplataBlik(String bogactwo, String nrKarty, int kodBlik, Log logowanie) {
        this.bogactwo = bogactwo;
        this.nrKarty = nrKarty;
        this.kodBlik = kodBlik;
        this.logowanie = logowanie;
        initialize();
    }

    private void initialize() {
        getContentPane().setBackground(new Color(0, 175, 185));
        setTitle("Wpłata BLIK");
        setBounds(100, 100, 600, 380);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lblKwota = new JLabel("Kwota:");
        lblKwota.setHorizontalAlignment(SwingConstants.RIGHT);
        lblKwota.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblKwota.setBounds(185, 62, 76, 30);
        getContentPane().add(lblKwota);

        JLabel lblBlik = new JLabel("Kod BLIK:");
        lblBlik.setHorizontalAlignment(SwingConstants.RIGHT);
        lblBlik.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblBlik.setBounds(123, 128, 138, 30);
        getContentPane().add(lblBlik);

        textField = new JTextField();
        textField.setBounds(273, 62, 150, 30);
        getContentPane().add(textField);

        textField_1 = new JTextField();
        textField_1.setBounds(273, 128, 150, 30);
        getContentPane().add(textField_1);

        JButton potwierdzButton = new JButton("Potwierdź");
        potwierdzButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String enteredCode = textField_1.getText();
                int kod = Integer.parseInt(enteredCode);
                String amountText = textField.getText();
                int amount = Integer.parseInt(amountText);
                int currentBalance = Integer.parseInt(bogactwo);

                if (kod == kodBlik) {
                    if (amount >= 0) {
                        currentBalance += amount;
                        bogactwo = String.valueOf(currentBalance);
                        
                        // Zapisz nowy stan konta
                        zapiszStanKonta(nrKarty, bogactwo);
                        
                        JOptionPane.showMessageDialog(null, "Wpłacono " + amount + " zł!");

                        dispose();
                        OknoGlowne oknoGlowne = new OknoGlowne(logowanie, bogactwo, nrKarty);
                        oknoGlowne.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(WplataBlik.this, "Niepoprawna kwota!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(WplataBlik.this, "Niepoprawny kod BLIK.", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        potwierdzButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        potwierdzButton.setBounds(327, 214, 157, 43);
        getContentPane().add(potwierdzButton);

        JButton anulujButton = new JButton("Anuluj");
        anulujButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        anulujButton.setBounds(123, 214, 157, 43);
        anulujButton.addActionListener(e -> {
            dispose();
            OknoGlowne oknoGlowne = new OknoGlowne(logowanie, bogactwo, nrKarty);
            oknoGlowne.setVisible(true);
        });
        getContentPane().add(anulujButton);
    }
    
    private void zapiszStanKonta(String nrKarty, String nowyStanKonta) {
        File plik = new File("Konta.dat");
        File tempPlik = new File("temp_Konta.dat");

        try (DataInputStream reader = new DataInputStream(new FileInputStream(plik));
             DataOutputStream writer = new DataOutputStream(new FileOutputStream(tempPlik))) {

            boolean kartaZnaleziono = false;

            while (reader.available() > 0) {
                // Odczyt numeru karty
                int nrKartyLength = reader.readInt();
                byte[] nrKartyBytes = new byte[nrKartyLength];
                reader.read(nrKartyBytes);
                String cardNumber = new String(nrKartyBytes, "UTF-8");

                // Jeśli numer karty się zgadza
                if (cardNumber.equals(nrKarty)) {
                    kartaZnaleziono = true;

                    // Zapisz numer karty, PIN oraz nowy stan konta
                    byte[] nrKartyBytesNew = nrKarty.getBytes("UTF-8");
                    writer.writeInt(nrKartyBytesNew.length);
                    writer.write(nrKartyBytesNew);

                    // Odczytujemy PIN i zapisujemy go
                    int pinLength = reader.readInt();
                    byte[] pinBytes = new byte[pinLength];
                    reader.read(pinBytes);
                    writer.writeInt(pinBytes.length);
                    writer.write(pinBytes);

                    // Zapisz nowy stan konta
                    byte[] newStanKontaBytes = nowyStanKonta.getBytes("UTF-8");
                    writer.writeInt(newStanKontaBytes.length);
                    writer.write(newStanKontaBytes);

                    // Historia transakcji
                    writer.writeUTF("HISTORIA: Wpłata " + nowyStanKonta);
                } else {
                    // Kopiujemy dane, jeśli numer karty się nie zgadza
                    int nrKartyLength1 = reader.readInt();
                    byte[] nrKartyBytesBackup = new byte[nrKartyLength1];
                    reader.read(nrKartyBytesBackup);
                    writer.writeInt(nrKartyLength1);
                    writer.write(nrKartyBytesBackup);

                    int pinLength = reader.readInt();
                    byte[] pinBytesBackup = new byte[pinLength];
                    reader.read(pinBytesBackup);
                    writer.writeInt(pinLength);
                    writer.write(pinBytesBackup);

                    int stanKontaLength = reader.readInt();
                    byte[] stanKontaBytesBackup = new byte[stanKontaLength];
                    reader.read(stanKontaBytesBackup);
                    writer.writeInt(stanKontaBytesBackup.length);
                    writer.write(stanKontaBytesBackup);

                    // Zapisz historię
                    String linia;
                    while ((linia = reader.readLine()) != null && linia.startsWith("HISTORIA:")) {
                        writer.writeUTF(linia);
                    }
                }
            }

            if (!kartaZnaleziono) {
                byte[] nrKartyBytesNew = nrKarty.getBytes("UTF-8");
                writer.writeInt(nrKartyBytesNew.length);
                writer.write(nrKartyBytesNew);

                writer.writeInt(0000); // Domyślny PIN

                byte[] newStanKontaBytes = nowyStanKonta.getBytes("UTF-8");
                writer.writeInt(newStanKontaBytes.length);
                writer.write(newStanKontaBytes);

                writer.writeUTF("HISTORIA: Wpłata " + nowyStanKonta);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Błąd podczas zapisywania stanu konta: " + e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        // Zastępujemy stary plik nowym
        if (plik.delete() && tempPlik.renameTo(plik)) {
            System.out.println("Plik zaktualizowany pomyślnie.");
        } else {
            JOptionPane.showMessageDialog(this, "Nie udało się zaktualizować pliku konta.", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
}
