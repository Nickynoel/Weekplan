package TopicList.Topic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class of the main component of the program, a topic consisting of
 * String: name, int: progress, int: goal
 * When created and saved the corresponding string codes the emptyspace in the name with the '#' symbol
 */

public class Topic
{
    private String _title;
    private int _progress;
    private int _goaltime;
    
    /**
     * factory method for creating a topic from a given string
     */
    
    public static Topic getInstance(String input)
    {
        return new Topic(input);
    }
    
    /**
     * Private constructer for class Topic
     * @param input: String loaded from a file with the data of the Topic
     */
    private Topic(String input)
    {
        String tmp = input.trim(); //making sure emptyspaces are only inbetween
        List<String> tmplist = new ArrayList<>();
        Collections.addAll(tmplist, tmp.split(" ")); //splitting the input into the different parts
        
        if (isValidInput(tmplist)) //checks that below commands can be done without exception
        {
            setTitle(tmplist.get(0).replace('#', ' ')); //'#' as blank symbol
            _progress = Integer.parseInt(tmplist.get(1));
            setGoalTime(Integer.parseInt(tmplist.get(2)));
        }
        else //an error occurred and get's replaced by a blank topic
        {
            _title = " ";
            _progress = 0;
            _goaltime = 60;
        }
    }
    
    /**
     * Checks if the given string is usable to create a topic
     *
     * @param input: List<String> consisting of a String an int and a positive int
     * @return result
     */
    private boolean isValidInput(List<String> input)
    {
        if (input.size() == 3)
        {
            try
            {
                int progress = Integer.parseInt(input.get(1));
                int goal = Integer.parseInt(input.get(2));
                if (goal > 0)
                {
                    return true;
                }
            }
            catch (NumberFormatException e)
            {
            
            }
        }
        return false;
    }
    
    /**
     * Returns the titel of the Topic
     *
     * @return _name
     */
    public String getTitle()
    {
        return _title;
    }
    
    /**
     * Changes the titel of the topic to a given String
     *
     * @param s (cant include '#')
     */
    public void setTitle(String s)
    {
        if (!s.contains("#"))
        {
            _title = s;
        }
    }
    
    /**
     * Returns the progress of the Topic
     *
     * @return _progress
     */
    public int getProgress()
    {
        return _progress;
    }
    
    /**
     * SetA method for _progress
     *
     * @param i: value for the topic
     */
    public void setProgress(int i)
    {
        _progress = i;
    }
    
    /**
     * Increases the progress by the given amount
     *
     * @param i: added progress
     */
    public void addProgress(int i)
    {
        _progress += i;
    }
    
    /**
     * Returns the percentual progress of the Topic
     *
     * @return _progress*100/_goal
     */
    public int getPercentProgress()
    {
        return _progress * 100 / _goaltime;
    }
    
    /**
     * Returns the length of the Topic
     *
     * @return _goal
     */
    public int getGoalTime()
    {
        return _goaltime;
    }
    
    /**
     * Changes the goal of the Topic to a given value >0
     *
     * @param number
     */
    public void setGoalTime(int number)
    {
        if (number > 0)
        {
            _goaltime = number;
        }
    }
    
    /**
     * Returns a string representation of the Topic
     *
     * @return _name _progress _goal
     */
    public String toString()
    {
        return _title + " " + _progress + " " + _goaltime;
    }
    
    /**
     * Returns the string in a state suited for saving
     */
    public String toSavableString()
    {
        return _title.replace(' ', '#') + " " + _progress + " " + _goaltime;
    }
}
