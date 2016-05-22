package sample.Controllers;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Objects.Deposit;
import sample.Utils.DialogManager;
import sample.Validation.ImplValidation;

/**
 * Created by Павел on 13.04.2016.
 */
public class AddDialogController {
    @FXML
    private Button buttonClose;
    @FXML
    private TextField fieldCreateName;
    @FXML
    private TextField fieldCreateTime;
    @FXML
    private TextField fieldCreatePercent;
    @FXML
    private TextArea areaCreateInfo;
    @FXML
    private TextField fieldCreateMinSum;
    @FXML
    private void initialize(){
        areaCreateInfo.setWrapText(true);
        ImplValidation.validName(fieldCreateName);
        ImplValidation.validTime(fieldCreateTime);
        ImplValidation.validPercent(fieldCreatePercent);
        ImplValidation.validMinSum(fieldCreateMinSum);
    }
    private static Deposit deposit=null;

    public Deposit getDeposit(){
        return deposit;
    }
    public void setDeposit(Deposit deposit) {
        this.deposit=deposit;
    }

    public void closeDialog(ActionEvent actionEvent) {
        Node source =(Node) actionEvent.getSource();
        Stage stage=(Stage) source.getScene().getWindow();
        stage.hide();
    }
    public static boolean checkEqualsNameObject(ObservableList<Deposit>deposits){
        for(Deposit dep:deposits){
            if(dep.getName().equals(deposit.getName())){
                return false;
            }
        }
        return true;
    }
    public void actionAdd(ActionEvent actionEvent) {
        if(fieldCreateMinSum.getText().length()==0 || fieldCreateName.getText().length()==0 || fieldCreatePercent.getText().length()==0 || fieldCreateTime.getText().length()==0 || areaCreateInfo.getText().length()==0){

            DialogManager.showErrorDialog("Ошибка","Поля не могут быть пустыми!");
        }
        else {
            deposit = new Deposit();
            deposit.setName(fieldCreateName.getText());
            deposit.setTime(Integer.parseInt(fieldCreateTime.getText()));
            deposit.setInsertRate(Double.parseDouble(fieldCreatePercent.getText()));
            deposit.setInfo(areaCreateInfo.getText());
            deposit.setMinSum(Integer.parseInt(fieldCreateMinSum.getText()));
            if(checkEqualsNameObject(UserDialogController.collectionDepositsImpl.getListDeposits())==true){
                closeDialog(actionEvent);
            }
            else{
                DialogManager.showInfoDialog("Информация","Такой депозит уже присутствует!");
                deposit=null;
            }
        }
    }
}
