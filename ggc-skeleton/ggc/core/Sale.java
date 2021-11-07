package ggc.core;

public abstract class Sale extends Transaction{
    private double _amountPaid;

    Sale(int id, double baseValue, int quantity, Product product, Partner partner){
        super(id, baseValue, quantity, product, partner);
        _amountPaid=0;
    }
    public double getAmountPaid(){
        return _amountPaid;
    }

    public void setAmountPaid(double payment){
        _amountPaid=payment;
    }

    public abstract double calculatePayment(Date currentDate);
}
