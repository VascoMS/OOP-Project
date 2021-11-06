package ggc.core;

public class SimpleProduct extends Product{
    SimpleProduct(String id){
        super(id);
    }
    
    @Override
    public boolean checkQuantity(int quantity, Partner partner){
        return true;
    }

    public String toString(){
        return ""+super.getId()+"|"+(int)super.getMaxPrice()+"|"+super.getTotalStock();
    }
    
}
