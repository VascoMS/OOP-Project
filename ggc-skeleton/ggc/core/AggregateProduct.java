package ggc.core;

import java.util.ArrayList;
import java.util.List;

import ggc.core.exception.CoreUnavailableProductException;

public class AggregateProduct extends Product{
    private Recipe _recipe;

    AggregateProduct(String id, Recipe recipe){
        super(id);
        _recipe = recipe;
        super.setPeriodN(3);
    }

    Recipe getRecipe(){
        return _recipe;
    }

    boolean checkAggregation(int amount, int available) throws CoreUnavailableProductException{
        int necessary = amount-available;
        for(Component component : _recipe.getComponents()){
            if(!component.getProduct().checkQuantity(component.getQuantity()*necessary))
                return component.getProduct().checkAggregation(component.getQuantity()*necessary, component.getProduct().getTotalStock());
        }
        return true;
    }

    void aggregateProduct(int amount, int available, Partner partner, Warehouse warehouse) throws CoreUnavailableProductException{
        int necessary = amount-available;
        double price=0;
        List<Batch> batches = new ArrayList<>();
        for(Component component : _recipe.getComponents()){
            if(!component.getProduct().checkQuantity(component.getQuantity()*necessary))
                component.getProduct().aggregateProduct(component.getQuantity()*necessary, component.getProduct().getTotalStock(), partner, warehouse);
            price = component.getProduct().calculatePrice(component.getQuantity()*necessary, warehouse);
            
            batches.add(new Batch(price, component.getQuantity(), partner, component.getProduct()));
        }
        warehouse.addBatch(new Batch(calculatePrice(batches), necessary, partner, this), false);

    }

    double calculatePrice(int amount,Warehouse warehouse){
        return super.calculatePrice(amount, warehouse);
    }

    double calculatePrice(List<Batch> batches){
        double price = 0;
        for(Batch batch: batches){
            price += batch.getPrice() * _recipe.getComponentByProduct(batch.getProduct()).getQuantity();
        }
        return price * (1 + _recipe.getAlpha());
    }

    @Override
    boolean checkQuantity(int quantity) {
        return quantity <= super.getTotalStock();
    }

    @Override
    public String toString() {
        return super.getId()+"|"+Math.round(super.getMaxPrice())+"|"+super.getTotalStock()+"|"+_recipe.toString();
    }

}
