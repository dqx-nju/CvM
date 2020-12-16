package org.cvm.view;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.cvm.app.View;

import java.io.File;

import static org.cvm.Framework.*;

public class PlayView extends View {

    private File file;

    public PlayView() {
        super();
        file = null;
    }

    @Override
    public void onLaunch() {
        System.out.println("PlayView onLaunch");
        Button homeBtn = new Button("Home");
        homeBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,(e) -> {
            System.out.println("PlayView home -> test");
            homeBtn.setText("test");
            app.gotoView("Home");
        });
        homeBtn.resize(900,500);
        getChildren().add(homeBtn);
    }

    @Override
    public void onEnter() {
        if(app.getFile() != null) {
            file = app.getFile();
            System.out.println(file);
            playBack();
            file = null;
            app.setFile(null);
        }
        else {
            System.out.println("No file");
        }
    }

    private void playBack() {
        // do something
        System.out.println("in playBack");
    }
}
