package Startup;

import Settings.Settings;
import TaskList.TaskList;
import Weekplan.Weekplan;

public class Startup
{
    private static TaskList _listOfTasks;
    private static Settings _listOfSettings;
    
    public static void main(String[] args)
    {
        _listOfTasks = TaskList.getInstance();
        _listOfSettings = Settings.getInstance(Settings.DEFAULTSETTINGSFILE);

        if(_listOfSettings.checkWeeklyReset())
        {
            _listOfTasks.resetProgress(_listOfSettings.getResetProgram());
        }
        Weekplan.getInstance();
    }
}

