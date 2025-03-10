package deafult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WlasnaWyplata extends JFrame {

    private Wyplata parent;
    private Log logowanie;
    private String bogactwo;
    private String nrKarty;

    public WlasnaWyplata(Log logowanie, String bogactwo, String nrKarty) {
    	this.logowanie = logowanie;
    	this.bogactwo = bogactwo;
    	this.nrKarty = nrKarty;
        this.parent = parent; 
        initialize();
    }

    private void initialize() {

        setTitle("Własna kwota");
        setBounds(100, 100, 600, 380);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(0, 175, 185));

        
        JLabel label = new JLabel("Wprowadź kwotę:");
        label.setFont(new Font("Tahoma", Font.BOLD, 15));
        label.setBounds(148, 99, 150, 30);
        getContentPane().add(label);

        
        JTextField textField = new JTextField();
        textField.setBounds(308, 102, 150, 30);
        getContentPane().add(textField);

       
        JButton wplacButton = new JButton("Wypłać");
        wplacButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        wplacButton.setBounds(122, 221, 157, 43);
        wplacButton.addActionListener(e -> {
            try {
                String kwotaText = textField.getText();
                int kwota = Integer.parseInt(kwotaText);

                if (kwota <= 0) {
                    JOptionPane.showMessageDialog(this, "Wprowadź kwotę większą od 0!", "Błąd", JOptionPane.ERROR_MESSAGE);
                } else {

            		int stanKont = Integer.parseInt(bogactwo);
            		stanKont -= kwota;
            		if (stanKont < 0) {
            			 JOptionPane.showMessageDialog(this, "Brak wystarczających środków na koncie!", "Błąd", JOptionPane.ERROR_MESSAGE);	
            		}
            		else {

                        JOptionPane.showMessageDialog(this, "Wypłacono " + kwota + " zł!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            			bogactwo = Integer.toString(stanKont);
                		zapiszStanKonta(nrKarty, bogactwo);
                    	dispose();
                    	OknoGlowne OknoGlowne = new OknoGlowne(logowanie, bogactwo, nrKarty);
                    	OknoGlowne.setVisible(true);
            		}
                    
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Wprowadź poprawną liczbę!", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });
        getContentPane().add(wplacButton);

        JButton anulujButton = new JButton("Anuluj");
        anulujButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        anulujButton.setBounds(231, 150, 157, 43);
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
                int nrKartyLength = reader.readInt();  // Odczyt długości numeru karty
                byte[] nrKartyBytes = new byte[nrKartyLength];
                reader.read(nrKartyBytes);  // Odczyt numeru karty
                String cardNumber = new String(nrKartyBytes, "UTF-8");

                // Jeśli numer karty się zgadza, zapisujemy jej dane w odpowiedniej kolejności
                if (cardNumber.equals(nrKarty)) {
                    kartaZnaleziono = true;

                    // Zapisujemy numer karty, PIN oraz nowy stan konta w poprawnej kolejności
                    byte[] nrKartyBytesNew = nrKarty.getBytes("UTF-8");
                    writer.writeInt(nrKartyBytesNew.length);  // Długość numeru karty
                    writer.write(nrKartyBytesNew);  // Numer karty

                    // Odczytujemy PIN
                    int pinLength = reader.readInt();
                    byte[] pinBytes = new byte[pinLength];
                    reader.read(pinBytes);
                    writer.writeInt(pinBytes.length);  // Zapisujemy długość PIN-u
                    writer.write(pinBytes);  // Zapisujemy PIN

                    // Zapisujemy nowy stan konta
                    byte[] newStanKontaBytes = nowyStanKonta.getBytes("UTF-8");
                    writer.writeInt(newStanKontaBytes.length);  // Długość stanu konta
                    writer.write(newStanKontaBytes);  // Nowe saldo

                    // Zapisujemy historię transakcji
                    writer.writeUTF("HISTORIA: Wypłata " + nowyStanKonta);
                } else {
                    // Jeśli numer karty się nie zgadza, kopiujemy resztę danych
                    writer.writeInt(nrKartyLength);  // Długość numeru karty
                    writer.write(nrKartyBytes);  // Numer karty

                    // Kopiujemy PIN
                    int pinLength = reader.readInt();
                    byte[] pinBytes = new byte[pinLength];
                    reader.read(pinBytes);
                    writer.writeInt(pinBytes.length);  // Długość PIN-u
                    writer.write(pinBytes);  // PIN

                    // Kopiujemy stan konta
                    int stanKontaLength = reader.readInt();
                    byte[] stanKontaBytes = new byte[stanKontaLength];
                    reader.read(stanKontaBytes);
                    writer.writeInt(stanKontaBytes.length);  // Długość stanu konta
                    writer.write(stanKontaBytes);  // Stan konta

                    // Kopiujemy historię
                    String linia;
                    while ((linia = reader.readLine()) != null && linia.startsWith("HISTORIA:")) {
                        writer.writeUTF(linia);  // Historia
                    }
                }
            }

            // Jeśli numer karty nie został znaleziony, zapisujemy ją na nowo
            if (!kartaZnaleziono) {
                byte[] nrKartyBytesNew = nrKarty.getBytes("UTF-8");
                writer.writeInt(nrKartyBytesNew.length);  // Długość numeru karty
                writer.write(nrKartyBytesNew);  // Numer karty

                writer.writeInt(0000);  // Zapisujemy domyślny PIN

                byte[] newStanKontaBytes = nowyStanKonta.getBytes("UTF-8");
                writer.writeInt(newStanKontaBytes.length);  // Długość stanu konta
                writer.write(newStanKontaBytes);  // Stan konta

                writer.writeUTF("HISTORIA: Wypłata " + nowyStanKonta);  // Historia transakcji
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Błąd podczas zapisywania stanu konta: " + e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        // Po zapisaniu pliku, zastępujemy stary plik nowym
        if (plik.delete() && tempPlik.renameTo(plik)) {
            System.out.println("Plik zaktualizowany pomyślnie.");
        } else {
            JOptionPane.showMessageDialog(this, "Nie udało się zaktualizować pliku konta.", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }


}
