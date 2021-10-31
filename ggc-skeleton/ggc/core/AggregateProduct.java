package ggc.core;

public class AggregateProduct extends Product{
    private Recipe _recipe;

    AggregateProduct(String id, Recipe recipe){
        super(id);
        _recipe = recipe;
    }

    public Recipe getRecipe(){
        return _recipe;
    }

    @Override
    int checkQuantity(int quantity, Partner partner) {
        return quantity;
    }

    @Override
    public String toString() {
        return super.getId()+"|"+(int)super.getMaxPrice()+"|"+super.getTotalStock()+"|"+_recipe.getAlpha()+"|"+_recipe.getComponents();
    }
}
