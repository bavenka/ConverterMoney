package sample.Objects;

/**
 * Created by Павел on 27.04.2016.
 */
public class Payroll {
    private Double sumDeposit;
    private Double sumPercent;
    private Double sumTotal;

    public double getSumDeposit() {
        return sumDeposit;
    }

    public void setSumDeposit(double sumDeposit) {
        this.sumDeposit = sumDeposit;
    }

    public Double getSumPercent() {
        return sumPercent;
    }

    public void setSumPercent(double sumPercent) {
        this.sumPercent = sumPercent;
    }

    public Double getSumTotal() {
        return sumTotal;
    }

    public void setSumTotal(double sumTotal) {
        this.sumTotal = sumTotal;
    }

    @Override
    public String toString() {
        return "Payroll{" +
                "sumDeposit=" + sumDeposit +
                ", sumPercent=" + sumPercent +
                ", sumTotal=" + sumTotal +
                '}';
    }
}
