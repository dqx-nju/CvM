package org.cvm;

import org.cvm.app.Game;
import org.cvm.view.ServerView;

import static org.cvm.Framework.app;
import static org.cvm.Framework.engine;

public class ServerMain extends Game {
    @Override
    public void onLaunch() {
        app.setTitle("Server");
        app.setWidth(200);
        app.setHeight(400);

        app.regView("Server", new ServerView());
        app.gotoView("Server");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onFinish() {
        System.out.println("ServerMain Finish");
    }

    @Override
    public boolean onExit() {
        engine.stop();
        System.out.println("ServerMain Exit");
        System.exit(0);
        return true;
    }
}