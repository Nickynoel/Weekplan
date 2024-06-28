package BackEnd.ActionQueue;

import BackEnd.ActionQueue.Action.Action;
import BackEnd.TaskList.Task.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * The test class for ActionQueue
 */
public class ActionQueueTest
{
    @Test
    public void testConstructor()
    {
        ActionQueue queue = new ActionQueue();
        assertTrue(queue.hasNoPriorActions());
        assertTrue(queue.hasNoUndoneActions());
    }

    @Test
    public void testAddAction()
    {
        ActionQueue queue = new ActionQueue();
        Action action = new Action(Task.getInstance(), 15);
        queue.addAction(action);
        assertFalse(queue.hasNoPriorActions());
        assertTrue(queue.hasNoUndoneActions());
    }

    @Test
    public void testUndoLastAction()
    {
        ActionQueue queue = new ActionQueue();
        Action action = new Action(Task.getInstance(), 15);
        queue.addAction(action);
        Action action2 = queue.undoLastAction();
        assertEquals(action, action2);
        assertTrue(queue.hasNoPriorActions());
        assertFalse(queue.hasNoUndoneActions());
    }

    @Test
    public void testRedoLastAction()
    {
        ActionQueue queue = new ActionQueue();
        Action action = new Action(Task.getInstance(), 15);
        queue.addAction(action);
        Action action2 = queue.undoLastAction();
        queue.redoLastAction();
        assertFalse(queue.hasNoPriorActions());
        assertTrue(queue.hasNoUndoneActions());
        Action action3 = queue.undoLastAction();
        assertEquals(action2, action3);
    }
}