package TaskList;

import RowFileReader.RowFileReader;
import RowFileWriter.RowFileWriter;
import TaskList.Task.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * List of the tasks of the program, consisting of the _taskList and its corresponding _file
 * with DATAFILE being the default file
 */

public class TaskList
{
    private static final File DEFAULTFILENAME = new File("Weekplan.csv"); //Globally accessible file

    private List<Task> _taskList;
    private File _file;

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
        _taskList = new ArrayList<>();
        _file = file;

        loadTasksFromFile(_file);

        if (_taskList.isEmpty())
        {
            createDefaultList();
        } else
        {
            _taskList.sort(new TaskComparatorByProgressInPercent());
        }

    }

    /**
     * The actual generation of the list, by loading the data from the file
     * If the file doesn't exist, the RowFileReader will create it as an empty file
     */
    private void loadTasksFromFile(File file)
    {
        RowFileReader reader = RowFileReader.getInstance(file);
        List<String> weekplan = reader.getList();
        for (String s : weekplan)
        {
            _taskList.add(Task.getInstance(s));
        }
    }

    public void saveTasksOnFile()
    {
        List<String> list = new ArrayList<>();
        for (Task t : _taskList)
        {
            list.add(t.toSavableString());
        }
        RowFileWriter writer = RowFileWriter.getInstance(list, _file);
        writer.saveFile();
    }

    public File getFile()
    {
        return _file;
    }

    public List<Task> getList()
    {
        return _taskList;
    }

    public void addNewEmptyTask()
    {
        _taskList.add(Task.getInstance());
    }

    public void addTask(Task t)
    {
        _taskList.add(t);
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

    /**
     * Returns the titles of all topics as an array
     *
     * @return array with all topic-titles
     */
    public String[] getArrayOfTaskTitles()
    {
        String[] titleArray = new String[_taskList.size()];
        for (int i = 0; i < _taskList.size(); i++)
        {
            titleArray[i] = _taskList.get(i).getTitle();
        }
        return titleArray;
    }

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
     * Returns the index of a topic in the _topicList, -1 if not in the list
     *
     * @param task: the checked topic
     * @return the topic's position
     */
    public int indexOf(Task task)
    {
        return _taskList.indexOf(task);
    }

    /**
     * Returns the first index of a topic in the _topicList with the given title, -1 if not in the list
     *
     * @param taskTitle: the title of the topic
     * @return the topic's position
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
     * Returns the title of the i-th topic in the list
     *
     * @param i: index of the topic
     * @return title of the i-th topic
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
     * @return the topic in the position i
     */
    public Task get(int i)
    {
        return _taskList.get(i);
    }

    /**
     * Removes the topics in the positions, given by an array, of the _topicList
     * TODO: as list maybe -> requires no duplicates
     *
     * @param array: indices of topics in the list to be removed
     */
    public void removeTasks(int[] array)
    {
        //going the list backwards and deleting them from the _topicList. to ensure indices
        for (int i = array.length - 1; i >= 0; i--)
        {
            _taskList.remove(array[i]);
        }
    }

    /**
     * Returns the total target time of all topics
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
     * Returns the total progress time of all topics
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
     * capping the individual percentage of a single topic to 100
     *
     * @return total percent progress
     */
    public int getTotalProgressInPercent()
    {
        double sum = _taskList.stream()
                .map(Task::getProgressInPercent)
                .reduce(0.0, (subtotal, element) -> subtotal + element);
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
     * Navigates based on the given resetProgram how the taskList is supposed to be reset
     * ToDo: Enum would be rad here
     *
     * @param resetProgram: parameter to choose the way of resetting
     */
    public void resetProgress(int resetProgram)
    {
        switch (resetProgram)
        {
            case 0:
                fullProgressReset();
                break;
            case 1:
                adjustedProgressReset();
                break;
        }
    }

    /**
     * Resets the progress of the topics
     */
    private void fullProgressReset()
    {
        for (Task t : _taskList)
        {
            t.setProgress(0);
        }
        saveTasksOnFile();
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
        saveTasksOnFile();
    }


    /**
     * Checks if the (usually weekly) reset criteria is fulfilled
     * Criteria #1: Current day is monday
     * Criteria #2: Goals are at least half completed
     * TODO: Currently handled in Settings, maybe here later
     */
    public void checkReset()
    {
        int weekday = ((Calendar.getInstance()
                .get(Calendar.DAY_OF_WEEK)) + 5) % 7; //Monday == 0... and so on
        boolean sufficientProgress = (getTotalProgressTime() * 2 > getTotalTargetTime());
        sufficientProgress = true; //no sufficient progress for now
        if (weekday == 0 && sufficientProgress)
        {
            fullProgressReset();
        }
        saveTasksOnFile();
    }

    /**
     * Creates a string that reflects the progress in form "xx,x of xx,x hours"
     *
     * @return
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

        return progressMinutes == 0 ? (sign + progressHours + " of " + goalHours + "," + goalMinutes + " hours") : (sign + progressHours + "," + progressMinutes + " of " + goalHours + "," + goalMinutes + " hours");
    }
}