package TopicList.Topic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class of the main component of the program, a task consisting of
 * String: name, int: currentProgress, int: targetTime
 * When created and saved the corresponding string codes the emptyspace in the name with the '#' symbol
 */

//TODO: Überprüfe goal > 0

public class Task
{
    private String _name;
    private int _currentProgress;
    private int _targetTime;

    private static final char CSVBANNEDLETTER = ',';

    /**
     * factory method for creating a topic from a given string
     */
    public static Task getInstance(String input)
    {
        assert(input.trim().split(",").length == 3);

        List<String> tmplist = new ArrayList<>();
        Collections.addAll(tmplist, input.trim().split(",")); //splitting the input into the different parts

        try{
            String title = tmplist.get(0);
            int progress = Integer.parseInt(tmplist.get(1));
            int targetTime = Integer.parseInt(tmplist.get(2));
            return new Task(title, progress, targetTime);
        }
        catch(NumberFormatException e)
        {
            System.err.println(e);
            return new Task("", 0, 60);
        }
    }

    public static Task getInstance(String title, int progress, int targettime){
        return new Task(title, progress, targettime);
    }

    public static Task getInstance(){
        return new Task("", 0, 60);
    }

    private Task(String title, int progress, int targetTime )
    {
        _name = title;
        _currentProgress = progress;
        _targetTime = targetTime > 0 ? targetTime : 60;
    }

    public String getTitle()
    {
        return _name;
    }
    
    /**
     * Changes the titel of the topic to a given String
     *
     * @param s (cant include ',')
     */
    public void setTitle(String s)
    {
        if (s.indexOf(CSVBANNEDLETTER) == -1)
        {
            _name = s;
        }
    }
    
    /**
     * Returns the progress of the Topic
     *
     * @return _progress
     */
    public int getProgress()
    {
        return _currentProgress;
    }
    
    /**
     * SetA method for _progress
     *
     * @param i: value for the topic
     */
    public void setProgress(int i)
    {
        _currentProgress = i;
    }
    
    /**
     * Increases the progress by the given amount
     *
     * @param i: added progress
     */
    public void addProgress(int i)
    {
        _currentProgress += i;
    }
    
    /**
     * Returns the percentual progress of the Topic
     *
     * @return _progress*100/_goal
     */
    public double getPercentProgress()
    {
        return (_currentProgress * 100.0) / _targetTime;
    }
    
    /**
     * Returns the length of the Topic
     *
     * @return _goal
     */
    public int getTargetTime()
    {
        return _targetTime;
    }
    
    /**
     * Changes the goal of the Topic to a given value >0
     *
     * @param number new targetTime
     */
    public void setTargetTime(int number)
    {
        if (number > 0)
        {
            _targetTime = number;
        }
    }
    
    /**
     * Returns a string representation of the Task
     *
     * @return _name _progress _goal
     */
    public String toString()
    {
        return _name + " " + _currentProgress + " " + _targetTime;
    }
    
    /**
     * Returns the string in a state suited for saving
     */
    public String toSavableString()
    {
        return _name + "," + _currentProgress + "," + _targetTime;
    }
}
