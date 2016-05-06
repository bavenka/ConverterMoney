package sample.Calculations;

import sample.Objects.Money;

import java.util.ArrayList;

/**
 * Created by Павел on 26.04.2016.
 */
public class ClcConvert {
    public static Double[] convertPayroll(ArrayList<Money>allMoney,Double sumTotal){
        Double[] convertSum=new Double[allMoney.size()];
        for(int i=0;i<allMoney.size();i++){
            convertSum[i]=sumTotal/allMoney.get(i).getCourse();
        }
        return convertSum;
    }
}
