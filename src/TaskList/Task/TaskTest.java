package TaskList.Task;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The test class for Task
 */

public class TaskTest
{
    @Test
    public void testDefaultConstructor()
    {
        Task task = Task.getInstance();
        assertEquals(task.getTitle(),Task.DEFAULTNAME);
        assertEquals(task.getProgress(), 0);
        assertEquals(task.getTargetTime(), 60);

    }

    @Test
    public void testValueConstructor(){
        Task task = Task.getInstance("Hello", 42, 69);
        assertEquals(task.getTitle(), "Hello");
        assertEquals(task.getProgress(), 42);
        assertEquals(task.getTargetTime(), 69);
    }

    @Test
    public void testInputConstructor(){
        Task task = Task.getInstance("Hello,42,69");
        assertEquals(task.getTitle(), "Hello");
        assertEquals(task.getProgress(), 42);
        assertEquals(task.getTargetTime(), 69);
    }

    @Test
    public void testGetTitle(){
        Task task = Task.getInstance("Hello", 42, 69);
        assertEquals(task.getTitle(), "Hello");
    }

    @Test
    public void testSetTitle(){
        Task task = Task.getInstance("Hello", 42, 69);
        task.setTitle("Doom");
        assertEquals(task.getTitle(), "Doom");
    }

    @Test
    public void testGetProgress(){
        Task task = Task.getInstance("Hello", 42, 69);
        assertEquals(task.getProgress(), 42);
    }

    @Test
    public void testSetProgress(){
        Task task = Task.getInstance("Hello", 42, 69);
        task.setProgress(420);
        assertEquals(task.getProgress(), 420);
    }

    @Test
    public void testAddProgress(){
        Task task = Task.getInstance("Hello", 42, 69);
        task.addProgress(69);
        assertEquals(task.getProgress(), 111);
    }

    @Test
    public void testGetProgressInPercent(){
        Task task = Task.getInstance("Hello", 42, 420);
        Task task2 = Task.getInstance("Hello", 4, 4000);
        assertEquals(task.getProgressInPercent(), 10.0, 0.0005);
        assertEquals(task2.getProgressInPercent(), 0.1, 0.0005);
    }

    @Test
    public void testGetTargetTime(){
        Task task = Task.getInstance("Hello", 42, 420);
        assertEquals(task.getTargetTime(), 420);
    }

    @Test
    public void testSetTargetTime(){
        Task task = Task.getInstance("Hello", 42, 420);
        task.setTargetTime(69);
        assertEquals(task.getTargetTime(), 69);
        task.setTargetTime(-5);
        assertEquals(task.getTargetTime(), 69);
        assertNotEquals(task.getTargetTime(), 68);
    }

    @Test
    public void testToString(){

    }

    @Test
    public void testToSavableString(){

    }

}
