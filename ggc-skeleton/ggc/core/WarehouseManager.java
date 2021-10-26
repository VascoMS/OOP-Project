package ggc.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.CoreDuplicatePartnerKeyException;
import ggc.core.exception.CoreUnknownPartnerKeyException;
import ggc.core.exception.ImportFileException;
import ggc.core.exception.MissingFileAssociationException;
import ggc.core.exception.UnavailableFileException;

/** Façade for access. */
public class WarehouseManager {

  /** Name of file storing current warehouse. */
  private String _filename = "";

  /** The warehouse itself. */
  private Warehouse _warehouse = new Warehouse();

  public Date getDateWarehouse(){
    return _warehouse.getDate();
  }

  public Product getProduct(String id) throws BadEntryException{
    return _warehouse.getProduct(id);
  }

  public void RegisterAggregateProduct(String id, double alpha, ArrayList<Product> products, ArrayList<Integer> quantities) throws BadEntryException{
    ArrayList<Component> components = _warehouse.createComponents(products, quantities);
    Recipe recipe = new Recipe(alpha, components);
    Product newProduct = new AggregateProduct(id, recipe);
    _warehouse.addProduct(newProduct);
  }

  public void RegisterSimpleProduct(String id) throws BadEntryException{
    Product newProduct = new SimpleProduct(id);
    _warehouse.addProduct(newProduct);
  }

  public HashMap<String,Product> getAllProductsWarehouse(){
    return _warehouse.getProducts();
  }

  public ArrayList<Product> getProductsSorted(){
    return _warehouse.getSortedProducts();
  }

  public ArrayList<Batch> getAllBatchesWarehouse(){
    return _warehouse.getBatches();
  }

  public void addBatch(double price, int quantity, Partner partner, Product product){
    Batch batch = new Batch(price, quantity, partner, product);
    _warehouse.addBatch(batch);
  }
  public ArrayList<Batch> getBatchesSorted(){
    return _warehouse.getSortedBatches();
  }

  public ArrayList<Batch> getBatchesPartner(String partnerId) throws CoreUnknownPartnerKeyException{
    Partner partner = _warehouse.getPartner(partnerId);
    return _warehouse.getBatchesPartner(this.getBatchesSorted(), partner);
  }

  public ArrayList<Batch> getBatchesProduct(String productId) throws BadEntryException{
    Product product = _warehouse.getProduct(productId);
    return _warehouse.getBatchesProduct(this.getBatchesSorted(), product);
  }

  public void incrementDate(int days) throws BadEntryException{
    _warehouse.newDate(days);
  }

  public void addPartner(String id, String name, String address) throws CoreDuplicatePartnerKeyException{
    Partner newPartner = new Partner(id, name, address);
    _warehouse.addPartner(newPartner);
  }

  public Partner getPartner(String id) throws CoreUnknownPartnerKeyException{
    return _warehouse.getPartner(id);
  }

  public ArrayList<Partner> getSortedPartners(){
    return _warehouse.getSortedPartners();
  }

  public HashMap<String, Partner> getAllPartnersWarehouse(){
    return _warehouse.getPartners();
  }



  //FIXME define other attributes
  //FIXME define constructor(s)
  //FIXME define other methods

  /**
   * @@throws IOException
   * @@throws FileNotFoundException
   * @@throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    if(_filename.equals(""))
      throw new MissingFileAssociationException();
    FileOutputStream file = new FileOutputStream(_filename);
    ObjectOutputStream out = new ObjectOutputStream(file);
    out.writeObject(_warehouse);
    out.close();
    file.close();
  }

  /**
   * @@param filename
   * @@throws MissingFileAssociationException
   * @@throws IOException
   * @@throws FileNotFoundException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
    _filename = filename;
    save();
  }

  /**
   * @@param filename
   * @@throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException, ClassNotFoundException  {
    try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))){
      _warehouse = (Warehouse) in.readObject();
      _filename = filename;
    } catch(ClassNotFoundException | IOException a){
      throw new UnavailableFileException(filename);
    }
    //FIXME implement serialization method
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException{
    try {
      _warehouse.importFile(textfile, this);
    } catch (IOException | BadEntryException | CoreDuplicatePartnerKeyException | CoreUnknownPartnerKeyException /* FIXME maybe other exceptions */ e) {
      throw new ImportFileException(textfile, e);
    }
  }

}
