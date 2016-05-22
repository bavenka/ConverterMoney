package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Database.Connect;
import sample.Database.Operations;
import sample.Objects.User;
import sample.Utils.DialogManager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Павел on 22.05.2016.
 */
public class RegisterDialogController {
    @FXML
    private TextField inputLogin;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private PasswordField inputConfirmPassword;
    private Connection connection;
    @FXML
    private void initialize(){
        connection= Connect.getDBConnection();
    }

    public void actionRegister(ActionEvent actionEvent) {
        if(inputLogin.getText().length()==0 || inputPassword.getText().length()==0 || inputConfirmPassword.getText().length()==0) {
            DialogManager.showErrorDialog("Ошибка", "Поля не могут быть пустыми!");
        }
            else if (!inputPassword.getText().equals(inputConfirmPassword.getText())) {
            DialogManager.showErrorDialog("Ошибка","Пароли не совпадают!");
            inputPassword.setText("");
            inputConfirmPassword.setText("");
        }
        else{
            User user=new User(inputLogin.getText(),inputPassword.getText());
            try {
                if(Operations.checkLogin(connection,user)==null) {
                    Operations.addUser(connection, user);
                    LoginDialogController.users.add(user);
                    actionCancel(actionEvent);
                }else{
                    DialogManager.showInfoDialog("Информация","Пользователь с таким именем уже существует!");
                    inputLogin.setText("");
                    inputPassword.setText("");
                    inputConfirmPassword.setText("");
                }
            }catch (SQLException e){
                e.fillInStackTrace();
            }
        }
    }

    public void actionCancel(ActionEvent actionEvent) {
        Node source =(Node) actionEvent.getSource();
        Stage stage=(Stage) source.getScene().getWindow();
        stage.close();
    }
}
