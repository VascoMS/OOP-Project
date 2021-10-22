package ggc.app.partners;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.WarehouseManager;
import ggc.core.exception.BadEntryException;
import pt.tecnico.uilib.Display;
//FIXME import classes
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show partner.
 */
class DoShowPartner extends Command<WarehouseManager> {

  DoShowPartner(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER, receiver);
    addStringField("partnerId", Message.requestPartnerKey());//FIXME add command fields
  }

  @Override
  public void execute() throws CommandException {
      String id = stringField("partnerId");
      try {
        _display.popup(_receiver.getPartner(id));
      } catch (BadEntryException e) {
        throw new UnknownPartnerKeyException(id);//TODO: handle exception
      }
      //show notifications
  }

}
