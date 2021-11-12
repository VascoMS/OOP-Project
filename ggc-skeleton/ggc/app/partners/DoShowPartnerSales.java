package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.Sale;
import ggc.core.WarehouseManager;
//FIXME import classes
import ggc.core.exception.CoreUnknownPartnerKeyException;

/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerSales extends Command<WarehouseManager> {

  DoShowPartnerSales(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_SALES, receiver);
    addStringField("partnerId", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    String partnerId = stringField("partnerId");
    try{
      for(Sale sale : _receiver.getPartnerSales(partnerId))
        _display.addLine(sale.toString(_receiver.getDateWarehouse()));
      _display.display();
    } catch(CoreUnknownPartnerKeyException e){
      throw new UnknownPartnerKeyException(stringField("partnerId"));
    }
  }

}
