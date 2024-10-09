package com.cgvsu.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.Random;

public class Rasterization {

    //ищет координаты x по y
    public static int[] findCoordX(int r, int x0, int y0, int y) {
        return new int[]{(int) -Math.sqrt(Math.pow(r, 2) - Math.pow((y - y0), 2)) + x0,
                (int) Math.sqrt(Math.pow(r, 2) - Math.pow((y - y0), 2)) + x0};
    }

    //ищет координаты вектора по двум точкам
    public static int[] findVector(int x, int y, int x2, int y2) {
        return new int[]{x2 - x, y2 - y};
    }

    //проверяет что точка внутри сектора
    public static int checkPoint(int[] vector1, int[] vector2) {
        return vector1[0] * vector2[1] - vector1[1] * vector2[0];
    }

    public static double euclideanDistance(int x0, int y0, int x1, int y1) {
        return Math.sqrt(Math.pow(x1 - x0, 2) + Math.pow(y1 - y0, 2));
    }

    public static int getColor(double c1, double c2, double d1, double d2) {
        return (int) (((c2 - c1) * d1) / d2 + c1);
    }

    public static int[] gradient(Color color1, Color color2, int x, int y, int col, int row, int r) {
        int red = getColor(255 * color1.getRed(), 255 * color2.getRed(),
                euclideanDistance(x, y, col, row), r);
        int green = getColor(255 * color1.getGreen(), 255 * color2.getGreen(),
                euclideanDistance(x, y, col, row), r);
        int blue = getColor(255 * color1.getBlue(), 255 * color2.getBlue(),
                euclideanDistance(x, y, col, row), r);
        return new int[]{red, green, blue};
    }

    public static void drawSector(
            final GraphicsContext graphicsContext,
            final int x, final int y,
            final int r,
            final Color color1, final Color color2,
            int startAngle, int rotate) {

        graphicsContext.clearRect(0, 0, 800, 600);
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();

        if (rotate < 0){
            startAngle = rotate + startAngle;
            rotate = -rotate;
        }

        //нахожу точки на окружности
        int x1 = (int) (x - r * Math.cos(Math.toRadians(startAngle)));
        int y1 = (int) (y + r * Math.sin(Math.toRadians(startAngle)));

        int x2 = (int) (x - r * Math.cos(Math.toRadians(startAngle + rotate)));
        int y2 = (int) (y + r * Math.sin(Math.toRadians(startAngle + rotate)));

        //вектор 1
        int[] v1 = findVector(x, y, x1, y1);
        //вектор 2
        int[] v2 = findVector(x, y, x2, y2);

        for (int row = y - r; row < y + r; row++) {
            int[] coordsX = findCoordX(r, x, y, row);
            for (int col = coordsX[0]; col < coordsX[1]; ++col) {
                int[] v3 = findVector(x, y, col, row);
                if (rotate <= 180) {
                    if (checkPoint(v1, v3) > 0 && checkPoint(v3, v2) > 0) {
                        int[] rgb = gradient(color1, color2, x, y, col, row, r);
                        pixelWriter.setColor(col, row, Color.rgb(rgb[0], rgb[1], rgb[2]));
                    }
                } else {
                    if (!(checkPoint(v2, v3) > 0 && checkPoint(v3, v1) > 0)) {
                        int[] rgb = gradient(color1, color2, x, y, col, row, r);
                        pixelWriter.setColor(col, row, Color.rgb(rgb[0], rgb[1], rgb[2]));
                    }
                }
            }
        }
    }
}
