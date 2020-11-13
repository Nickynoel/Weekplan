package Weekplan;

import AddArea.AddArea;
import MP3Player.MP3Player;
import MusicArea.MusicArea;
import OptionArea.OptionArea;
import TopicEditArea.TopicEditArea;
import TopicList.Topic.Topic;
import TopicList.TopicList;

import javax.swing.*;
import java.io.FileNotFoundException;

/**
 * Functional class of the main UI
 * Now with music...hopefully
 */
public class Weekplan
{
    private TopicList _topiclist;
    private WeekplanUI _ui;
    private MP3Player _player;
    
    public Weekplan()
    {
        _topiclist = TopicList.getInstance(TopicList.FILENAME);
        _ui = new WeekplanUI(_topiclist);
        addListener();
    }
    
    /**
     * Adds the listeners of all components in the UI:
     * AddButton.actionlistener: Opens AddArea and gives it an observer
     * Label.mouselistener: Opens TopicArea and gives it an observer
     * SaveButton.actionlistener: Saves the topic-values onto the file TopicList.FILENAME
     * OptionButton.actionlistener: Closes window and opens OptionArea
     */
    private void addListener()
    {
        //listeners for topic-options
        for (JButton title : _ui.getTitleButtonList())
        {
            title.addActionListener(event ->
            {
                int topicnumber = _ui.getTitleButtonList().indexOf(title);
                Topic topic = _topiclist.get(topicnumber);
                final TopicEditArea area = new TopicEditArea(topic, _ui.getMainframe());
                
                area.addPropertyChangeListener(evt ->
                {
                    _ui.updateTopicName(topic);
                    _ui.updateGoal(topic);
                    _ui.colorBar(topic);
                });
                area.showUI();
            });
        }
        
        //listeners for adding values
        for (JButton button : _ui.getAddButtonlist())
        {
            button.addActionListener(event ->
            {
                int topicnumber = _ui.getAddButtonlist().indexOf(button);
                Topic topic = _topiclist.get(topicnumber);
                final AddArea area = new AddArea(topic, _ui.getMainframe());
                
                area.addPropertyChangeListener(evt -> _ui.colorBar(topic));
                area.showUI();
            });
        }
    
        //listener for the timer
        _ui.getTimerButton().addActionListener(event ->
        {
            _player = null;
            try
            {
                _player = MP3Player.getInstance("D:/Musik/Archangel.mp3");
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            final MusicArea area = new MusicArea(_player, _ui.getMainframe());
            area.addPropertyChangeListener(evt ->
            {
                _ui.close();
            });
            area.showUI();
        });
        
        //listener for saving
        _ui.getSaveButton().addActionListener(event ->
        {
            _topiclist.save();
        });
        
        //listener for options
        _ui.getOptionButton().addActionListener(event ->
        {
            closeUI();
            new OptionArea();
        });
        
        _ui.getCloseButton().addActionListener(event ->
        {
            closeUI();
        });
    }
    
    /**
     * Close the UI to exit the program or move to different parts
     */
    private void closeUI()
    {
        _player.quit();
        _topiclist.save();
        _ui.close();
    }
    
    /**
     * Opens the TopicArea of the newest created Topic, aka the upmost Topic with the title "New"
     */
    public void activateNewTopic()
    {
        _ui.openNewTopicMenu();
    }
}
