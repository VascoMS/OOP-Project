package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.FileOpenFailedException;
import ggc.core.WarehouseManager;
//FIXME import classes
import ggc.core.exception.UnavailableFileException;

/**
 * Open existing saved state.
 */
class DoOpenFile extends Command<WarehouseManager> {


  /** @param receiver */
  DoOpenFile(WarehouseManager receiver) {
    super(Label.OPEN, receiver);
    addStringField("filename", Message.openFile());
    //FIXME maybe add command fields
  }

  @Override
  public final void execute() throws CommandException {
    String _filename;
    try {
      //FIXME implement command
      _filename = stringField("filename");
      _receiver.load(_filename);
    } catch (UnavailableFileException ufe) {
      throw new FileOpenFailedException(ufe.getFilename());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

}
