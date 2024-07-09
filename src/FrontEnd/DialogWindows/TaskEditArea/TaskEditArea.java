package FrontEnd.DialogWindows.TaskEditArea;

import BackEnd.TaskList.Task.Task;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Functional class TaskEditArea for the window that allows editing basic values of the given Task:
 * Name, targetTime
 */
public class TaskEditArea
{
    private final PropertyChangeSupport _support;
    private final Task _task;
    private final TaskEditAreaUI _ui;

    //These values only get saved upon hitting "confirm" - temporary cache until then
    private String _taskName;
    private int _taskTargetTime;

    public TaskEditArea(Task task, JFrame frame) {
        _support = new PropertyChangeSupport(this);
        _task = task;

        _ui = new TaskEditAreaUI();
        _ui.loadTaskData(task);
        _ui.setPositionRelativeToMainFrame(frame);

        _taskName = task.getTitle();
        _taskTargetTime = task.getTargetTime();

        addUIListeners();
    }

    /**
     * Adds the listeners of all components in the UI
     */
    private void addUIListeners() {
        _ui.getBackButton().addActionListener(event -> _ui.close());

        _ui.getTaskField().addActionListener(event -> changeTaskTitle());
        _ui.getTargetTimeField().addActionListener(event -> changeTaskTargetTime());
        _ui.getConfirmButton().addActionListener(event -> submitChanges());
    }

    // ----------------------- Listeners: Start ----------------------------------

    /**
     * If the entry is valid, it becomes the temporarily new name of the task
     */
    private void changeTaskTitle() {
        String input = _ui.getTaskField().getText();
        if (isValidTaskTitle(input)) {
            _taskName = input;
            _ui.setTaskLabel(_taskName);
            _ui.clearTaskField();
        }
    }

    /**
     * If the entry is valid, it becomes the temporarily new targetTime of the task
     */
    private void changeTaskTargetTime() {
        String input = _ui.getTargetTimeField().getText();
        if (isValidTargetTime(input)) {
            _taskTargetTime = Integer.parseInt(input);
            _ui.setTargetTimeLabel(_taskTargetTime);
            _ui.clearTargetTimeField();
        }
    }

    /**
     * Closes the UI and saves the temporarily changed values as new values for the task
     */
    private void submitChanges() {
        _task.setTitle(_taskName);
        _task.setTargetTime(_taskTargetTime);
        _support.firePropertyChange("Foo", 0, 1);
        _ui.close();
    }

    /**
     * Checks if a given string is valid as a title for a task:
     * Cant begin or end with an empty space or contain an '#'
     *
     * @param s: String to potentially become the title of a task
     * @return result as boolean
     */
    private boolean isValidTaskTitle(String s) {
        return s.equals(s.trim()) && !s.contains("#");
    }

    /**
     * Checks if a given string is valid as a goal for a task:
     * Has to be a positive integer-value
     *
     * @param input: String to potentially become the target time of a task
     * @return result as boolean
     */
    private boolean isValidTargetTime(String input) {
        return input.matches("\\d+") && !input.equals("0");
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
     * Makes the UI visible
     */
    public void showUI() {
        _ui.showUI(); // important for the observer FrontEnd.Weekplan
    }
}