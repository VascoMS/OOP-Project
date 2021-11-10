package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.Batch;
import ggc.core.WarehouseManager;
//FIXME import classes
import ggc.core.exception.CoreUnknownProductKeyException;

/**
 * Lookup products cheaper than a given price.
 */
public class DoLookupProductBatchesUnderGivenPrice extends Command<WarehouseManager> {

  public DoLookupProductBatchesUnderGivenPrice(WarehouseManager receiver) {
    super(Label.PRODUCTS_UNDER_PRICE, receiver);
    addIntegerField("price", Message.requestPriceLimit());
  }

  @Override
  public void execute() throws CommandException {
    int price = integerField("price");
      for(Batch batch : _receiver.getBatchesProductUnderPrice(price))
        _display.addLine(batch);
      _display.display();
    }
  }
