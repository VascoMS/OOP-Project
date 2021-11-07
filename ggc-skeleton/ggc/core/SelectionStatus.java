package ggc.core;

import java.io.Serializable;

public class SelectionStatus implements PartnerStatus, Serializable{
    private static SelectionStatus _status;

    private SelectionStatus(){}

    public static SelectionStatus getInstance()
    {
        if (_status == null)
            _status = new SelectionStatus();
 
        return _status;
    }

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

    public double computePoints(Date currentDate, Sale transaction, double points){
        if(transaction instanceof BreakdownSale)
            return 10*((BreakdownSale)transaction).getAmountPaid();

        Date deadline = ((SaleByCredit) transaction).getDeadline();

        if(deadline.difference(currentDate) <= 0)
            return 10*((SaleByCredit) transaction).getAmountOwed();

        if(deadline.difference(currentDate) > 2)
            return -0.90*points;

        return 0; // nao altera

    }

    @Override
    public String toString(){
        return "SELECTION";
    }
}

