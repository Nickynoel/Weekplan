package Weekplan;

import AddArea.AddArea;
import OptionArea.OptionArea;
import TopicEditArea.TopicEditArea;
import TopicList.Topic.Topic;
import TopicList.TopicList;

import javax.swing.*;

/**
 * Functional class of the main UI
 */
public class Weekplan
{
    private TopicList _topiclist;
    private WeekplanUI _ui;
    
    public Weekplan()
    {
        _topiclist = TopicList.getInstance();
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
        //listeners for adding values
        for (JButton button : _ui.getButtonlist())
        {
            button.addActionListener(event ->
            {
                int topicnumber = _ui.getButtonlist().indexOf(button);
                Topic topic = _topiclist.get(topicnumber);
                final AddArea area = new AddArea(topic, _ui.getMainframe());
                
                area.addPropertyChangeListener(evt -> _ui.colorBar(topic));
                area.showUI();
            });
        }
        
        //listeners for topic-options
        for (JButton label : _ui.getTitleButtonList())
        {
            label.addActionListener(event ->
            {
                int topicnumber = _ui.getTitleButtonList().indexOf(label);
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
        
        //listener for saving
        _ui.getSaveButton().addActionListener(event ->
        {
            _topiclist.save();
        });
        
        //listener for options
        _ui.getOptionButton().addActionListener(event ->
        {
            _topiclist.save();
            new OptionArea();
            _ui.close();
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
