package deafult;

import javax.swing.*;
import java.awt.*;

public class OknoGlowne extends OknoBazowe { //Dziedziczenie

    public OknoGlowne(Log logowanie, String bogactwo, String nrKarty) {
        super(logowanie, bogactwo, nrKarty);
        initialize();
    }

    @Override
    public void initialize() {
        getContentPane().setBackground(new Color(0, 175, 185));
        getContentPane().setLayout(null);

        JButton wplaty = new JButton("Wpłaty");
        wplaty.addActionListener(e -> {
            setVisible(false);
            Wplata okno = new Wplata(logowanie, bogactwo, nrKarty);
            okno.setVisible(true);
        });
        wplaty.setFont(new Font("Tahoma", Font.BOLD, 15));
        wplaty.setBounds(36, 167, 130, 43);
        getContentPane().add(wplaty);

        JButton wyplaty = new JButton("Wypłaty");
        wyplaty.addActionListener(e -> {
            setVisible(false);
            Wyplata okno = new Wyplata(logowanie, bogactwo, nrKarty);
            okno.setVisible(true);
        });
        wyplaty.setFont(new Font("Tahoma", Font.BOLD, 15));
        wyplaty.setBounds(230, 167, 130, 43);
        getContentPane().add(wyplaty);

        JButton wyloguj = new JButton("Wyloguj");
        wyloguj.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Uwaga! \nWylogowano z konta!");
            dispose();
            Log login = new Log();
            login.setVisible(true);
        });
        wyloguj.setFont(new Font("Tahoma", Font.BOLD, 15));
        wyloguj.setBounds(422, 167, 130, 43);
        getContentPane().add(wyloguj);

        JLabel stanKontatxt = new JLabel(bogactwo);
        stanKontatxt.setFont(new Font("Tahoma", Font.BOLD, 18));
        stanKontatxt.setBounds(346, 96, 80, 26);
        getContentPane().add(stanKontatxt);

        JLabel lblNewLabel = new JLabel("Stan konta: ");
        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel.setBounds(216, 96, 130, 26);
        getContentPane().add(lblNewLabel);
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
