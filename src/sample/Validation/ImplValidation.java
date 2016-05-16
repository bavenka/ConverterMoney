package sample.Validation;


import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import sample.Utils.DialogManager;

/**
 * Created by Павел on 01.05.2016.
 */
public class ImplValidation {
    public static void validPercent(TextField textField){
        textField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) {
                if (!textField.getText().matches("[1-9]{1,2}\\.[0-9]")) {
                    textField.setText("");
                }
            }
        });
    }

    public static void validName(TextField textField){
        textField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) {
                if (!textField.getText().matches("[ \\-а-яА-Я()1-9]+")) {
                    textField.setText("");
                }
            }
        });
    }

    public static void validTime(TextField textField){
        textField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (!event.getCharacter().matches("\\d|\b")) {
                    DialogManager.showErrorDialog("Ошибка","Поле предусмотрено только для ввода цифр!");
                    event.consume();
                }
                else if(textField.getText().length()>=2){
                    DialogManager.showErrorDialog("Ошибка","Значение времени не должно превышать двух знаков!");
                    event.consume();
                }
            }
        });
    }

    public static void validSum(TextField textField){
        textField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (!event.getCharacter().matches("\\d|\b")) {
                    DialogManager.showErrorDialog("Ошибка","Поле предусмотрено только для ввода цифр!");
                    event.consume();
                }
                else if(textField.getText().length()>=18){
                    DialogManager.showErrorDialog("Ошибка","Значение суммы не должно превышать восемнадцати знаков!");
                    event.consume();
                }
            }
        });
    }

    public static void validMinSum(TextField textField){
        textField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!event.getCharacter().matches("\\d|\b")) {
                    DialogManager.showErrorDialog("Ошибка","Поле предусмотрено только для ввода цифр!");
                    event.consume();
                }
                else if(textField.getText().length()>=7){
                    DialogManager.showErrorDialog("Ошибка","Значение минимальной суммы не должно превышать семи знаков!");
                    event.consume();
                }
            }
        });
    }
}
