package pl.put.poznan.sk2_project_client.ui;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {
    private static final String basePath = "";
    private static final Map<String, BufferedImage> images = new HashMap<>();

    public ImageLoader loadImage(String id, String path) {
        URL file = getClass().getResource(path);
        try {
            System.out.println(file.getPath());
            images.put(id, ImageIO.read(file));
        } catch (IOException e) {
            e.printStackTrace();
            images.put(id, new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)); // put empty image
        }
        return this;
    }

    public BufferedImage getImage(String id) {
        return images.get(id);
    }
}
