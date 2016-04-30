package sample.Interfaces.Impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Interfaces.Deposits;
import sample.Objects.Deposit;

/**
 * Created by Павел on 17.04.2016.
 */
public class CollectionDeposits implements Deposits {

public static ObservableList<Deposit> listDeposits= FXCollections.observableArrayList();

    @Override
    public void add(Deposit deposit){
        listDeposits.add(deposit);
    }
    @Override
    public void edit(int index,Deposit deposit){
        listDeposits.set(index,deposit);
    }
    @Override
    public void delete(Deposit deposit){
        listDeposits.remove(deposit);
    }

    public ObservableList<Deposit> getListDeposits() {
        return listDeposits;
    }

    public void setListDeposits(ObservableList<Deposit> listDeposits) {
        this.listDeposits = listDeposits;
    }
}
