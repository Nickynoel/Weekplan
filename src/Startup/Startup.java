package Startup;

import BackEnd.Settings.Settings;
import BackEnd.TaskList.TaskList;
import FrontEnd.Weekplan.Weekplan;

public class Startup
{
    private static TaskList _listOfTasks;
    private static Settings _listOfSettings;
    
    public static void main(String[] args)
    {
        _listOfTasks = TaskList.getInstance();
        _listOfSettings = Settings.getInstance(Settings.DEFAULTSETTINGSFILE);

        if(_listOfSettings.isMonday() && _listOfTasks.isSufficientProgress())
        {
            _listOfTasks.resetProgress(_listOfSettings.getResetProgram());
        }
        Weekplan.getInstance();
    }
}

