package org.cvm.test;

import static org.cvm.Framework.*;

import javafx.stage.Stage;
import org.cvm.app.Game;

public class TestGame extends Game {

    @Override
    public void onLaunch() {
        Stage stage = app.getStage();
        stage.setTitle("Test Game");
        stage.setWidth(800);
        stage.setHeight(600);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
