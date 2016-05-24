package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Database.Connect;
import sample.Database.Operations;
import sample.Objects.User;
import sample.Utils.DialogManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Павел on 21.05.2016.
 */
public class LoginDialogController {
    private Stage mainStage;
    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonRegister;
    @FXML
    private TextField inputLogin;
    @FXML
    private PasswordField inputPassword;

    private Connection connection;
    private Stage mainDialogStage;
    private Stage registerDialogStage;
    private UserDialogController userDialogController;

    private Parent root;
    public static ArrayList<User> users;
    private User newUser = null;

    @FXML
    private void initialize() throws SQLException {
        userDialogController=new UserDialogController();
        connection = Connect.getDBConnection();
        users = Operations.getUsers(connection);
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    private boolean checkUser() {
        for (User user : users) {
            if (inputLogin.getText().equals(user.getLogin())) {
                newUser = user;
                return true;
            }
        }
        return false;
    }

    public void actionLogin(ActionEvent actionEvent) {
        if(inputLogin.getText().length()==0 || inputPassword.getText().length()==0){
            DialogManager.showErrorDialog("Ошибка", "Поля не могут быть пустыми!");
        }
        else if(checkUser()==true && newUser.getLogin().equals("pavel") && newUser.getPassword().equals("123")){
       userDialogController.isVisible=true;
            actionClose();
            showDialogUser();
        }
        else if (checkUser() == true && newUser.getPassword().equals(inputPassword.getText())) {
            //userDialogController.getFile().setVisible(false);
            userDialogController.isVisible=false;
            actionClose();
            showDialogUser();
        }
        else if(checkUser()== true && !newUser.getPassword().equals(inputPassword.getText())){
            DialogManager.showErrorDialog("Ошибка", "Пароль введён неверно!");
        }

        else DialogManager.showErrorDialog("Ошибка", " Пользователь с таким именем не зарегистрирован!");
    }

    public void actionRegister(ActionEvent actionEvent) {
        inputLogin.setText("");
        inputPassword.setText("");
        showDialogRegister();

    }

      private void actionClose(){
          Node source =(Node) buttonLogin;
          Stage stage=(Stage) source.getScene().getWindow();
          stage.close();
      }

    private void showDialogUser() {
        try {
            mainDialogStage= new Stage();
            root = FXMLLoader.load(getClass().getResource("../FXSML/main.fxml"));

            mainDialogStage.setScene(new Scene(root));
            mainDialogStage.setTitle("Беларусбанк");
            mainDialogStage.initModality(Modality.APPLICATION_MODAL);
            mainDialogStage.initOwner(mainStage);
            mainDialogStage.showAndWait();
        } catch (IOException e) {
            System.out.println("Файл не наден!");
        }

    }

    private void showDialogRegister() {
        try {
            registerDialogStage= new Stage();
            root = FXMLLoader.load(getClass().getResource("../FXSML/register.fxml"));
            registerDialogStage.setScene(new Scene(root));
            registerDialogStage.setTitle("Registration");
            registerDialogStage.initModality(Modality.APPLICATION_MODAL);
            registerDialogStage.initOwner(mainStage);
            registerDialogStage.showAndWait();
        } catch (IOException e) {
            System.out.println("Файл не наден!");
        }

    }
}
