package sample.Calculations;

import sample.Objects.Money;

import java.util.ArrayList;

/**
 * Created by Павел on 26.04.2016.
 */
public class ClcConvert {
    public static Double convertSum(String firstCode,String  secondCode,Integer inputSum,ArrayList<Money>allMoney){
        Double total=0.0;
        for(Money money:allMoney) {
            if (firstCode.equals("BLR") && secondCode.equals(money.getCode())) {
                total = inputSum.doubleValue() / money.getCourse();
            } else if (firstCode.equals(money.getCode()) && secondCode.equals("BLR")) {
                total = inputSum.doubleValue() * money.getCourse();
            }
            else if(money.getCode().equals(firstCode)){
               for(Money m:allMoney){
                   if(m.getCode().equals(secondCode)){
                       total=inputSum.doubleValue()*money.getCourse()/m.getCourse();
                   }
               }

            }
        }
        return total;
    }
}
