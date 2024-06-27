package Weekplan;

import AddProgressArea.AddProgressArea;
import DeleteArea.DeleteArea;
import OptionArea.OptionArea;
import TaskEditArea.TaskEditArea;
import TaskList.Task.Task;
import TaskList.TaskList;
import TaskList.ActionQueue;
import TaskList.Action.Action;

import javax.swing.*;

/**
 * Functional class of the main UI
 */
public class Weekplan
{
    private TaskList _listOfTasks;
    private WeekplanUI _ui;
    private ActionQueue _actionQueue;

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
        _actionQueue = new ActionQueue();
    }

    private void addListeners()
    {
        _ui.getCreateItem().addActionListener(event -> createTask());
        _ui.getDeleteItem().addActionListener(event -> openDeleteArea());
        _ui.getOptionsItem().addActionListener(event -> openOptionsArea());
        _ui.getCloseItem().addActionListener(event -> closeTracker());

        _ui.getUndoItem().addActionListener(event -> undoLastAction());
        _ui.getRedoItem().addActionListener(event -> redoLastAction());

        for (JButton taskTitle : _ui.getTitleButtonList())
            taskTitle.addActionListener(event -> openTaskEditArea(taskTitle));

        for (JButton addButton : _ui.getAddButtonlist())
            addButton.addActionListener(event -> openAddProgressArea(addButton));

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

    public void openDeleteArea()
    {
        final DeleteArea area = new DeleteArea(_ui.getMainframe());
        area.addPropertyChangeListener(evt -> refreshUI());
        area.showUI();
    }

    /**
     * OptionButton.actionListener: Closes window and opens OptionArea
     */
    private void openOptionsArea()
    {
        final OptionArea area = new OptionArea(_ui.getMainframe());
        area.addPropertyChangeListener(evt -> refreshUI());
        area.showUI();
    }

    /**
     * CloseButton.actionListener: Saves the Tracker and closes it
     */
    private void closeTracker()
    {
        _ui.close();
    }

    /**
     * Undo the last commited progress
     */
    public void undoLastAction()
    {
        Action lastAction = _actionQueue.undoLastAction();
        _ui.updateProgress(lastAction.getTask());
        if (_actionQueue.hasNoPriorActions())
            _ui.disableUndoButton();
        _ui.enableRedoButton();
    }

    /**
     * Redo the last commited progress
     */
    public void redoLastAction()
    {
        Action lastAction = _actionQueue.redoLastAction();
        _ui.updateProgress(lastAction.getTask());
        _ui.enableUndoButton();
        if (_actionQueue.hasNoUndoneActions())
            _ui.disableRedoButton();
    }

    /**
     * TitleButton.actionListener: Opens TaskEditArea and gives it an observer
     */
    private void openTaskEditArea(JButton button)
    {
        int taskNumber = _ui.getTitleButtonList().indexOf(button);
        Task task = _listOfTasks.get(taskNumber);
        final TaskEditArea area = new TaskEditArea(task, _ui.getMainframe());

        area.addPropertyChangeListener(evt -> {
            _listOfTasks.saveTasksOnFile();
            _ui.updateTask(task);
        });
        area.showUI();
    }

    /**
     * AddButton.actionListener: Opens AddProgressArea and gives it an observer
     */
    private void openAddProgressArea(JButton button)
    {
        int taskNumber = _ui.getAddButtonlist().indexOf(button);
        Task task = _listOfTasks.get(taskNumber);
        final AddProgressArea area = new AddProgressArea(task, _ui.getMainframe());

        area.addPropertyChangeListener(evt -> {
            _actionQueue.addAction(new Action(task, (int) evt.getNewValue()));
            _listOfTasks.saveTasksOnFile();
            _ui.updateProgress(task);
            _ui.enableUndoButton();
        });
        area.showUI();
    }

//---------------------------- Listeners: End -----------------------------------
    /**
     * Refreshes the UI to accurately show new information
     * Current behaviour: Close UI and redo it
     * TODO: use _mainframe.removeAll(), .validate() and .repaint() within the UI class
     */
    private void refreshUI()
    {
        _ui.close();
        Weekplan.getInstance();
    }

    /**
     * Opens the editing field of a (newly created) task
     * - the "TaskEditArea" with the title "New Task"
     * TODO: This smells
     */
    public void openTaskEdit()
    {
        _ui.openNewTaskMenu();
    }
}
