package TaskEditArea;

import TaskList.Task.Task;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Functional class TaskEditArea for the window that allows editing basic values of the given Topic:
 * Name, targetLength
 */
public class TaskEditArea
{
    private final PropertyChangeSupport _support;
    private final Task _task;
    private final TaskEditAreaUI _ui;

    //These values only get saved upon hitting "confirm" - temporary cache until then
    private String _taskName;
    private int _taskTargetTime;

    public TaskEditArea(Task task, JFrame frame)
    {
        _support = new PropertyChangeSupport(this);
        _task = task;

        _ui = new TaskEditAreaUI();
        _ui.loadTaskData(task);
        _ui.setPositionRelativeToMainFrame(frame);

        _taskName = task.getTitle();
        _taskTargetTime = task.getTargetTime();

        addListener();
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
                _ui.setTaskLabel(_taskName);
                _ui.clearTaskField();
            }
        });
        
        _ui.getTargetTimeField().addActionListener(event ->
        {
            String input = _ui.getTargetTimeField().getText();
            if (isValidTargetTime(input))
            {
                _taskTargetTime = Integer.parseInt(input);
                _ui.setTargetTimeLabel(_taskTargetTime);
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
