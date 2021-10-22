package ggc.app.partners;

import ggc.app.exception.DuplicatePartnerKeyException;
import ggc.core.WarehouseManager;
//FIXME import classes
import ggc.core.exception.BadEntryException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register new partner.
 */
class DoRegisterPartner extends Command<WarehouseManager> {

  DoRegisterPartner(WarehouseManager receiver) {
    super(Label.REGISTER_PARTNER, receiver);
    addStringField("partnerId", Message.requestPartnerKey());
    addStringField("partnerName", Message.requestPartnerName());
    addStringField("partnerAddress", Message.requestPartnerAddress());
    //FIXME add command fields
  }

  @Override
  public void execute() throws CommandException {
    String name = stringField("partnerName");
    String address = stringField("partnerAddress");
    String id = stringField("partnerId"); 
    try {
      _receiver.addPartner(name, address, id);
    } catch (BadEntryException e) {
      throw new DuplicatePartnerKeyException(id);//TODO: handle exception
    }
    
      //TODO: handle exception
    
    //FIXME implement command
  }

}
