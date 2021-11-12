package ggc.app.partners;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.Notification;
import ggc.core.WarehouseManager;
import ggc.core.exception.CoreUnknownPartnerKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show partner.
 */
class DoShowPartner extends Command<WarehouseManager> {

  DoShowPartner(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER, receiver);
    addStringField("partnerId", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    String id = stringField("partnerId");
    try{
      _display.addLine(_receiver.showPartner(id));
      for(Notification notification : _receiver.showNotifications(id))
        _display.addLine(notification.toString());
      _display.display();
    } catch(CoreUnknownPartnerKeyException ex){
      throw new UnknownPartnerKeyException(id);
    }
  }

}
