package BackEnd.TaskList;

import BackEnd.TaskList.Task.Task;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The Test class for BackEnd.TaskList
 */

public class TaskListTest
{
    File _testFile = new File("Test.csv");
    Task _task1 = Task.getInstance("First", 5, 50);
    Task _task2 = Task.getInstance("Second", 25, 50);
    Task _task3 = Task.getInstance("Third", 100, 50);

    @Test
    public void testDefaultConstructor() {
        TaskList taskList = TaskList.getInstance();
        assertEquals(taskList.getSaveFile(), new File("Weekplan.csv"));
    }

    @Test
    public void testFileConstructor() {
        TaskList taskList = TaskList.getInstance(_testFile);
        assertTrue(_testFile.delete());
        assertEquals(taskList.getSaveFile(), new File("Test.csv"));
        assertEquals(taskList.getList().size(), 1);
        assertFalse(_testFile.delete());
    }

    @Test
    public void testGetSize() {
        TaskList taskList = TaskList.getInstance(_testFile);
        assertEquals(taskList.getSize(), 1);
        assertTrue(_testFile.delete());
    }

    @Test
    public void testAddNewDefaultTask() {
        TaskList taskList = TaskList.getInstance(_testFile);
        taskList.addNewDefaultTask();
        assertEquals(taskList.getSize(), 2);
        taskList.addNewDefaultTask();
        assertNotEquals(taskList.getSize(), 2);
        assertEquals(taskList.getSize(), 3);
        assertTrue(_testFile.delete());
    }

    @Test
    public void testAddTask() {
        TaskList taskList = TaskList.getInstance(_testFile);
        taskList.addTask(_task1);
        taskList.addTask(_task1);
        assertEquals(taskList.getSize(), 3);
        assertTrue(_testFile.delete());
    }

    @Test
    public void testIndexOf() {
        TaskList taskList = TaskList.getInstance(_testFile);
        taskList.addTask(_task1);
        taskList.addTask(_task3);
        assertEquals(taskList.indexOf(_task3), 2);
        assertEquals(taskList.indexOf("First"), 1);
        assertTrue(_testFile.delete());
    }

    @Test
    public void testRemoveTasks() {
        TaskList taskList = TaskList.getInstance(_testFile);
        int[] tmp = {0};
        taskList.removeTasks(tmp);
        assertEquals(taskList.getSize(), 0);
        taskList.addTask(_task1);
        taskList.addTask(_task3);
        taskList.addTask(_task2);
        taskList.addTask(_task2);
        taskList.addTask(_task2);

        int[] tmp2 = {1, 3};
        taskList.removeTasks(tmp2);
        assertEquals(taskList.getSize(), 3);
        assertEquals(taskList.get(0), _task1);
        assertEquals(taskList.get(1), _task2);
        assertEquals(taskList.get(2), _task2);
        assertTrue(_testFile.delete());
    }

    @Test
    public void testGet() {
        TaskList taskList = TaskList.getInstance(_testFile);
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.get(1));
        taskList.addTask(_task1);
        assertEquals(taskList.get(1), _task1);
        assertTrue(_testFile.delete());
    }

    @Test
    public void testSaveTasksOnFile() {
        TaskList taskList = TaskList.getInstance(_testFile);
        taskList.addTask(_task3);
        taskList.saveTasksOnFile();
        TaskList taskList2 = TaskList.getInstance(_testFile);
        //Equals currently not overwritten, so not the same object
        assertEquals(taskList2.get(1).getTitle(), _task3.getTitle());
        assertEquals(taskList2.get(1).getProgress(), _task3.getProgress());
        assertEquals(taskList2.get(1).getTargetTime(), _task3.getTargetTime());
        assertTrue(_testFile.delete());
    }

    @Test
    public void testGetListOfTaskTitles() {
        TaskList taskList = TaskList.getInstance(_testFile);
        taskList.addTask(_task2);
        taskList.addTask(_task1);
        List<String> titleList = Arrays.asList(Task.DEFAULTNAME, "Second", "First");
        assertEquals(taskList.getListOfTaskTitles(), titleList);
        assertTrue(_testFile.delete());
    }

    @Test
    public void testGetTotalTargetTime() {
        TaskList taskList = TaskList.getInstance(_testFile);
        taskList.addTask(_task2);
        assertEquals(taskList.getTotalTargetTime(), 110);
        assertTrue(_testFile.delete());
    }

    @Test
    public void testGetTotalProgressTime() {
        TaskList taskList = TaskList.getInstance(_testFile);
        taskList.addTask(_task2);
        assertEquals(taskList.getTotalProgressTime(), 25);
        assertTrue(_testFile.delete());
    }

    @Test
    public void testGetTotalProgressInPercent() {
        TaskList taskList = TaskList.getInstance(_testFile);
        taskList.addTask(_task2);
        assertEquals(taskList.getTotalProgressInPercent(), 25.0, 0.001);
        assertTrue(_testFile.delete());
    }

    @Test
    public void testSetTotalTargetTime() {
        TaskList taskList = TaskList.getInstance(_testFile);
        taskList.addTask(_task3);
        taskList.addTask(_task1);
        taskList.addTask(_task2);
        assertEquals(taskList.getTotalTargetTime(), 210);
        taskList.setTotalTargetTime(420);
        assertEquals(_task3.getTargetTime(), 100);
        assertTrue(_testFile.delete());
    }

    @Test
    public void testResetProgress() {
        TaskList taskList = TaskList.getInstance(_testFile);
        taskList.get(0).addProgress(120);
        taskList.resetProgress("On Goal");
        // (progress - target) / 2 -> (120 - 60) / 2
        assertEquals(taskList.getTotalProgressTime(), 30);
        taskList.resetProgress("Total");
        assertEquals(taskList.getTotalProgressTime(), 0);
        assertTrue(_testFile.delete());
    }

    @Test
    public void testSortList(){
        TaskList taskList = TaskList.getInstance(_testFile);
        taskList.addTask(_task2);
        taskList.addTask(_task1);
        // current order: default (0%), _task2 (50%), _task1 (10%)
        // target order: default (0%), _task1 (10%), _task2 (50%)
        assertEquals(taskList.get(2), _task1);
        taskList.sortList();
        assertEquals(taskList.get(2), _task2);
        assertNotEquals(taskList.get(2), _task1);
        assertTrue(_testFile.delete());
    }

    @Test
    public void testContainsTask(){
        TaskList taskList = TaskList.getInstance(_testFile);
        assertFalse(taskList.containsTask(_task3));
        taskList.addTask(_task3);
        assertTrue(taskList.containsTask(_task3));
        assertTrue(_testFile.delete());
    }

    @Test
    public void testGetTotalProgressInText(){
        TaskList taskList = TaskList.getInstance(_testFile);
        assertEquals(taskList.getTotalProgressInText(), "0 of 1,0 hours");
        taskList.get(0).addProgress(6);
        assertEquals(taskList.getTotalProgressInText(), "0,1 of 1,0 hours");
        assertTrue(_testFile.delete());
    }

    @Test
    public void testIsSufficientProgress(){
        TaskList taskList = TaskList.getInstance(_testFile);
        assertFalse(taskList.isSufficientProgress()); // 0%
        taskList.get(0).addProgress(60);
        assertTrue(taskList.isSufficientProgress()); // 100%
        assertTrue(_testFile.delete());
    }
}

/*
 * Functions to Test: TODO
 *
 *
 * DefaultTest
 *     @Test
 *     public void test()
 *     {
 *         TaskList taskList = TaskList.getInstance(testFile);
 *         assertTrue(testFile.delete());
 *     }
 */
