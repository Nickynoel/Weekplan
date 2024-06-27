package BackEnd.ActionQueue.Action;

import BackEnd.TaskList.Task.Task;

/*
 * A simple tuple of a task and a progress to reflect an action in the weekplan.
 */

public class Action
{
    private final Task _task;
    private final int _progress;

    public Action(Task task, int progress)
    {
        _task = task;
        _progress = progress;
    }

    public Task getTask()
    {
        return _task;
    }

    public int getProgress()
    {
        return _progress;
    }
}
