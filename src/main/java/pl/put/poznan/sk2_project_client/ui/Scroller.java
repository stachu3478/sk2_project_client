package pl.put.poznan.sk2_project_client.ui;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;

public class Scroller {
    private final static int SCROLLING_PIXELS_PER_SECOND = 500;
    private int scrollingX = 0;
    private int scrollingY = 0;
    private int mScrollingX = 0;
    private int mScrollingY = 0;
    private Instant lastScrollTime = Instant.now();
    private final TileCamera camera;

    public Scroller(TileCamera camera) {
        this.camera = camera;
    }

    public void setX(int x) {
        this.scrollingX = x;
    }

    public void setY(int y) {
        this.scrollingY = y;
    }

    public void process(Point mousePosition) {
        processMouse(mousePosition);
        Instant now = Instant.now();
        int millisBetweenLastScroll = (int) Duration.between(lastScrollTime, now).toMillis();
        int pixelsToScroll = millisBetweenLastScroll * SCROLLING_PIXELS_PER_SECOND / 1000;
        camera.scrollBy(pixelsToScroll * (scrollingX + mScrollingX), pixelsToScroll * (scrollingY + mScrollingY));
        lastScrollTime = now;
    }

    private void processMouse(Point mousePosition) {
        int mouseX = mousePosition.x;
        int mouseY = mousePosition.y;
        int maxX = camera.getScreenWidth();
        int maxY = camera.getScreenHeight();
        mScrollingX = 0;
        mScrollingY = 0;
        if (mouseX < 0 || mouseY < 0 || mouseX > maxX || mouseY > maxY) return;
        if (mouseX < 50) mScrollingX = -1;
        else if (mouseX > maxX - 50) mScrollingX = 1;
        if (mouseY < 50) mScrollingY = -1;
        else if (mouseY > maxY - 50) mScrollingY = 1;
    }
}
