package Settings;

import RowFileReader.RowFilereader;
import RowFileWriter.RowFileWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Organizes the management of the main settings, that are saved on an external file
 */

public class Settings
{
    public static final File DEFAULTSETTINGSFILE = new File("Settings.txt");
    
    private List<String> _settingsList; //Settings given by the file
    private File _file; //file that saves the settings
    private int _resetProgram; //number that says
    private boolean _weeklyReset; //Checks if we are moving from sunday to monday
    /**
     * Factory method that returns the _settings given a file
     * @param file: file with the settings
     * @return _settings
     */
    public static Settings getInstance(File file)
    {
        return new Settings(file);
    }
    
    private Settings(File file)
    {
        _settingsList = new ArrayList<>();
        _file = file;
        _resetProgram = 0;
        _weeklyReset = false;
    
        fillList(_file);
        initializeList();
    }
    
    /**
     * The actual generation of the list, by loading the data from the file
     * If the file doesn't exist, the RowFileReader will create it as an empty file
     */
    private void fillList(File file)
    {
        RowFilereader reader = RowFilereader.getInstance(file);
        _settingsList = reader.getList();
    }
    
    /**
     * Loads the data from the list
     */
    private void initializeList()
    {
        for (String s: _settingsList)
        {
            if (s.startsWith("Resetprogram:"))
            {
                String tmp = s.substring(13).strip();
                _resetProgram = Integer.parseInt(tmp);
            }
            if(s.startsWith("Weekly Reset:"))
            {
                String tmp = s.substring(13).strip();
                _weeklyReset = Boolean.parseBoolean(tmp);
            }
        }
    }
    
    /**
     * Checks if the weekly reset criteria is fulfilled
     * Criteria #1: Current day is monday
     * Criteria #2: Goals are at least half completed
     */
    public boolean checkWeeklyReset()
    {
        int weekday = ((Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) + 5) % 7; //Monday == 0... and so on
        if(weekday == 6)
        {
            _weeklyReset = true;
        }
        if (weekday == 0 && _weeklyReset)
        {
            _weeklyReset = false;
            save();
            return true;
        }
        save();
        return false;
    }
    
    /**
     * Returns the number which signals how the plan is supposed to be reset
     * @return resetProgram
     */
    public int getResetProgram()
    {
        return _resetProgram;
    }
    
    /**
     * Sets the resetProgram to the given index
     * @param selectedIndex
     */
    public void setResetProgram(int selectedIndex)
    {
        _resetProgram = selectedIndex;
    }
    
    /**
     * Saves the _settingList on _file
     */
    public void save()
    {
        List<String> list = new ArrayList<>();
        list.add("Resetprogram: "+ _resetProgram);
        list.add("Weekly Reset: " + _weeklyReset);
        RowFileWriter writer = RowFileWriter.getInstance(list, _file);
        writer.saveFile();
    }
}
