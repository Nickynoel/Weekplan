package Weekplan;

import AddProgressArea.AddProgressArea;
import DeleteArea.DeleteArea;
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

    public static Weekplan getInstance()
    {
        Weekplan plan = new Weekplan();
        plan.addListeners();
        return plan;
    }

    private Weekplan()
    {
        _listOfTasks = TaskList.getInstance();
        _ui = new WeekplanUI(_listOfTasks);
    }

    private void addListeners()
    {
        _ui.getCreateItem().addActionListener(event -> createTask());
        _ui.getDeleteItem().addActionListener(event -> openDeleteArea());
        _ui.getOptionsItem().addActionListener(event -> openOptionsArea());
        _ui.getCloseItem().addActionListener(event -> closeTracker());

        for (JButton taskTitle : _ui.getTitleButtonList())
            taskTitle.addActionListener(event -> openTaskEditArea(taskTitle));

        for (JButton addButton : _ui.getAddButtonlist())
            addButton.addActionListener(event -> openAddProgressArea(addButton));

        _ui.getOptionButton().addActionListener(event -> openOptionsArea());
        _ui.getSaveButton().addActionListener(event -> saveTracker());
        _ui.getCloseButton().addActionListener(event -> closeTracker());
    }

//---------------------------- Listeners: Start -----------------------------------

    private void createTask()
    {
        _listOfTasks.addNewEmptyTask();
        _listOfTasks.saveTasksOnFile();
        _ui.close();
        Weekplan plan = Weekplan.getInstance();
        plan.openTaskEdit();
    }

    private void openDeleteArea()
    {
        _ui.close();
        new DeleteArea();
    }

    /**
     * OptionButton.actionListener: Closes window and opens OptionArea
     */
    private void openOptionsArea()
    {
        _listOfTasks.saveTasksOnFile();
        _ui.close();
        new OptionArea();
    }

    /**
     * CloseButton.actionListener: Saves the Tracker and closes it
     */
    private void closeTracker()
    {
        _listOfTasks.saveTasksOnFile();
        _ui.close();
    }

    /**
     * TitleButton.actionListener: Opens TaskEditArea and gives it an observer
     * @param button
     */
    private void openTaskEditArea(JButton button)
    {
        int taskNumber = _ui.getTitleButtonList().indexOf(button);
        Task task = _listOfTasks.get(taskNumber);
        final TaskEditArea area = new TaskEditArea(task, _ui.getMainframe());

        area.addPropertyChangeListener(evt -> _ui.updateTask(task));
        area.showUI();
    }

    /**
     * AddButton.actionListener: Opens AddProgressArea and gives it an observer
     * @param button
     */
    private void openAddProgressArea(JButton button)
    {
        int topicNumber = _ui.getAddButtonlist().indexOf(button);
        Task task = _listOfTasks.get(topicNumber);
        final AddProgressArea area = new AddProgressArea(task, _ui.getMainframe());

        area.addPropertyChangeListener(evt -> _ui.updateProgress(task));
        area.showUI();
    }

    /**
     * SaveButton.actionListener: Saves the topic-values onto the file TopicList.FILENAME
     */
    private void saveTracker()
    {
        _listOfTasks.saveTasksOnFile();
    }
//---------------------------- Listeners: End -----------------------------------
    /**
     * Opens the editing field of a (newly created) task
     * - the "TaskEditArea" with the title "New Task"
     * TODO: This smells
     */
    public void openTaskEdit()
    {
        _ui.openNewTopicMenu();
    }
}
