package sample.Interfaces;

import sample.Objects.Deposit;

/**
 * Created by Павел on 10.04.2016.
 */
public interface Deposits {
    void add(Deposit deposit);
    void edit(int index,Deposit deposit);
    void delete(Deposit deposit);
}
