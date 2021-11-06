package ggc.core;

import java.io.Serializable;

public class NormalStatus implements PartnerStatus, Serializable{
    
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

    public double computePoints(Date currentDate, Sale transaction, double points){
        if(transaction instanceof BreakdownSale)
            return 10*transaction.calculatePayment(currentDate);

        Date deadline = ((SaleByCredit) transaction).getDeadline();

        if(deadline.difference(currentDate) > 0)
            return -points;
            
        return 10*transaction.calculatePayment(currentDate);
    }

    @Override
    public String toString(){
        return "NORMAL";
    }
    
}
