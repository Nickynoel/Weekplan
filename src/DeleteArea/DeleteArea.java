package DeleteArea;

import TaskList.TaskList;
import Weekplan.Weekplan;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;

/**
 * Functional class DeleteArea, in which the user is able to delete topics out of the TopicList
 *
 * Returns to Startup afterwards
 */

public class DeleteArea
{
    private DeleteAreaUI _ui;
    private TaskList _taskList;
    
    public DeleteArea()
    {
        _taskList = TaskList.getInstance();
        _ui = new DeleteAreaUI();
        _ui.setList(_taskList.getArrayOfTaskTitles());
        addListener();
    }
    
    /**
     * Adds the listeners of the components of DeleteAreaUI:
     * List.ListSelectionListener: Disables the DeleteButton iff nothing in the list is selected
     * AddButton.actionlistener: adds a new blank topic and goes back to Weekplan
     * DeleteButton.actionlistener: opens a new menu with topics of which the chosen ones can be deleted
     * BackButton.actionlistener: goes back to Weekplan
     */
    private void addListener()
    {
        //Checks number of selected entries, cause you can only delete if something is selected
        _ui.getJList().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
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
        });
        
        //Actual deletion of entries: Delete, save progress, back to Weekplan
        _ui.getDeleteButton().addActionListener(event ->
        {
            int[] list = _ui.getJList().getSelectedIndices(); //returns a list of indeces in increasing order
            _taskList.removeTasks(list);
            _taskList.saveTasksOnFile();
            exitDeleteArea();
        });
        
        //Back to mainscreen
        _ui.getBackButton().addActionListener(event ->
        {
            exitDeleteArea();
        });
    }

    private void exitDeleteArea()
    {
        _ui.close();
        new Weekplan();
    }
}
