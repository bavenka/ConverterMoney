package sample.Objects;

/**
 * Created by Павел on 26.04.2016.
 */
public class Money {
    private String code;
    private String name;
    private int course;
    public Money(){

    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "Money{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", course=" + course +
                '}';
    }
}
