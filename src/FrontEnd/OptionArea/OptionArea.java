package FrontEnd.OptionArea;

import BackEnd.Settings.Settings;
import BackEnd.TaskList.TaskList;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Functional class FrontEnd.OptionArea, in which the user can choose between several options:
 * Change total targetTime to a positive number of hours
 */

public class OptionArea
{
    private final PropertyChangeSupport _support; //basically observable just newer
    private final Settings _settingList;
    private final OptionAreaUI _ui;
    private boolean _isChanged;

    public OptionArea(JFrame frame)
    {
        _support = new PropertyChangeSupport(this);
        _settingList = Settings.getInstance(Settings.DEFAULTSETTINGSFILE);

        _ui = new OptionAreaUI();
        _ui.loadSettings(_settingList);
        _ui.setPositionRelativeToMainFrame(frame);

        _isChanged = false;
        addListeners();
    }

    /**
     * Adds the listeners of the components of AddAreaUI
     */
    private void addListeners()
    {
        _ui.getTotalTargetInput().addActionListener(event -> changeTotalTargetTime());
        _ui.getResetComboBox().addActionListener(event -> changeResetType());
        _ui.getBackButton().addActionListener(event -> goToWeekplan());
    }

    /**
     * Sets the total amount to a given number and adjust individual target times
     */
    private void changeTotalTargetTime()
    {
        String input = _ui.getTotalTargetInput().getText();
        if (isValidTotalTime(input))
        {
            int number = Integer.parseInt(input);
            TaskList list = TaskList.getInstance();

            list.setTotalTargetTime(number * 60); // Turn entry from hours to minutes
            list.saveTasksOnFile();
            _ui.clearTotalTargetInput();
            _isChanged = true;
        }
        else
        {
            JOptionPane.showMessageDialog(new JFrame(), "Entry is not a positive integer");
        }
    }

    private void changeResetType()
    {
        _settingList.setResetProgram(String.valueOf(_ui.getResetComboBox().getSelectedItem()));
        _settingList.saveSettings();
        _isChanged = true;
    }

    /**
     * Returns back to FrontEnd.Weekplan
     */
    private void goToWeekplan()
    {
        if (_isChanged) _support.firePropertyChange("Test", 0, 1);
        _ui.close();
    }

    /**
     * Checks if a given string is valid as a total amount of time for all targets:
     * Has to be a positive integer-value
     *
     * @param input: String to potentially become the length of a task
     * @return result as boolean
     */
    private boolean isValidTotalTime(String input)
    {
        return input.matches("\\d+") && !input.equals("0");
    }

    /**
     * Allows listeners to be added
     *
     * @param pcl: the new listener
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl)
    {
        _support.addPropertyChangeListener(pcl);
    }

    /**
     * Sets the visibility of the UI to true
     */
    public void showUI()
    {
        _ui.showUI();
    }
}
