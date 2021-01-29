package pl.put.poznan.sk2_project_client.ui.rotary_menu;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.ArrayList;
import java.util.List;

public class RotaryMenu {
    private static final int SPREADING_FRAMES = 12;
    private final List<MenuItem> items = new ArrayList<>();
    private Point position;
    private int unSpreadingFrames = 0;
    private MenuItem selectedItem;
    private boolean shown = false;
    private String defaultText = "";

    public void render(Graphics2D g) {
        processSpreadingFrames();
        if (unSpreadingFrames == 0) return;
        iterateItems((item, x, y) -> {
            BufferedImage image = item.getImage();
            if (item == selectedItem) {
                RescaleOp op = new RescaleOp(new float[]{1f}, new float[]{50f}, null);
                g.drawImage(image, op, (int)Math.round(x) - image.getWidth() / 2, (int)Math.round(y) - image.getHeight() / 2);
                printCentralText(g, item.getName());
            } else {
                g.drawImage(image, (int)Math.round(x)  - image.getWidth() / 2, (int)Math.round(y) - image.getHeight() / 2, null);
            }
            if (selectedItem == null) printCentralText(g, defaultText);
            return true;
        });
    }

    public void selectItemAtPosition(Point p, int radius) {
        selectedItem = getItemAtPosition(p, radius);
    }

    public MenuItem getItemAtPosition(Point p, int radius) {
        final Container<MenuItem> lock = new Container<>();
        iterateItems((item, x, y) -> {
            if (Math.hypot(x - p.x, y - p.y) <= radius) {
                lock.set(item);
                return false;
            }
            return true;
        });
        return lock.get();
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    public boolean isShown() {
        return unSpreadingFrames > 0;
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void addItem(MenuItem item, int at) {
        items.add(at, item);
    }

    public void removeItem(String name) {
        MenuItem item = null;
        for (MenuItem i : items) {
            if (i.getName().equals(name)) item = i;
        }
        if (item != null) items.remove(item);
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }

    private void processSpreadingFrames() {
        if (shown) {
            if (unSpreadingFrames < SPREADING_FRAMES) unSpreadingFrames++;
        } else {
            if (unSpreadingFrames > 0) unSpreadingFrames--;
        }
    }

    private void iterateItems(ItemCallback callback) {
        if (position == null) return;
        int menuRadius = items.size() * unSpreadingFrames * 2;
        int angleStep = 360 / items.size();
        int rotationOffset = 0;
        if (90 % angleStep == 0) rotationOffset += angleStep / 2;
        for (MenuItem item : items) {
            double x = position.x + Math.sin(rotationOffset * Math.PI / 180) * menuRadius;
            double y = position.y - Math.cos(rotationOffset * Math.PI / 180) * menuRadius;
            rotationOffset += angleStep;
            if (!callback.call(item, x, y)) return;
        }
    }

    private void printCentralText(Graphics2D g, String text) {
        g.setColor(Color.LIGHT_GRAY);
        g.setStroke(new BasicStroke(2));
        g.setFont(new Font("Consolas", Font.PLAIN, 12));
        int textWidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, position.x - textWidth / 2, position.y + g.getFontMetrics().getHeight() / 2);
    }

    private interface ItemCallback {
        boolean call(MenuItem i, double x, double y);
    }

    private class Container<T> {
        private T o;

        T get() {
            return o;
        }

        void set(T o) {
            this.o = o;
        }
    }
}
