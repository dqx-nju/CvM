package org.cvm.view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import org.cvm.app.View;
import org.cvm.input.Key;
import org.cvm.input.KeyInput;
import org.cvm.input.Mouse;
import org.cvm.input.MouseInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.cvm.Framework.*;

public class PlayView extends View {

    private File file;
    VBox vbox;
    VBox[] blocks;
    int selected_id = -1;
    int selected_block = -1;

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

        vbox = new VBox();
        vbox.setSpacing(10);
        blocks = new VBox[5 * 9];
        for(int i = 0; i < 5; i++) {
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            for(int j = 0; j < 9; j++) {
                VBox vbox_figure = new VBox();
                ImageView img_figure;
                ImageView img_blood;
                if (i * 9 + j == 15) {
                    img_figure = new ImageView(new Image(getClass().getResourceAsStream("../world/b1_right.png")));
                    img_blood = new ImageView(new Image(getClass().getResourceAsStream("play.png")));
                }
                else {
                    img_figure = new ImageView();
                    img_blood = new ImageView();
                }
                img_figure.setFitWidth(70);
                img_figure.setFitHeight(70);

                img_blood.setFitWidth(70);
                img_blood.setFitHeight(16);
                vbox_figure.getChildren().add(img_blood);
                vbox_figure.getChildren().add(img_figure);
                VBox vbox_figure_outside = new VBox(vbox_figure);
                hbox.getChildren().add(vbox_figure_outside);
                blocks[9 * i + j] = vbox_figure_outside;
                vbox_figure_outside.addEventHandler(MouseEvent.MOUSE_CLICKED,(e) -> {
                    int k = 0;
                    for(;k < 5 * 9; k++) {
                        if (blocks[k] == vbox_figure_outside) {
                            break;
                        }
                    }
                    solve_clicked(k);
                });
                vbox_figure_outside.addEventHandler(MouseEvent.MOUSE_ENTERED,(e) -> {
                    int k = 0;
                    for(;k < 5 * 9; k++) {
                        if (blocks[k] == vbox_figure_outside) {
                            break;
                        }
                    }
                    solve_entered(k);
                });
                vbox_figure_outside.addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> {
                    int k = 0;
                    for(;k < 5 * 9; k++) {
                        if (blocks[k] == vbox_figure_outside) {
                            break;
                        }
                    }
                    solve_exited(k);
                });
            }
            vbox.getChildren().add(hbox);
        }

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(vbox);
        AnchorPane.setBottomAnchor(vbox, 60.0);
        AnchorPane.setRightAnchor(vbox, 60.0);
        getChildren().add(group);
        getChildren().add(anchorPane);
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
        if (mouseInput.isClicked(Mouse.RIGHT)) {
            System.out.println("Clicked RIGHT");
            selected_block = -1;
            selected_id = -1;
        }
        if (keyInput.isPressed(Key.ESCAPE)) {
            System.out.println("Pressed ESC");
            app.gotoView("Home");
        }
        if (keyInput.isPressed(Key.SPACE)) {
            System.out.println("Pressed SPACE");
            blocks[1].getChildren().remove(1);
            ImageView img_figure = new ImageView(new Image(getClass().getResourceAsStream("../world/b1_left.png")));
            img_figure.setFitWidth(70);
            img_figure.setFitHeight(70);
            blocks[1].getChildren().add(img_figure);
        }
        if (keyInput.isReleased(Key.A)) {
            System.out.println("Pressed A");
            if (selected_block != -1) {
                System.out.println("selected_block: " + selected_block);
                Node s = blocks[selected_block].getChildren().get(0);
                blocks[selected_block].getChildren().remove(0);
                Node t = blocks[selected_block - 1].getChildren().get(0);
                blocks[selected_block - 1].getChildren().remove(0);
                blocks[selected_block - 1].getChildren().add(s);
                blocks[selected_block].getChildren().add(t);
                selected_block -= 1;
            }
        }
        if (keyInput.isReleased(Key.D)) {
            System.out.println("Pressed D");
            if (selected_block != -1) {
                System.out.println("selected_block: " + selected_block);
                Node s = blocks[selected_block].getChildren().get(0);
                blocks[selected_block].getChildren().remove(0);
                Node t = blocks[selected_block + 1].getChildren().get(0);
                blocks[selected_block + 1].getChildren().remove(0);
                blocks[selected_block + 1].getChildren().add(s);
                blocks[selected_block].getChildren().add(t);
                selected_block += 1;
            }
        }
        if (keyInput.isReleased(Key.W)) {
            System.out.println("Pressed W");
            if (selected_block != -1) {
                System.out.println("selected_block: " + selected_block);
                Node s = blocks[selected_block].getChildren().get(0);
                blocks[selected_block].getChildren().remove(0);
                Node t = blocks[(selected_block - 9 + 45) % 45].getChildren().get(0);
                blocks[(selected_block - 9 + 45) % 45].getChildren().remove(0);
                blocks[(selected_block - 9 + 45) % 45].getChildren().add(s);
                blocks[selected_block].getChildren().add(t);
                selected_block = (selected_block - 9 + 45) % 45;
            }
        }
        if (keyInput.isReleased(Key.S)) {
            System.out.println("Pressed S");
            if (selected_block != -1) {
                System.out.println("selected_block: " + selected_block);
                Node s = blocks[selected_block].getChildren().get(0);
                blocks[selected_block].getChildren().remove(0);
                Node t = blocks[(selected_block + 9) % 45].getChildren().get(0);
                blocks[(selected_block + 9) % 45].getChildren().remove(0);
                blocks[(selected_block + 9) % 45].getChildren().add(s);
                blocks[selected_block].getChildren().add(t);
                selected_block = (selected_block + 9) % 45;
            }
        }
    }

    public void solve_clicked(int k) {
        selected_block = k;
        System.out.println("You clicked " + k);
    }

    public void solve_entered(int k) {
        System.out.println("You entered " + k);
    }

    private void solve_exited(int k) {
        System.out.println("You exited " + k);
    }

    private void playBack() {
        // do something
        System.out.println("in playBack");
        String content = readFileContent(file);
        String[] s = content.split("\n");
        for (String t : s)
            System.out.println(t);
    }

    public static String readFileContent(File file) {
        BufferedReader reader = null;
        StringBuilder sbf = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
                sbf.append('\n');
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }
}
