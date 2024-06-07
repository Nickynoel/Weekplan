package DeleteArea;

import TaskList.TaskList;
import Weekplan.Weekplan;

/**
 * Functional class DeleteArea, in which the user is able to delete tasks out of the TaskList
 * Returns to Startup afterward
 */

public class DeleteArea
{
    private TaskList _taskList;
    private DeleteAreaUI _ui;

    public DeleteArea()
    {
        _taskList = TaskList.getInstance();
        _ui = new DeleteAreaUI();
        _ui.loadTaskList(_taskList);

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
    }

    private void exitDeleteArea()
    {
        _ui.close();
        Weekplan.getInstance();
    }
}
