package ggc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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

    private int _totalStock;

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

    public double getLowestPrice(){
        if(_batches==null)
            return _maxPrice;
        double lowPrice = _maxPrice;
        for(Batch batch : _batches){
            if(batch.getPrice() < lowPrice)
                lowPrice = batch.getPrice();
        }
        return lowPrice;
    }

    public Batch getLowestBatch(){
        if(_batches==null)
            return null;
        Batch lowBatch = _batches.get(0);
        for(Batch batch : _batches){
            if(batch.getPrice() < lowBatch.getPrice())
                lowBatch = batch;
        }
        return lowBatch;
    }

    /**
     * Adiciona um lote à coleção que guarda os lotes onde o produto está contido.
     * @param batch lote a adicionar.
     */
    public void addBatch(Batch batch) {
        _batches.add(batch);
        updateTotalStock();
        updateMaxPrice();
    }

    public void removeBatch(Batch batch){
        _batches.remove(batch);
        updateTotalStock();
    }

    public void updateTotalStock(){
        Iterator<Batch> iter = _batches.iterator();
        int totalStock = 0;
        while(iter.hasNext()){
            Batch batch = iter.next();
            totalStock += batch.getQuantity();
        }
        _totalStock = totalStock;
    }

    /**
     * Devolve o id do produto
     * @return id do produto 
     */
    public String getId(){
        return _id;
    }

    /**
     * Devolve a lista de lotes do produto
     * @return lista de lotes do produto
     */
    public List<Batch> getBatches(){
        return Collections.unmodifiableList(_batches);
    }

    /**
     * Devolve o stock total do produto
     * @return stock total do produto
     */
    public int getTotalStock(){
        return _totalStock;
    }

    public List<Batch> getBatchesSortedByPrice(){
        List<Batch> sortedBatches = new ArrayList<>(_batches);
        sortedBatches.sort(new BatchPriceComparator());
        return Collections.unmodifiableList(sortedBatches);
    }

    /** substitui o metodo para comparar produtos utilizando o identificador unico
     * @param Obj um objeto
     * @return boolean se dois objetos sao iguais
     */
    @Override
    public boolean equals(Object obj){
        return obj instanceof Product && _id.equals(((Product)obj).getId());
    }

    /** substitui o metodo para guardar os objetos no Map de modo a certificar que dois produtos diferentes tem dois hashcodes diferentes
     * @return int o valor do id apos a funcao hash
     */
    @Override
    public int hashCode(){
        return Objects.hash(_id);
    }

    abstract boolean checkQuantity(int quantity, Partner partner);
    
}
