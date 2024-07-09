package FrontEnd.AddProgressArea;

import BackEnd.TaskList.Task.Task;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Functional class AddProgressArea for the window that accepts the input of the length of an action
 * and adds its value to the progress of a given task
 */
public class AddProgressArea
{
    private final PropertyChangeSupport _support; //basically observable just newer
    private final Task _task;
    private final AddProgressAreaUI _ui;

    public AddProgressArea(Task task, JFrame frame) {
        _support = new PropertyChangeSupport(this);
        _task = task;

        _ui = new AddProgressAreaUI();
        _ui.loadTaskData(task);
        _ui.setPositionRelativeToMainFrame(frame);

        addUIListeners();
    }

    private void addUIListeners() {
        _ui.getBackButton().addActionListener(event -> _ui.close());

        _ui.getTextField().addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                validateInputForConfirmButton();
            }
        });

        _ui.getTextField().addActionListener(event -> submitEntry()); //Shortcut for enter-key
        _ui.getConfirmButton().addActionListener(event -> submitEntry());
    }

    // ----------------------- Listeners: Start ----------------------------------

    private boolean isTextFieldEntryValid() {
        return _ui.getUserInput().matches("-?\\d+"); // positive or negative number
    }

    private void validateInputForConfirmButton() {
        if (isTextFieldEntryValid())
            _ui.enableConfirmButton();
        else
            _ui.disableConfirmButton();
    }

    private void submitEntry() {
        if (isTextFieldEntryValid()) {
            try {
                int number = Integer.parseInt(_ui.getUserInput());
                _task.addProgress(number);
                //Tells the PropertyChangeListeners that a change happens if number!=0
                _support.firePropertyChange("Test", "0", number);
                _ui.close();
            }
            //should never happen, cause the textField-keyListener checks this
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Entry is NaN and the TextField-check was wrong");
            }
        }
    }

    //---------------------------- Listeners: End -----------------------------------

    /**
     * Allows listeners to be added
     *
     * @param pcl: the new listener
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        _support.addPropertyChangeListener(pcl);
    }

    /**
     * Sets the visibility of the UI to true
     */
    public void showUI() {
        _ui.showUI();
    }
}