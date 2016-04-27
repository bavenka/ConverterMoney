package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Controllers.MainController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("FXSML/main.fxml"));
        Parent fxmlMain=fxmlLoader.load();
        MainController mainController=fxmlLoader.getController();
        mainController.setMainStage(primaryStage);
        primaryStage.setTitle("Калькулятор вкладов");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(fxmlMain, 700, 800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
