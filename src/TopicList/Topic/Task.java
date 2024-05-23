package TopicList.Topic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class of the main component of the program, a task consisting of
 * String: name, int: currentProgress, int: targetTime
 * When created and saved the corresponding string codes the emptyspace in the name with the '#' symbol
 */

//TODO: ToString and ToSavableString Rework?

public class Task
{
    private String _name;
    private int _currentProgress;
    private int _targetTime;

    private static final char CSVBANNEDLETTER = ',';


    public static Task getInstance(String name, int progress, int target){
        return new Task(name, progress, target);
    }

    public static Task getInstance(){
        return new Task();
    }

    public static Task getInstance(String input){
        if (isValidInput(input))
        {
            String[] splitInput = input.split(",");
            return new Task(splitInput[0], Integer.parseInt(splitInput[1]), Integer.parseInt(splitInput[2]));
        }
        else {
            return new Task();
        }
    }

    private static boolean isValidInput(String input){
        String[] tmpList = input.split(",");
        try{
            String title = tmpList[0];
            int progress = Integer.parseInt(tmpList[1]);
            int targetTime = Integer.parseInt(tmpList[2]);
            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }

    private Task(String title, int progress, int targetTime)
    {
        _name = title;
        _currentProgress = progress;
        _targetTime = targetTime > 0 ? targetTime : 60;
    }

    private Task(){
        _name = "New Task";
        _currentProgress = 0;
        _targetTime = 60;
    }

    public String getTitle() {
        return _name;
    }

    public void setTitle(String s) {
        if (s.indexOf(CSVBANNEDLETTER) == -1) { _name = s; }
    }

    public int getProgress(){
        return _currentProgress;
    }

    public void setProgress(int i){
        _currentProgress = i;
    }

    public void addProgress(int i) {
        _currentProgress += i;
    }

    public double getProgressInPercent() {
        return (_currentProgress * 100.0) / _targetTime;
    }

    public int getTargetTime() {
        return _targetTime;
    }

    public void setTargetTime(int number)
    {
        if (number > 0) { _targetTime = number; }
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
