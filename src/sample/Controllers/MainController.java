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
import sample.Interfaces.Impl.CollectionDeposits;
import sample.Objects.Deposit;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainController {
    private CollectionDeposits collectionDepositsImpl = new CollectionDeposits();
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
    private Button rezult;

    private Parent fxmlAdd;
    private Parent fxmlDelete;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private AddDialogController addDialogController;
    private DeleteDialogController deleteDialogController;
    private Stage addDialogStage;
    private Stage deleteDialogStage;
    private Stage mainStage;
public void setMainStage(Stage mainStage){
    this.mainStage=mainStage;
}

    @FXML
    private void initialize() {
        collectionDepositsImpl.fillTestData();
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
       // initLoaderAdd();
        initLoaderDelete();
    }
private void initLoaderAdd(){
    try {
        fxmlLoader.setLocation(getClass().getResource("../FXSML/add.fxml"));
        fxmlAdd = fxmlLoader.load();
        addDialogController = fxmlLoader.getController();
    } catch (IOException e) {
        System.out.println("Файл не найден!");
    }
}
    private void initLoaderDelete(){
        try {
            fxmlLoader.setLocation(getClass().getResource("../FXSML/delete.fxml"));
            fxmlDelete = fxmlLoader.load();
            deleteDialogController = fxmlLoader.getController();
        } catch (IOException e) {
            System.out.println("Файл не найден!");
        }
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
        rezult.setVisible(false);

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
        rezult.setVisible(true);

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
                fieldTime.setText(String.valueOf(deposit.getTime()) + " месяц(а/ев)");
                fieldPercent.setText(String.valueOf(deposit.getInsertRate()) + " %");
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
                addDialogController.setDeposit(new Deposit());
                showDialogAdd();
                collectionDepositsImpl.add(addDialogController.getDeposit());
                break;
            case "buttonEdit":
                System.out.println("edit");
                break;
            case "buttonDelete":
               // deleteDialogController.setDeposit(new Deposit());
                showDialogDelete();
                collectionDepositsImpl.delete(deleteDialogController.getDeposit());
                System.out.println(deleteDialogController.getDeposit());
               privateControls();
                break;
        }
    }

    private void showDialogAdd() {
        if (addDialogStage == null) {
            addDialogStage = new Stage();
            addDialogStage.setTitle("Добавление вклада");
            addDialogStage.setScene(new Scene(fxmlAdd));
            addDialogStage.initModality(Modality.WINDOW_MODAL);
            addDialogStage.initOwner(mainStage);
        }
            addDialogStage.showAndWait();

    }
    private void showDialogDelete(){
        if(deleteDialogStage==null){
            deleteDialogStage = new Stage();
            deleteDialogStage.setTitle("Удаление вклада");
            deleteDialogStage.setScene(new Scene(fxmlDelete));
            deleteDialogStage.initModality(Modality.WINDOW_MODAL);
            deleteDialogStage.initOwner(mainStage);
        }
        deleteDialogStage.showAndWait();
        }
    }





