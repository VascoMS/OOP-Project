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

    public double computePoints(Date currentDate, Sale transaction, double points){
        if(transaction instanceof BreakdownSale)
            return 10*transaction.calculatePayment(currentDate);
            
        Date deadline = ((SaleByCredit) transaction).getDeadline();

        if(deadline.difference(currentDate) <= 0)
            return 10*transaction.calculatePayment(currentDate);
            
        if(deadline.difference(currentDate) > 15)
            return -0.75*points;
            
        return 0; //nao altera os pontos
    }

    @Override
    public String toString(){
        return "ELITE";
    }
}
