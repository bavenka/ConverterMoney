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
import sample.Calculations.ClcDeposit;
import sample.Database.Deserialization;
import sample.Database.Serialisation;
import sample.Interfaces.Impl.CollectionDeposits;
import sample.Objects.Deposit;
import sample.Objects.Payroll;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    private Parent root;
    private AddDialogController addDialogController;
    private  DeleteDialogController deleteDialogController;
    private EditDialogController editDialogController;
    private Stage addDialogStage;
    private Stage deleteDialogStage;
    private Stage editDialogStage;
    private Stage mainStage;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    private void initialize() {
        collectionDepositsImpl=new CollectionDeposits();
        collectionDepositsImpl.setListDeposits(Deserialization.readBD("C:\\Users\\Павел\\IdeaProjects\\kurs\\src\\sample\\Database\\bd.xml"));
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        fieldDate.setText(df.format(new Date()));
        fillComboBox(collectionDepositsImpl.getListDeposits());
        collectionDepositsImpl.getListDeposits().addListener(new ListChangeListener<Deposit>() {
            @Override
            public void onChanged(Change<? extends Deposit> c) {
                selectDeposit.getItems().clear();
                fillComboBox(collectionDepositsImpl.getListDeposits());
            }
        });

        privateControls();
        fieldSum.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!event.getCharacter().matches("\\d")) {
                    event.consume();
                }
            }
        });

    }

    private void privateControls() {
        labelTime.setVisible(false);
        labelPercent.setVisible(false);
        labelInfo.setVisible(false);
        labelMinSum.setVisible(false);
        areaInfo.setVisible(false);

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
                addDialogController.setDeposit(new Deposit());
                showDialogAdd();
                collectionDepositsImpl.add(addDialogController.getDeposit());
                Serialisation.writeBD("C:\\Users\\Павел\\IdeaProjects\\kurs\\src\\sample\\Database\\bd.xml",collectionDepositsImpl.getListDeposits());
                for(Deposit dep:collectionDepositsImpl.getListDeposits()){
                    System.out.println(dep);
                }
               privateControls();
                break;
            case "buttonEdit":
                editDialogController=new EditDialogController();
                editDialogController.setDeposit(new Deposit());
                showDialogEdit();
                collectionDepositsImpl.edit(editDialogController.getIndexDeposit(), editDialogController.getDeposit());
                Serialisation.writeBD("C:\\Users\\Павел\\IdeaProjects\\kurs\\src\\sample\\Database\\bd.xml",collectionDepositsImpl.getListDeposits());
               privateControls();
                break;
            case "buttonDelete":
                deleteDialogController=new DeleteDialogController();
                 showDialogDelete();
                collectionDepositsImpl.delete(deleteDialogController.getDeposit());
                Serialisation.writeBD("C:\\Users\\Павел\\IdeaProjects\\kurs\\src\\sample\\Database\\bd.xml",collectionDepositsImpl.getListDeposits());
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

    public void actionCalculate(ActionEvent actionEvent) {
        Payroll payroll=ClcDeposit.calculateDeposit(Double.parseDouble(fieldSum.getText()),Integer.parseInt(fieldTime.getText()),Double.parseDouble(fieldPercent.getText()));
        String s=" ";
        s="Сумма вклада: "+fieldSum.getText()+"\r\n"+
                "Сумма процентов на день наступления срока возврата вклада: "+payroll.getSumPercent().intValue()+"\r\n"+
                "Общая сумма на день возврата вклада: "+payroll.getSumTotal().intValue();
        areaRezult.setText(s);
    }

    public void startServer(ActionEvent actionEvent) {
    }
}
