package client;

import model.PezzoScacchiera;

import javax.swing.*;
import java.awt.*;

public class QuadratoScacchiera extends JButton {
    JPanel parent;
    private int riga;
    private int colonna;
    private PezzoScacchiera pezzo;

    public QuadratoScacchiera(PlayPanel parent, Color backgColor, String colorePezzo, String tipoPezzo) {
        super();
        this.parent = parent;
        this.setSize();
        this.setBackground(backgColor);
        this.pezzo = new PezzoScacchiera(colorePezzo, tipoPezzo);
        setIcona();
    }
    public QuadratoScacchiera(PlayPanel parent, String colorePezzo, String tipoPezzo) {
        this(parent, Color.white, colorePezzo, tipoPezzo);
    }

    private void setSize() {
        Dimension dim = this.parent.getSize();
        dim.width = (int)dim.getWidth()/8;
        dim.height = (int)dim.getHeight()/8;
        this.setSize(dim);
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
        return pezzo.getColore();
    }
    public void setColorePezzo(String colorePezzo) {
        pezzo.setColore(colorePezzo);
    }
    public String getTipoPezzo() {
        return pezzo.getTipo();
    }
    public void setTipoPezzo(String tipoPezzo) {
        pezzo.setTipo(tipoPezzo);
    }

    //ICONA
    public void setIcona() {
        if(pezzo.getTipo() != null) {
            ImageIcon icon;
            if (pezzo.getColore().equals("nero")) {
                icon = new ImageIcon("./res/pezzi_neri/" + pezzo.getTipo() + ".png");
            } else {
                icon = new ImageIcon("./res/pezzi_bianchi/" + pezzo.getTipo() + ".png");
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

    //OBJECT OVERRIDE
    @Override
    public String toString() {
        return "QuadratoScacchiera{" +
                "riga=" + riga +
                ", colonna=" + colonna +
                ", colorePezzo='" + pezzo.getColore() + '\'' +
                ", tipoPezzo='" + pezzo.getTipo() + '\'' +
                '}';
    }
}
