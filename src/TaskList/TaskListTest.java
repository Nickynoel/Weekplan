package TaskList;

import TaskList.Task.Task;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * The Test class for TaskList
 */

public class TaskListTest
{
    File testFile = new File("Test.csv");
    Task task1 = Task.getInstance("First", 5, 50);
    Task task2 = Task.getInstance("Second", 25, 50);
    Task task3 = Task.getInstance("Third", 100, 50);

    @Test
    public void testDefaultConstructor()
    {
        TaskList taskList = TaskList.getInstance();
        assertEquals(taskList.getFile(), new File("Weekplan.csv"));
    }

    @Test
    public void testFileConstructor()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        assertEquals(taskList.getFile(), new File("Test.csv"));
        assertEquals(taskList.getList().size(), 1);
        assertTrue(testFile.delete());
    }

    @Test
    public void testGetSize()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        assertEquals(taskList.getSize(), 1);
        assertTrue(testFile.delete());
    }

    @Test
    public void testAddNewEmptyTask()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        taskList.addNewEmptyTask();
        assertEquals(taskList.getSize(), 2);
        taskList.addNewEmptyTask();
        assertEquals(taskList.getSize(), 2);
        assertEquals(taskList.getSize(), 3);
        assertTrue(testFile.delete());
    }

    @Test
    public void testAddTask()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        taskList.addTask(task1);
        taskList.addTask(task1);
        assertEquals(taskList.getSize(), 3);
        assertTrue(testFile.delete());
    }

    @Test
    public void testIndexOf()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        taskList.addTask(task1);
        taskList.addTask(task3);
        assertEquals(taskList.indexOf(task3), 2);
        assertEquals(taskList.indexOf("First"), 1);
        assertTrue(testFile.delete());
    }

    @Test
    public void testRemoveTasks()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        int[] tmp = {0};
        taskList.removeTasks(tmp);
        assertEquals(taskList.getSize(), 0);
        taskList.addTask(task1);
        taskList.addTask(task3);
        taskList.addTask(task2);
        taskList.addTask(task2);
        taskList.addTask(task2);

        int[] tmp2 = {1, 3};
        taskList.removeTasks(tmp2);
        assertEquals(taskList.getSize(), 3);
        assertEquals(taskList.get(0), task1);
        assertEquals(taskList.get(1), task2);
        assertEquals(taskList.get(2), task2);
        assertTrue(testFile.delete());
    }

    @Test
    public void testGet()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.get(1));
        taskList.addTask(task1);
        assertEquals(taskList.get(1), task1);
        assertTrue(testFile.delete());
    }

    @Test
    public void testSaveTasksOnFile()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        taskList.addTask(task3);
        taskList.saveTasksOnFile();
        TaskList taskList2 = TaskList.getInstance(testFile);
        //Equals currently not overwritten, so not the same object
        assertEquals(taskList2.get(1).getTitle(), task3.getTitle());
        assertEquals(taskList2.get(1).getProgress(), task3.getProgress());
        assertEquals(taskList2.get(1).getTargetTime(), task3.getTargetTime());
        assertTrue(testFile.delete());
    }

    @Test
    public void testArrayAndListOfTaskTitles()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        taskList.addTask(task2);
        taskList.addTask(task1);
        String[] titleArray = {Task.DEFAULTNAME, "Second", "First"};
        List<String> titleList = Arrays.asList(Task.DEFAULTNAME, "Second", "First");
        assertArrayEquals(taskList.getArrayOfTaskTitles(), titleArray);
        assertEquals(taskList.getListOfTaskTitles(), titleList);
        assertTrue(testFile.delete());
    }

    @Test
    public void testGetTotalTargetTime()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        taskList.addTask(task2);
        assertEquals(taskList.getTotalTargetTime(), 110);
        assertTrue(testFile.delete());
    }

    @Test
    public void testGetTotalProgressTime()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        taskList.addTask(task2);
        assertEquals(taskList.getTotalProgressTime(), 25);
        assertTrue(testFile.delete());
    }

    @Test
    public void testGetTotalProgressInPercent()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        taskList.addTask(task2);
        assertEquals(taskList.getTotalProgressInPercent(), 50.0, 0.001);
        assertTrue(testFile.delete());
    }

    @Test
    public void testSetTotalTime()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        taskList.addTask(task3);
        taskList.addTask(task1);
        taskList.addTask(task2);
        assertEquals(taskList.getTotalTargetTime(), 210);
        taskList.setTotalTargetTime(420);
        assertEquals(task3.getTargetTime(), 100);
        assertTrue(testFile.delete());
    }

    @Test
    public void testResetProgress()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        taskList.get(0).addProgress(120);
        taskList.resetProgress(1);
        // (progress - target) / 2 -> (120 - 60) / 2
        assertEquals(taskList.getTotalProgressTime(), 30);
        taskList.resetProgress(0);
        assertEquals(taskList.getTotalProgressTime(), 0);
        assertTrue(testFile.delete());
    }

}

/*
 * Functions to Test:
 * checkReset()??? maybe
 *
 * DefaultTest
 *     @Test
 *     public void test()
 *     {
 *         TaskList taskList = TaskList.getInstance(testFile);
 *         assertTrue(testFile.delete());
 *     }
 */
