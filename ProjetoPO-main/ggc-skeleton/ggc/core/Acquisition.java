package ggc.core;

public class Acquisition extends Transaction{
   
    Acquisition(int id, Date paymentDate, double baseValue, int quantity, Product product, Partner partner){
        super(id, paymentDate, baseValue, quantity, product, partner);
    }

    public boolean isPaid(){
        return true;
    }

    @Override
    public String toString(){
        return "COMPRA"+"|"+super.getId()+"|"+super.getPartner().getId()+"|"+super.getProduct().getId()+"|"+super.getQuantity()+"|"+super.getBaseValue()+"|"+super.getPaymentDate().toString(); 
    }
}
