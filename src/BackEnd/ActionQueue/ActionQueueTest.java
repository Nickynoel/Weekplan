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
    public void testConstructor() {
        ActionQueue queue = ActionQueue.getInstance();
        assertTrue(queue.hasNoPriorActions());
        assertTrue(queue.hasNoUndoneActions());
        queue.reset();
    }

    @Test
    public void testAddNewAction() {
        ActionQueue queue = ActionQueue.getInstance();
        Action action = new Action(Task.getInstance(), 15);
        queue.addNewAction(action);
        assertFalse(queue.hasNoPriorActions());
        assertTrue(queue.hasNoUndoneActions());
        queue.reset();
    }

    @Test
    public void testUndoLastAction() {
        ActionQueue queue = ActionQueue.getInstance();
        Action action = new Action(Task.getInstance(), 15);
        queue.addNewAction(action);
        Action action2 = queue.undoLastAction();
        assertEquals(action, action2);
        assertTrue(queue.hasNoPriorActions());
        assertFalse(queue.hasNoUndoneActions());
        queue.reset();
    }

    @Test
    public void testRedoLastAction() {
        ActionQueue queue = ActionQueue.getInstance();
        Action action = new Action(Task.getInstance(), 15);
        queue.addNewAction(action);
        Action action2 = queue.undoLastAction();
        queue.redoLastAction();
        assertFalse(queue.hasNoPriorActions());
        assertTrue(queue.hasNoUndoneActions());
        Action action3 = queue.undoLastAction();
        assertEquals(action2, action3);
        queue.reset();
    }

    @Test
    public void testFilterActions() {
        // TODO
    }
}