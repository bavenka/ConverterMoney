package sample.Controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Objects.Deposit;
/**
 * Created by Павел on 23.04.2016.
 */
public class EditDialogController {
    @FXML
    private Label labelName;
    @FXML
    private Label labelTime;
    @FXML
    private Label labelPercent;
    @FXML
    private Label labelInfo;
    @FXML
    private Label labelMinSum;
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
    private Button buttonEdit;
    @FXML
    private Button buttonClose;
    @FXML
    private ComboBox selectDeposit;
    private static Deposit deposit;
    private static int indexDeposit;

    public  void setIndexDeposit(int indexDeposit) {
        this.indexDeposit = indexDeposit;
    }

    public void setDeposit(Deposit deposit){
        this.deposit=deposit;
    }
    public Deposit getDeposit(){
        return  deposit;
    }

    public  int getIndexDeposit() {
        return indexDeposit;
    }

    @FXML
    private void initialize(){
        fillComboBoxEdit(MainController.collectionDepositsImpl.getListDeposits());
        privateControls();

    }
    private void privateControls(){
        labelName.setVisible(false);
        labelTime.setVisible(false);
        labelPercent.setVisible(false);
        labelInfo.setVisible(false);
        labelMinSum.setVisible(false);
        fieldName.setVisible(false);
        fieldPercent.setVisible(false);
        fieldTime.setVisible(false);
        fieldMinSum.setVisible(false);
        areaInfo.setVisible(false);
        buttonClose.setVisible(false);
        buttonEdit.setVisible(false);
    }
    private void publicControls(){
        labelName.setVisible(true);
        labelTime.setVisible(true);
        labelPercent.setVisible(true);
        labelInfo.setVisible(true);
        labelMinSum.setVisible(true);
        fieldName.setVisible(true);
        fieldPercent.setVisible(true);
        fieldTime.setVisible(true);
        fieldMinSum.setVisible(true);
        areaInfo.setVisible(true);
        buttonClose.setVisible(true);
        buttonEdit.setVisible(true);
    }
    private void fillComboBoxEdit(ObservableList<Deposit> listDeposits){
        selectDeposit.getItems().add(0, "Выберите вклад");
        int i = 1;
        for (Deposit deposit : listDeposits) {
            selectDeposit.getItems().add(i, deposit.getName());
            i++;
        }
    }

    public void fillControls(ObservableList<Deposit> allDeposits) {
        for (Deposit deposit : allDeposits) {
            if (selectDeposit.getValue() == deposit.getName()) {
                fieldName.setText(deposit.getName());
                fieldTime.setText(String.valueOf(deposit.getTime()));
                fieldPercent.setText(String.valueOf(deposit.getInsertRate()));
                areaInfo.setText(deposit.getInfo());
                fieldMinSum.setText(String.valueOf(deposit.getMinSum()));
            }
        }
    }

    public void comboBoxEdit(ActionEvent actionEvent) {
        if (selectDeposit.getValue() == "Выберите вклад") {
            privateControls();
        } else {
            fillControls(MainController.collectionDepositsImpl.getListDeposits());
            publicControls();
        }
    }

    public void actionEdit(ObservableList<Deposit>allDeposits){
        for(Deposit deposit:allDeposits){
            if(selectDeposit.getValue().equals(deposit.getName())){
                setIndexDeposit(allDeposits.indexOf(deposit));
            }
        }
    }

    public void editDeposit(ActionEvent actionEvent) {
        actionEdit(MainController.collectionDepositsImpl.getListDeposits());
        deposit.setName(fieldName.getText());
        deposit.setTime(Integer.parseInt(fieldTime.getText()));
        deposit.setInsertRate(Double.parseDouble(fieldPercent.getText()));
        deposit.setInfo(areaInfo.getText());
        deposit.setMinSum(Integer.parseInt(fieldMinSum.getText()));
        closeDialog(actionEvent);
    }

    public void closeDialog(ActionEvent actionEvent) {
        Node source =(Node) actionEvent.getSource();
        Stage stage=(Stage) source.getScene().getWindow();
        stage.hide();
    }
}
