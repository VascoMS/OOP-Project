package ggc.core;

import ggc.core.exception.CoreUnavailableProductException;

public class SimpleProduct extends Product{
    SimpleProduct(String id){
        super(id);
        setPeriodN(5);
    }

    @Override
    public void aggregateProduct(String productId, int amount) throws CoreUnavailableProductException{
        throw new CoreUnavailableProductException(productId, amount);

    }
    
    @Override
    public boolean checkQuantity(int quantity, Partner partner){
        return quantity <= super.getTotalStock();
    }

    @Override
    public String toString(){
        return ""+super.getId()+"|"+(int)super.getMaxPrice()+"|"+super.getTotalStock();
    }
    
}
