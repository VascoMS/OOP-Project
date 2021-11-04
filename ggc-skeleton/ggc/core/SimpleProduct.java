package ggc.core;

public class SimpleProduct extends Product{
    SimpleProduct(String id){
        super(id);
    }
    
    @Override
    boolean checkQuantity(int quantity, Partner partner){
        return quantity;
    }

    public String toString(){
        return ""+super.getId()+"|"+(int)super.getMaxPrice()+"|"+super.getTotalStock();
    }
    
}
