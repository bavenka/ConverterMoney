package sample.Controllers;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Calculations.ClcDeposit;
import sample.Database.Connect;
import sample.Database.Operations;
import sample.Interfaces.Impl.CollectionDeposits;
import sample.Objects.Deposit;
import sample.Objects.Money;
import sample.Objects.Payroll;
import sample.Utils.DialogManager;
import sample.Validation.ImplValidation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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

    private Parent root;
    private AddDialogController addDialogController;
    private  DeleteDialogController deleteDialogController;
    private EditDialogController editDialogController;
    private ConvertDialogController convertDialogController;
    private SearchDialogController searchDialogController;
    private Stage addDialogStage;
    private Stage deleteDialogStage;
    private Stage editDialogStage;
    private Stage convertDialogStage;
    private Stage searchDialogStage;
    private Stage mainStage;
    private Payroll payroll;
    private ArrayList<Money> allObjects;
    private Double[] convertSum;
    private static Connection connection;
    private Calendar calendar;


    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    private void initialize() {
        collectionDepositsImpl=new CollectionDeposits();
        try {
            connection = Connect.getDBConnection();
            collectionDepositsImpl.setListDeposits(Operations.getInfo(connection));
        }catch(SQLException e) {
            System.out.println("Ошибка соединения!");
        }
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        calendar = Calendar.getInstance();
        fieldDate.setText(df.format(calendar.getTime()));
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
                   try {
                       Operations.addInfo(connection, addDialogController.getDeposit());
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
                    try {
                        Operations.updateInfo(connection, editDialogController.getDeposit(),editDialogController.getOldDeposit());
                    }catch (SQLException e){
                        e.fillInStackTrace();
                    }

                    privateControls();
                }
                break;
            case "buttonDelete":
                deleteDialogController=new DeleteDialogController();
                showDialogDelete();
                if(deleteDialogController.getDeposit()!=null) {
                    collectionDepositsImpl.delete(deleteDialogController.getDeposit());
                    try {
                        Operations.deleteInfo(connection, deleteDialogController.getDeposit());
                    } catch (SQLException e) {
                        e.fillInStackTrace();
                    }
                    privateControls();
                }
                break;
            case "buttonConvert":
                convertDialogController=new ConvertDialogController();
                showDialogConvert();
                break;
            case "buttonSearch":
                searchDialogController=new SearchDialogController();
                showDialogSearch();
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

    private void showDialogConvert() {
        try {
            convertDialogStage = new Stage();
            root = FXMLLoader.load(getClass().getResource("../FXSML/convert.fxml"));
            convertDialogStage.setScene(new Scene(root));
            convertDialogStage.setTitle("Конвертер валют");
            convertDialogStage.getIcons().add(new Image("file:C:\\Users\\Павел\\IdeaProjects\\kurs\\src\\sample\\resources\\convert.png"));
            convertDialogStage.initModality(Modality.APPLICATION_MODAL);
            convertDialogStage.initOwner(mainStage);
            convertDialogStage.showAndWait();
        } catch (IOException e) {
            DialogManager.showInfoDialog("Информация","Не удаётся соединиться с сервером!");
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

    private void showDialogSearch() {
        try {
            searchDialogStage = new Stage();
            root = FXMLLoader.load(getClass().getResource("../FXSML/search.fxml"));
            searchDialogStage.setScene(new Scene(root));
            searchDialogStage.setTitle("Поиск депозита");
            searchDialogStage.initModality(Modality.APPLICATION_MODAL);
            searchDialogStage.initOwner(mainStage);
            searchDialogStage.showAndWait();
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
            payroll = ClcDeposit.calculateDeposit(Double.parseDouble(fieldSum.getText()), Integer.parseInt(fieldTime.getText()), Double.parseDouble(fieldPercent.getText()),calendar);
            String s = " ";
            s = "Вклад: " + selectDeposit.getValue() + "\r\n" +
                    "Сумма вклада: " + fieldSum.getText() + "\r\n" +
                    "Сумма процентов на день наступления срока возврата вклада: " + payroll.getSumPercent().intValue() + "\r\n" +
                    "Общая сумма на день возврата вклада: " + payroll.getSumTotal().intValue();
            areaRezult.setText(s);
            areaRezult.setVisible(true);
        }
    }
}
