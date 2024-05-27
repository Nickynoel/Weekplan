package AddProgressArea;

import TaskList.Task.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Functional class AddArea for the window that accepts the input of the length of an action
 * and adds its value to the progress of a given topic
 */
public class AddProgressArea
{
    private AddProgressAreaUI _ui;
    private Task _task;

    private PropertyChangeSupport _support; //basically observable just newer

    public AddProgressArea(Task task, JFrame frame)
    {
        _support = new PropertyChangeSupport(this);
        _task = task;
        _ui = new AddProgressAreaUI();
        _ui.setTitle(task.getTitle());
        //Sets position based on the mainframe
        _ui.setPosition(new Point(frame.getLocation().x + 100, frame.getLocation().y + 100));
        addListener();
    }

    /**
     * Adds the listeners of the components of AddAreaUI:
     * BackButton.actionlistener: just closes
     * TextField.keylistener: checks the validity of the entry
     * Textfield.actionlistener: Shortcut to confirmButton
     * ConfirmButton.actionlistener: Processing of the entry
     */
    private void addListener()
    {
        _ui.getBackButton().addActionListener(event ->
        {
            _ui.close();
        });

        //If the text gets changed it checks it anew and controls the availability of the button
        _ui.getTextfield().addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                super.keyReleased(e);
                String tmp = _ui.getTextfield().getText();
                if (isValidEntry(tmp))
                {
                    _ui.enableConfirmButton();
                } else
                {
                    _ui.disableConfirmButton();
                }
            }
        });

        //Shortcut for enter-key
        _ui.getTextfield().addActionListener(event ->
        {
            //doClick() automatically checks "isEnabled()"
            _ui.getConfirmButton().doClick();
        });

        _ui.getConfirmButton().addActionListener(event ->
        {
            String tmp = _ui.getTextfield().getText();
            try
            {
                int number = Integer.parseInt(tmp);
                _task.addProgress(number);
                confirmChange(number);
                _ui.close();
            }
            //should never happen, cause the textField-keyListener checks this
            catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(new JFrame(), "Entry is NaN and the textfield-check was wrong");
            }
        });
    }

    /**
     * Checks if the input/given string is a number or a negative number
     *
     * @param tmp: checked entry
     * @return boolean: validity of the string
     */
    private boolean isValidEntry(String tmp)
    {
        return tmp.matches("-?\\d+");
    }

    /**
     * Tells the PropertyChangeListeners that a change happens if number!=0
     *
     * @param number: the number typed into the JTextField
     */
    private void confirmChange(int number)
    {
        _support.firePropertyChange("Test", 0, number);
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
     * Shows the AddAreaUI
     * Neccessary for observer Weekplan
     */
    public void showUI()
    {
        _ui.showUI();
    }
}
