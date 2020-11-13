package MP3Player;


import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

public class MP3Player
{
    private FileInputStream _song;
    Thread _songthread;
    //private int _volume;
    private int _status; //0: off, 1: on, 2: waiting
    
    public static MP3Player getInstance(String filepath) throws FileNotFoundException
    {
        return new MP3Player(new FileInputStream(new File(filepath)));
    }
    
    private MP3Player(FileInputStream file)
    {
        _song = file;
    }
    
    public void addToQueue(int delay)
    {
        assert (delay >= 0) : "Delay mustn't be negative!";
        try
        {
            TimeUnit.SECONDS.sleep(delay);
            run();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        
        
        //        String input = javax.swing.JOptionPane.showInputDialog(new JFrame(), "Gib Arbeitszeit an:");
        //        int delay = -1;
        //        try
        //        {
        //            delay = Integer.parseInt(input);
        
        //        }
        //        catch (NumberFormatException e)
        //        {
        //
        //        }
    }
    
    private void run()
    {
        _songthread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    //FileInputStream fis = new FileInputStream("D:/Musik/Archangel.mp3");
                    Player playMP3 = new Player(_song);
                    playMP3.play();
                }
                catch (JavaLayerException e)
                {
                    System.out.println(e);
                }
            }
        });
        _songthread.start();
    }
    
    public Thread getSongThread()
    {
        return _songthread;
    }
    
    public void quit()
    {
        _songthread.stop();
    }
}

