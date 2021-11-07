package ggc.core;

public class BreakdownSale extends Sale{

    BreakdownSale(int id, double baseValue, int quantity, Product product, Partner partner, double payedValue){
        super(id, baseValue, quantity, product, partner);
        super.setAmountPaid(payedValue);
    }

    public boolean isPaid() {
        return true;
    }

    @Override
    public String toString(){
        //conseguir imprimir todos os componentes
        return "DESAGREGACAO"+"|"+super.getId()+"|"+super.getPartner().getId()+"|"
        +super.getProduct().getId()+"|"+super.getQuantity()+"|"+super.getBaseValue()+"|"
        +super.getAmountPaid()+"|"+super.getPaymentDate()+"|"+ ((AggregateProduct)super.getProduct()).getRecipe().toString();   
    }
}