package ggc.core;

public class SimpleProduct extends Product{
    SimpleProduct(String id){
        super(id);
    }
    @Override
    int checkQuantity(int quantity, Partner partner){
        //FIXME nao sei o que por aqui
        return quantity;
    }

    public String toString(){
        return ""+super.getId()+"|"+(int)super.getMaxPrice()+"|"+super.getTotalStock();
    }
    
}
