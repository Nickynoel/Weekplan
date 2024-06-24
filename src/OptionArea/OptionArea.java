package OptionArea;

import Settings.Settings;
import TaskList.TaskList;
import Weekplan.Weekplan;

import javax.swing.*;

/**
 * Functional class OptionArea, in which the user can choose between several options:
 * Change total targetTime to a positive number of hours
 * AddTask, DeleteTask, Back
 */

public class OptionArea
{
    private final Settings _settingList;
    private final OptionAreaUI _ui;

    public OptionArea()
    {
        _settingList = Settings.getInstance(Settings.DEFAULTSETTINGSFILE);
        _ui = new OptionAreaUI();
        _ui.loadSettings(_settingList);

        addListeners();
    }

    /**
     * Adds the listeners of the components of AddAreaUI
     */
    private void addListeners()
    {
        _ui.getTotalTargetInput().addActionListener(event -> changeTotalTargetTime());
        _ui.getResetComboBox().addActionListener(event -> changeResetType());
        _ui.getAddButton().addActionListener(event -> createNewTask());
        _ui.getDeleteButton().addActionListener(event -> goToDeleteArea());
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
    }

    /**
     * Creates a new blank task and goes back to Weekplan
     */
    private void createNewTask()
    {
        TaskList list = TaskList.getInstance();
        list.addNewEmptyTask();
        list.saveTasksOnFile();
        _ui.close();
        Weekplan plan = Weekplan.getInstance();
        plan.openTaskEdit();
    }

    /**
     * Opens a new menu with a list of tasks of which the chosen ones can be deleted
     */
    private void goToDeleteArea()
    {
        _ui.close();
        Weekplan.getInstance().openDeleteArea();
    }

    /**
     * Returns back to Weekplan
     */
    private void goToWeekplan()
    {
        _ui.close();
        Weekplan.getInstance();
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
}
