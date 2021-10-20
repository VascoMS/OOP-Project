package ggc.core;

public class Acquisition extends Transaction{
    private Partner _partner;

    Acquisition(Product p, int quantity, Partner part){
        //FIXME CONSTRUCTOR
    }

    public Partner getPartner(){
        return _partner;
    }

    public boolean isPaid(){
        return true;
    }

    @Override
    public String toString(){
        return "";
        //return "COMPRA"+"|"+_id+"|"+_partner.getId()+"|"+_product.getId()+"|"+_quantity+"|"+_baseValue+"|"+_paymentDate; 
    }
}
