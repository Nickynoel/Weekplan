package RowFileReader;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A class that gets a file and reads line by line, putting each of the lines into a list/array of String
 */

public class RowFileReader
{
    private final List<String> _list;

    /**
     * factory method to call a fileReader from a string, which reads each line
     * calls constructor if input is valid, null if invalid
     */
    public static RowFileReader getInstance(String file)
    {
        return getInstance(new File(file));
    }

    /**
     * factory method to call a fileReader from a file, which reads each line
     * calls constructor if input is valid, null if invalid
     * ToDo: This feels smelly
     */
    public static RowFileReader getInstance(File file)
    {
        if (!file.isFile())
        {
            try (FileWriter wr = new FileWriter(file.getName(), StandardCharsets.UTF_8))
            {
                return new RowFileReader(file);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        if (file.canRead())
        {
            return new RowFileReader(file);
        }
        return null;
    }

    private RowFileReader(File file)
    {
        _list = loadFile(file);
    }

    /**
     * reads the file and saves each non-empty line as string into a list
     */
    private List<String> loadFile(File file)
    {
        List<String> stringList = new ArrayList<>();

        try (Scanner res = new Scanner(file))
        {
            while (res.hasNextLine())
            {
                String line = res.nextLine();
                if (!(line.trim().isEmpty()))
                    stringList.add(line);
            }
        }
        catch (FileNotFoundException e1)
        {
            e1.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(new JFrame(),"Unexpected error while reading from the file!");
        }

        return stringList;
    }

    /**
     * Returns the created list as a List
     *
     * @return _list
     */
    public List<String> getList()
    {
        return _list;
    }
}

