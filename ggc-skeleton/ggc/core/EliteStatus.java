package ggc.core;

import java.io.Serializable;

public class EliteStatus implements PartnerStatus, Serializable{
    private static EliteStatus _status;

    private EliteStatus(){}

    public static EliteStatus getInstance()
    {
        if (_status == null)
            _status = new EliteStatus();
 
        return _status;
    }

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

    public double computePoints(Date currentDate, Sale transaction, Partner partner){
        if(transaction.getDeadline().difference(currentDate) <= 0)
            return 10*transaction.getAmountPaid();
            
        if(transaction.getDeadline().difference(currentDate) > 15){
            partner.setStatus(SelectionStatus.getInstance());
            return -0.75*partner.getPoints();
        }
            
        return 0; //nao altera os pontos
    }

    @Override
    public String toString(){
        return "ELITE";
    }
}
