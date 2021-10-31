package ggc.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.CoreDuplicatePartnerKeyException;
import ggc.core.exception.CoreInvalidDateException;
import ggc.core.exception.CoreUnknownPartnerKeyException;
import ggc.core.exception.CoreUnknownProductKeyException;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;
  private Date _date;
  private int _nextTransactionId;
  private HashMap<String,Product> _products;
  private List<Batch> _batches;
  private HashMap<String,Partner> _partners;
  private HashMap<Integer, Transaction> _transactions;

  public Warehouse(){
    _date = new Date(0);
    _products = new HashMap<>();
    _batches = new ArrayList<>();
    _partners = new HashMap<>();
    _transactions = new HashMap<>();
  }

  public Date getDate(){
    return _date;
  }

  public int getNextTransactionId(){
    return _nextTransactionId;
  }

  public HashMap<String,Product> getProducts(){
    return _products;
  }

  public List<Product> getSortedProducts(){
    List<Product> products = new ArrayList<Product>();
    for (String i : _products.keySet()) {
      products.add(_products.get(i));
    }
    products.sort(new ProductComparator());
    return products;
  }

  public HashMap<String,Partner> getPartners(){
    return _partners;
  }

  public List<Partner> getSortedPartners(){
    List<Partner> partners = new ArrayList<Partner>();
    for (String i : _partners.keySet()) {
      partners.add(_partners.get(i));
    }
    partners.sort(new PartnerComparator());
    return partners;
  }

  public List<Batch> getBatches(){
    return _batches;
  }

  public List<Batch> getSortedBatches(){
    List<Batch> batches = new ArrayList<Batch>();
    batches.addAll(_batches);
    batches.sort(new BatchComparator());
    return batches;
  }

  public List<Batch> getBatchesPartner(List<Batch> sortedBatches, Partner partner){
    List<Batch> batchesPartner = new ArrayList<>();
    for(Batch b: sortedBatches){
      if(b.getPartner().equals(partner))
        batchesPartner.add(b);
    }
    return batchesPartner;
  }

  public List<Batch> getBatchesProduct(List<Batch> sortedBatches, Product product){
    List<Batch> batchesProduct = new ArrayList<>();
    for(Batch b: sortedBatches){
      if(b.getProduct().equals(product))
        batchesProduct.add(b);
    }
    
    return batchesProduct;
  }

  public void newDate(int days) throws CoreInvalidDateException{
    if(days < 0)
      throw new CoreInvalidDateException(days);
    _date.add(days);
  }

  public void addProduct(Product product) throws CoreUnknownProductKeyException{
    if(_products.containsKey(product.getId()))
      throw new CoreUnknownProductKeyException(product.getId());
    _products.put(product.getId(), product);
  }

  public Product getProduct(String id) throws CoreUnknownProductKeyException{
    if(_products.containsKey(id))
      return _products.get(id);
    throw new CoreUnknownProductKeyException(id);
  }

  public List<Component> createComponents(List<Product> products, List<Integer> quantities){
    List<Component> components = new ArrayList<>();
    for(int i=0; i<quantities.size(); i++){
      components.add(new Component(quantities.get(i), products.get(i)));
    }
    return components;
  }

  public void addPartner(Partner partner) throws CoreDuplicatePartnerKeyException{
    if(_partners.containsKey(partner.getId().toLowerCase()))
      throw new CoreDuplicatePartnerKeyException(partner.getId());
    _partners.put(partner.getId().toLowerCase(), partner);
  }

  public Partner getPartner(String id) throws CoreUnknownPartnerKeyException{
    if(_partners.containsKey(id.toLowerCase()))
      return _partners.get(id.toLowerCase());
    throw new CoreUnknownPartnerKeyException(id);
      
  }


  public void addBatch(Batch batch){
    _batches.add(batch);
    batch.getProduct().addBatch(batch);
  }

  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile, WarehouseManager warehouseManager) throws IOException, BadEntryException, CoreDuplicatePartnerKeyException, CoreUnknownPartnerKeyException, CoreUnknownProductKeyException{
    Parser parser = new Parser(warehouseManager);
    parser.parseFile(txtfile);
  }

}
