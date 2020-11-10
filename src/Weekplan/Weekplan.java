package Weekplan;

import AddArea.AddArea;
import OptionArea.OptionArea;
import TopicEditArea.TopicEditArea;
import TopicList.Topic.Topic;
import TopicList.TopicList;

import javax.swing.*;

/**
 * Functional class of the main UI
 * Bla
 */
public class Weekplan
{
    private TopicList _topiclist;
    private WeekplanUI _ui;
    
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
        
        
        //listener for saving
        _ui.getSaveButton().addActionListener(event ->
        {
            _topiclist.save();
        });
        
        //listener for options
        _ui.getOptionButton().addActionListener(event ->
        {
            _topiclist.save();
            _ui.close();
            new OptionArea();
        });
        
        _ui.getCloseButton().addActionListener(event ->
        {
            _topiclist.save();
            _ui.close();
        });
    }
    
    /**
     * Opens the TopicArea of the newest created Topic, aka the upmost Topic with the title "New"
     */
    public void activateNewTopic()
    {
        _ui.openNewTopicMenu();
    }
}
