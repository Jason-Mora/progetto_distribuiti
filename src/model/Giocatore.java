package model;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class Giocatore  implements Serializable {

    public static String GIOCATORE_NERO = "nero";
    public static String GIOCATORE_BIANCO = "bianco";
    private String ip;
    private int port;
    private int colore;

    //COSTRUTTORE
    public Giocatore()
    {
        this(null, 0, false);
    }
    public Giocatore(String ip, int port, Boolean isWhite) {
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
    public String getFullIp() {
        if(isGiocatoreNull())
            return null;
        return getIp()+":"+getPort();
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
    public void setCasualPort() throws IOException {
        ServerSocket ss = new ServerSocket(0);
        this.port = ss.getLocalPort();
        ss.close();
    }

    //COLORE
    public String getColore() {
        if(this.colore == 0)
            return GIOCATORE_NERO;
        else
            return GIOCATORE_BIANCO;
    }
    public void setColore(boolean isWhite) {
        if(isWhite)
            setColore(1);
        else
            setColore(0);
    }
    public void setColore(String colore) {
        if(colore.equals(GIOCATORE_NERO))
            this.colore = 0;
        else
            this.colore = 1;
    }
    public void setColore(int colore)
    {
        this.colore = colore;
    }
    public boolean isWhite()
    {
        return this.colore!=0;
    }

    //ALTRO
    public boolean isGiocatoreNull()
    {
        return getIp()==null;
    }
    public Socket getSocket() throws IOException {
        return new Socket(getIp(), getPort());
    }

    //OBJECT OVERRIDE
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
