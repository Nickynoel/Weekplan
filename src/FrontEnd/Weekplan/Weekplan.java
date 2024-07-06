package FrontEnd.Weekplan;

import FrontEnd.AddProgressArea.AddProgressArea;
import FrontEnd.DeleteArea.DeleteArea;
import FrontEnd.OptionArea.OptionArea;
import FrontEnd.TaskEditArea.TaskEditArea;
import BackEnd.TaskList.Task.Task;
import BackEnd.TaskList.TaskList;
import BackEnd.ActionQueue.ActionQueue;
import BackEnd.ActionQueue.Action.Action;

import javax.swing.*;

/**
 * Functional class of the main UI
 */
public class Weekplan
{
    private TaskList _listOfTasks;
    private ActionQueue _actionQueue;
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
        _actionQueue = new ActionQueue();
        _ui = new WeekplanUI(_listOfTasks);
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
        refreshUI();
        openTaskEdit(); //TODO: THIS! -> Direct call
    }

    public void openDeleteArea()
    {
        final DeleteArea area = new DeleteArea(_ui.getMainframe());
        area.addPropertyChangeListener(evt -> {
            _actionQueue.filterActions();
            if (_actionQueue.hasNoPriorActions())
                _ui.disableUndoButton();
            if (_actionQueue.hasNoUndoneActions())
                _ui.disableRedoButton();
            refreshUI();
        });
        area.showUI();
    }

    /**
     * OptionButton.actionListener: Closes window and opens FrontEnd.OptionArea
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
     * TitleButton.actionListener: Opens FrontEnd.TaskEditArea and gives it an observer
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
     * AddButton.actionListener: Opens FrontEnd.AddProgressArea and gives it an observer
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
     */
    private void refreshUI()
    {
        _listOfTasks.sortList();
        _ui.refreshTaskDisplay();
        for (JButton taskTitle : _ui.getTitleButtonList())
            taskTitle.addActionListener(event -> openTaskEditArea(taskTitle));

        for (JButton addButton : _ui.getAddButtonlist())
            addButton.addActionListener(event -> openAddProgressArea(addButton));
    }

    /**
     * Opens the editing field of a (newly created) task
     * - the "FrontEnd.TaskEditArea" with the title "New Task"
     * TODO: This smells
     */
    public void openTaskEdit()
    {
        _ui.openNewTaskMenu();
    }
}
