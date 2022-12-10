package model;

import java.io.Serializable;
import java.util.Objects;

public class Giocatore  implements Serializable {

    public static String GIOCATORE_NERO = "nero";
    public static String GIOCATORE_BIANCO = "bianco";
    private String ip;
    private int port;
    private int colore;

    public Giocatore()
    {
        this(null, 0, false);
    }
    public Giocatore(String ip, int port, Boolean isWhite)
    {
        setIp(ip);
        setPort(port);
        setColore(isWhite);
    }

    //IP
    public String getIp()
    {
        return this.ip;
    }
    public void setIp(String ip)
    {
        this.ip = ip;
    }

    //PORT
    public int getPort()
    {
        return this.port;
    }
    public void setPort(int port)
    {
        this.port = port;
    }

    //COLOR
    public String getColore()
    {
        if(this.colore == 0)
            return GIOCATORE_NERO;
        else
            return GIOCATORE_BIANCO;
    }
    public boolean isWhite()
    {
        return this.colore!=0;
    }
    public void setColore(boolean isWhite)
    {
        if(isWhite)
            setColore(1);
        else
            setColore(0);
    }
    public void setColore(int colore)
    {
        this.colore = colore;
    }
    public void setColore(String colore)
    {
        if(colore.equals(GIOCATORE_NERO))
            this.colore = 0;
        else
            this.colore = 1;
    }

    public String getFullIp()
    {
        if(isGiocatoreNull())
            return null;
        return getIp()+":"+getPort();
    }

    public boolean isGiocatoreNull()
    {
        return getIp()==null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Giocatore giocatore = (Giocatore) o;
        return port == giocatore.port && Objects.equals(ip, giocatore.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }
}
