package frame.menu.searchClasses;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class Entryearch {

    private BufferedImage img;

    private int width;

    private WritableRaster raster;

    public Entryearch(BufferedImage img){
        this.img = img;
        width = img.getWidth();

        raster = img.getRaster();
    }

    public Point2D.Double searchForEntry(int tileSize){
        Point2D.Double point = null;
        for (int i = 1; i<width; i+=tileSize/2){
                int[] pixel = raster.getPixel(i, 0, (int[]) null);
                    if (pixel[0] > 150){
                        point = new Point2D.Double(i ,tileSize/2);
                        break;
                    }

        }
        return point;
    }

    public int getWidth() {
        return width;
    }

    public WritableRaster getRaster(){
        return raster;
    }
}
