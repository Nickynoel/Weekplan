package Startup;

import BackEnd.Settings.Settings;
import BackEnd.TaskList.TaskList;
import FrontEnd.Weekplan.WeekPlan;

public class Startup
{
    public static void main(String[] args) {
        TaskList listOfTasks = TaskList.getInstance();
        Settings listOfSettings = Settings.getInstance(Settings.DEFAULTSETTINGSFILE);

        if (listOfSettings.isMonday() && listOfTasks.isSufficientProgress()) {
            listOfTasks.resetProgress(listOfSettings.getResetProgram());
        }

        WeekPlan.getInstance();
    }
}

