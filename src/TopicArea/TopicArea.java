package TopicArea;

import TopicList.Topic.Topic;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Observable;

/**
 * Functional class TopicArea for the window that allows editing basic values of the given Topic:
 * Name, goallength
 */
public class TopicArea
{
    private Topic _topic;
    private TopicAreaUI _ui;
    
    private String _topicName; //temporary name of the topic
    private int _topicLength; //temporary length of the topic
    
    private PropertyChangeSupport _support;
    
    public TopicArea(Topic topic, JFrame frame)
    {
        _support = new PropertyChangeSupport(this);
        _topic = topic;
        _topicName = topic.getTitel();
        _topicLength = topic.getLength();
        
        createUI(frame);
        addListener();
    }
    
    /**
     * Creates the UI provided by TopicAreaUI
     * Also sets its title and provides the topics name and length
     */
    private void createUI(JFrame frame)
    {
        _ui = new TopicAreaUI(frame);
        _ui.setTitle(_topic.getTitel() + ": " + _topic.getProgress() + " Min.");
        updateTopicLabel();
        updateLengthLabel();
    }
    
    /**
     * Adds the listeners of all components in the UI:
     * BackButton.actionlistener: Closes the UI and returns to the mainscreen
     * ConfirmButton.actionlistener: Closes the UI and saves the temporarily changed values as new values for the topic
     * TopicField.actionlistener: If the entry is valid (trimmed and no '#'), it becomes the temporarily new name of the topic
     * LengthField.actionlistener: If the entry is valid (int > 0), it becomes the temporarily new length of the topic
     */
    private void addListener()
    {
        _ui.getBackButton().addActionListener(event ->
        {
            _ui.close();
        });
        
        _ui.getConfirmButton().addActionListener(event ->
        {
            _topic.setName(_topicName);
            _topic.setGoal(_topicLength);
            setNews(1);
            _ui.close();
        });
        
        _ui.getTopicField().addActionListener(event ->
        {
            String tmp = _ui.getTopicField().getText();
            if (isValidTitel(tmp))
            {
                _topicName = tmp;
                updateTopicLabel();
                _ui.getTopicField().setText("");
            }
        });
        
        _ui.getLengthField().addActionListener(event ->
        {
            String tmp = _ui.getLengthField().getText();
            if (isValidGoal(tmp))
            {
                _topicLength = Integer.parseInt(tmp);
                updateLengthLabel();
                _ui.getLengthField().setText("");
            }
        });
    }
    
    /**
     * Checks if a given string is valid as a title for a topic:
     * Cant begin or end with an emptyspace or contain an '#'
     *
     * @param s: String to potentially become the title of a topic
     * @return result as boolen
     */
    private boolean isValidTitel(String s)
    {
        if (s.equals(s.trim()) && !s.contains("#"))
        {
            return true;
        }
        return false;
    }
    
    /**
     * Checks if a given string is valid as a goal for a topic:
     * Has to be a positive integer-value
     *
     * @param tmp: String to potentially become the length of a topic
     * @return result as boolean
     */
    private boolean isValidGoal(String tmp)
    {
        try
        {
            int tmpnumber = Integer.parseInt(tmp);
            if (tmpnumber > 0)
            {
                return true;
            }
        }
        catch (NumberFormatException e)
        {
        
        }
        return false;
    }
    
    /**
     * Updates the text on the UI's lengthLabel with the current temporary _topicLength value
     */
    private void updateLengthLabel()
    {
        _ui.setLengthLabel("Goal: " + _topicLength + " Minutes");
    }
    
    /**
     * Updates the text on the UI's topicLabel with the current temporary _topicName
     */
    private void updateTopicLabel()
    {
        _ui.setTopicLabel("Name: " + _topicName);
    }
    
    /**
     * Makes the UI visible (important for the Observer Weekplan)
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
     * Tells the PropertyChangeListeners that a change happens if confirm was clicked
     *
     * @param number: the number typed into the textfield
     */
    public void setNews(int number)
    {
        _support.firePropertyChange("Test", 0, number);
    }
}
