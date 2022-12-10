package client;

import javax.swing.*;
import java.awt.*;

public class QuadratoScacchiera extends JButton {
    JPanel parent;
    private int riga;
    private int colonna;
    private String colorePezzo;
    private String tipoPezzo;

    public QuadratoScacchiera(JPanel parent, Color backgColor, String colorePezzo, String tipoPezzo) {
        super();
        this.parent = parent;
        this.setSize();
        this.setBackground(backgColor);
        setColorePezzo(colorePezzo);
        setTipoPezzo(tipoPezzo);
        setIcona();
    }

    private void setSize() {
        Dimension dim = this.parent.getSize();
        dim.width = (int)dim.getWidth()/8;
        dim.height = (int)dim.getHeight()/8;
        this.setSize(dim);
    }
    public QuadratoScacchiera(String colorePezzo, String tipoPezzo) {
        super();
        this.setBackground(Color.white);
        this.setSize(50, 50);
        setColorePezzo(colorePezzo);
        setTipoPezzo(tipoPezzo);
        setIcona();
        this.setEnabled(false);
    }

    //POSIZIONE
    public int getRiga() {
        return riga;
    }
    public void setRiga(int riga) {
        this.riga = riga;
    }
    public int getColonna() {
        return colonna;
    }
    public void setColonna(int colonna) {
        this.colonna = colonna;
    }
    public void setPosition(int riga, int colonna) {
        setRiga(riga);
        setColonna(colonna);
    }

    //PEZZO

    public String getColorePezzo() {
        return colorePezzo;
    }
    public void setColorePezzo(String colorePezzo) {
        this.colorePezzo = colorePezzo;
    }
    public String getTipoPezzo() {
        return tipoPezzo;
    }
    public void setTipoPezzo(String tipoPezzo) {
        this.tipoPezzo = tipoPezzo;
    }
    public void setIcona() {
        if(tipoPezzo != null) {
            ImageIcon icon;
            if (colorePezzo.equals("nero")) {
                icon = new ImageIcon("./res/pezzi_neri/" + tipoPezzo + ".png");
            } else {
                icon = new ImageIcon("./res/pezzi_bianchi/" + tipoPezzo + ".png");
            }
            this.setIcon(new ImageIcon(icon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), 1)));
        }
        else {
            removeIcona();
        }
    }
    private void removeIcona() {
        this.setIcon(null);
    }

    @Override
    public String toString() {
        return "QuadratoScacchiera{" +
                "riga=" + riga +
                ", colonna=" + colonna +
                ", colorePezzo='" + colorePezzo + '\'' +
                ", tipoPezzo='" + tipoPezzo + '\'' +
                '}';
    }
}
