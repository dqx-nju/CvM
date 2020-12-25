package org.cvm.view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.cvm.app.View;
import org.cvm.input.Key;
import org.cvm.net.ATTACK_MSG;
import org.cvm.net.FINISH_MSG;
import org.cvm.net.MOVE_MSG;
import org.cvm.net.Msg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import static org.cvm.Framework.*;

public class PlayView extends View {

    ImageView turn1_img = new ImageView(new Image(getClass().getResourceAsStream("turn1.png")));
    ImageView turn2_img = new ImageView(new Image(getClass().getResourceAsStream("turn2.png")));
    VBox turn_vbox = new VBox(turn2_img);

    int teamSkillNumber;
    int teamActionnumber;
    Text action_text = new Text();
    Text skill_text = new Text();

    private File file;
    VBox vbox;
    VBox[] blocks;
    ProgressBar[] bloods_T1;
    ProgressBar[] bloods_T2;
    int[] pos_T1;
    int[] pos_T2;
    int selected_id = -1;
    int selected_block = -1;
    int selected_team = -1;


    public PlayView() {
        super();
        file = null;
    }

    public void set_inform(int team, int action, int skill) {
        teamActionnumber = action;
        teamSkillNumber = skill;
        action_text.setText(String.valueOf(teamActionnumber));
        skill_text.setText(String.valueOf(teamSkillNumber));
    }

    public void start_turn(int team) {
        turn_vbox.getChildren().remove(0);
        turn_vbox.getChildren().add(turn1_img);
    }

    public void setPos(int team, int id, int src, int dst) {
        if (team == 1) {
            assert(pos_T1[id-1] == src);
            pos_T1[id-1] = dst;
        }
        else {
            assert(pos_T2[id-1] == src);
            pos_T2[id-1] = dst;
        }
    }

    public void delete_creature(int team, int id) {
        VBox vbox_figure = new VBox();
        ImageView img_figure = new ImageView();
        img_figure.setFitWidth(70);
        img_figure.setFitHeight(86);
        vbox_figure.getChildren().add(img_figure);
        int x = 0;
        if (team == 1) {
            x = pos_T1[id-1];
            pos_T1[id-1] = -1;
            bloods_T1[id-1] = null;
        }
        else {
            x = pos_T2[id-1];
            pos_T2[id-1] = -1;
            bloods_T2[id-1] = null;
        }
        blocks[x].getChildren().remove(0);
        blocks[x].getChildren().add(vbox_figure);
    }

    @Override
    public void onLaunch() {

        teamSkillNumber = calabashbrotherTeam.getMaxTeamSkillNumber();
        teamActionnumber = calabashbrotherTeam.getMaxTeamAcitonNumber();

        pos_T1 = calabashbrotherTeam.getallpostion();
        pos_T2 = monsterTeam.getallpostion();

        Group group = new Group();
        Image img_play_bg = new Image(getClass().getResourceAsStream("play_bg.png"));
        ImageView img_play_bg_view = new ImageView(img_play_bg);
        group.getChildren().add(img_play_bg_view);

        vbox = new VBox();
        vbox.setSpacing(10);
        blocks = new VBox[5 * 9];
        bloods_T1 = new ProgressBar[7];
        bloods_T2 = new ProgressBar[7];

        for(int i = 0; i < 5; i++) {
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            for(int j = 0; j < 9; j++) {
                VBox vbox_figure = new VBox();
                ImageView img_figure = new ImageView();
                img_figure.setFitWidth(70);
                img_figure.setFitHeight(86);
                vbox_figure.getChildren().add(img_figure);
                VBox vbox_figure_outside = new VBox(vbox_figure);
                hbox.getChildren().add(vbox_figure_outside);
                blocks[9 * i + j] = vbox_figure_outside;
            }
            vbox.getChildren().add(hbox);
        }
        for (int i = 0; i < 7; i++) {
            int x = pos_T1[i];
            VBox vbox_figure = new VBox();
            ImageView img_figure = new ImageView(new Image(getClass().getResourceAsStream("../world/b" + (i + 1) + "_right.png")));
            ProgressBar blood = new ProgressBar();
            blood.setPrefWidth(70);
            blood.setStyle("-fx-accent: red;");
            blood.setProgress(1);
            bloods_T1[i] = blood;
            img_figure.setFitWidth(70);
            img_figure.setFitHeight(66);
            vbox_figure.getChildren().add(blood);
            vbox_figure.getChildren().add(img_figure);
            blocks[x].getChildren().remove(0);
            blocks[x].getChildren().add(vbox_figure);
        }

        for (int i = 0; i < 7; i++) {
            int x = pos_T2[i];
            VBox vbox_figure = new VBox();
            ImageView img_figure = new ImageView(new Image(getClass().getResourceAsStream("../world/m" + (i + 1) + "_left.png")));
            ProgressBar blood = new ProgressBar();
            blood.setPrefWidth(70);
            blood.setStyle("-fx-accent: red;");
            blood.setProgress(1);
            bloods_T2[i] = blood;
            img_figure.setFitWidth(70);
            img_figure.setFitHeight(66);
            vbox_figure.getChildren().add(blood);
            vbox_figure.getChildren().add(img_figure);
            blocks[x].getChildren().remove(0);
            blocks[x].getChildren().add(vbox_figure);
        }

        for(int i = 0; i < 45; i++) {
            int finalI = i;
            blocks[i].addEventHandler(MouseEvent.MOUSE_CLICKED,(e) -> {
                solve_clicked(finalI);
            });
        }

        Text action = new Text("行动力: ");
        Text skill = new Text("技能值: ");
        action_text = new Text(String.valueOf(teamActionnumber));
        skill_text = new Text(String.valueOf(teamSkillNumber));
        action.setFont(Font.font(30));
        skill.setFont(Font.font(30));
        action_text.setFont(Font.font(30));
        skill_text.setFont(Font.font(30));
        HBox action_hbox = new HBox(action,action_text);
        HBox skill_hbox = new HBox(skill,skill_text);
        VBox action_skill_vbox = new VBox(action_hbox,skill_hbox);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(action_skill_vbox);
        AnchorPane.setRightAnchor(action_skill_vbox, 60.0);
        AnchorPane.setTopAnchor(action_skill_vbox, 60.0);

        anchorPane.getChildren().add(turn_vbox);
        AnchorPane.setLeftAnchor(turn_vbox, 10.0);
        AnchorPane.setTopAnchor(turn_vbox, 10.0);

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
        if (keyInput.isReleased(Key.SPACE)) {
            Msg msg = new FINISH_MSG(selected_team);
            netClient.send(msg);
            turn_vbox.getChildren().remove(0);
            turn_vbox.getChildren().add(turn2_img);
        }
        if (keyInput.isPressed(Key.ESCAPE)) {
            System.out.println("Pressed ESC");
            app.gotoView("Home");
        }
        if (keyInput.isReleased(Key.A)) {
            if (selected_block != -1) {
                Msg msg = new MOVE_MSG(selected_team,selected_id,3);
                netClient.send(msg);
            }
        }
        if (keyInput.isReleased(Key.D)) {
            if (selected_block != -1) {
                Msg msg = new MOVE_MSG(selected_team,selected_id,4);
                netClient.send(msg);
            }
        }
        if (keyInput.isReleased(Key.W)) {
            if (selected_block != -1) {
                Msg msg = new MOVE_MSG(selected_team,selected_id,1);
                netClient.send(msg);
            }
        }
        if (keyInput.isReleased(Key.S)) {
            if (selected_block != -1) {
                Msg msg = new MOVE_MSG(selected_team,selected_id,2);
                netClient.send(msg);
            }
        }
        if (keyInput.isReleased(Key.NUM1)) {
            if (selected_block != -1) {
                Msg msg = new ATTACK_MSG(selected_team,selected_id,false);
                netClient.send(msg);
            }
        }
        if (keyInput.isReleased(Key.NUM2)) {
            if (selected_block != -1) {
                Msg msg = new ATTACK_MSG(selected_team,selected_id,true);
                netClient.send(msg);
            }
        }
    }

    public void swap_block(int src,int dst) {
        Node s = blocks[src].getChildren().get(0);
        blocks[src].getChildren().remove(0);
        Node t = blocks[dst].getChildren().get(0);
        blocks[dst].getChildren().remove(0);
        blocks[dst].getChildren().add(s);
        blocks[src].getChildren().add(t);
    }

    public void set_blood(int team, int id, double blood) {
        if (team == 1) {
            bloods_T1[id-1].setProgress(blood);
        }
        else {
            bloods_T2[id-1].setProgress(blood);
        }
    }

    public int getNo(int team, int k) {
        if (team == 1) {
            for (int i = 0; i < 7; i++) {
                if (pos_T1[i] == k) {
                    return i + 1;
                }
            }
        }
        else {
            for (int i = 0; i < 7; i++) {
                if (pos_T2[i] == k) {
                    return i + 1;
                }
            }
        }
        return -1;
    }

    public void solve_clicked(int k) {
        selected_block = k;
        int x = getNo(1,k);
        int y = getNo(2,k);
        if (x != -1) {
            selected_team = 1;
            selected_id = x;
            System.out.println("You clicked b " + selected_id + " at " + k);
        }
        else if (y != -1) {
            selected_team = 2;
            selected_id = y;
            System.out.println("You clicked m " + selected_id + " at " + k);
        }
        else {
            System.out.println("You clicked nothing at " + k);
        }
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
