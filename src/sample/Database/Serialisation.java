package sample.Database;

import javafx.collections.ObservableList;
import sample.Objects.Deposit;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Павел on 10.04.2016.
 */
public class Serialisation {
    public static void writeBD(String fileName, ObservableList<Deposit> allObjects){
        try{
            XMLEncoder writer=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)));
            writer.writeObject(allObjects);
            writer.close();
        }catch (FileNotFoundException e){
            System.out.println("Файл не найден!");
        }
    }
}
