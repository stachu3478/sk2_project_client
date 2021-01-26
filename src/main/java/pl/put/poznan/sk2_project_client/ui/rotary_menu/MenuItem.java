package pl.put.poznan.sk2_project_client.ui.rotary_menu;

import pl.put.poznan.sk2_project_client.ui.ImageLoader;

import java.awt.image.BufferedImage;

public class MenuItem {
    private static ImageLoader loader;
    private final String name;
    private final String imageId;

    public static void useLoader(ImageLoader l) {
        loader = l;
    }

    public MenuItem(String name, String imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getImage() {
        return loader.getImage(imageId);
    }
}
