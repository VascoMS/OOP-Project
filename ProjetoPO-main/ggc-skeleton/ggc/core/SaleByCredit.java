package ggc.core;

public class SaleByCredit extends Sale{
    private Date _deadline;
    private double _amountPaid;

    SaleByCredit(Product p, int quantity, Partner part, int deadline){
        super(p, quantity, part);
        _deadline = new Date(deadline);
        //FIXME CONSTRUCTOR
    }
    public boolean isPaid() {
        //FIXME
        return false;
        
    }



}