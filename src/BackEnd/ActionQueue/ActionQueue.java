package BackEnd.ActionQueue;

/*
 * Class that records the prior progresses to undo and redo if wished
 * ToDo: Test as Singleton
 */

import BackEnd.ActionQueue.Action.Action;

import java.util.Stack;

public class ActionQueue
{
    private Stack<Action> _priorActions;
    private Stack<Action> _undoneActions;

    public ActionQueue()
    {
        _priorActions = new Stack<>();
        _undoneActions = new Stack<>();
    }

    public void addAction(Action action)
    {
        _priorActions.add(action);
        _undoneActions.clear();
    }

    public Action undoLastAction()
    {
        if (!_priorActions.empty())
        {
            Action a = _priorActions.pop();
            _undoneActions.push(a);
            a.getTask().addProgress(a.getProgress() * -1);
            return a;
        }
        return null;
    }

    public Action redoLastAction()
    {
        if (!_undoneActions.empty())
        {
            Action a = _undoneActions.pop();
            _priorActions.push(a);
            a.getTask().addProgress(a.getProgress());
            return a;
        }
        return null;
    }

    public boolean hasNoPriorActions()
    {
        return _priorActions.empty();
    }

    public boolean hasNoUndoneActions()
    {
        return _undoneActions.empty();
    }

}
