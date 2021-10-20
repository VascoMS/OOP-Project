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
    boolean checkQuantity(int quantity, Partner partner) {
        //FIXME nao sei o que por aqui
        return false;
    }

    @Override
    public String toString() {
    //FIXME
    return "";    
    //return super.toString()+"|"+_recipe.getAplha()+"|"+;
    }
}
