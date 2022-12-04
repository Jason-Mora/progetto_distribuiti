package chess_client;

import javax.swing.*;
import java.awt.*;

public class PlayPanel extends JPanel {
    JButton[][] scacchiera;
    public PlayPanel() {
        this.setLayout(new GridLayout(8, 8));
        scacchiera = inizializzaScacchiera();
    }

    private JButton[][] inizializzaScacchiera()
    {
        JButton[][] scacchiera = new JButton[8][8];
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
                            tipoPezzo = "fante";
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
                JButton b = new QuadratoScacchiera(isWhite?Color.WHITE:Color.BLACK, colorePezzo, tipoPezzo);
                scacchiera[i][j] = b;
                this.add(scacchiera[i][j]);
                isWhite = !isWhite;
            }
        }
        return scacchiera;
    }
}
