package ggc.app.partners;

import ggc.core.Partner;
import ggc.core.WarehouseManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show all partners.
 */
class DoShowAllPartners extends Command<WarehouseManager> {

  DoShowAllPartners(WarehouseManager receiver) {
    super(Label.SHOW_ALL_PARTNERS, receiver);
  }

  @Override
  public void execute() throws CommandException {
    for(Partner partner : _receiver.getSortedPartners()){
      _display.addLine(partner);
    }
    _display.display();
  }

}
