package BackEnd.ActionQueue.Action;

import BackEnd.TaskList.Task.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class for Action
 */

class ActionTest
{
    @Test
    public void testConstructor() {
        Task task = Task.getInstance();
        Action action = new Action(task, 5);
        assertEquals(action.getTask(), task);
        assertEquals(action.getProgress(), 5);
    }

    @Test
    public void testUndoProgress() {
        // TODO
    }

    @Test
    public void testRedoProgress() {
        // TODO
    }
}
