package zak.adcs;

import javax.swing.*;
import java.net.URL;

public class ImageIconLoader {

    private static ImageIconLoader instance;

    private ImageIconLoader() {}

    public static ImageIconLoader getInstance() {
        if (instance == null) {
            instance = new ImageIconLoader();
        }
        return instance;
    }

    public ImageIcon getImageIcon(String path) {
        ImageIcon imageIcon = null;
        URL imageURL = getClass().getResource(path);
        if (imageURL == null) {
            imageIcon = new ImageIcon(path.substring(1));
        } else {
            imageIcon = new ImageIcon(imageURL);
        }
        return imageIcon;
    }

}
