package sample.Calculations;

import sample.Objects.Payroll;

/**
 * Created by Павел on 26.04.2016.
 */
public class ClcDeposit {
    private static int []capitalization;
    public static Payroll calculateDeposit(double sum,int time,double percent){
        Payroll payroll=new Payroll();
        capitalization=new int[time];
        double firstSum=sum;
        for(int i=0;i<time;i++){
            Double value=firstSum+(firstSum*percent*30)/(366*100);
            capitalization[i]=value.intValue();
            firstSum =capitalization[i];
        }
        payroll.setSumDeposit(sum);
        payroll.setSumTotal(capitalization[capitalization.length-1]);
        payroll.setSumPercent(capitalization[capitalization.length-1]-sum);
         System.out.println(payroll);
        return payroll;
    }
}
