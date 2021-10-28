package ggc.core;
import java.io.Serializable;
import java.util.Objects;

public class Date implements Serializable{
    private int _days;

    public Date(int days){
        _days = days;
    }
    public int getDays(){
        return _days;
    }

    public Date add(int days){
        _days += days;
        return this;
    }

    public int difference(Date other){
        return Math.abs(other.getDays()-_days);
    }
    @Override
    public boolean equals(Object obj){
        return obj instanceof Partner && _days==(((Date)obj).getDays());
    }
    @Override
    public int hashCode(){
        return Objects.hash(_days);
    }

    }


