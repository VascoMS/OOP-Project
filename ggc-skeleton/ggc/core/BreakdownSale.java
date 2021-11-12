package ggc.core;

public class BreakdownSale extends Sale{

    BreakdownSale(int id, double baseValue, int quantity, Product product, Partner partner, double payedValue, int deadline){
        super(id, baseValue, quantity, product, partner, deadline);
        super.setAmountPaid(payedValue);
    }

    public boolean isPaid() {
        return true;
    }

    double calculatePayment(Date currentDate){
        return super.getAmountPaid();
    }

    String showComponents(){
        Recipe recipe = ((AggregateProduct)super.getProduct()).getRecipe();
        String componentsString="";
        for(Component component : recipe.getComponents()){
            if(componentsString!="")
                componentsString += "#";
            componentsString += component.getProduct().getId()+":"
            +Math.round(component.getQuantity()*super.getQuantity())+":"
            +Math.round(component.getProduct().getLowestPrice()*component.getQuantity()*super.getQuantity());
       }
       return componentsString;
    }

    public String toString(Date date){
        return "DESAGREGAÇÃO"+"|"+super.getId()+"|"+super.getPartner().getId()+"|"
        +super.getProduct().getId()+"|"+super.getQuantity()+"|"+Math.round(super.getBaseValue())+"|"
        +Math.round(super.getAmountPaid())+"|"+super.getPaymentDate()+"|"+ showComponents();   
    }
}