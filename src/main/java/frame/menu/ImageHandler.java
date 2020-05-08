package frame.menu;

import frame.menu.searchClasses.Entryearch;
import frame.menu.searchClasses.SearchEntity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageHandler extends JComponent implements IImageHandler {


    private int panelWidth;
    private int mapWidth =0;
    private SearchEntity startingPoint;
    Entryearch search ;

    private BufferedImage img;

    public ImageHandler(int panelWidth,int height){
    this.panelWidth = panelWidth;
    setPreferredSize(new Dimension(panelWidth,height - height/6));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
            if (img != null)
                g2.drawImage(img, panelWidth /2 - mapWidth/2,20,this);
            if(startingPoint != null) {
                startingPoint.draw(g2);
            }
        repaint();
        super.paintComponent(g2);
    }


    @Override
    public void loadFile(File file){
        try {
            img = ImageIO.read(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        search = new Entryearch(img);
        mapWidth = search.getWidth();
    }

    @Override
    public void load(int tileSize) {

        if (tileSize == 0)
            tileSize = 16;

        mapWidth = search.getWidth();
        startingPoint = new SearchEntity(tileSize, panelWidth,search.getRaster(),search.searchForEntry(tileSize),null,null);
    }


}
