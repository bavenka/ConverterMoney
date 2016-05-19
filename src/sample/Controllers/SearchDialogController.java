package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Objects.Deposit;
import sample.Utils.DialogManager;
import sample.Validation.ImplValidation;

import java.util.ArrayList;

/**
 * Created by Павел on 17.05.2016.
 */
public class SearchDialogController {
    @FXML
    private TextField fieldName;
    @FXML
    private TextField fieldTime;
    @FXML
    private TextField fieldPercent;
    @FXML
    private TextArea areaInfo;
    @FXML
    private TextField fieldMinSum;
    @FXML
    private TextArea areaMain;
    @FXML
    private Button buttonSearch;
    @FXML
    private ComboBox selectDeposit;
    private ObservableList<Deposit> allDeposits;
    private ArrayList<Deposit>searchedDeposits;
    @FXML
    private void initialize(){
        ImplValidation.validName(fieldName);
        ImplValidation.validTime(fieldTime);
        ImplValidation.validPercent(fieldPercent);
        ImplValidation.validMinSum(fieldMinSum);
        searchedDeposits= new ArrayList<>();
        areaInfo.setWrapText(true);
        areaMain.setWrapText(true);
        allDeposits=MainController.collectionDepositsImpl.getListDeposits();
    }

    public void actionClose(ActionEvent actionEvent) {
        Node source =(Node) actionEvent.getSource();
        Stage stage=(Stage) source.getScene().getWindow();
        stage.close();
    }

    public void actionSearch(ActionEvent actionEvent) {
        selectDeposit.getItems().clear();
        areaMain.clear();
        if(fieldName.getText().length()!=0 || fieldTime.getText().length()!=0 || fieldPercent.getText().length()!=0 || areaInfo.getText().length()!=0 || fieldMinSum.getText().length()!=0) {
            for (Deposit deposit : allDeposits) {
                if ((fieldName.getText().length() == 0 || deposit.getName().equals(fieldName.getText()))
                        && (fieldTime.getText().length() == 0 || deposit.getTime() == Integer.parseInt(fieldTime.getText()))
                        && (fieldPercent.getText().length() == 0 || deposit.getInsertRate() == Double.parseDouble(fieldPercent.getText()))
                        && (areaInfo.getText().length() == 0 || deposit.getInfo().equals(areaInfo.getText()))
                        && (fieldMinSum.getText().length() == 0 || deposit.getMinSum() == Integer.parseInt(fieldMinSum.getText()))) {
                    // searchedDeposits.add(deposit);
                    selectDeposit.getItems().add(deposit.getName());
                }
            }
            if(selectDeposit.getItems().isEmpty()){
                DialogManager.showInfoDialog("Информация","Совпадений не найдено!");
            }
        }else DialogManager.showInfoDialog("Информация","Заполните хотя бы одно поле!");
    }
    public void actionSelectDeposit(ActionEvent actionEvent) {
        String s=" ";
        for(Deposit deposit:allDeposits){
            if(!selectDeposit.getItems().isEmpty() && selectDeposit.getValue().equals(deposit.getName())){
                s=      "Название вклада: "+deposit.getName()+"\r\n"+
                        "Процкнтная ставка: "+deposit.getInsertRate()+"\r\n"+
                        "Информация о вкладе: "+deposit.getInfo()+"\r\n"+
                        "Минимальная сумма вкладов: "+deposit.getMinSum();
                areaMain.setText(s);
            }
        }
    }
}
