package MP3Player;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;

/**
 * Class that allows playing an mp3
 */

public class MP3Player
{
    public static String DEFAULTSONG = "D:/Musik/Archangel.mp3";
    
    private FileInputStream _song;
    Thread _songThread;
    private float _volume;
    private int _status; //0: off, 1: on, 2: waiting
    
    private Calendar _nextSongTime;
    
    public static MP3Player getInstance(String filepath) throws FileNotFoundException
    {
        return new MP3Player(new FileInputStream(new File(filepath)));
    }
    
    private MP3Player(FileInputStream file)
    {
        _song = file;
        _volume = 0.05f;
        outputVolume();
    }
    
    private void outputVolume()
    {
        Info source = Port.Info.HEADPHONE;
        if (AudioSystem.isLineSupported(source))
        {
            try
            {
                Port outline = (Port) AudioSystem.getLine(source);
                outline.open();
                FloatControl volumeControl = (FloatControl) outline.getControl(FloatControl.Type.VOLUME);                System.out.println("  volume: " + volumeControl.getValue());
                volumeControl.setValue(_volume);
            }
            catch (LineUnavailableException ex)
            {
                System.err.println("source not supported");
                ex.printStackTrace();
            }
        }
    }
    
    public void addToQueue(int delay)
    {
        assert (delay >= 0) : "Delay mustn't be negative!";
        _nextSongTime = Calendar.getInstance();
        _nextSongTime.add(Calendar.MINUTE, delay);
        
        try
        {
            TimeUnit.SECONDS.sleep(delay); //TODO change to minutes once finished
            run();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    private void run()
    {
        _songThread = new Thread(new Runnable()
        {
        @Override
            public void run()
            {
                try
                {
                    Player playMP3 = new Player(_song);
                    playMP3.play();
                }
                catch (JavaLayerException e)
                {
                    System.out.println(e);
                }
            }
        });
        _songThread.start();
    }
    
    /**
     * GetA for the Thread that plays the actual song
     *
     * @return _songThread
     */
    public Thread getSongThread()
    {
        return _songThread;
    }
    
    /**
     * Returns a String of the time the next song is played
     *
     * @return time the next song is played as String
     */
    public String getNextSongTime()
    {
        String time = _nextSongTime.get(Calendar.HOUR) + ":" + _nextSongTime.get(Calendar.MINUTE);
        return time;
    }
    
    public void quit()
    {
        _songThread.stop();
    }
}

