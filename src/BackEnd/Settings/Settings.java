package BackEnd.Settings;

import RowFileReader.RowFileReader;
import RowFileWriter.RowFileWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Organizes the management of the main settings, that are saved on an external file
 */

public class Settings
{
    public static final File DEFAULTSETTINGSFILE = new File("Settings.txt");
    public static final List<String> RESETPROGRAMS = Arrays.asList("Total", "On Goal", "None");
    private final String RESETPROGRAM = "ResetProgram";
    private final String WEEKLYRESET = "Weekly Reset";

    private final File _saveFile;
    private String _resetProgram;
    private boolean _isSunday;

    /**
     * Factory method that returns the _settings given a file
     *
     * @param file: file with the settings saved
     * @return object with the settings
     */
    public static Settings getInstance(File file) {
        Settings s = new Settings(file);
        s.loadSettingsList(file);
        return s;
    }

    private Settings(File file) {
        _saveFile = file;
        _resetProgram = "None";
        _isSunday = false;
    }

    /**
     * Loads the data from the file and reads the settings out of it
     * If the file doesn't exist, the RowFileReader will create it as an empty file
     */
    private void loadSettingsList(File file) {
        RowFileReader reader = RowFileReader.getInstance(file);

        if (reader != null) {
            for (String s : reader.getList()) {
                if (s.startsWith(RESETPROGRAM)) {
                    String prog = s.strip().split("#")[1];
                    _resetProgram = RESETPROGRAMS.contains(prog) ? prog : "None";
                }
                if (s.startsWith(WEEKLYRESET)) {
                    String week = s.strip().split("#")[1];
                    _isSunday = Boolean.parseBoolean(week);
                }
            }
        }
    }

    /**
     * Checks if the weekly reset criteria of it being monday (for the first time) is fulfilled
     */
    public boolean isMonday() {
        // Monday == 0... and so on
        int weekday = ((Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) + 5) % 7;
        if (weekday == 6) {
            _isSunday = true;
            saveSettings();
        } else if (weekday == 0 && _isSunday) {
            _isSunday = false;
            saveSettings();
            return true;
        }
        return false;
    }

    /**
     * Returns the number which signals how the plan is supposed to be reset
     *
     * @return resetProgram
     */
    public String getResetProgram() {
        return _resetProgram;
    }

    /**
     * Sets the resetProgram to the given index
     *
     * @param prog name of the newly chosen resetProgram
     */
    public void setResetProgram(String prog) {
        if (RESETPROGRAMS.contains(prog)) {
            _resetProgram = prog;
        }
    }

    /**
     * Saves the settings on the _saveFile
     */
    public void saveSettings() {
        RowFileWriter writer = RowFileWriter.getInstance(stringifySettings(), _saveFile);
        if (writer != null) {
            writer.saveFile();
        }
    }

    /**
     * Turns the settings into a string form
     *
     * @return The settings as strings in a list
     */
    private List<String> stringifySettings() {
        List<String> list = new ArrayList<>();
        list.add(RESETPROGRAM + "#" + _resetProgram);
        list.add(WEEKLYRESET + "#" + _isSunday);
        return list;
    }
}
