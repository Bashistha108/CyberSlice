package de.hsh.gui;

import de.hsh.app.GameModel;
import de.hsh.app.SpielObjekt;
import de.hsh.app.objects.*;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;


public class SpielPanel extends JPanel implements Observer {
    private GameModel gameModel;
    private GUIController guiController;

    public SpielPanel(GameModel gameModel, GUIController guiController) {
        this.gameModel = gameModel;
        this.guiController = guiController;


        this.gameModel.addObserver(this);


        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                guiController.onMausBewegung(e.getX(), e.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                guiController.onMausBewegung(e.getX(), e.getY());
            }
        });

        // TODO: Pausen-Button usw. hinzufügen [cite: 2863]
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);


            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, getWidth(), getHeight());


            g2.setColor(Color.WHITE);
            g2.drawString("Punkte: " + gameModel.getPunkte(), 10, 20);
            g2.drawString("Leben: " + gameModel.getLeben(), 10, 40);
            g2.drawString("Zeit: " + gameModel.getZeit(), 10, 60);
            g2.drawString("Level: " + gameModel.getLevel().name(), 10, 80);
            if (gameModel.isPowerMode()) {
                g2.setColor(Color.YELLOW);
                g2.drawString("POWER MODE!", 10, 100);
            }



            for (SpielObjekt obj : gameModel.getObjektListe()) {

                if (obj instanceof Virus || obj instanceof UltraVirus) {
                    g2.setColor(Color.RED);
                } else if (obj instanceof Antivirus || obj instanceof UltraAntivirus) {
                    g2.setColor(Color.GREEN);
                } else if (obj instanceof Datei) {
                    g2.setColor(Color.LIGHT_GRAY);
                } else if (obj instanceof Uhr) {
                    g2.setColor(Color.CYAN);
                } else if (obj instanceof USBStick) {
                    g2.setColor(Color.YELLOW);
                }
                g2.fillOval((int)obj.getPosX(), (int)obj.getPosY(), 50, 50);
            }

        } finally {
            g2.dispose();
        }
    }


    @Override
    public void update(Observable o, Object arg) {

        if (o == gameModel) {
            this.repaint();
        }
    }
}