package Startup;

import Settings.Settings;
import TopicList.TopicList;
import Weekplan.Weekplan;

public class Startup
{
    private static TopicList _topicList;
    private static Settings _settingList;
    
    public static void main(String[] args)
    {
        _topicList = TopicList.getInstance(TopicList.DATAFILE);
        _settingList = Settings.getInstance(Settings.SETTINGSFILE);
        _topicList.addIfEmpty();
        if(_settingList.checkReset())
        {
            _topicList.reset(_settingList.getResetProgram());
        }
        new Weekplan();
    }
}

