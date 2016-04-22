package sample.Objects;

/**
 * Created by Павел on 10.04.2016.
 */
public class Deposit {
    private String name;
    private int time;
    private double insertRate;
    private String info;
    private int minSum;

    public Deposit(String name,int time, Double insertRate, String info, int minSum) {
        this.name=name;
        this.time = time;
        this.insertRate = insertRate;
        this.info = info;
        this.minSum = minSum;
    }
    public Deposit(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Double getInsertRate() {
        return insertRate;
    }

    public void setInsertRate(Double insertRate) {
        this.insertRate = insertRate;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getMinSum() {
        return minSum;
    }

    public void setMinSum(int minSum) {
        this.minSum = minSum;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "name='" + name + '\'' +
                ", time=" + time +
                ", insertRate=" + insertRate +
                ", info='" + info + '\'' +
                ", minSum=" + minSum +
                '}';
    }
}
