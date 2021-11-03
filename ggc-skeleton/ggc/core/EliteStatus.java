package ggc.core;

import java.io.Serializable;

public class EliteStatus implements PartnerStatus, Serializable{
    public double computeFine(Date currentDate, Date deadline, int period){
        return 0;
    }

    public double computeDiscount(Date currentDate, Date deadline, int period){
        if(currentDate.difference(deadline) >= period)
            return 0.1;
        if(0 <= currentDate.difference(deadline) && currentDate.difference(deadline) <= period)
            return 0.1;
        if(0 <= currentDate.difference(deadline) && currentDate.difference(deadline) <= period)
            return 0.05;
        return 0;
    }

    @Override

    public String toString(){
        return "ELITE";
    }
}
