package Startup;

import Settings.Settings;
import TopicList.TopicList;
import Weekplan.Weekplan;

public class Startup
{
    private static TopicList _listOfTasks;
    private static Settings _listOfSettings;
    
    public static void main(String[] args)
    {
        _listOfTasks = TopicList.getInstance(TopicList.DATAFILE);
        _listOfSettings = Settings.getInstance(Settings.SETTINGSFILE);

        _listOfTasks.addIfEmpty();
        if(_listOfSettings.checkReset())
        {
            _listOfTasks.reset(_listOfSettings.getResetProgram());
        }
        new Weekplan();
    }
}

