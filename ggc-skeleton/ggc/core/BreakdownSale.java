package ggc.core;

public class BreakdownSale extends Sale{

    BreakdownSale(int id, Date paymentDate, double baseValue, int quantity, Product product, Partner partner, int deadline){
        super(id, paymentDate, baseValue, quantity, product, partner);
    }

    public boolean isPaid() {
        //FIXME
        return false;
        
    }

}