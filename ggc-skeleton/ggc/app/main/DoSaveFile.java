package ggc.app.main;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


import ggc.core.WarehouseManager;
import ggc.core.exception.MissingFileAssociationException;

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<WarehouseManager> {

  /** @param receiver */
  DoSaveFile(WarehouseManager receiver) {
    super(Label.SAVE, receiver);
  }

  @Override
  public final void execute() throws CommandException {
    try{
      _receiver.save();
    } catch(MissingFileAssociationException exe){
      try{
      _receiver.saveAs(Form.requestString(Message.newSaveAs()));
      } catch(MissingFileAssociationException e){
        System.err.println("Invalid filename");
        }
    }
  }
}
