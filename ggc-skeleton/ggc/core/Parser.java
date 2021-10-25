package ggc.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.CoreDuplicatePartnerKeyException;
import ggc.core.exception.CoreUnknownPartnerKeyException;

public class Parser{

  // It could be WarehouseManager too. Or something else.
  private WarehouseManager _store;

  public Parser(WarehouseManager w) {
    _store = w;
  }

  void parseFile(String filename) throws IOException, BadEntryException, CoreDuplicatePartnerKeyException, CoreUnknownPartnerKeyException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;

      while ((line = reader.readLine()) != null)
        parseLine(line);
    }
  }

  private void parseLine(String line) throws BadEntryException, BadEntryException, CoreUnknownPartnerKeyException, CoreDuplicatePartnerKeyException {
    String[] components = line.split("\\|");

    switch (components[0]) {
      case "PARTNER":
        parsePartner(components, line);
        break;
      case "BATCH_S":
        parseSimpleProduct(components, line);
        break;

      case "BATCH_M":
        parseAggregateProduct(components, line);
        break;
        
      default:
        throw new BadEntryException("Invalid type element: " + components[0]);
    }
  }

  //PARTNER|id|nome|endereço
  private void parsePartner(String[] components, String line) throws BadEntryException, CoreDuplicatePartnerKeyException {
    if (components.length != 4)
      throw new BadEntryException("Invalid partner with wrong number of fields (4): " + line);
    
    String id = components[1];
    String name = components[2];
    String address = components[3];
    _store.addPartner(id, name, address);
    // add code here to
    // register partner with id, name, address in _store;
  }

  //BATCH_S|idProduto|idParceiro|prec ̧o|stock-actual
  private void parseSimpleProduct(String[] components, String line) throws CoreUnknownPartnerKeyException, BadEntryException {
    if (components.length != 5)
      throw new BadEntryException("Invalid number of fields (4) in simple batch description: " + line);
    
    String idProduct = components[1];
    String idPartner = components[2];
    double price = Double.parseDouble(components[3]);
    int stock = Integer.parseInt(components[4]);
    
    if(!_store.getAllProductsWarehouse().containsKey(idProduct))
      _store.RegisterSimpleProduct(idProduct);
    
    Product product = _store.getProduct(idProduct); 
    Partner partner = _store.getPartner(idPartner);
    _store.addBatch(price, stock, partner, product);
  }
 
    
  //BATCH_M|idProduto|idParceiro|prec ̧o|stock-actual|agravamento|componente-1:quantidade-1#...#componente-n:quantidade-n
  
  private void parseAggregateProduct(String[] components, String line) throws BadEntryException, CoreUnknownPartnerKeyException {
    if (components.length != 7)
      throw new BadEntryException("Invalid number of fields (7) in aggregate batch description: " + line);
    
    String idProduct = components[1];
    String idPartner = components[2];

    if (!_store.getAllProductsWarehouse().containsKey(idProduct)){
      ArrayList<Product> products = new ArrayList<>();
      ArrayList<Integer> quantities = new ArrayList<>();
      
      for (String component : components[6].split("#")) {
        String[] recipeComponent = component.split(":");
         
        products.add(_store.getProduct(recipeComponent[0]));
        quantities.add(Integer.parseInt(recipeComponent[1]));
      }
      double aggravation=Double.parseDouble(components[5]);
      _store.RegisterAggregateProduct(idProduct, aggravation, products, quantities);
    }
     
    Product product = _store.getProduct(idProduct);
    Partner partner = _store.getPartner(idPartner);
    double price = Double.parseDouble(components[3]);
    int stock = Integer.parseInt(components[4]);
    _store.addBatch(price, stock, partner, product);
  }
}
