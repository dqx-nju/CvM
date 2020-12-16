package org.cvm.view;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.cvm.app.View;
import static org.cvm.Framework.*;

public class PlayView extends View {

    @Override
    public void onLaunch() {
        Text homeBtn = new Text("Home");
        homeBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,(e) -> {
            app.gotoView("Home");
        });
        homeBtn.resize(900,500);
        getChildren().add(homeBtn);
    }
}
