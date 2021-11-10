package ggc.core;

import ggc.core.exception.CoreUnavailableProductException;

public class AggregateProduct extends Product{
    private Recipe _recipe;

    AggregateProduct(String id, Recipe recipe){
        super(id);
        _recipe = recipe;
        super.setPeriodN(3);
    }

    public Recipe getRecipe(){
        return _recipe;
    }

    public void aggregateProduct(String productId, int amount) throws CoreUnavailableProductException{
        
    }

    @Override
    public boolean checkQuantity(int quantity, Partner partner) {
        return quantity <= super.getTotalStock();
    }

    @Override
    public String toString() {
        return super.getId()+"|"+Math.round(super.getMaxPrice())+"|"+super.getTotalStock()+"|"+_recipe.toString();
    }
}
