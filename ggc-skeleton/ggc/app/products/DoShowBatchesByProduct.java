package ggc.app.products;

import java.util.ArrayList;

import ggc.app.exception.UnknownProductKeyException;
import ggc.core.Batch;
import ggc.core.WarehouseManager;
import ggc.core.exception.CoreUnknownProductKeyException;
//FIXME import classes
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show all products.
 */
class DoShowBatchesByProduct extends Command<WarehouseManager> {

  DoShowBatchesByProduct(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_BY_PRODUCT, receiver);
      addStringField("productId", Message.requestProductKey());
    //FIXME maybe add command fields
  }

  @Override
  public final void execute() throws CommandException {
    String productId = stringField("productId");

    try{
    ArrayList<Batch> batches = _receiver.getBatchesProduct(productId);
        for(Batch b: batches){
      _display.addLine(b.toString());
    }
    _display.display();
    } catch (CoreUnknownProductKeyException e) {
      throw new UnknownProductKeyException(productId);//TODO: handle exception
      
    }
    //FIXME implement command
  }

}
