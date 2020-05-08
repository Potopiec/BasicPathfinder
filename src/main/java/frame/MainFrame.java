package frame;

import frame.menu.HeadMenu;
import frame.menu.ImageHandler;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(int width,int height){
        super("Dijkstra");
        setSize(width,height);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);

        ImageHandler imageHandler = new ImageHandler(width,height);


        JPanel mainPanel = new JPanel();
        add(mainPanel);
        HeadMenu menu = new HeadMenu(width,height,imageHandler);

        mainPanel.add(menu);
        mainPanel.add(imageHandler);

        setVisible(true);

    }
}
