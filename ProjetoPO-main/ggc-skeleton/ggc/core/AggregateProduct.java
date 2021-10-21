package ggc.core;

public class AggregateProduct extends Product{
    private Recipe _recipe;

    AggregateProduct(String id){
        super(id);
    }

    public Recipe getRecipe(){
        return _recipe;
    }

    @Override
    int checkQuantity(int quantity, Partner partner) {
        //FIXME nao sei o que por aqui
        return quantity;
    }

    @Override
    public String toString() {
        return super.getId()+"|"+super.getMaxPrice()+"|"+super.getTotalStock()+"|"+_recipe.getAlpha()+"|"+_recipe.getComponents();
    }
}
