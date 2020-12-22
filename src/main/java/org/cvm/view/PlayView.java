package org.cvm.view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.cvm.app.View;
import org.cvm.input.Key;
import org.cvm.input.KeyInput;
import org.cvm.input.Mouse;
import org.cvm.input.MouseInput;
import org.cvm.net.CREATURE_ATTACK_MSG;
import org.cvm.net.CREATURE_DEAD_MSG;
import org.cvm.net.CREATURE_MOVE_MSG;
import org.cvm.net.Msg;
import org.cvm.world.Team.CalabashbrotherTeam;

import java.awt.font.ImageGraphicAttribute;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.cvm.Framework.*;

public class PlayView extends View {

    private File file;
    VBox vbox;
    VBox[] blocks;
    ProgressBar[] bloods;
    int selected_id = -1;
    int selected_block = -1;

    public PlayView() {
        super();
        file = null;
    }

    @Override
    public void onLaunch() {

        int[] pos_45 = calabashbrotherTeam.getallpostion();

        Group group = new Group();
        Image img_play_bg = new Image(getClass().getResourceAsStream("play_bg.png"));
        ImageView img_play_bg_view = new ImageView(img_play_bg);
        group.getChildren().add(img_play_bg_view);

        vbox = new VBox();
        vbox.setSpacing(10);
        blocks = new VBox[5 * 9];
        bloods = new ProgressBar[7];

        for(int i = 0; i < 5; i++) {
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            for(int j = 0; j < 9; j++) {
                VBox vbox_figure = new VBox();
                ImageView img_figure = new ImageView();
                img_figure.setFitWidth(70);
                img_figure.setFitHeight(66);
                vbox_figure.getChildren().add(img_figure);
                VBox vbox_figure_outside = new VBox(vbox_figure);
                hbox.getChildren().add(vbox_figure_outside);
                blocks[9 * i + j] = vbox_figure_outside;
            }
            vbox.getChildren().add(hbox);
        }
        for (int i = 0; i < 7; i++) {
            int x = pos_45[i];
            VBox vbox_figure = new VBox();
            ImageView img_figure = new ImageView(new Image(getClass().getResourceAsStream("../world/b" + (i + 1) + "_right.png")));
            ProgressBar blood = new ProgressBar();
            blood.setPrefWidth(70);
            blood.setStyle("-fx-accent: red;");
            blood.setProgress(1);
            bloods[0] = blood;
            img_figure.setFitWidth(70);
            img_figure.setFitHeight(66);
            vbox_figure.getChildren().add(blood);
            vbox_figure.getChildren().add(img_figure);
            blocks[x].getChildren().remove(0);
            blocks[x].getChildren().add(vbox_figure);

            blocks[x].addEventHandler(MouseEvent.MOUSE_CLICKED,(e) -> {
                solve_clicked(x);
            });
            blocks[x].addEventHandler(MouseEvent.MOUSE_ENTERED,(e) -> {
                solve_entered(x);
            });
            blocks[x].addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> {
                solve_exited(x);
            });
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
        if (keyInput.isReleased(Key.SPACE)) {
            System.out.println("Pressed SPACE");
            Msg msg = new CREATURE_MOVE_MSG(1,2,3);
            netClient.send(msg);
            msg = new CREATURE_ATTACK_MSG(2,7,2);
            netClient.send(msg);
            msg = new CREATURE_DEAD_MSG(1,5);
            netClient.send(msg);
        }
        if (keyInput.isReleased(Key.A)) {
            System.out.println("Pressed A");
            if (selected_block != -1) {
                String move_left_str = calabashbrotherTeam.moveleft(selected_id);
//                Node s = blocks[selected_block].getChildren().get(0);
//                blocks[selected_block].getChildren().remove(0);
//                Node t = blocks[selected_block - 1].getChildren().get(0);
//                blocks[selected_block - 1].getChildren().remove(0);
//                blocks[selected_block - 1].getChildren().add(s);
//                blocks[selected_block].getChildren().add(t);
//                selected_block -= 1;
                System.out.println(move_left_str);
            }
        }
        if (keyInput.isReleased(Key.D)) {
            System.out.println("Pressed D");
            if (selected_block != -1) {
                String move_left_str = calabashbrotherTeam.moveright(selected_id);
                System.out.println(move_left_str);
//                System.out.println("selected_block: " + selected_block);
//                Node s = blocks[selected_block].getChildren().get(0);
//                blocks[selected_block].getChildren().remove(0);
//                Node t = blocks[selected_block + 1].getChildren().get(0);
//                blocks[selected_block + 1].getChildren().remove(0);
//                blocks[selected_block + 1].getChildren().add(s);
//                blocks[selected_block].getChildren().add(t);
//                selected_block += 1;
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
        if (keyInput.isReleased(Key.J)) {
            System.out.println("Pressed J");
            if (selected_block != -1) {
                System.out.println("selected_block: " + selected_block);
                bloods[0].setProgress(bloods[0].getProgress()-0.1);
            }
        }
    }

    public void solve_clicked(int k) {
        selected_block = k;
        selected_id = calabashbrotherTeam.getNo(k);
        System.out.println("You clicked " + selected_id + " at " + k);
    }

    public void solve_entered(int k) {
        //System.out.println("You entered " + k);
    }

    private void solve_exited(int k) {
        //System.out.println("You exited " + k);
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
