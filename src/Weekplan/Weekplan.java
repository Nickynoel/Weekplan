package Weekplan;

import AddProgressArea.AddProgressArea;
import OptionArea.OptionArea;
import TaskEditArea.TaskEditArea;
import TaskList.Task.Task;
import TaskList.TaskList;

import javax.swing.*;

/**
 * Functional class of the main UI
 */
public class Weekplan
{
    private TaskList _listOfTasks;
    private WeekplanUI _ui;
    
    public Weekplan()
    {
        _listOfTasks = TaskList.getInstance();
        _ui = new WeekplanUI(_listOfTasks);
        addListener();
    }
    
    /**
     * Adds the listeners of all components in the UI:
     * AddButton.actionListener: Opens AddArea and gives it an observer
     * Label.mouseListener: Opens TopicArea and gives it an observer
     * SaveButton.actionListener: Saves the topic-values onto the file TopicList.FILENAME
     * OptionButton.actionListener: Closes window and opens OptionArea
     */
    private void addListener()
    {
        //listeners for task-options on the left side
        for (JButton title : _ui.getTitleButtonList())
        {
            title.addActionListener(event ->
            {
                int taskNumber = _ui.getTitleButtonList().indexOf(title);
                Task task = _listOfTasks.get(taskNumber);
                final TaskEditArea area = new TaskEditArea(task, _ui.getMainframe());
                
                area.addPropertyChangeListener(evt -> _ui.updateTask(task));
                area.showUI();
            });
        }
        
        //listeners for adding values on the right side
        for (JButton button : _ui.getAddButtonlist())
        {
            button.addActionListener(event ->
            {
                int topicNumber = _ui.getAddButtonlist().indexOf(button);
                Task task = _listOfTasks.get(topicNumber);
                final AddProgressArea area = new AddProgressArea(task, _ui.getMainframe());
                
                area.addPropertyChangeListener(evt -> _ui.updateProgress(task));
                area.showUI();
            });
        }
        
        
        //listener for saving
        _ui.getSaveButton().addActionListener(event -> _listOfTasks.saveTasksOnFile());
        
        //listener for options - TODO: Options into a JMenuBar
        _ui.getOptionButton().addActionListener(event ->
        {
            _listOfTasks.saveTasksOnFile();
            _ui.close();
            new OptionArea();
        });
        
        _ui.getCloseButton().addActionListener(event ->
        {
            _listOfTasks.saveTasksOnFile();
            _ui.close();
        });
    }
    
    /**
     * Opens the editing field of a (newly created) task
     * - the "TopicArea" (TODO: Rename) -
     * with the title "New Task"
     */
    public void openTaskEdit()
    {
        _ui.openNewTopicMenu();
    }
}
