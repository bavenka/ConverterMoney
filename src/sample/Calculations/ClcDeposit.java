package sample.Calculations;

import sample.Objects.Payroll;

import java.util.Calendar;

/**
 * Created by Павел on 26.04.2016.
 */
public class ClcDeposit {
    private static int []capitalization;
    public static Payroll calculateDeposit(double sum, int time, double percent, Calendar calendar){
        Payroll payroll=new Payroll();
        capitalization=new int[time];
        double firstSum=sum;
        for(int i=0;i<time;i++){
            Double value=firstSum+(firstSum*percent*calendar.getActualMaximum(Calendar.DAY_OF_MONTH))/(366*100);
            capitalization[i]=value.intValue();
            firstSum =capitalization[i];
            calendar.add(Calendar.MONTH,i+1);
        }
        payroll.setSumDeposit(sum);
        payroll.setSumTotal(capitalization[capitalization.length-1]);
        payroll.setSumPercent(capitalization[capitalization.length-1]-sum);
        return payroll;
    }
}
