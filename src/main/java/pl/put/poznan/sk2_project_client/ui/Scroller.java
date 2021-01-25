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
        if (mousePosition.x < 50 && mousePosition.x > 0) mScrollingX = -1;
        else if (mousePosition.x > camera.getScreenWidth() - 50 && mousePosition.x < camera.getScreenWidth()) mScrollingX = 1;
        else mScrollingX = 0;
        if (mousePosition.y < 50 && mousePosition.y > 0) mScrollingY = -1;
        else if (mousePosition.y > camera.getScreenHeight() - 50 && mousePosition.y < camera.getScreenHeight()) mScrollingY = 1;
        else mScrollingY = 0;
        Instant now = Instant.now();
        int millisBetweenLastScroll = (int) Duration.between(lastScrollTime, now).toMillis();
        int pixelsToScroll = millisBetweenLastScroll * SCROLLING_PIXELS_PER_SECOND / 1000;
        camera.scrollBy(pixelsToScroll * (scrollingX + mScrollingX), pixelsToScroll * (scrollingY + mScrollingY));
        lastScrollTime = now;
    }
}
