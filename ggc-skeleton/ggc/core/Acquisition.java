package ggc.core;

public class Acquisition extends Transaction{
   
    Acquisition(int id, double baseValue, int quantity, Product product, Partner partner, int deadline){
        super(id, baseValue, quantity, product, partner, deadline);
        super.setAmountPaid(-baseValue);
    }

    public boolean isPaid(){
        return true;
    }

    public double calculatePayment(Date currentDate){
        return super.getAmountPaid();
    }

    public String toString(Date date){
        return "COMPRA"+"|"+super.getId()+"|"+super.getPartner().getId()+"|"+super.getProduct().getId()+"|"
        +super.getQuantity()+"|"+Math.round(super.getBaseValue())+"|"+super.getPaymentDate().toString(); 
    }
}
