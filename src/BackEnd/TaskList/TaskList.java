package BackEnd.TaskList;

import RowFileReader.RowFileReader;
import RowFileWriter.RowFileWriter;
import BackEnd.TaskList.Task.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * List of the tasks of the program, consisting of the _taskList and its corresponding _file
 * with DATAFILE being the default file
 */

public class TaskList
{
    //Globally accessible file
    private static final File DEFAULTFILENAME = new File("FrontEnd.Weekplan.csv");

    private final File _saveFile;
    private final List<Task> _taskList;

    public static TaskList getInstance(File file)
    {
        return new TaskList(file);
    }

    public static TaskList getInstance()
    {
        return new TaskList(DEFAULTFILENAME);
    }

    private TaskList(File file)
    {
        _saveFile = file;
        _taskList = new ArrayList<>();
        loadTasksFromFile(file);

        if (_taskList.isEmpty())
            createDefaultList();
        else
            _taskList.sort(new TaskComparatorByProgressInPercent());
    }

    /**
     * The actual generation of the list, by loading the data from the file
     * If the file doesn't exist, the RowFileReader will create it as an empty file
     */
    private void loadTasksFromFile(File file)
    {
        RowFileReader reader = RowFileReader.getInstance(file);
        List<String> savedStrings = reader.getList();
        for (String s : savedStrings)
        {
            _taskList.add(Task.getInstance(s));
        }
    }

    private void createDefaultList()
    {
        emptyList();
        addNewEmptyTask();
        saveTasksOnFile();
    }

    private void emptyList()
    {
        _taskList.clear();
    }

    public void addNewEmptyTask()
    {
        _taskList.add(Task.getInstance());
    }

    public void saveTasksOnFile()
    {
        List<String> list = new ArrayList<>();
        for (Task t : _taskList)
        {
            list.add(t.toSavableString());
        }
        RowFileWriter writer = RowFileWriter.getInstance(list, _saveFile);
        writer.saveFile();
    }

    public File getSaveFile()
    {
        return _saveFile;
    }

    public List<Task> getList()
    {
        return _taskList;
    }

    public void addTask(Task t)
    {
        _taskList.add(t);
    }

    /**
     * Returns a list with the titles of all tasks
     *
     * @return list with all task-titles
     */
    public List<String> getListOfTaskTitles()
    {
        List<String> titleList = new ArrayList<>();
        _taskList.forEach(x -> titleList.add(x.getTitle()));
        return titleList;
    }


    public int getSize()
    {
        return _taskList.size();
    }

    /**
     * Returns the index of a task in the _taskList, -1 if not in the list
     *
     * @param task: the checked task
     * @return the task's position
     */
    public int indexOf(Task task)
    {
        return _taskList.indexOf(task);
    }

    /**
     * Returns the first index of a task in the _taskList with the given title,
     * -1 if not in the list
     *
     * @param taskTitle: the title of the task
     * @return the task's position
     */
    public int indexOf(String taskTitle)
    {
        for (int i = 0; i < _taskList.size(); i++)
        {
            if (getTitle(i).equals(taskTitle))
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the title of the i-th task in the list
     *
     * @param i: index of the task
     * @return title of the i-th task
     */
    private String getTitle(int i)
    {
        if (_taskList.get(i) != null)
        {
            return _taskList.get(i).getTitle();
        }
        return "ERROR!";
    }

    /**
     * Returns the task on the given position in the _taskList
     *
     * @param i: position in the list
     * @return the task in the position i
     */
    public Task get(int i)
    {
        return _taskList.get(i);
    }

    /**
     * Removes the tasks in the positions, given by an array, of the _taskList
     *
     * @param array: indices of tasks in the list to be removed
     */
    public void removeTasks(int[] array)
    {
        //going the list backwards and deleting them from the _taskList to ensure indices
        for (int i = array.length - 1; i >= 0; i--)
        {
            _taskList.remove(array[i]);
        }
    }

    /**
     * Returns the total target time of all tasks
     *
     * @return sum of target times
     */
    public int getTotalTargetTime()
    {
        int sum = 0;
        for (Task t : _taskList)
        {
            sum += t.getTargetTime();
        }
        return sum;
    }

    /**
     * Returns the total progress time of all tasks
     *
     * @return sum of progresses times
     */
    public int getTotalProgressTime()
    {
        int sum = 0;
        for (Task t : _taskList)
        {
            sum += t.getProgress();
        }
        return sum;
    }

    /**
     * Returns the percentage progress for the whole list
     * capping the individual percentage of a single task to 100
     *
     * @return total percent progress
     */
    public int getTotalProgressInPercent()
    {
        double sum = _taskList.stream()
                .map(Task::getProgressInPercent)
                .reduce(0.0, (subtotal, element) -> subtotal + Math.min(element, 100));
        return (int) (sum / _taskList.size());
    }

    /**
     * Changes the total amount of target time to a certain value and adjusts
     * the times of the individual tasks
     *
     * @param time: total time intended for the week
     */
    public void setTotalTargetTime(int time)
    {
        int totalTime = 0;
        for (Task t : _taskList)
        {
            totalTime += t.getTargetTime();
        }
        for (Task t : _taskList)
        {
            t.setTargetTime((t.getTargetTime() * time) / totalTime);
        }
    }

    /**
     * Checks if the progress was sufficient for a weekly reset so that not everything goes
     * into the deep red.
     * Current Criteria: Half of TotalTargetTime
     * @return bool
     */
    public boolean isSufficientProgress()
    {
        return getTotalProgressTime() * 2 > getTotalTargetTime();
    }

    /**
     * Navigates based on the given resetProgram how the taskList is supposed to be reset
     *
     * @param resetProgram: parameter to choose the way of resetting
     */
    public void resetProgress(String resetProgram)
    {
        switch (resetProgram)
        {
            case "Total":
                fullProgressReset();
                saveTasksOnFile();
                break;
            case "On Goal":
                adjustedProgressReset();
                saveTasksOnFile();
                break;
        }
    }

    /**
     * Resets the progress of the tasks
     */
    private void fullProgressReset()
    {
        for (Task t : _taskList)
        {
            t.setProgress(0);
        }
    }

    /**
     * Standard weekly reset, subtracting the weekly goal and
     * halves the backlog or prior work
     */
    private void adjustedProgressReset()
    {
        for (Task t : _taskList)
        {
            t.setProgress(t.getProgress() - t.getTargetTime());
            t.setProgress(t.getProgress() / 2);
        }
    }

    /**
     * Creates a string that reflects the progress in form "xx,x of xx,x hours"
     *
     * @return String that reflects the full progress for the TotalLabel of the UI
     */
    public String getTotalProgressInText()
    {
        String sign = "";
        int progressHours = getTotalProgressTime() / 60;
        int progressMinutes = (getTotalProgressTime() / 6) % 10;

        //If the progress is negative, create an extra sign to display properly
        if (progressHours < 0 || progressMinutes < 0)
        {
            progressMinutes = Math.abs(progressMinutes);
            progressHours = Math.abs(progressHours);
            sign = "-";
        }

        int goalHours = getTotalTargetTime() / 60;
        int goalMinutes = (getTotalTargetTime() / 6) % 10;

        return progressMinutes == 0
                ? (sign + progressHours + " of " + goalHours + "," + goalMinutes + " hours")
                : (sign + progressHours + "," + progressMinutes + " of " + goalHours + "," +
                    goalMinutes + " hours");
    }


}