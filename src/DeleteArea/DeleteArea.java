package DeleteArea;

import TaskList.TaskList;
import Weekplan.Weekplan;

/**
 * Functional class DeleteArea, in which the user is able to delete topics out of the TopicList
 * Returns to Startup afterward
 */

public class DeleteArea
{
    private DeleteAreaUI _ui;
    private TaskList _taskList;
    
    public DeleteArea()
    {
        initializeFields();
        addDeleteAreaUIListeners();
    }

    private void initializeFields()
    {
        _taskList = TaskList.getInstance();
        _ui = new DeleteAreaUI();
        _ui.setList(_taskList.getArrayOfTaskTitles());
    }

    private void addDeleteAreaUIListeners()
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
        {
            _ui.disableDeleteButton();
        }
        else
        {
            _ui.enableDeleteButton();
        }
    }

    private void deleteSelectedTasks()
    {
        int[] list = _ui.getJList().getSelectedIndices(); //returns a list of indeces in increasing order
        _taskList.removeTasks(list);
        _taskList.saveTasksOnFile();
    }

    private void exitDeleteArea()
    {
        _ui.close();
        new Weekplan();
    }
}
