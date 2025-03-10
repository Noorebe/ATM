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

public class Wplata extends JFrame {
    
    private Log logowanie;
    private String bogactwo;
    private String nrKarty;
    
    public Wplata(Log logowanie, String bogactwo, String nrKarty) {
        this.logowanie = logowanie;
        this.bogactwo = bogactwo;
        this.nrKarty = nrKarty;
 
        initialize();
    }

    private void initialize() {
        getContentPane().setBackground(new Color(0, 175, 185));
        setTitle("Wpłaty");
        setBounds(100, 100, 600, 380);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Wybierz kwotę wypłaty");
        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel.setBounds(97, 23, 309, 26);
        getContentPane().add(lblNewLabel);
        
        JButton btnNewButton = new JButton("50");
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton.setBounds(124, 75, 130, 43);
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int stanKont = Integer.parseInt(bogactwo);
	        		JOptionPane.showMessageDialog(null, "Wpłacono 50 zł!");
	        		stanKont += 50;
	        		bogactwo = Integer.toString(stanKont);
	        		zapiszStanKonta(nrKarty, bogactwo);
	        		dispose();
	                OknoGlowne OknoGlowne = new OknoGlowne(logowanie, bogactwo, nrKarty);
	                OknoGlowne.setVisible(true);
        	}
        });
        getContentPane().add(btnNewButton);
        
        JButton btnNewButton1 = new JButton("100");
        btnNewButton1.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton1.setBounds(124, 145, 130, 43);
        btnNewButton1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int stanKont = Integer.parseInt(bogactwo);
	        		JOptionPane.showMessageDialog(null, "Wpłacono 100 zł!");
	        		stanKont += 100;
	        		bogactwo = Integer.toString(stanKont);
	        		zapiszStanKonta(nrKarty, bogactwo);
	        		dispose();
	                OknoGlowne OknoGlowne = new OknoGlowne(logowanie, bogactwo, nrKarty);
	                OknoGlowne.setVisible(true);
        	}
        });
        getContentPane().add(btnNewButton1);
        
        JButton btnNewButton2 = new JButton("200");
        btnNewButton2.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton2.setBounds(124, 208, 130, 43);
        btnNewButton2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int stanKont = Integer.parseInt(bogactwo);
	        		JOptionPane.showMessageDialog(null, "Wpłacono 200 zł!");
	        		stanKont += 200;
	        		bogactwo = Integer.toString(stanKont);
	        		zapiszStanKonta(nrKarty, bogactwo);
	        		dispose();
	                OknoGlowne OknoGlowne = new OknoGlowne(logowanie, bogactwo, nrKarty);
	                OknoGlowne.setVisible(true);
        	}
        });
        getContentPane().add(btnNewButton2);
        
        JButton btnNewButton3 = new JButton("500");
        btnNewButton3.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton3.setBounds(334, 75, 130, 43);
        btnNewButton3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int stanKont = Integer.parseInt(bogactwo);
	        		JOptionPane.showMessageDialog(null, "Wpłacono 500 zł!");
	        		stanKont += 500;
	        		bogactwo = Integer.toString(stanKont);
	        		zapiszStanKonta(nrKarty, bogactwo);
	        		dispose();
	                OknoGlowne OknoGlowne = new OknoGlowne(logowanie, bogactwo, nrKarty);
	                OknoGlowne.setVisible(true);
        	}
        });
        getContentPane().add(btnNewButton3);
        
        JButton btnNewButton4 = new JButton("1000");
        btnNewButton4.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton4.setBounds(334, 145, 130, 43);
        btnNewButton4.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int stanKont = Integer.parseInt(bogactwo);
	        		JOptionPane.showMessageDialog(null, "Wpłacono 1000 zł!");
	        		stanKont += 1000;
	        		bogactwo = Integer.toString(stanKont);
	        		zapiszStanKonta(nrKarty, bogactwo);
	        		dispose();
	                OknoGlowne OknoGlowne = new OknoGlowne(logowanie, bogactwo, nrKarty);
	                OknoGlowne.setVisible(true);
        	}
        });
        getContentPane().add(btnNewButton4);
        
        JButton btnNewButton5 = new JButton("Własna kwota");
        btnNewButton5.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnNewButton5.setBounds(334, 210, 130, 43);
        btnNewButton5.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		setVisible(false);
                WlasnaWplata okno = new WlasnaWplata(logowanie, bogactwo, nrKarty);
                okno.setVisible(true);
        	}
        });
        getContentPane().add(btnNewButton5);
    
       
        JButton btnWpataBlik = new JButton("Wpłata Blik");
        btnWpataBlik.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Blik blikInstance = new Blik(); // Tworzymy instancję Blik
                int kodBlik = Integer.parseInt(blikInstance.getCurrentCode());
                if (kodBlik != 0) {
                dispose();
					WplataBlik WPblk = new WplataBlik(bogactwo, nrKarty, kodBlik, logowanie);
					blikInstance.setVisible(true);
					WPblk.setVisible(true);
                }
            }
        });
        btnWpataBlik.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnWpataBlik.setBounds(218, 274, 157, 43);
        getContentPane().add(btnWpataBlik);
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
                    writer.writeUTF("HISTORIA: Wpłata " + nowyStanKonta);
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


}
