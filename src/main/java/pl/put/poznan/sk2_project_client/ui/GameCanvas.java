package pl.put.poznan.sk2_project_client.ui;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameCanvas {
    private Canvas canvas;
    private GraphicsContext graphicsContext;

    public GameCanvas() {
        canvas = new Canvas(800, 600);
        graphicsContext = canvas.getGraphicsContext2D();
    }

    public void appendTo(Group group) {
        group.getChildren().add(canvas);
    }

    public void draw() {
        graphicsContext.setFill(Color.GREEN);
        graphicsContext.setStroke(Color.BLUE);
        graphicsContext.setLineWidth(5);
        graphicsContext.strokeLine(40, 10, 10, 40);

        canvas.setTranslateX(20);
        canvas.setTranslateX(0);
    }
}
