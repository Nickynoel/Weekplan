package TopicList;

import RowFileReader.RowFilereader;
import RowFileWriter.RowFileWriter;
import TopicList.Topic.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * List of the topics of the program, consisting of the _topicList and its corresponding _file
 * with FILENAME being the default file
 */

public class TopicList
{
    public static final File FILENAME = new File("Weekplan.txt"); //Globally accessible file
    
    private List<Topic> _topicList;
    private File _file;
    
    /**
     * Factory method that returns the _topicList given a file
     *
     * @param file: file with the topics
     * @return _topicList
     */
    public static TopicList getInstance(File file)
    {
        return new TopicList(file);
    }
    
    /**
     * Private constructor for the class TopicList
     * @param file: the corresponding file
     */
    private TopicList(File file)
    {
        _topicList = new ArrayList<>();
        _file = file;
        
        fillList(_file);
        _topicList.sort(new TopicPercentComperator());
    }
    
    /**
     * The actual generation of the list, by loading the data from the file
     * If the file doesn't exist, the RowFileReader will create it as an empty file
     */
    private void fillList(File file)
    {
        RowFilereader reader = RowFilereader.getInstance(file);
        List<String> weekplan = reader.getList();
        for (String s : weekplan)
        {
            _topicList.add(Topic.getInstance(s));
        }
    }
    
//    /**
//     * Factory method that returns the _topicList of the default FILENAME
//     *
//     * @return _topicList
//     */
//    public static TopicList getInstance()
//    {
//        return new TopicList(FILENAME);
//    }
    
    /**
     * Checks if the (usually weekly) reset criteria is fulfilled
     * Criteria #1: Current day is monday
     * Criteria #2: Goals are at least half completed
     */
    public void checkReset()
    {
        int weekday = ((Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) + 5) % 7; //Monday == 0... and so on
        boolean sufficientProgress = (getTotalProgress() * 2 > getTotalGoaltime());
        if (weekday == 0 && sufficientProgress)
        {
            fullReset();
        }
        save();
    }
    
    /**
     * Saves the _topicList on _file
     */
    public void save()
    {
        List<String> list = new ArrayList<>();
        for (Topic t : _topicList)
        {
            list.add(t.toSavableString());
        }
        RowFileWriter writer = RowFileWriter.getInstance(list, _file);
        writer.saveFile();
    }
    
    /**
     * Adds a new topic to the last position
     *
     * @param t: new topic
     */
    public void add(Topic t)
    {
        _topicList.add(t);
    }
    
    /**
     * Adds a generic topic to the _topicList
     */
    public void addTopic()
    {
        _topicList.add(Topic.getInstance("New 0 60"));
    }
    
    /**
     * Adds a default Topic, if _topicList is empty
     */
    public void addIfEmpty()
    {
        if (_topicList.isEmpty())
        {
            addTopic();
        }
        save();
    }
    
    /**
     * GetA for _topicList
     *
     * @return _topicList
     */
    public List<Topic> getList()
    {
        return _topicList;
    }
    
    /**
     * Returns the titles of all topics as an array
     *
     * @return array with all topic-titles
     */
    public String[] getTitleArray()
    {
        String[] list = new String[_topicList.size()];
        for (int i = 0; i < _topicList.size(); i++)
        {
            list[i] = _topicList.get(i).getTitle();
        }
        return list;
    }
    
    /**
     * Returns the size of the _topicList
     *
     * @return _topicList.size()
     */
    public int size()
    {
        return _topicList.size();
    }
    
    /**
     * Checks if the list is empty
     *
     * @return result
     */
    public boolean isEmpty()
    {
        if (_topicList.size() == 0)
        {
            return true;
        }
        return false;
    }
    
    /**
     * Returns the index of a topic in the _topicList, -1 if not in the list
     *
     * @param topic: the checked topic
     * @return the topic's position
     */
    public int indexOf(Topic topic)
    {
        return _topicList.indexOf(topic);
    }
    
    /**
     * Returns the first index of a topic in the _topicList with the given title, -1 if not in the list
     *
     * @param topic: the title of the topic
     * @return the topic's position
     */
    public int indexOf(String topic)
    {
        for (int i=0;i<_topicList.size();i++)
        {
            if(getTitle(i).equals(topic))
            {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Returns the title of the i-th topic in the list
     * @param i: index of the topic
     * @return title of the i-th topic
     */
    private String getTitle(int i)
    {
        if (_topicList.get(i) != null)
        {
            return _topicList.get(i).getTitle();
        }
        return "ERROR!";
    }
    
    /**
     * Returns the topic on the given position in the _topicList
     *
     * @param i: position in the list
     * @return the topic in the position i
     */
    public Topic get(int i)
    {
        return _topicList.get(i);
    }
    
    /**
     * Removes the topic in position i of the _topicList
     *
     * @param i: position to be removed
     */
    public void remove(int i)
    {
        _topicList.remove(i);
    }
    
    /**
     * Removes the topics in the positions, given by an array, of the _topicList
     *
     * @param array: indeces of topics in the list to be removed
     */
    
    public void remove(int[] array)
    {
        for (int i = array.length - 1; i >= 0; i--) //going the list backwards and deleting them from the _topicList
        {
            _topicList.remove(array[i]);
        }
    }
    
    /**
     * Returns the total goaltime of all topics
     *
     * @return sum of goals
     */
    public int getTotalGoaltime()
    {
        int sum = 0;
        for (Topic t : _topicList)
        {
            sum += t.getGoalTime();
        }
        return sum;
    }
    
    /**
     * Returns the total progresstime of all topics
     *
     * @return sum of progresses
     */
    public int getTotalProgress()
    {
        int sum = 0;
        for (Topic t : _topicList)
        {
            sum += t.getProgress();
        }
        return sum;
    }
    
    /**
     * Returns the percent progress for the whole list
     * capping the percentage of a single topic to 100
     *
     * @return total percent progress
     */
    public int getTotalPercentProgress()
    {
        int sum = 0;
        for (Topic t : _topicList)
        {
            sum += Math.min(t.getPercentProgress(), 100);
        }
        return sum;
    }
    
    /**
     * Resets the progress of the topics
     */
    public void fullReset()
    {
        for (Topic t : _topicList)
        {
            t.setProgress(0);
        }
    }
    
    /**
     * Changes the total amount of goaltime to a certain value
     */
    public void setTotalGoal(int time)
    {
        int totaltime = 0;
        for (Topic t: _topicList)
        {
            totaltime += t.getGoalTime();
        }
        for (Topic t: _topicList)
        {
            t.setGoalTime((t.getGoalTime()*time)/totaltime);
        }
    }
}