package TaskEditArea;

import TaskList.Task.Task;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Functional class TaskEditArea for the window that allows editing basic values of the given Topic:
 * Name, targetLength
 */
public class TaskEditArea
{
    private int MAXTASKDISPLAYLENGTH = 13;

    private final Task _task;
    private final TaskEditAreaUI _ui;

    //These values only get saved upon hitting "confirm"
    private String _taskName; // temporary name of the topic
    private int _taskTargetTime; // temporary length of the topic
    
    private PropertyChangeSupport _support;
    
    public TaskEditArea(Task task, JFrame frame)
    {
        _task = task;
        _ui = new TaskEditAreaUI();
        _taskName = task.getTitle();
        _taskTargetTime = task.getTargetTime();
        _support = new PropertyChangeSupport(this);

        createUI(frame);
        addListener();
    }
    
    /**
     * Creates the UI provided by TopicAreaUI
     * Also sets its title and provides the topics name and length
     */
    private void createUI(JFrame frame)
    {
        _ui.setTitle(_task.getTitle() + ": " + _task.getProgress() + " Min.");
        //location relative to the frame in the background
        _ui.setPosition(new Point(frame.getLocation().x + 50, frame.getLocation().y + 100));
        updateTaskLabel();
        updateTargetTimeLabel();
    }
    
    /**
     * Adds the listeners of all components in the UI:
     * BackButton.actionListener: Closes the UI and returns to the mainScreen
     * ConfirmButton.actionListener: Closes the UI and saves the temporarily changed values as new values for the task
     * TaskField.actionListener: If the entry is valid (trimmed and no '#'), it becomes the temporarily new name of the task
     * TargetTimeField.actionListener: If the entry is valid (int > 0), it becomes the temporarily new targetTime of the task
     */
    private void addListener()
    {
        _ui.getTaskField().addActionListener(event ->
        {
            String input = _ui.getTaskField().getText();
            if (isValidTaskTitle(input))
            {
                _taskName = input;
                updateTaskLabel();
                _ui.clearTaskField();
            }
        });
        
        _ui.getTargetTimeField().addActionListener(event ->
        {
            String input = _ui.getTargetTimeField().getText();
            if (isValidTargetTime(input))
            {
                _taskTargetTime = Integer.parseInt(input);
                updateTargetTimeLabel();
                _ui.clearTargetTimeField();
            }
        });
        
        _ui.getConfirmButton().addActionListener(event ->
        {
            _task.setTitle(_taskName);
            _task.setTargetTime(_taskTargetTime);
            confirmChange(1);
            _ui.close();
        });
        
        _ui.getBackButton().addActionListener(event -> _ui.close());
    }
    
    /**
     * Checks if a given string is valid as a title for a topic:
     * Cant begin or end with an empty space or contain an '#'
     *
     * @param s: String to potentially become the title of a topic
     * @return result as boolean
     */
    private boolean isValidTaskTitle(String s)
    {
        return s.equals(s.trim()) && !s.contains("#");
    }
    
    /**
     * Checks if a given string is valid as a goal for a task:
     * Has to be a positive integer-value
     *
     * @param input: String to potentially become the length of a task
     * @return result as boolean
     */
    private boolean isValidTargetTime(String input)
    {
        return input.matches("\\d+") && !input.equals("0");
    }
    
    /**
     * Updates the text on the UI's lengthLabel with the current temporary _topicLength value
     */
    private void updateTargetTimeLabel()
    {
        _ui.setLengthLabel("Goal: " + _taskTargetTime + " Minutes");
    }
    
    /**
     * Updates the text on the UI's taskLabel with the current temporary _taskName
     */
    private void updateTaskLabel()
    {
        if (_taskName.length() > MAXTASKDISPLAYLENGTH)
        {
            _ui.setTopicLabel("Name: " + _taskName.substring(0, MAXTASKDISPLAYLENGTH)+".");
        }
        else
        {
            _ui.setTopicLabel("Name: " + _taskName);
        }
        
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
     * Tells the PropertyChangeListeners that a change happens if confirm was clicked
     *
     * @param number: the number typed into the textField
     */
    public void confirmChange(int number)
    {
        _support.firePropertyChange("Test", 0, number);
    }
}
