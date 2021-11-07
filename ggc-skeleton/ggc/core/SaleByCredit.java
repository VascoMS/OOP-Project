package ggc.core;

public class SaleByCredit extends Sale{
    private Date _deadline;
    private double _amountOwed;

    SaleByCredit(int id, double baseValue, int quantity, Product product, Partner partner, int deadline){
        super(id, baseValue, quantity, product, partner);
        _deadline = new Date(deadline);
        _amountOwed = baseValue;
    }
    public boolean isPaid() {
        return super.getAmountPaid() != 0;   
    }

    public Date getDeadline(){
        return _deadline;
    }

    public void updateAmountOwed(Date currentDate){
        _amountOwed = calculatePayment(currentDate);
    }

    public void setAmountOwed(double amount){
        _amountOwed = amount;
    }

    public double getAmountOwed(){
        return _amountOwed;
    }

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

    @Override
    public String toString(){ 
        if(isPaid())
            return "VENDA" + "|" + super.getId() + "|" + super.getPartner().getId() + "|" 
            + super.getProduct().getId() + "|" + super.getQuantity() + "|" + 
            super.getBaseValue() + "|" + _amountOwed + "|" + _deadline + "|" + super.getPaymentDate();
        else
            return "VENDA" + "|" + super.getId() + "|" + super.getPartner().getId() + "|" 
            + super.getProduct().getId() + "|" + super.getQuantity() + "|" + 
            super.getBaseValue() + "|" + _amountOwed + "|" + _deadline;
    }

}