package ggc.core;

import java.io.Serializable;

public class SelectionStatus implements PartnerStatus, Serializable{

    public double computeFine(Date currentDate, Date deadline, int period){
        /*if(currentDate.difference(deadline) >= period)
            return 0;
        if(0 <= currentDate.difference(deadline) && currentDate.difference(deadline) <= period)
            return 0;
        */
        if(1 < deadline.difference(currentDate) && deadline.difference(currentDate) <= period)
            return 0.02 * deadline.difference(currentDate);
        if(deadline.difference(currentDate) > period)
            return 0.05*deadline.difference(currentDate);
        return 0;
    }

    public double computeDiscount(Date currentDate, Date deadline, int period){
        if(currentDate.difference(deadline) >= period)
            return 0.1;
        if(2 <= currentDate.difference(deadline) && currentDate.difference(deadline) <= period)
            return 0.05;
        return 0;
    }

    @Override

    public String toString(){
        return "SELECTION";
    }
}

