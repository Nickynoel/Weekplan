package OptionArea;

import TopicList.TopicList;
import DeleteArea.DeleteArea;
import Weekplan.Weekplan;

/**
 * Functional class OptionArea, in which the user can choose between several options:
 * AddTopic, DeleteTopic, Back
 */

public class OptionArea
{
    private OptionAreaUI _ui;
    
    public OptionArea()
    {
        _ui = new OptionAreaUI();
        addListener();
    }
    
    /**
     * Adds the listeners of the components of AddAreaUI:
     * AddButton.actionlistener: adds a new blank topic and goes back to Weekplan
     * DeleteButton.actionlistener: opens a new menu with topics of which the chosen ones can be deleted
     * BackButton.actionlistener: goes back to Weekplan
     */
    private void addListener()
    {
        _ui.getAddButton().addActionListener(event ->
        {
            TopicList list = TopicList.getInstance();
            list.addTopic();
            list.save();
            _ui.close();
            Weekplan plan = new Weekplan();
            plan.activateNewTopic();
        });
        
        _ui.getDeleteButton().addActionListener(event ->
        {
            new DeleteArea();
            _ui.close();
        });
        
        _ui.getBackButton().addActionListener(event ->
        {
            _ui.close();
            new Weekplan();
        });
    }
}
