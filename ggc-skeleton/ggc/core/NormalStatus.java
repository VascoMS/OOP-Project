package ggc.core;

import java.io.Serializable;

public class NormalStatus implements PartnerStatus, Serializable{
    private static NormalStatus _status;

    private NormalStatus(){}

    public static NormalStatus getInstance()
    {
        if (_status == null)
            _status = new NormalStatus();
 
        return _status;
    }
    
    public double computeFine(Date currentDate, Date deadline, int period){
        /*if(currentDate.difference(deadline) >= period)
            return 0;
        if(0 <= currentDate.difference(deadline) && currentDate.difference(deadline) <= period)
            return 0;
        */
        if(0 < deadline.difference(currentDate) && deadline.difference(currentDate) <= period)
            return 0.05 * deadline.difference(currentDate);
        if(deadline.difference(currentDate) > period)
            return 0.1*deadline.difference(currentDate);
        return 0;
    }

    public double computeDiscount(Date currentDate, Date deadline, int period){
        if(currentDate.difference(deadline) >= period)
            return 0.1;
        return 0;
    }

    public double computePoints(Date currentDate, Sale transaction, Partner partner){
        if(transaction.getDeadline().difference(currentDate) <= 0)
            return 10*transaction.getAmountPaid();

        if(transaction.getDeadline().difference(currentDate) > 0)
            return -partner.getPoints();
            
        return 10*transaction.getAmountPaid();
    }

    @Override
    public String toString(){
        return "NORMAL";
    }
    
}
