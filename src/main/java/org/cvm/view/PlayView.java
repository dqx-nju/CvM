package org.cvm.view;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.cvm.app.View;
import org.cvm.input.Key;

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
        Group group = new Group();
        Image img_play_bg = new Image(getClass().getResourceAsStream("play_bg.png"));
        ImageView img_play_bg_view = new ImageView(img_play_bg);
        group.getChildren().add(img_play_bg_view);
        getChildren().add(group);
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

    @Override
    public void onUpdate(double time) {
        if (keyInput.isPressed(Key.ESCAPE)) {
            System.out.println("Pressed ESC");
            app.gotoView("Home");
        }
    }

    private void playBack() {
        // do something
        System.out.println("in playBack");
    }
}
