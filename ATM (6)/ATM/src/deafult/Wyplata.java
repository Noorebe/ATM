package deafult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Wyplata extends JFrame {
    
    private Log logowanie;
    private String bogactwo;
    private String nrKarty;
    
    public Wyplata(Log logowanie, String bogactwo, String nrKarty) {
        this.logowanie = logowanie;
        this.bogactwo = bogactwo;
        this.nrKarty = nrKarty;
        initialize();
    }

    private void initialize() {
        getContentPane().setBackground(new Color(0, 175, 185));
        setTitle("Wypłata");
        setBounds(100, 100, 600, 380);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Wybierz kwotę wypłaty");
        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel.setBounds(97, 23, 309, 26);
        getContentPane().add(lblNewLabel);

        addButton("50", 120, 75, 50);
        addButton("100", 120, 145, 100);
        addButton("200", 120, 208, 200);
        addButton("500", 334, 75, 500);
        addButton("1000", 334, 145, 1000);
        
        JButton btnNewButton5 = new JButton("Własna kwota");
        btnNewButton5.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnNewButton5.setBounds(334, 210, 130, 43);
        btnNewButton5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                WlasnaWyplata okno = new WlasnaWyplata(logowanie, bogactwo, nrKarty);
                okno.setVisible(true);
            }
        });
        getContentPane().add(btnNewButton5);

        JButton btnWpataBlik = new JButton("Wypłata Blik");
        btnWpataBlik.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Blik blikInstance = new Blik();
                int kodBlik = Integer.parseInt(blikInstance.getCurrentCode());
                if (kodBlik != 0) {
                    dispose();
                    WyplataBlik WPblk = new WyplataBlik(bogactwo, nrKarty, kodBlik, logowanie);
                    blikInstance.setVisible(true);
                    WPblk.setVisible(true);
                }
            }
        });
        btnWpataBlik.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnWpataBlik.setBounds(218, 275, 157, 43);
        getContentPane().add(btnWpataBlik);
    }

    private void addButton(String label, int x, int y, int amount) {
        JButton button = new JButton(label);
        button.setFont(new Font("Tahoma", Font.BOLD, 15));
        button.setBounds(x, y, 130, 43);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int stanKont = Integer.parseInt(bogactwo);
                if (stanKont >= amount) {
                    JOptionPane.showMessageDialog(null, "Wypłacono " + amount + " zł!");
                    stanKont -= amount;
                    bogactwo = Integer.toString(stanKont);
                    zapiszStanKonta(nrKarty, bogactwo);
                    dispose();
                    OknoGlowne OknoGlowne = new OknoGlowne(logowanie, bogactwo, nrKarty);
                    OknoGlowne.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Brak wystarczającej\n ilości środków!");
                    dispose();
                    OknoGlowne OknoGlowne = new OknoGlowne(logowanie, bogactwo, nrKarty);
                    OknoGlowne.setVisible(true);
                }
            }
        });
        getContentPane().add(button);
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

                    // Zapisujemy stan konta
                    byte[] newStanKontaBytes = nowyStanKonta.getBytes("UTF-8");
                    writer.writeInt(newStanKontaBytes.length);  // Długość stanu konta
                    writer.write(newStanKontaBytes);  // Stan konta

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

                writer.writeUTF("HISTORIA: Wpłata " + nowyStanKonta);  // Historia transakcji
            }

        } catch (IOException e) {
        
        }

        // Po zapisaniu pliku, zastępujemy stary plik nowym
        if (plik.delete() && tempPlik.renameTo(plik)) {
            System.out.println("Plik zaktualizowany pomyślnie.");
        } else {
           
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Log frame = new Log();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
