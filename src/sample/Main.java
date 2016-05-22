package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Controllers.LoginDialogController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        setUserAgentStylesheet(STYLESHEET_MODENA);
        FXMLLoader fxmlLoader=new FXMLLoader();
        //fxmlLoader.setLocation(getClass().getResource("FXSML/main.fxml"));
        fxmlLoader.setLocation(getClass().getResource("FXSML/login.fxml"));
        Parent fxmlMain=fxmlLoader.load();
        //UserDialogController mainController=fxmlLoader.getController();
        //mainController.setMainStage(primaryStage);
        LoginDialogController loginDialogController=fxmlLoader.getController();
        loginDialogController.setMainStage(primaryStage);



        primaryStage.setTitle("Authorization");
        primaryStage.setResizable(false);
       // primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("resources/money.png")));
        primaryStage.setScene(new Scene(fxmlMain, 600, 400));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
