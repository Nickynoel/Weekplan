package Settings;

import RowFileReader.RowFileReader;
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
    private final String RESETPROGRAM = "Resetprogram:";
    private final String WEEKLYRESET = "Weekly Reset:";

    private List<String> _settingsList; //Settings given by the file
    private File _file; //file that saves the settings
    private int _resetProgram; //number that says
    private boolean _isSunday; //Checks if we are moving from sunday to monday
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
        initializeFields(file);
        loadSettingsList(file);
        executeSettingsList();
    }

    private void initializeFields(File file)
    {
        _settingsList = new ArrayList<>();
        _file = file;
        _resetProgram = 0;
        _isSunday = false;
    }

    /**
     * The actual generation of the list, by loading the data from the file
     * If the file doesn't exist, the RowFileReader will create it as an empty file
     */
    private void loadSettingsList(File file)
    {
        RowFileReader reader = RowFileReader.getInstance(file);
        _settingsList = reader.getList();
    }
    
    /**
     * Loads the data from the list
     */
    private void executeSettingsList()
    {
        for (String s: _settingsList)
        {
            if (s.startsWith(RESETPROGRAM))
            {
                String prog = s.substring(RESETPROGRAM.length()).strip();
                _resetProgram = Integer.parseInt(prog);
            }
            if(s.startsWith(WEEKLYRESET))
            {
                String week = s.substring(WEEKLYRESET.length()).strip();
                _isSunday = Boolean.parseBoolean(week);
            }
        }
    }
    
    /**
     * Checks if the weekly reset criteria is fulfilled
     * Criteria #1: Current day is monday
     * Criteria #2: Targets are at least half completed - ToDo: Not Implemented
     * TODO: Does this even belong here? Maybe startup even
     */
    public boolean checkWeeklyReset()
    {
        int weekday = ((Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) + 5) % 7; //Monday == 0... and so on
        if(weekday == 6)
        {
            _isSunday = true;
            saveSettings();
        }
        else if (weekday == 0 && _isSunday)
        {
            _isSunday = false;
            saveSettings();
            return true;
        }
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
     * @param selectedIndex index of the newly chosen resetProgram
     */
    public void setResetProgram(int selectedIndex)
    {
        _resetProgram = selectedIndex;
    }
    
    /**
     * Saves the _settingList on _file
     */
    public void saveSettings()
    {
        RowFileWriter writer = RowFileWriter.getInstance(stringifySettings(), _file);
        writer.saveFile();
    }

    private List<String> stringifySettings()
    {
        List<String> list = new ArrayList<>();
        list.add(RESETPROGRAM + " " + _resetProgram);
        list.add(WEEKLYRESET + " " + _isSunday);
        return list;
    }
}
