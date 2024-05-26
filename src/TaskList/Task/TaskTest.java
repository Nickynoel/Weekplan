package TaskList.Task;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The Testclass for Task
 */

public class TaskTest
{
    @Test
    public void testDefaultConstructor()
    {
        Task task = Task.getInstance();
        assertEquals(task.getTitle(),Task.DEFAULTNAME);
        assertTrue(task.getProgress() == 0);
        assertTrue(task.getTargetTime() == 60);

    }

    @Test
    public void testValueConstructor(){
        Task task = Task.getInstance("Hello", 42, 69);
        assertTrue(task.getTitle().equals("Hello"));
        assertTrue(task.getProgress() == 42);
        assertTrue(task.getTargetTime() == 69);
    }

    @Test
    public void testInputConstructor(){
        Task task = Task.getInstance("Hello,42,69");
        assertTrue(task.getTitle().equals("Hello"));
        assertTrue(task.getProgress() == 42);
        assertTrue(task.getTargetTime() == 69);
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
        assertTrue(task.getProgressInPercent() == 10);
        assertTrue(task2.getProgressInPercent() == 0.1);
    }

    @Test
    public void testGetTargetTime(){
        Task task = Task.getInstance("Hello", 42, 420);
        assertTrue(task.getTargetTime() == 420);
    }

    @Test
    public void testSetTargetTime(){
        Task task = Task.getInstance("Hello", 42, 420);
        task.setTargetTime(69);
        assertTrue(task.getTargetTime() == 69);
        task.setTargetTime(-5);
        assertTrue(task.getTargetTime() == 69);
        assertFalse(task.getTargetTime() == 68);
    }

    @Test
    public void testToString(){

    }

    @Test
    public void testToSavableString(){

    }

}
