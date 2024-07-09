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
public class WeekPlan
{
    private final TaskList _listOfTasks;
    private final ActionQueue _actionQueue;
    private final WeekPlanUI _ui;

    public static WeekPlan getInstance() {
        WeekPlan plan = new WeekPlan();
        plan.addUIListeners();
        return plan;
    }

    private WeekPlan() {
        _listOfTasks = TaskList.getInstance();
        _actionQueue = ActionQueue.getInstance();
        _ui = new WeekPlanUI(_listOfTasks);
    }

    private void addUIListeners() {
        _ui.getCreateItem().addActionListener(event -> createTask());
        _ui.getDeleteItem().addActionListener(event -> openDeleteArea());
        _ui.getOptionsItem().addActionListener(event -> openOptionsArea());
        _ui.getCloseItem().addActionListener(event -> closeTracker());

        _ui.getUndoItem().addActionListener(event -> undoLastAction());
        _ui.getRedoItem().addActionListener(event -> redoLastAction());

        for (JButton taskTitle : _ui.getTitleButtonList()) {
            taskTitle.addActionListener(event -> openTaskEditArea(taskTitle));
        }

        for (JButton addButton : _ui.getAddButtonlist()) {
            addButton.addActionListener(event -> openAddProgressArea(addButton));
        }

        _ui.getCloseButton().addActionListener(event -> closeTracker());
    }

    //---------------------------- Listeners: Start -----------------------------------

    private void createTask() {
        Task task = _listOfTasks.addNewDefaultTask();
        _listOfTasks.saveTasksOnFile();
        refreshUI();
        _ui.openTaskMenu(task);
    }

    public void openDeleteArea() {
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
    private void openOptionsArea() {
        final OptionArea area = new OptionArea(_ui.getMainframe());
        area.addPropertyChangeListener(evt -> refreshUI());
        area.showUI();
    }

    /**
     * CloseButton.actionListener: Saves the Tracker and closes it
     */
    private void closeTracker() {
        _ui.close();
    }

    /**
     * Undo the last commited progress
     */
    public void undoLastAction() {
        Action lastAction = _actionQueue.undoLastAction();
        _ui.updateProgress(lastAction.getTask());
        if (_actionQueue.hasNoPriorActions())
            _ui.disableUndoButton();
        _ui.enableRedoButton();
    }

    /**
     * Redo the last commited progress
     */
    public void redoLastAction() {
        Action lastAction = _actionQueue.redoLastAction();
        _ui.updateProgress(lastAction.getTask());
        _ui.enableUndoButton();
        if (_actionQueue.hasNoUndoneActions())
            _ui.disableRedoButton();
    }

    /**
     * TitleButton.actionListener: Opens FrontEnd.TaskEditArea and gives it an observer
     */
    private void openTaskEditArea(JButton button) {
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
    private void openAddProgressArea(JButton button) {
        int taskNumber = _ui.getAddButtonlist().indexOf(button);
        Task task = _listOfTasks.get(taskNumber);
        final AddProgressArea area = new AddProgressArea(task, _ui.getMainframe());

        area.addPropertyChangeListener(evt -> {
            _actionQueue.addNewAction(new Action(task, (int) evt.getNewValue()));
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
    private void refreshUI() {
        _listOfTasks.sortList();
        _ui.refreshTaskDisplay();

        for (JButton taskTitle : _ui.getTitleButtonList()) {
            taskTitle.addActionListener(event -> openTaskEditArea(taskTitle));
        }

        for (JButton addButton : _ui.getAddButtonlist()) {
            addButton.addActionListener(event -> openAddProgressArea(addButton));
        }
    }
}
