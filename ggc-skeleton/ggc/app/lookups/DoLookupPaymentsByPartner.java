package ggc.app.lookups;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.Sale;
import ggc.core.WarehouseManager;
//FIXME import classes
import ggc.core.exception.CoreUnknownPartnerKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Lookup payments by given partner.
 */
public class DoLookupPaymentsByPartner extends Command<WarehouseManager> {

  public DoLookupPaymentsByPartner(WarehouseManager receiver) {
    super(Label.PAID_BY_PARTNER, receiver);
    addStringField("partnerId", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    String partnerId = stringField("partnerId");
    try {
      for(Sale sale : _receiver.getPayedSalesByPartner(partnerId))
        _display.addLine(sale.toString(_receiver.getDateWarehouse()));
      _display.display();
    } catch (CoreUnknownPartnerKeyException e) {
      throw new UnknownPartnerKeyException(partnerId);
    }
    
  }

}
