package OptionArea;

import Settings.Settings;
import TaskList.TaskList;
import DeleteArea.DeleteArea;
import Weekplan.Weekplan;

import javax.swing.*;

/**
 * Functional class OptionArea, in which the user can choose between several options:
 * Change total targetTime to a positive number of hours
 * AddTopic, DeleteTopic, Back
 */

public class OptionArea
{
    private OptionAreaUI _ui;
    private Settings _settingList;
    
    public OptionArea()
    {
        initializeFields();
        setSettings();
        addListener();
    }

    private void initializeFields()
    {
        _ui = new OptionAreaUI();
        _settingList = Settings.getInstance(Settings.DEFAULTSETTINGSFILE);
    }

    /**
     * Transfers the settings onto the UI
     */
    private void setSettings()
    {
        _ui.setResetProgram(_settingList.getResetProgram());
    }
    
    /**
     * Adds the listeners of the components of AddAreaUI:
     * TotalGoalInput.actionListener: Sets the total amount to a given number
     * AddButton.actionListener: adds a new blank topic and goes back to Weekplan
     * DeleteButton.actionListener: opens a new menu with topics of which the chosen ones can be deleted
     * BackButton.actionListener: goes back to Weekplan
     */
    private void addListener()
    {
        _ui.getTotalTargetInput().addActionListener(event ->
        {
            String input = _ui.getTotalTargetInput().getText();
            if (isValidTotalTime(input))
            {
                int number = Integer.parseInt(input);
                TaskList list = TaskList.getInstance();

                list.setTotalTargetTime(number * 60); //Turn entry from hours to minutes
                list.saveTasksOnFile();
                _ui.clearTotalTargetInput();
            }
            else
            {
                JOptionPane.showMessageDialog(new JFrame(), "Entry is not a positive integer");
            }
        });
        
        _ui.getResetComboBox().addActionListener(event ->
        {
            _settingList.setResetProgram(_ui.getResetComboBox().getSelectedIndex());
            _settingList.save();
        });

        _ui.getAddButton().addActionListener(event ->
        {
            TaskList list = TaskList.getInstance();
            list.addNewEmptyTask();
            list.saveTasksOnFile();
            _ui.close();
            Weekplan plan = new Weekplan();
            plan.openTaskEdit();
        });
        
        _ui.getDeleteButton().addActionListener(event ->
        {
            _ui.close();
            new DeleteArea();
        });
        
        _ui.getBackButton().addActionListener(event ->
        {
            _ui.close();
            new Weekplan();
        });
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
