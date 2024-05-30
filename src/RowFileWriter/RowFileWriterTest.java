package RowFileWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RowFileWriterTest
{
    List<String> _list = Arrays.asList("test", "t m p");
    File _testFile = new File("Test.txt");

    @Test
    public void testSaveFile() throws FileNotFoundException
    {
        RowFileWriter wr = RowFileWriter.getInstance(_list, _testFile);
        wr.saveFile();

        Scanner res = new Scanner(_testFile);
        assertEquals(res.nextLine(), "test");
        assertEquals(res.nextLine(), "t m p");
        res.close();

        assertTrue(_testFile.delete());
    }
}
