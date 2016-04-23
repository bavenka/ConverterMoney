package sample.Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Interfaces.Impl.CollectionDeposits;
import sample.Objects.Deposit;

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
//private CollectionDeposits collectionDeposits=new CollectionDeposits();
    private static Deposit deposit;

    public Deposit getDeposit(){
        return deposit;
    }
    public void setDeposit(Deposit deposit) {
        this.deposit=deposit;
    }

//        if(deposit==null){
//            return;
//        }
//        this.deposit=deposit;
//        fieldCreateName.setText(deposit.getName());
//        fieldCreateTime.setText(String.valueOf(deposit.getTime()));
//        fieldCreatePercent.setText(String.valueOf(deposit.getInsertRate()));
//        areaCreateInfo.setText(deposit.getInfo());
//        fieldCreateMinSum.setText(String.valueOf(deposit.getMinSum()));
//    }



    public void closeDialog(ActionEvent actionEvent) {
        Node source =(Node) actionEvent.getSource();
        Stage stage=(Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void actionAdd(ActionEvent actionEvent) {
        deposit.setName(fieldCreateName.getText());
        deposit.setTime(Integer.parseInt(fieldCreateTime.getText()));
        deposit.setInsertRate(Double.parseDouble(fieldCreatePercent.getText()));
        deposit.setInfo(areaCreateInfo.getText());
        deposit.setMinSum(Integer.parseInt(fieldCreateMinSum.getText()));
        closeDialog(actionEvent);
    }
}
