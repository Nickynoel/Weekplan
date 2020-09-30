package Startup;

import TopicList.Topic.Topic;
import TopicList.TopicList;
import Weekplan.Weekplan;

import java.util.Calendar;

public class Startup
{
    private static TopicList _list;
    
    public static void main(String[] args)
    {
        _list = TopicList.getInstance();
        checkList();
        checkReset();
        
        new Weekplan();
    }
    
    /**
     * Checks the file and inserts default data if empty
     */
    private static void checkList()
    {
        if (_list.isEmpty())
        {
            _list.add(Topic.getInstance("Test 0 60"));
            _list.add(Topic.getInstance("Test2 0 60"));
            
        }
        _list.save(TopicList.FILENAME);
    }
    
    /**
     * Checks if the (usually weekly) reset criteria is fulfilled
     * Criteria #1: Current day is monday
     * Criteria #2: Goals are at least half completed
     */
    private static void checkReset()
    {
        int weekday = ((Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) + 5) % 7; //Monday == 0... and so on
        boolean sufficientProgress = (_list.getTotalProgress() * 2 > _list.getTotalGoaltime());
        if (weekday == 0 && sufficientProgress)
        {
            _list.fullReset();
        }
        _list.save(TopicList.FILENAME);
    }
}

