package frame.menu.searchClasses;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.WritableRaster;
import java.util.Arrays;
import java.util.Objects;

public class SearchEntity extends Ellipse2D.Double {
    private int tileSize;
    private int panelWidth =0;
    private int mapHeight,mapWidth;
    private WritableRaster raster;
    private Point2D.Double pos;
    private SearchEntity parent = null;
    private SearchEntity[] children;
    private int childrenCount = 0;
    private int childBlockCount = 0;


    private enum parentPosib {LEFT,RIGHT,TOP,DOWN}
    private boolean first = false;
    private boolean last = false;

    private boolean blocked = false;

    private parentPosib parentPos;

    public SearchEntity (int tileSize,int panelWidth, WritableRaster raster,Point2D.Double pos,SearchEntity parent, parentPosib parentPos){
        super(pos.getX() - tileSize/2+4 +panelWidth/2 - raster.getWidth()/2,pos.getY() - tileSize/2+4+20,tileSize-8,tileSize-8);
        this.raster = raster;
        this.panelWidth = panelWidth;
        this.mapHeight = raster.getHeight();
        this.mapWidth = raster.getWidth();
        this.parent = parent;
        this.tileSize = tileSize;
        this.pos = pos;
        this.parentPos = parentPos;
        if (parent == null){
            first = true;
        }
        children = new SearchEntity[4];
        checkCollisons();

    }


    public void draw(Graphics2D g2){
        if(!blocked){
            g2.setColor(Color.BLACK);
            if (first||last)
                g2.setColor(Color.GREEN);
            g2.fill(this);
        }
        for (SearchEntity s:children) {
            if (s!=null)
                s.draw(g2);
            }

    }



    public void checkCollisons(){
        int pixelsRight[] = raster.getPixel((int)pos.getX() + tileSize/2 ,(int)pos.getY(),(int[])null);
        int pixelsLeft[] = raster.getPixel((int)pos.getX() - tileSize/2,(int)pos.getY(),(int[])null);
        int pixelsDown[] = raster.getPixel((int)pos.getX(),(int)pos.getY()+tileSize/2,(int[])null);
        int pixelsTop[] = raster.getPixel((int)pos.getX(),(int)pos.getY()-tileSize/2,(int[])null);

        if (((pixelsDown[0] > 150)&&(pos.getY() + tileSize < mapHeight)&&(parent == null || !parentPos.equals(parentPosib.DOWN)))) {
            children[0] = new SearchEntity(tileSize,panelWidth,raster,new Point2D.Double(pos.getX(),(pos.getY()+tileSize)),this,parentPosib.TOP);
            if (children[0].isBlocked())
                childBlockCount++;
            childrenCount ++;
        }else if (((pixelsDown[0] > 150)&&(pos.getY() + tileSize > mapHeight)&&(parent == null || !parentPos.equals(parentPosib.DOWN)))){
            last = true;
        }
        if (((pixelsTop[0] > 150)&&(pos.getY() - tileSize  > 0)&&(parent == null || !parentPos.equals(parentPosib.TOP))))  {
            children[1] = new SearchEntity(tileSize,panelWidth,raster,new Point2D.Double(pos.getX(),(pos.getY()-tileSize)),this,parentPosib.DOWN);
            if (children[1].isBlocked())
                childBlockCount++;
            childrenCount ++;
        }

        if (((pixelsLeft[0] > 150)&& (pos.getX() - tileSize > 0)&&(parent == null || !parentPos.equals(parentPosib.LEFT)))) {
            children[2] = new SearchEntity(tileSize,panelWidth,raster,new Point2D.Double(pos.getX() - tileSize ,pos.getY()),this,parentPosib.RIGHT);
            if (children[2].isBlocked())
                childBlockCount++;
            childrenCount ++;
        }

        if (((pixelsRight[0] > 150) && ( pos.getX() + tileSize < mapWidth )&&(parent == null || !parentPos.equals(parentPosib.RIGHT)))) {
            children[3] = new SearchEntity(tileSize,panelWidth,raster,new Point2D.Double(pos.getX() + tileSize,pos.getY()),this,parentPosib.LEFT);
            if (children[3].isBlocked())
                childBlockCount++;
            childrenCount ++;
        }

        if (!((first)||(last)) ) {
            if (Arrays.stream(children).allMatch(Objects::isNull)) {
                blocked = true;
            }

            if (childrenCount <= childBlockCount){
                blocked = true;
            }else{
                blocked = false;
            }
        }
    }

    public boolean isBlocked(){
        return blocked;
    }


}
