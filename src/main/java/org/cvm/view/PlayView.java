package org.cvm.view;

import javafx.scene.control.Button;
import org.cvm.app.View;
import static org.cvm.Framework.*;

public class PlayView extends View {

    private Button homeBtn;

    @Override
    public void onLaunch() {
        homeBtn = new Button("Home");
        homeBtn.setOnAction((event) -> {
            app.gotoView("Home");
        });

        getChildren().add(homeBtn);
    }
}
