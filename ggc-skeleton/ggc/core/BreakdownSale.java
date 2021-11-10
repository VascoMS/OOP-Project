package ggc.core;

public class BreakdownSale extends Sale{

    BreakdownSale(int id, double baseValue, int quantity, Product product, Partner partner, double payedValue, int deadline){
        super(id, baseValue, quantity, product, partner, deadline);
        super.setAmountPaid(payedValue);
    }

    public boolean isPaid() {
        return true;
    }

    public double calculatePayment(Date currentDate){
        return super.getAmountPaid();
    }

    public String toString(Date date){
        return "DESAGREGACAO"+"|"+super.getId()+"|"+super.getPartner().getId()+"|"
        +super.getProduct().getId()+"|"+super.getQuantity()+"|"+Math.round(super.getBaseValue())+"|"
        +Math.round(super.getAmountPaid())+"|"+super.getPaymentDate()+"|"+ ((AggregateProduct)super.getProduct()).getRecipe().toString();   
    }
}