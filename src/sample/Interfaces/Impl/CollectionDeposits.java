package sample.Interfaces.Impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Interfaces.Deposits;
import sample.Objects.Deposit;

/**
 * Created by Павел on 17.04.2016.
 */
public class CollectionDeposits implements Deposits {

private ObservableList<Deposit> listDeposits= FXCollections.observableArrayList();

    @Override
    public void add(Deposit deposit){
        listDeposits.add(deposit);
    }
    @Override
    public void edit(Deposit deposit){

    }
    @Override
    public void delete(Deposit deposit){
       listDeposits.remove(deposit);
    }

    public ObservableList<Deposit> getListDeposits() {
        return listDeposits;
    }

    //    public void printTestData(){
//        for(Deposit deposit:listDeposits){
//            System.out.println(deposit);
//        }
//    }
    public void fillTestData(){
        listDeposits.add(new Deposit("Велком",6, 21.5,"Возможность пополнения вклада",50000));
        listDeposits.add(new Deposit("МТС",3, 23.0,"Возможность пополнения вклада",80000));
        listDeposits.add(new Deposit("Life",6, 18.2,"Возможность пополнения вклада",50000));
        listDeposits.add(new Deposit("Megafon",3, 13.0,"Возможность пополнения вклада",20000));
    }
}
