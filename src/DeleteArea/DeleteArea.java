package DeleteArea;

import TaskList.TaskList;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Functional class DeleteArea, in which the user is able to delete tasks out of the TaskList
 * Returns to Startup afterward
 */

public class DeleteArea
{
    private final PropertyChangeSupport _support; //basically observable just newer
    private final TaskList _taskList;
    private final DeleteAreaUI _ui;

    public DeleteArea(JFrame frame)
    {
        _support = new PropertyChangeSupport(this);
        _taskList = TaskList.getInstance();

        _ui = new DeleteAreaUI();
        _ui.loadTaskList(_taskList);
        _ui.setPositionRelativeToMainFrame(frame);

        addListeners();
    }

    private void addListeners()
    {
        _ui.getJList().addListSelectionListener(event -> validateDeleteButton());

        _ui.getDeleteButton().addActionListener(event ->
        {
            deleteSelectedTasks();
            exitDeleteArea();
        });

        _ui.getBackButton().addActionListener(event -> exitDeleteArea());
    }

    /**
     * Disables the DeleteButton iff nothing in the list is selected
     */
    private void validateDeleteButton()
    {
        if (_ui.getJList().getMinSelectionIndex() == -1)
            _ui.disableDeleteButton();
        else
            _ui.enableDeleteButton();
    }

    private void deleteSelectedTasks()
    {
        int[] list = _ui.getJList().getSelectedIndices(); //returns a list of indices in increasing order
        _taskList.removeTasks(list);
        _taskList.saveTasksOnFile();
        _support.firePropertyChange("Test", 0, 1);
    }

    private void exitDeleteArea()
    {
        _ui.close();
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
