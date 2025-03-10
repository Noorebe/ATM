package deafult;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Blik extends JFrame {

    private String currentCode; // Przechowuje wygenerowany kod

    public Blik() {
        initialize();
    }

    private void initialize() {
        getContentPane().setBackground(new Color(0, 175, 185));
        setTitle("Wygenerowany kod BLIK");
        setBounds(800, 100, 600, 380);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lblWygenerowanyKodBlik = new JLabel("Wygenerowany kod BLIK:");
        lblWygenerowanyKodBlik.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblWygenerowanyKodBlik.setBounds(64, 128, 204, 30);
        getContentPane().add(lblWygenerowanyKodBlik);

        JLabel labelKodBlik = new JLabel();
        labelKodBlik.setFont(new Font("Tahoma", Font.BOLD, 15));
        labelKodBlik.setBounds(293, 128, 150, 30);
        currentCode = generateRandomCode(); // Ustaw i zapisz losowy kod BLIK
        labelKodBlik.setText(currentCode);
        getContentPane().add(labelKodBlik);

        JButton anulujButton = new JButton("Zamknij");
        anulujButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        anulujButton.setBounds(210, 240, 157, 43);
        anulujButton.addActionListener(e -> dispose());
        getContentPane().add(anulujButton);
    }

    private String generateRandomCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Generuje liczbę z zakresu 100000-999999
        return String.valueOf(code);
    }

    public String getCurrentCode() {
        return currentCode;
    }
}
