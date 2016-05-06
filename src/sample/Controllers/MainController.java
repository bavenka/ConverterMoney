package sample.Controllers;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import sample.Calculations.ClcConvert;
import sample.Calculations.ClcDeposit;
import sample.Database.DataBase;
import sample.Database.Deserialization;
import sample.Database.Serialisation;
import sample.Interfaces.Impl.CollectionDeposits;
import sample.Objects.Deposit;
import sample.Objects.Money;
import sample.Objects.Payroll;
import sample.Parsers.ParserMoney;
import sample.Utils.DialogManager;
import sample.Validation.ImplValidation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainController {
    public static CollectionDeposits collectionDepositsImpl;
    @FXML
    private ComboBox selectDeposit;
    @FXML
    private TextField fieldTime;
    @FXML
    private TextField fieldDate;
    @FXML
    private Label labelTime;
    @FXML
    private Label labelPercent;
    @FXML
    private Label labelInfo;
    @FXML
    private Label labelMinSum;
    @FXML
    private TextField fieldSum;
    @FXML
    private TextField fieldPercent;
    @FXML
    private TextArea areaInfo;
    @FXML
    private TextField fieldMinSum;
    @FXML
    private Button buttonRezult;
    @FXML
    private MenuItem buttonDelete;
    @FXML
    private TextArea areaRezult;
    @FXML
    private Button buttonConvert;

    private Parent root;
    private AddDialogController addDialogController;
    private  DeleteDialogController deleteDialogController;
    private EditDialogController editDialogController;
    private Stage addDialogStage;
    private Stage deleteDialogStage;
    private Stage editDialogStage;
    private Stage mainStage;
    private Payroll payroll;
    private ArrayList<Money> allObjects;
    private Double[] convertSum;
    private Connection connection;


    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    private void initialize() {
        collectionDepositsImpl=new CollectionDeposits();
        //collectionDepositsImpl.setListDeposits(Deserialization.readBD("C:\\Users\\Павел\\IdeaProjects\\kurs\\src\\sample\\Database\\bd.xml"));
        try {
            connection = DataBase.getDBConnection();
            collectionDepositsImpl.setListDeposits(DataBase.getInfo(connection));
        }catch(SQLException e) {
            System.out.println("Ошибка соединения!");
        }
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        fieldDate.setText(df.format(new Date()));
        areaInfo.setWrapText(true);
        fillComboBox(collectionDepositsImpl.getListDeposits());
        collectionDepositsImpl.getListDeposits().addListener(new ListChangeListener<Deposit>() {
            @Override
            public void onChanged(Change<? extends Deposit> c) {
                selectDeposit.getItems().clear();
                fillComboBox(collectionDepositsImpl.getListDeposits());
            }
        });

        privateControls();
        ImplValidation.validSum(fieldSum);

    }

    private void privateControls() {
        labelTime.setVisible(false);
        labelPercent.setVisible(false);
        labelInfo.setVisible(false);
        labelMinSum.setVisible(false);
        areaInfo.setVisible(false);
        areaRezult.setVisible(false);
        fieldPercent.setVisible(false);
        fieldTime.setVisible(false);
        fieldMinSum.setVisible(false);
        buttonRezult.setVisible(false);
    }

    private void publicControls() {
        labelTime.setVisible(true);
        labelPercent.setVisible(true);
        labelInfo.setVisible(true);
        labelMinSum.setVisible(true);
        areaInfo.setVisible(true);
        fieldPercent.setVisible(true);
        fieldTime.setVisible(true);
        fieldMinSum.setVisible(true);
        buttonRezult.setVisible(true);

    }

    public void showElements(ActionEvent actionEvent) {

        if (selectDeposit.getValue() == "Выберите вклад") {
            privateControls();
        } else {
            fillControls(collectionDepositsImpl.getListDeposits());
            publicControls();
        }
    }

    public void fillControls(ObservableList<Deposit> allDeposits) {
        for (Deposit deposit : allDeposits) {
            if (selectDeposit.getValue() == deposit.getName()) {
                fieldTime.setText(String.valueOf(deposit.getTime()));
                fieldPercent.setText(String.valueOf(deposit.getInsertRate()));
                areaInfo.setText(deposit.getInfo());
                fieldMinSum.setText(String.valueOf(deposit.getMinSum()));
            }
        }
    }

    public void fillComboBox(ObservableList<Deposit> listDeposits) {
        selectDeposit.getItems().add(0, "Выберите вклад");
        int i = 1;
        for (Deposit deposit : listDeposits) {
            selectDeposit.getItems().add(i, deposit.getName());
            i++;
        }
    }

    public void actionMenuItemPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof MenuItem)) {
            return;
        }
        MenuItem clickedItem = (MenuItem) source;
        switch (clickedItem.getId()) {
            case "buttonAdd":
                addDialogController=new AddDialogController();
                showDialogAdd();

                if(addDialogController.getDeposit()!=null) {
                    collectionDepositsImpl.add(addDialogController.getDeposit());
                   // Serialisation.writeBD("C:\\Users\\Павел\\IdeaProjects\\kurs\\src\\sample\\Database\\bd.xml", collectionDepositsImpl.getListDeposits());
                   try {
                       DataBase.addInfo(connection, addDialogController.getDeposit());
                   }catch (SQLException e){
                       e.fillInStackTrace();
                   }
                    privateControls();
                }
                break;
            case "buttonEdit":
                editDialogController=new EditDialogController();
                showDialogEdit();
                if(editDialogController.getDeposit()!=null) {
                    collectionDepositsImpl.edit(editDialogController.getIndexDeposit(), editDialogController.getDeposit());
                   // Serialisation.writeBD("C:\\Users\\Павел\\IdeaProjects\\kurs\\src\\sample\\Database\\bd.xml", collectionDepositsImpl.getListDeposits());
                   // System.out.println(editDialogController.getDeposit());
                    try {
                        DataBase.updateInfo(connection, editDialogController.getDeposit(),editDialogController.getOldDeposit());
                       // System.out.println(editDialogController.getDeposit());
                    }catch (SQLException e){
                        e.fillInStackTrace();
                    }

                    privateControls();
                }
                break;
            case "buttonDelete":
                deleteDialogController=new DeleteDialogController();
                showDialogDelete();
                collectionDepositsImpl.delete(deleteDialogController.getDeposit());
               // Serialisation.writeBD("C:\\Users\\Павел\\IdeaProjects\\kurs\\src\\sample\\Database\\bd.xml",collectionDepositsImpl.getListDeposits());

                try {
                    DataBase.deleteInfo(connection,deleteDialogController.getDeposit());
                }catch (SQLException e){
                    e.fillInStackTrace();
                }
                privateControls();
                break;
        }
    }

    private void showDialogDelete() {
        try {
            deleteDialogStage = new Stage();
            root = FXMLLoader.load(getClass().getResource("../FXSML/delete.fxml"));
            deleteDialogStage.setScene(new Scene(root));
            deleteDialogStage.setTitle("Удаление вклада");
            deleteDialogStage.initModality(Modality.APPLICATION_MODAL);
            deleteDialogStage.initOwner(mainStage);
            deleteDialogStage.showAndWait();
        } catch (IOException e) {
            System.out.println("Файл не наден!");
        }
    }
    private void showDialogAdd() {
        try {
            addDialogStage = new Stage();
            root = FXMLLoader.load(getClass().getResource("../FXSML/add.fxml"));
            addDialogStage.setScene(new Scene(root));
            addDialogStage.setTitle("Добавление вклада");
            addDialogStage.initModality(Modality.APPLICATION_MODAL);
            addDialogStage.initOwner(mainStage);
            addDialogStage.showAndWait();
        } catch (IOException e) {
            System.out.println("Файл не наден!");
        }
    }

    private void showDialogEdit() {
        try {
            editDialogStage = new Stage();
            root = FXMLLoader.load(getClass().getResource("../FXSML/edit.fxml"));
            editDialogStage.setScene(new Scene(root));
            editDialogStage.setTitle("Редактирование вклада");
            editDialogStage.initModality(Modality.APPLICATION_MODAL);
            editDialogStage.initOwner(mainStage);
            editDialogStage.showAndWait();
        } catch (IOException e) {
            System.out.println("Файл не наден!");
        }
    }

    private Deposit choiceDeposit(ObservableList<Deposit>deposits){
        Deposit deposit=new Deposit();
        for(Deposit dep:deposits){
            if(dep.getName().equals(selectDeposit.getValue())){
                deposit=dep;
            }
        }
        return deposit;
    }

    public void actionCalculate(ActionEvent actionEvent) {
        Deposit deposit=choiceDeposit(collectionDepositsImpl.getListDeposits());
        if(fieldSum.getText().trim().length()==0){
            DialogManager.showErrorDialog("Ошибка","Сумма вклада должна быть числом!");
        }
        else if(Integer.parseInt(fieldSum.getText())<deposit.getMinSum()){
            DialogManager.showInfoDialog("Информация","Минимальная сумма вклада: "+deposit.getMinSum());
        }
        else {
            payroll = ClcDeposit.calculateDeposit(Double.parseDouble(fieldSum.getText()), Integer.parseInt(fieldTime.getText()), Double.parseDouble(fieldPercent.getText()));
            String s = " ";
            s = "Вклад: " + selectDeposit.getValue() + "\r\n" +
                    "Сумма вклада: " + fieldSum.getText() + "\r\n" +
                    "Сумма процентов на день наступления срока возврата вклада: " + payroll.getSumPercent().intValue() + "\r\n" +
                    "Общая сумма на день возврата вклада: " + payroll.getSumTotal().intValue();
            areaRezult.setText(s);
            areaRezult.setVisible(true);
        }
    }

    public void actionConvert(ActionEvent actionEvent) {
        try {
            Document doc = Jsoup.connect("http://www.nbrb.by/statistics/rates/ratesdaily.asp").get();
            allObjects = ParserMoney.parseHTML(doc);
        } catch (IOException e) {
            System.out.println("Ошибка соединения!");
            return;
        }finally {
            System.out.println("Соединение с сервером установлено!");
        }
       // ClcConvert.convertPayroll(allObjects,payroll.getSumTotal());
        convertSum=ClcConvert.convertPayroll(allObjects,payroll.getSumTotal());
String s=null;
        for(int i=0;i<allObjects.size();i++){
            s+=allObjects.get(i).getCode()+" "+convertSum[i]+"\r\n";
        }
        areaRezult.setText(s);
    }
}
