package org.cvm;

import org.cvm.app.Game;
import org.cvm.view.HomeView;
import org.cvm.view.PlayView;

import static org.cvm.Framework.app;

public class Main extends Game {
    @Override
    public void onLaunch() {
        app.setTitle("CvM");
        app.setWidth(1000);
        app.setHeight(700);

        app.regView("Home", new HomeView());
        app.regView("Play", new PlayView());
        app.gotoView("Home");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onFinish() {
        System.out.println("Finish");
    }

    @Override
    public boolean onExit() {
        System.out.println("Exit");
        return true;
    }
}
