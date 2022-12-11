package client;

import model.Giocatore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayPanel extends JPanel implements ActionListener {
    String colore;
    MatchFrame parent;
    QuadratoScacchiera[][] scacchiera;
    boolean selezionaCella = false;
    int rigaCellaSelezionata = 0;
    int colonnaCellaSelezionata = 0;
    String pezzoCellaSelezionata = null;
    String move = null;
    boolean turno = false;

    public PlayPanel(MatchFrame parent) {
        super();
        this.colore = parent.getGiocatore().getColore();
        this.parent = parent;
        this.setSize();
        this.setLayout(new GridLayout(8, 8));
        scacchiera = inizializzaScacchiera();
    }

    private void setSize() {
        this.setSize(this.parent.getSize());
    }

    private QuadratoScacchiera[][] inizializzaScacchiera()
    {
        QuadratoScacchiera[][] scacchiera = new QuadratoScacchiera[8][8];
        boolean isWhite;
        String colorePezzo = null;
        for(int i=0;i<scacchiera.length;i++)
        {
            if(i==0 || i==1) {
                colorePezzo = "nero";
            }
            else if(i==7 || i==6) {
                colorePezzo = "bianco";
            }
            else {
                colorePezzo = null;
            }
            isWhite = i%2==0;
            for(int j=0;j<scacchiera[i].length;j++)
            {
                String tipoPezzo = null;
                if(colorePezzo != null)
                {
                    if(i==0 || i==7) {
                        if (j == 0 || j == 7) {
                            tipoPezzo = "torre";
                        }
                        else if (j == 1 || j == 6) {
                            tipoPezzo = "alfiere";
                        }
                        else if (j == 2 || j == 5) {
                            tipoPezzo = "cavallo";
                        }
                        else if (j == 3) {
                            tipoPezzo = "re";
                        }
                        else {
                            tipoPezzo = "regina";
                        }
                    }
                    else if(i==1 || i==6) {
                        tipoPezzo = "pedina";
                    }
                }
                QuadratoScacchiera b = new QuadratoScacchiera(this, isWhite?Color.WHITE:Color.BLACK, colorePezzo, tipoPezzo);
                b.addActionListener(this);
                b.setPosition(i, j);
                scacchiera[i][j] = b;
                this.add(scacchiera[i][j]);
                isWhite = !isWhite;
            }
        }
        return scacchiera;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(getTurno()) {
            this.move = null;
            QuadratoScacchiera qs = (QuadratoScacchiera) actionEvent.getSource();
            System.out.println(qs.toString());
            if (!selezionaCella && qs.getColorePezzo() != null && qs.getColorePezzo().equals(this.colore)) //ho selezionato un pezzo
            {
                rigaCellaSelezionata = qs.getRiga();
                colonnaCellaSelezionata = qs.getColonna();
                pezzoCellaSelezionata = qs.getTipoPezzo();
                selezionaCella = true;
            } else if (selezionaCella)//devo posizionare il pezzo
            {
                if (permittedMove(qs.getRiga(), qs.getColonna())) {
                    muoviPezzo(rigaCellaSelezionata, colonnaCellaSelezionata, qs.getRiga(), qs.getColonna());
                    this.move = rigaCellaSelezionata+" "+colonnaCellaSelezionata+" "+qs.getRiga()+" "+qs.getColonna();
                    rigaCellaSelezionata = -1;
                    colonnaCellaSelezionata = -1;
                    pezzoCellaSelezionata = null;
                }
                selezionaCella = false;
            } else {
                selezionaCella = false;
            }
        }
        else {
            System.out.print("Non è il tuo turno");
        }
    }

    private boolean permittedMove(int riga, int colonna)
    {
        switch (pezzoCellaSelezionata)
        {
            case "pedina":
                if(scacchiera[riga][colonna].getTipoPezzo()==null) {
                    if (colore.equals("nero"))
                        return (colonna == colonnaCellaSelezionata && rigaCellaSelezionata + 1 == riga);
                    else
                        return (colonna == colonnaCellaSelezionata && rigaCellaSelezionata - 1 == riga);
                }
                else {
                    if (colore.equals("nero"))
                        return ((colonnaCellaSelezionata==colonna+1 || colonnaCellaSelezionata==colonna-1) && rigaCellaSelezionata + 1 == riga);
                    else
                        return ((colonnaCellaSelezionata==colonna+1 || colonnaCellaSelezionata==colonna-1) && rigaCellaSelezionata - 1 == riga);
                }
            case "torre":
                int startIndex;
                int endIndex;
                if(colonna==colonnaCellaSelezionata && rigaCellaSelezionata!=riga) //mi sposto in colonna
                {
                    if (rigaCellaSelezionata < riga) {
                        startIndex = rigaCellaSelezionata + 1;
                        endIndex = riga;
                    }
                    else {
                        startIndex = riga + 1;
                        endIndex = rigaCellaSelezionata;
                    }
                    for (int i=startIndex;i<endIndex;i++) {
                        QuadratoScacchiera qs = scacchiera[i][colonnaCellaSelezionata];
                        if (qs.getTipoPezzo() != null)
                            return false;
                    }
                    return true;
                }
                if(riga==rigaCellaSelezionata && colonnaCellaSelezionata!=colonna) //mi sposto in riga
                {
                    if (colonnaCellaSelezionata < colonna) {
                        startIndex = colonnaCellaSelezionata + 1;
                        endIndex = colonna;
                    }
                    else {
                        startIndex = colonna + 1;
                        endIndex = colonnaCellaSelezionata;
                    }
                    for (int i=startIndex;i<endIndex;i++) {
                        QuadratoScacchiera qs = scacchiera[rigaCellaSelezionata][i];
                        if (qs.getTipoPezzo() != null)
                            return false;
                    }
                    return true;
                }
                return false;
            case "alfiere":
                if (Math.abs(riga-rigaCellaSelezionata)==Math.abs(colonna-colonnaCellaSelezionata))
                {

                }
            case "cavallo":
                if(riga==rigaCellaSelezionata+1 && colonna==colonnaCellaSelezionata+2)
                    return true;
                if(riga==rigaCellaSelezionata+1 && colonna==colonnaCellaSelezionata-2)
                    return true;
                if(riga==rigaCellaSelezionata-1 && colonna==colonnaCellaSelezionata+2)
                    return true;
                if(riga==rigaCellaSelezionata-1 && colonna==colonnaCellaSelezionata-2)
                    return true;
                if(riga==rigaCellaSelezionata+2 && colonna==colonnaCellaSelezionata+1)
                    return true;
                if(riga==rigaCellaSelezionata+2 && colonna==colonnaCellaSelezionata-1)
                    return true;
                if(riga==rigaCellaSelezionata-2 && colonna==colonnaCellaSelezionata+1)
                    return true;
                if(riga==rigaCellaSelezionata-2 && colonna==colonnaCellaSelezionata-1)
                    return true;
                return false;
            case "regina":
                if(Math.abs(riga-rigaCellaSelezionata)==Math.abs(colonna-colonnaCellaSelezionata))
                {
                    int startIndexRow;
                    int endIndexRow;
                    int startIndexCol;
                    int endIndexCol;
                    if(riga>rigaCellaSelezionata)
                    {
                        startIndexRow = rigaCellaSelezionata+1;
                        endIndexRow = riga;
                    }
                    else
                    {
                        startIndexRow = riga+1;
                        endIndexRow = rigaCellaSelezionata;
                    }
                    if(colonna>colonnaCellaSelezionata)
                    {
                        startIndexCol = colonnaCellaSelezionata+1;
                        endIndexCol = colonna;
                    }
                    else
                    {
                        startIndexCol = colonna+1;
                        endIndexCol = colonnaCellaSelezionata;
                    }
                    for(int i=startIndexRow;i<endIndexRow;i++)
                    {
                        for(int j=startIndexCol;j<endIndexCol;j++)
                        {
                            QuadratoScacchiera qs = scacchiera[i][j];
                            if (qs.getTipoPezzo() != null)
                                return false;
                        }
                    }
                    return true;
                }
                else if(colonna==colonnaCellaSelezionata && rigaCellaSelezionata!=riga) //mi sposto in colonna
                {
                    if (rigaCellaSelezionata < riga) {
                        startIndex = rigaCellaSelezionata + 1;
                        endIndex = riga;
                    }
                    else {
                        startIndex = riga + 1;
                        endIndex = rigaCellaSelezionata;
                    }
                    for (int i=startIndex;i<endIndex;i++) {
                        QuadratoScacchiera qs = scacchiera[i][colonnaCellaSelezionata];
                        if (qs.getTipoPezzo() != null)
                            return false;
                    }
                    return true;
                }
                else if(riga==rigaCellaSelezionata && colonnaCellaSelezionata!=colonna) //mi sposto in riga
                {
                    if (colonnaCellaSelezionata < colonna) {
                        startIndex = colonnaCellaSelezionata + 1;
                        endIndex = colonna;
                    }
                    else {
                        startIndex = colonna + 1;
                        endIndex = colonnaCellaSelezionata;
                    }
                    for (int i=startIndex;i<endIndex;i++) {
                        QuadratoScacchiera qs = scacchiera[rigaCellaSelezionata][i];
                        if (qs.getTipoPezzo() != null)
                            return false;
                    }
                    return true;
                }
                return false;
            case "re":
                if(riga==rigaCellaSelezionata-1 && colonna==colonnaCellaSelezionata-1)
                    return true;
                if(riga==rigaCellaSelezionata-1 && colonna==colonnaCellaSelezionata)
                    return true;
                if(riga==rigaCellaSelezionata-1 && colonna==colonnaCellaSelezionata+1)
                    return true;
                if(riga==rigaCellaSelezionata && colonna==colonnaCellaSelezionata-1)
                    return true;
                if(riga==rigaCellaSelezionata && colonna==colonnaCellaSelezionata+1)
                    return true;
                if(riga==rigaCellaSelezionata+1 && colonna==colonnaCellaSelezionata-1)
                    return true;
                if(riga==rigaCellaSelezionata+1 && colonna==colonnaCellaSelezionata)
                    return true;
                if(riga==rigaCellaSelezionata+1 && colonna==colonnaCellaSelezionata+1)
                    return true;
                return false;
            default:
                return false;
        }
    }

    public void muoviPezzo(int sourceRow, int sourceCol, int destRow, int destCol)
    {
        QuadratoScacchiera qs = scacchiera[sourceRow][sourceCol];
        QuadratoScacchiera qd = scacchiera[destRow][destCol];
        qd.setTipoPezzo(qs.getTipoPezzo());
        qd.setColorePezzo(qs.getColorePezzo());
        qd.setIcona();
        qs.setTipoPezzo(null);
        qs.setColorePezzo(null);
        qs.setIcona();
    }

    public boolean giocoFinito() {
        int countRe = 0;
        for(int i=0;i<scacchiera.length; i++)
        {
            for(int j=0;j<scacchiera[i].length;j++)
            {
                if(scacchiera[i][j].getTipoPezzo()!=null && scacchiera[i][j].getTipoPezzo().equals("re"))
                    countRe++;
                if(countRe==2)
                    return false;
            }
        }
        return true;
    }
    public String getVincitore() {
        for(int i=0;i<scacchiera.length; i++)
        {
            for(int j=0;j<scacchiera[i].length;j++)
            {
                if(scacchiera[i][j].getTipoPezzo()!=null && scacchiera[i][j].getTipoPezzo().equals("re"))
                    return scacchiera[i][j].getColorePezzo();
            }
        }
        return null;
    }

    public String getMove() {
        return this.move;
    }
    public void resetMove()
    {
        this.move = null;
    }

    public boolean getTurno() {
        return this.turno;
    }
    public void setTurno(boolean turno) {
        this.turno = turno;
    }
}
