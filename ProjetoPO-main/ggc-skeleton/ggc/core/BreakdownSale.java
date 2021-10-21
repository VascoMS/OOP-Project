package ggc.core;

public class BreakdownSale extends Sale{

    BreakdownSale(Product p, int quantity, Partner part){
        super(p, quantity, part);
    }

    public boolean isPaid() {
        //FIXME
        return false;
        
    }

}