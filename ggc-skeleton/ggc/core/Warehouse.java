package ggc.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.CoreDuplicatePartnerKeyException;
import ggc.core.exception.CoreInvalidDateException;
import ggc.core.exception.CoreUnknownPartnerKeyException;
import ggc.core.exception.CoreUnknownProductKeyException;
import ggc.core.exception.CoreUnknownTransactionKeyException;


/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;
  private Date _date;
  private int _nextTransactionId;
  private double _accountingBalance;
  private double _availableBalance;
  private Map<String,Product> _products;
  private List<Batch> _batches;
  private Map<String,Partner> _partners;
  private Map<Integer, Transaction> _transactions;

  public Warehouse(){
    _date = new Date(0);
    _products = new HashMap<>();
    _batches = new ArrayList<>();
    _partners = new HashMap<>();
    _transactions = new HashMap<>();
    _accountingBalance = 0;
    _availableBalance = 0;
  }

  public Date getDate(){
    return _date;
  }

  public int getNextTransactionId(){
    return _nextTransactionId;
  }

  public double getAvailableBalance(){
    return _availableBalance;
  }

  public double getAccountingBalance(){
    return _accountingBalance;
  }

  public Transaction getTransaction(int id) throws CoreUnknownTransactionKeyException{
    if(_transactions.containsKey(id))
      return _transactions.get(id);
    throw new CoreUnknownTransactionKeyException(id);
  }

  public List<Transaction> getTransactions(){
    List<Transaction> transactions = new ArrayList<>();
    transactions.addAll(_transactions.values());
    return transactions;
  }

  public List<Product> getProducts(){
    List<Product> products = new ArrayList<>();
    products.addAll(_products.values());
    return products;
  }

  public List<Product> getSortedProducts(){
    List<Product> products = getProducts();
    products.sort(new ProductComparator());
    return products;
  }

  public List<Partner> getPartners(){
    List<Partner> partners = new ArrayList<>();
    partners.addAll(_partners.values());
    return partners;
  }

  public boolean hasProduct(String id){
    try {
      getProduct(id);
      return true;
    } catch (CoreUnknownProductKeyException e) {
      return false;
    }
  }

  public List<Partner> getSortedPartners(){
    List<Partner> partners = getPartners();
    partners.sort(new PartnerComparator());
    return partners;
  }

  public List<Batch> getBatches(){
    return _batches;
  }

  public List<Batch> getSortedBatches(){
    List<Batch> batches = getBatches();
    batches.sort(new BatchComparator());
    return Collections.unmodifiableList(batches);
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

  public void advanceDate(int days) throws CoreInvalidDateException{
    if(days < 0)
      throw new CoreInvalidDateException(days);
    _date.add(days);
  }

  public void addProduct(Product product) {
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
//REGISTER OS PRODUTOS
  public void registerAcquisition(String partnerId, String productId, double productPrice, int quantity) throws CoreUnknownPartnerKeyException{
    Partner partner = getPartner(partnerId);
    Product product;
    try {
      product = getProduct(productId);
    } catch (CoreUnknownProductKeyException e) {
      product = new SimpleProduct(productId);
      addProduct(product);
    }
    Acquisition transaction = new Acquisition(_nextTransactionId, _date, productPrice, quantity, product, partner);
    _transactions.put(_nextTransactionId++, transaction);
    partner.addAcquisition(transaction);
    addBatch(new Batch(productPrice, quantity, partner, product));
  }

  public void registerAcquisition(String partnerId, String productId, double productPrice, int quantity, List<String> productsIDs, List<Integer> quantities, double alpha, int numberComponents) throws CoreUnknownPartnerKeyException, CoreUnknownProductKeyException{
    Partner partner = getPartner(partnerId);
    List<Component> components = new ArrayList<>();
    for(int i = 0;i < numberComponents; i++){
      components.add(new Component(quantities.get(i), getProduct(productsIDs.get(i))));
    }
    addProduct(new AggregateProduct(productId, new Recipe(alpha, components)));
    Acquisition transaction = new Acquisition(_nextTransactionId, _date, productPrice, quantity, _products.get(productId), partner);
    _transactions.put(_nextTransactionId++, transaction);
    partner.addAcquisition(transaction);
    addBatch(new Batch(productPrice, quantity, partner, _products.get(productId)));
  }


  public void addBatch(Batch batch){
    _batches.add(batch);
    batch.getProduct().addBatch(batch);
    batch.getPartner().addBatch(batch);
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
