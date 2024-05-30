package RowFileWriter;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * A class that takes a list of strings and writes them line by line into a given file
 */

public class RowFileWriter
{
    private final File _file;
    private final List<String> _list;

    /**
     * factory method to call a fileWriter for a file, which saves every String in a separate line
     * calls constructor if input is valid, null if invalid
     */
    public static RowFileWriter getInstance(List<String> list, File file)
    {
        return isValidFile(file) ? new RowFileWriter(list, file) : null;
    }

    /**
     * File is created if it doesn't exist and then gets tested if editable
     * ToDo: This feels very unclean
     * @param file Tested File
     * @return boolean if file is editable
     */
    private static boolean isValidFile(File file){
        if (!file.isFile())
        {
            try (FileWriter wr = new FileWriter(file.getName(), StandardCharsets.UTF_8))
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                javax.swing.JOptionPane.showMessageDialog(new JFrame(),"File cant be created");
                System.err.println("Error creating a new file");
                return false;
            }
        }
        return file.canWrite();
    }

    private RowFileWriter(List<String> list, File file)
    {
        _file = file;
        _list = list;
    }

    /**
     * opens the file and saves each line as string into a list
     */
    public void saveFile()
    {
        try (FileWriter wr = new FileWriter(_file))
        {
            for (String s : _list)
            {
                wr.write(s + "\n");
            }
        }
        catch (IOException e)
        {
            System.err.println("Error writing on a file");
            javax.swing.JOptionPane.showMessageDialog(new JFrame(),"An error occurred while trying to save!");
        }
    }
}
