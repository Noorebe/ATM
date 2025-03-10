package deafult;

import javax.swing.*;
import java.awt.*;

public abstract class OknoBazowe extends JFrame {
    protected Log logowanie;
    protected String bogactwo;
    protected String nrKarty;

    public OknoBazowe(Log logowanie, String bogactwo, String nrKarty) {
        this.logowanie = logowanie;
        this.bogactwo = bogactwo;
        this.nrKarty = nrKarty;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 380);
    }

    // Metoda abstrakcyjna, którą muszą zaimplementować podklasy
    public abstract void initialize();
}

