package FrontEnd.Weekplan.SubComponents;

import javax.swing.*;

public class MenuBarUI
{
    private JMenuBar _menuBar;
    private JMenuItem _createItem;
    private JMenuItem _deleteItem;
    private JMenuItem _optionsItem;
    private JMenuItem _closeItem;
    private JMenuItem _undoItem;
    private JMenuItem _redoItem;

    public MenuBarUI(){
        initializeVariables();
        createMenuBar();
    }

    private void initializeVariables() {
        _menuBar = new JMenuBar();
        _createItem = new JMenuItem("New Task", 'N');
        _deleteItem = new JMenuItem("Delete Tasks", 'D');
        _optionsItem = new JMenuItem("Options", 'P');
        _closeItem = new JMenuItem("Close", 'E');

        _undoItem = new JMenuItem("Undo", 'U');
        _undoItem.setEnabled(false);
        _redoItem = new JMenuItem("Redo", 'R');
        _redoItem.setEnabled(false);
    }

    private void createMenuBar() {
        JMenu menu = new JMenu("File");
        menu.setMnemonic('F');
        menu.add(_createItem);
        menu.add(_deleteItem);
        menu.add(_optionsItem);
        menu.add(_closeItem);
        _menuBar.add(menu);

        JMenu options = new JMenu("Options");
        options.setMnemonic('O');
        options.add(_undoItem);
        options.add(_redoItem);
        _menuBar.add(options);
    }

    public JMenuBar getMenuBar(){
        return _menuBar;
    }

    public JMenuItem getCreateItem() {
        return _createItem;
    }

    public JMenuItem getDeleteItem() {
        return _deleteItem;
    }

    public JMenuItem getOptionsItem() {
        return _optionsItem;
    }

    public JMenuItem getCloseItem() {
        return _closeItem;
    }

    public JMenuItem getUndoItem() {
        return _undoItem;
    }

    public JMenuItem getRedoItem() {
        return _redoItem;
    }

    public void enableUndoButton() {
        _undoItem.setEnabled(true);
    }

    public void enableRedoButton() {
        _redoItem.setEnabled(true);
    }

    public void disableUndoButton() {
        _undoItem.setEnabled(false);
    }

    public void disableRedoButton() {
        _redoItem.setEnabled(false);
    }
}
