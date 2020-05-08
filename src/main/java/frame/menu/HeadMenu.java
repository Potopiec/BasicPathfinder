package frame.menu;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class HeadMenu extends JPanel {
    private JButton button;
    public HeadMenu(int width, int height,IImageHandler handler){
        setPreferredSize(new Dimension(width,height/6));

        JLabel tileSizeLabel = new JLabel("Tile size:");
        JTextField tileSize =new JTextField(5);


        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File
                (System.getProperty("user.home") + System.getProperty("file.separator")+ "Desktop"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Png file", "png"));

        add(fileChooser);
        add(tileSizeLabel);
        add(tileSize);

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION){
            handler.loadFile(fileChooser.getSelectedFile());
        }

        button = new JButton("solve");

        button.addActionListener(e->{handler.load(tileSize.getText().isEmpty() ? 0:Integer.parseInt(tileSize.getText()));});
        add(button);

    }
}
