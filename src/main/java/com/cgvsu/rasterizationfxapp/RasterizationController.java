package com.cgvsu.rasterizationfxapp;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import com.cgvsu.rasterization.*;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

public class RasterizationController {

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        Rasterization.drawSector(canvas.getGraphicsContext2D(),
                400,  300, 200,
                Color.rgb(31, 88, 204), Color.rgb(31, 204, 132), 90, 270);

//        canvas.setOnMouseClicked(event -> {
//            switch (event.getButton()) {
//                case PRIMARY -> handlePrimaryClick(canvas.getGraphicsContext2D(), event);
//            }
//        });
    }
}