package ggc.core;

import java.io.Serializable;
import java.util.*;

 /**
 * Representa um produto 
 * Um produto é uma das entidades guardadas no armazém,é caracterizado por 
 * um identificador(o nome do mesmo) e está contido em lotes.
 * @author Vasco Silva ist199132, Vicente Gomes ist199135
 * @version 1.0
 */
public abstract class Product implements Serializable{
    /*Guarda o preço máximo do produto*/
    private double _maxPrice;
    /*Guarda o identificador do produto*/
    private String _id;
    /*Guarda os lotes onde o produto está contido*/
    private List<Batch> _batches;
    /*Guarda o stock total de um produto no armazém */
    private long _totalStock;

    /** 
     * Construtor da classe, define o id do produto.
     * @param id valor de input que define o id do produto.
     */
    Product(String id){
        _id=id;
        _batches = new ArrayList<Batch>();
        _maxPrice = _totalStock=0;
    }
    
    /**
     * Obter o preço máximo do produto.
     * @return preço máximo do produto.
     */
    public double getMaxPrice(){
        return _maxPrice;
    }

    /**
     * Atualiza o preço máximo do produto, procurando o preço maximo nos lotes do produto.
     */
    public void updateMaxPrice(){
        for(Batch batch: _batches){
            if(batch.getPrice() > _maxPrice)
                _maxPrice = batch.getPrice();
        }
    }

    /**
     * Adiciona um lote à coleção que guarda os lotes onde o produto está contido.
     * @param batch lote a adicionar.
     */
    public void addBatch(Batch batch) {
        _batches.add(batch);
        _totalStock += batch.getQuantity();
        updateMaxPrice();
    }

    public String getId(){
        return _id;
    }

    public List<Batch> getBatches(){
        return _batches;
    }

    public long getTotalStock(){
        return _totalStock;
    }

    @Override
    public boolean equals(Object obj){
        return obj instanceof Product && _id.equals(((Product)obj).getId());
    }

    @Override
    public int hashCode(){
        return Objects.hash(_id);
    }

    abstract int checkQuantity(int quantity, Partner partner);



}
