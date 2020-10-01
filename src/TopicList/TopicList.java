package TopicList;

import RowFileReader.RowFilereader;
import RowFileWriter.RowFileWriter;
import TopicList.Topic.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * List of the topics of the program
 */

public class TopicList
{
    public static final File FILENAME = new File("Weekplan.txt"); //Globally accessible file
    
    private List<Topic> _topicList;
    
    private TopicList(File file)
    {
        _topicList = new ArrayList<>();
        fillList(file);
        _topicList.sort(new TopicPercentComperator());
    }
    
    /**
     * the actual generation of the list, by loading the data from the file
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
     * Factory method that returns the _topicList of the default FILENAME
     *
     * @return _topicList
     */
    public static TopicList getInstance()
    {
        return new TopicList(FILENAME);
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
     * Adds a new topic to the last position
     *
     * @param t: new topic
     */
    public void add(Topic t)
    {
        _topicList.add(t);
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
     * Returns the size of the _topicList
     *
     * @return _topicList.size()
     */
    public int size()
    {
        return _topicList.size();
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
     * Saves the _topicList onto the given File
     *
     * @param file: File to be saved onto
     */
    
    public void save(File file)
    {
        List<String> list = new ArrayList<>();
        for (Topic t : _topicList)
        {
            list.add(t.toSavableString());
        }
        RowFileWriter writer = RowFileWriter.getInstance(list, file);
        writer.saveFile();
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
     * Returns the titles of all topics as an array
     *
     * @return array with all topic-titles
     */
    public String[] getTitleArray()
    {
        String[] list = new String[_topicList.size()];
        for (int i = 0; i < _topicList.size(); i++)
        {
            list[i] = _topicList.get(i).getTitel();
        }
        return list;
    }
    
    /**
     * Adds a generic topic to the _topicList
     */
    public void addTopic()
    {
        _topicList.add(Topic.getInstance("New 0 60"));
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
            sum += t.getLength();
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
}