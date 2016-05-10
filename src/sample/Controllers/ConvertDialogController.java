package sample.Controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import sample.Calculations.ClcConvert;
import sample.Objects.Money;
import sample.Parsers.ParserMoney;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Павел on 10.05.2016.
 */
public class ConvertDialogController {
    private ArrayList<Money> allMoney;
    @FXML
    private ComboBox selectInputCode;
    @FXML
    private ComboBox selectOutputCode;
    @FXML
    private TextField inputSum;
    @FXML
    private TextField outputSum;
    @FXML
    private void initialize(){
        try {
            Document doc = Jsoup.connect("http://www.nbrb.by/statistics/rates/ratesdaily.asp").get();
            allMoney = ParserMoney.parseHTML(doc);
        } catch (IOException e) {
            System.out.println("Ошибка соединения!");
            return;
        }finally {
            System.out.println("Соединение с сервером установлено!");
        }
        fillComboBoxInputCodes(allMoney);
        fillComboBoxOutputCodes(allMoney);
    }
    public void actionConvert(ActionEvent actionEvent) {
        Double digit=ClcConvert.convertSum(selectInputCode.getValue().toString(),selectOutputCode.getValue().toString(),Integer.parseInt(inputSum.getText()),allMoney);
        outputSum.setText(String.format("#0.000",digit.toString()));
    }

    private void checkSelectCode(){
        ArrayList<Object> objects=new ArrayList<>(selectOutputCode.getItems());
       for(Object item:objects){
           if(selectInputCode.getValue().equals(item)){
               int index=selectOutputCode.getItems().indexOf(item);
               selectOutputCode.getItems().remove(index);
           }
       }
    }

    public void actionClose(ActionEvent actionEvent) {
        Node source =(Node) actionEvent.getSource();
        Stage stage=(Stage) source.getScene().getWindow();
        stage.hide();
    }

    private void fillComboBoxInputCodes(ArrayList<Money>allMoney){
        selectInputCode.getItems().add(0,"Выберите код");
        selectInputCode.getItems().add(1,"BLR");
        int i=2;
        for(Money money:allMoney){
            selectInputCode.getItems().add(i,money.getCode());
            i++;
        }
    }

    private void fillComboBoxOutputCodes(ArrayList<Money>allMoney){
        selectOutputCode.getItems().add(0,"Выберите код");
        selectOutputCode.getItems().add(1,"BLR");
        int i=2;
        for(Money money:allMoney){
            selectOutputCode.getItems().add(i,money.getCode());
            i++;
        }
    }

    public void actionSelectCode(ActionEvent actionEvent) {
        fillComboBoxOutputCodes(allMoney);
        checkSelectCode();
    }
}
