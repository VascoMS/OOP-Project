package ggc.core;

public class SimpleProduct extends Product{
    SimpleProduct(String id){
        super(id);
    }
    @Override
    boolean checkQuantity(int quantity, Partner partner){
        //FIXME nao sei o que por aqui
        return false;
    }

    public String toString(){
        //FIXME
        return "";
    }
    
}
