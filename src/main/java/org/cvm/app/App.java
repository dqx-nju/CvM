package org.cvm.app;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;
import org.cvm.Framework;

public class App {

    private final Stage stage;
    private final Scene scene;
    private final Pane root;

    OnLaunch onLaunch;
    OnFinish onFinish;
    OnExit onExit;

    public App(Stage stage) {
        this.stage = stage;

        root = new Pane();
        scene = new Scene(root);
        stage.setScene(scene);

        initFramework();
        initApp();
    }

    private final void initFramework() {
        Framework.app = this;
    }

    private final void initApp() {
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST,(event) -> {
            if(onExit != null && !onExit.handle()) {
                event.consume();
            }
        });
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    void launch() {
        if(onLaunch != null) {
            onLaunch.handle();
        }
        stage.requestFocus();//窗口申请焦点，便于键盘输入
        stage.show();
    }

    void finish() {
        if(onFinish != null) {
            onFinish.handle();
        }
    }

    static interface OnLaunch {
        void handle();
    }

    static interface OnFinish {
        void handle();
    }

    static interface OnExit {
        boolean handle();
    }
}
