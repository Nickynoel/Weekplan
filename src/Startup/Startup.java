package Startup;

import TopicList.TopicList;
import Weekplan.Weekplan;

public class Startup
{
    private static TopicList _list;
    
    public static void main(String[] args)
    {
        _list = TopicList.getInstance();
        _list.addIfEmpty();
        _list.checkReset();
        
        new Weekplan();
    }
}

