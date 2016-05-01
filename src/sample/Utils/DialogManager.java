package sample.Utils;

import javafx.scene.control.Alert;

/**
 * Created by Павел on 01.05.2016.
 */
public class DialogManager {
    public static void showInfoDialog(String title,String text){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setHeaderText("");
        alert.showAndWait();
    }
    public static void showErrorDialog(String title,String text){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(text);
        // alert.setHeaderText("");
        alert.showAndWait();
    }
}
