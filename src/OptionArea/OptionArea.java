package OptionArea;

import Settings.Settings;
import TaskList.TaskList;
import DeleteArea.DeleteArea;
import Weekplan.Weekplan;

import javax.swing.*;

/**
 * Functional class OptionArea, in which the user can choose between several options:
 * Change total goaltime to a positive number of hours
 * AddTopic, DeleteTopic, Back
 */

public class OptionArea
{
    private OptionAreaUI _ui;
    private Settings _settingList;
    
    public OptionArea()
    {
        _ui = new OptionAreaUI();
        _settingList = Settings.getInstance(Settings.SETTINGSFILE);
        setSettings();
        addListener();
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
     * TotalGoalInput.actionlistener: Sets the total amount to a given number
     * AddButton.actionlistener: adds a new blank topic and goes back to Weekplan
     * DeleteButton.actionlistener: opens a new menu with topics of which the chosen ones can be deleted
     * BackButton.actionlistener: goes back to Weekplan
     */
    private void addListener()
    {
        _ui.getTotalGoalInput().addActionListener(event ->
        {
            String tmp = _ui.getTotalGoalInput().getText();
            try
            {
                int number = Integer.parseInt(tmp);
                if (number > 0)
                {
                    TaskList list = TaskList.getInstance();
                    
                    list.setTotalTargetTime(number * 60);//Turn entry from hours to minutes
                    list.saveTasksOnFile();
                    _ui.getTotalGoalInput().setText("");
                }
            }
            catch (NumberFormatException e) //should never happen, cause the textfield-keylistener checks this
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
}
