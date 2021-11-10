package ggc.app.products;

import java.util.List;

import ggc.core.Batch;
import ggc.core.WarehouseManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show available batches.
 */
class DoShowAvailableBatches extends Command<WarehouseManager> {

  DoShowAvailableBatches(WarehouseManager receiver) {
    super(Label.SHOW_AVAILABLE_BATCHES, receiver);
  }

  @Override
  public final void execute() throws CommandException {
    List<Batch> batches = _receiver.getBatchesSorted();
    for(Batch batch: batches){
      _display.addLine(batch.toString());
    }
    _display.display();
  }

}
