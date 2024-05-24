package TaskList;

import TaskList.Task.Task;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * The Testclass for TaskList
 */

public class TaskListTest
{
    File testFile = new File("Test.csv");
    Task task1 = Task.getInstance("First" , 5, 50);
    Task task2 = Task.getInstance("Second" , 25, 50);
    Task task3 = Task.getInstance("Third" , 100, 50);

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
        testFile.delete();
    }

    @Test
    public void testGetSize()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        assertEquals(taskList.getSize(), 1);
        testFile.delete();
    }

    @Test
    public void testAddNewEmptyTask()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        taskList.addNewEmptyTask();
        assertTrue(taskList.getSize() == 2);
        taskList.addNewEmptyTask();
        assertFalse(taskList.getSize() == 2);
        assertTrue(taskList.getSize() == 3);
        testFile.delete();
    }

    @Test
    public void testAddTask()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        taskList.addTask(task1);
        taskList.addTask(task1);
        assertTrue(taskList.getSize() == 3);
        testFile.delete();
    }

    @Test
    public void testIndexOf()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        taskList.addTask(task1);
        taskList.addTask(task3);
        assertTrue(taskList.indexOf(task3) == 2);
        assertTrue(taskList.indexOf("First") == 1);
        testFile.delete();
    }

    @Test
    public void testRemoveTasks()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        int[] tmp = {0};
        taskList.removeTasks(tmp);
        assertTrue(taskList.getSize() == 0);
        taskList.addTask(task1);
        taskList.addTask(task3);
        taskList.addTask(task2);
        taskList.addTask(task2);
        taskList.addTask(task2);

        int[] tmp2 = {1,3};
        taskList.removeTasks(tmp2);
        assertTrue(taskList.getSize() == 3);
        assertEquals(taskList.get(0), task1);
        assertEquals(taskList.get(1), task2);
        assertEquals(taskList.get(2), task2);
        testFile.delete();
    }

    @Test
    public void testGet()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.get(1));
        taskList.addTask(task1);
        assertEquals(taskList.get(1), task1);
        testFile.delete();
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
        testFile.delete();
    }

    @Test
    public void testArrayAndListOfTaskTitles()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        taskList.addTask(task2);
        taskList.addTask(task1);
        String[] titleArray = {"New Task", "Second", "First"};
        List<String> titleList = Arrays.asList("New Task", "Second", "First");
        assertEquals(taskList.getArrayOfTaskTitles(), titleArray);
        assertEquals(taskList.getListOfTaskTitles(), titleList);
        testFile.delete();
    }

    @Test
    public void testGetTotalTargetTime()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        taskList.addTask(task2);
        assertEquals(taskList.getTotalTargetTime(), 110);
        testFile.delete();
    }

    @Test
    public void testGetTotalProgressTime()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        taskList.addTask(task2);
        assertEquals(taskList.getTotalProgressTime(), 25);
        testFile.delete();
    }

    @Test
    public void testGetTotalProgressInPercent()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        taskList.addTask(task2);
        assertTrue(taskList.getTotalProgressInPercent() == 50.0);
        testFile.delete();
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
        testFile.delete();
    }

    @Test
    public void testResetProgress()
    {
        TaskList taskList = TaskList.getInstance(testFile);
        taskList.get(0).addProgress(120);
        taskList.resetProgress(1);
        //(120 - 60) / 2
        assertTrue(taskList.getTotalProgressTime() == 30);
        taskList.resetProgress(0);
        assertTrue(taskList.getTotalProgressTime() == 0);
        testFile.delete();
    }


}

/**
 * Functions to Test:
 * checkReset()??? maybe
 *
 * DefaultTest
 *     @Test
 *     public void test()
 *     {
 *         TaskList taskList = TaskList.getInstance(testFile);
 *         testFile.delete();
 *     }
 */
