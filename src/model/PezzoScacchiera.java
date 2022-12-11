package model;

public class PezzoScacchiera {
    private String colore;
    private String tipo;

    //COSTRUTTORE
    public PezzoScacchiera(String colore, String tipo) {
        this.colore = colore;
        this.tipo = tipo;
    }
    public PezzoScacchiera() {
        this(null, null);
    }

    //COLORE
    public String getColore() {
        return colore;
    }
    public void setColore(String colore) {
        this.colore = colore;
    }

    //TIPO
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
