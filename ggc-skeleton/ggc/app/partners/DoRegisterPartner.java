package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
//FIXME import classes

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
    _receiver.addPartner(name, address, id);
      //TODO: handle exception
    
    //FIXME implement command
  }

}
