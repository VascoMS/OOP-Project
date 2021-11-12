package ggc.core;


import ggc.core.exception.CoreUnavailableProductException;

public class SimpleProduct extends Product{
    SimpleProduct(String id){
        super(id);
        setPeriodN(5);

    }

    boolean checkAggregation(int amount, int available) throws CoreUnavailableProductException{
        throw new CoreUnavailableProductException(getId(), available, amount);
    }
    void aggregateProduct(int amount, int available, Partner partner, Warehouse warehouse) throws CoreUnavailableProductException{
        throw new CoreUnavailableProductException(getId(), available, amount);
    }

    
    @Override
    boolean checkQuantity(int quantity){
        return quantity <= super.getTotalStock();
    }

    @Override
    public String toString(){
        return ""+super.getId()+"|"+(int)super.getMaxPrice()+"|"+super.getTotalStock();
    }
    
}
