package BackEnd.TaskList.Task;

/**
 * Class of the main component of the program, a task consisting of
 * String: name, int: currentProgress, int: targetTime
 * When created and saved the corresponding string codes the empty space in the name with the '#' symbol
 */

public class Task
{
    public static final String DEFAULTNAME = "New Task";
    private static final char CSVBANNEDLETTER = ',';

    public static Task getInstance(String name, int progress, int target) {
        return new Task(name, progress, target);
    }

    public static Task getInstance() {
        return new Task();
    }

    public static Task getInstance(String csvInput) {
        if (isValidCSVInput(csvInput)) {
            String[] splitInput = csvInput.split(",");
            return new Task(splitInput[0], Integer.parseInt(splitInput[1]),
                    Integer.parseInt(splitInput[2]));
        }
        else {
            return new Task();
        }
    }

    private static boolean isValidCSVInput(String input) {
        String[] tmpList = input.split(",");
        try {
            String title = tmpList[0];
            int progress = Integer.parseInt(tmpList[1]);
            int targetTime = Integer.parseInt(tmpList[2]);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    private String _title;
    private int _currentProgress;
    private int _targetTime;

    private Task(String title, int progress, int targetTime) {
        _title = title;
        _currentProgress = progress;
        _targetTime = targetTime > 0 ? targetTime : 60;
    }

    private Task() {
        _title = DEFAULTNAME;
        _currentProgress = 0;
        _targetTime = 60;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String s) {
        if (s.indexOf(CSVBANNEDLETTER) == -1)
            _title = s;
    }

    public int getProgress() {
        return _currentProgress;
    }

    public void setProgress(int i) {
        _currentProgress = i;
    }

    public void addProgress(int i) {
        _currentProgress += i;
    }

    public double getProgressInPercent() {
        //Going over 100%, so those above are still compared properly
        return (_currentProgress * 100.0) / _targetTime;
    }

    public int getTargetTime() {
        return _targetTime;
    }

    public void setTargetTime(int number) {
        if (number > 0) {
            _targetTime = number;
        }
    }

    /**
     * Returns the string in a state suited for saving
     */
    public String toSavableString() {
        return _title + "," + _currentProgress + "," + _targetTime;
    }
}
