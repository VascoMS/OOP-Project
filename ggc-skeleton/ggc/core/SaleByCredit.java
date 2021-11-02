package ggc.core;

public class SaleByCredit extends Sale{
    private Date _deadline;
    private double _amountPaid;

    SaleByCredit(int id, Date paymentDate, double baseValue, int quantity, Product product, Partner partner, int deadline){
        super(id, paymentDate, baseValue, quantity, product, partner);
        _deadline = new Date(deadline);
        _amountPaid=0;
    }
    public boolean isPaid() {
        return false;
        
    }



}