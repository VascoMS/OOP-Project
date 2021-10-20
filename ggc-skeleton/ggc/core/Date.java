package ggc.core;
import java.lang.Math;

public class Date {
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

<<<<<<< HEAD
    
=======
>>>>>>> b59049519b33116d6c061b7c830dab86082d917b
}
