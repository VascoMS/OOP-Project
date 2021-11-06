package ggc.core;

public class SaleByCredit extends Sale{
    private Date _deadline;
    private double _amountPaid;

    SaleByCredit(int id, double baseValue, int quantity, Product product, Partner partner, int deadline){
        super(id, baseValue, quantity, product, partner);
        _deadline = new Date(deadline);
        _amountPaid=0;
    }
    public boolean isPaid() {
        return _amountPaid != 0;   
    }

    public Date getDeadline(){
        return _deadline;
    }

    public double getAmountPaid(){
        return _amountPaid;
    }
    @Override
    public double calculatePayment(Date currentDate){
        int period;
        if(super.getProduct() instanceof SimpleProduct)
            period=5;
        else
            period=3;
        double discount = super.getPartner().computeDiscount(currentDate, _deadline , period);
        double fine = super.getPartner().computeFine(currentDate, _deadline, period);
        double payment = super.getBaseValue() + super.getBaseValue()*discount + super.getBaseValue()*fine;
        return payment;
     }

}