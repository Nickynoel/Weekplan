package AddArea;

import TopicList.Topic.Topic;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Functional class AddArea for the window that accepts the input of the length of an action
 * and adds its value to the progress of a given topic
 */
public class AddArea
{
    private AddAreaUI _ui;
    private Topic _topic;
    
    private PropertyChangeSupport _support; //basically observable just newer
    
    public AddArea(Topic topic, JFrame frame)
    {
        _support = new PropertyChangeSupport(this);
        _topic = topic;
        _ui = new AddAreaUI(frame);
        _ui.setTitle(topic.getTitel());
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
        //Leaves if just going back
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
                if (isValid(tmp))
                {
                    _ui.enableConfirmButton();
                }
                else
                {
                    _ui.disableConfirmButton();
                }
                
            }
        });
        
        //Shortcut for enter-key if the _confirmButton is enabled
        _ui.getTextfield().addActionListener(event ->
        {
            _ui.getConfirmButton().doClick(); //doClick() automatically checks "isEnabled()"
        });
        
        //Actual action if the _confirmButton gets used and processes the entry
        _ui.getConfirmButton().addActionListener(event ->
        {
            String tmp = _ui.getTextfield().getText();
            try
            {
                int number = Integer.parseInt(tmp);
                _topic.addProgress(number);
                confirmChange(number);
                _ui.close();
            }
            catch (NumberFormatException e) //should never happen, cause the textfield-keylistener checks this
            {
                JOptionPane.showMessageDialog(new JFrame(), "Entry is NaN and check textfield-check was wrong");
            }
        });
    }
    
    /**
     * Checks if the input/given string is a number or a negative number
     *
     * @param tmp: checked entry
     * @return boolean: validity of the string
     */
    private boolean isValid(String tmp)
    {
        if (tmp.matches("-?\\d+"))
        {
            return true;
        }
        return false;
    }
    
    /**
     * Shows the AddAreaUI
     * Neccessary for observer Weekplan
     */
    public void showUI()
    {
        _ui.showUI();
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
     * Allows listeners to be removed
     *
     * @param pcl: the removed listener
     */
    public void removePropertyChangeListener(PropertyChangeListener pcl)
    {
        _support.removePropertyChangeListener(pcl);
    }
    
    
    /**
     * Tells the PropertyChangeListeners that a change happens if number!=0
     *
     * @param number: the number typed into the textfield
     */
    public void confirmChange(int number)
    {
        _support.firePropertyChange("Test", 0, number);
    }
}
