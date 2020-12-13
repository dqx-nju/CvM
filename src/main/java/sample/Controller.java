package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    @FXML
    void loginAction(ActionEvent event) {
        String userName = username.getText();
        String passWord = password.getText();
        if(userName.equals("pxy") && passWord.equals("123"))
            System.out.println("登陆成功");
        else
            System.out.println("账户密码错误");
    }

}
