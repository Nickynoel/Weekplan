package Weekplan;

import AddArea.AddArea;
import OptionArea.OptionArea;
import TopicArea.TopicArea;
import TopicList.Topic.Topic;
import TopicList.TopicList;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        for (JLabel label : _ui.getLabellist())
        {
            label.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    super.mouseClicked(e);
                    int topicnumber = _ui.getLabellist().indexOf(label);
                    Topic topic = _topiclist.get(topicnumber);
                    final TopicArea area = new TopicArea(topic, _ui.getMainframe());
                    
                    area.addPropertyChangeListener(evt ->
                    {
                        _ui.updateTopicName(topic);
                        _ui.updateGoal(topic);
                        _ui.colorBar(topic);
                    });
                    area.showUI();
                }
            });
        }
        
        //listener for saving
        _ui.getSaveButton().addActionListener(event ->
        {
            save();
        });
        
        //listener for options
        _ui.getOptionButton().addActionListener(event ->
        {
            save();
            new OptionArea();
            _ui.close();
        });
        
        _ui.getCloseButton().addActionListener(event ->
        {
            save();
            _ui.close();
        });
    }
    
    private void save()
    {
        _topiclist.save(TopicList.FILENAME);
    }
    
    public WeekplanUI getMainframe()
    {
        return _ui;
    }
}
