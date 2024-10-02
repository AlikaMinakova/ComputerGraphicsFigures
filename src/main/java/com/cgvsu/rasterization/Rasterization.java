package com.cgvsu.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.Random;

public class Rasterization {

    //ищет координаты x по y
    public static int[] findCoordX(int r, int x0, int y0, int y) {
        return new int[]{(int) Math.sqrt(Math.pow(r, 2) - Math.pow((y - y0), 2)) + x0,
                (int) -Math.sqrt(Math.pow(r, 2) - Math.pow((y - y0), 2)) + x0};
    }

    //ищет координаты вектора по двум точкам
    public static int[] findVector(int x, int y, int x2, int y2) {
        return new int[]{x2 - x, y2 - y};
    }

    //ищет координаты вектора по двум точкам
    public static int checkPoint(int[] vector1, int[] vector2) {
        return vector1[0] * vector2[1] - vector1[1] * vector2[0];
    }

    public static boolean incircle(int r, int x0, int y0, int x, int y) {
        return Math.pow(x - x0, 2) + Math.pow(y - y0, 2) <= Math.pow(r, 2);
    }

    public static double euclideanDistance(int x0, int y0, int x1, int y1){
        return Math.sqrt(Math.pow(x1-x0, 2)  + Math.pow(y1-y0, 2));
    }

    public static int getColor(double c1, double c2, double d1, double d2){
        c1 = 255 * c1;
        c2 = 255 * c2;
        return  (int) (((c2-c1)*d1) / d2 + c1);
    }

    public static void drawSector(
            final GraphicsContext graphicsContext,
            final int x, final int y,
            final int width, final int height,
            final Color color1, final Color color2) {

        graphicsContext.clearRect(0, 0, 800, 600);
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();

        int r = width / 2;
        //центр окружности
        final int[] centerCoords = new int[]{x + width / 2, y + height / 2};

        //произвольная точка на окружности 1
        int pointY = (int) (y + Math.random() * height);
        int[] pointX = findCoordX(width / 2, centerCoords[0], centerCoords[1], pointY);

        //произвольная точка на окружности 2
        int pointY2 = (int) (y + Math.random() * height);
        int[] pointX2 = findCoordX(r, centerCoords[0], centerCoords[1], pointY2);

        //вектор 1
        int[] v1 = findVector(centerCoords[0], centerCoords[1], pointX[0], pointY);
        //вектор 2
        int[] v2 = findVector(centerCoords[0], centerCoords[1], pointX2[0], pointY2);

        for (int row = y; row < y + height; ++row) {
            for (int col = x; col < x + width; ++col) {
                int[] v3 = findVector(centerCoords[0], centerCoords[1], col, row);
                if (incircle(r, centerCoords[0], centerCoords[1], col, row)
                        && (checkPoint(v1, v3) > 0 && checkPoint(v3, v2) > 0)) {
                    int red = getColor(color1.getRed(), color2.getRed(),
                            euclideanDistance(centerCoords[0], centerCoords[1], col, row), r);
                    int green = getColor( color1.getGreen(),  color2.getGreen(),
                            euclideanDistance(centerCoords[0], centerCoords[1], col, row), r);
                    int blue = getColor( color1.getBlue(), color2.getBlue(),
                            euclideanDistance(centerCoords[0], centerCoords[1], col, row), r);
                    pixelWriter.setColor(col, row, Color.rgb(red, green, blue));
                }
            }
        }
    }

    public static void drawSector(
            final GraphicsContext graphicsContext,
            final int x, final int y,
            final int width, final int height,
            final Color color) {
        drawSector(graphicsContext, x, y, width, height, color, color);
    }
}
