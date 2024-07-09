package BackEnd.ActionQueue;

/*
 * Class that records the prior progresses to undo and redo if wished
 */

import BackEnd.ActionQueue.Action.Action;
import BackEnd.TaskList.TaskList;

import java.util.Stack;

public class ActionQueue
{
    public static ActionQueue _singleton;

    public static ActionQueue getInstance() {
        if (_singleton == null) {
            _singleton = new ActionQueue();
        }
        return _singleton;
    }

    private Stack<Action> _priorActions;
    private Stack<Action> _undoneActions;

    private ActionQueue() {
        _priorActions = new Stack<>();
        _undoneActions = new Stack<>();
    }

    public void addNewAction(Action action) {
        _priorActions.add(action);
        _undoneActions.clear();
    }

    public Action undoLastAction() {
        if (!_priorActions.empty()) {
            Action a = _priorActions.pop();
            _undoneActions.push(a);
            a.undoProgress();
            return a;
        }
        return null;
    }

    public Action redoLastAction() {
        if (!_undoneActions.empty()) {
            Action a = _undoneActions.pop();
            _priorActions.push(a);
            a.redoProgress();
            return a;
        }
        return null;
    }

    public boolean hasNoPriorActions() {
        return _priorActions.empty();
    }

    public boolean hasNoUndoneActions() {
        return _undoneActions.empty();
    }

    public void filterActions() {
        TaskList taskList = TaskList.getInstance();

        Stack<Action> tmp = new Stack<>();
        for (Action a : _priorActions) {
            if (taskList.containsTask(a.getTask()))
                tmp.push(a);
        }
        _priorActions = tmp;

        Stack<Action> tmp2 = new Stack<>();
        for (Action a : _undoneActions) {
            if (taskList.containsTask(a.getTask()))
                tmp2.push(a);
        }
        _undoneActions = tmp2;
    }

    /**
     * Resets the ActionQueue in order to be able to do several tests individually
     */
    public void reset() {
        _priorActions.clear();
        _undoneActions.clear();
    }
}
