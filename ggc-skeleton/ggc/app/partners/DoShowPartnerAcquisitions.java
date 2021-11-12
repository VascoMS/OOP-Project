package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.Acquisition;
import ggc.core.WarehouseManager;
import ggc.core.exception.CoreUnknownPartnerKeyException;

/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerAcquisitions extends Command<WarehouseManager> {

  DoShowPartnerAcquisitions(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_ACQUISITIONS, receiver);
    addStringField("partnerId", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    try {
        for(Acquisition acquisition : _receiver.getPartnerAcquisitions(stringField("partnerId")))
          _display.addLine(acquisition.toString(_receiver.getDateWarehouse()));
        _display.display();
    } catch (CoreUnknownPartnerKeyException e) {
      throw new UnknownPartnerKeyException(stringField("partnerId"));
    }
  }

}
