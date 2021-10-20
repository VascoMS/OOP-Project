package ggc.core;

import java.io.FileNotFoundException;
import java.io.IOException;

import ggc.app.exception.DuplicatePartnerKeyException;
import ggc.core.exception.BadEntryException;
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

  public void incrementDate(int days){
    _warehouse.newDate(days);
  }

  public void addPartner(String name, String address, String id) throws DuplicatePartnerKeyException{
    Partner newPartner = new Partner(name, address, id);
    _warehouse.addPartner(newPartner);
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
    //FIXME implement serialization method
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
    //FIXME implement serialization method
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _warehouse.importFile(textfile);
    } catch (IOException | BadEntryException /* FIXME maybe other exceptions */ e) {
      throw new ImportFileException(textfile, e);
    }
  }

}
