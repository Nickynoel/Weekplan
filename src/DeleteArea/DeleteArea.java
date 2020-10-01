package DeleteArea;

import Startup.Startup;
import TopicList.TopicList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Functional class DeleteArea, in which the user is able to delete topics out of the TopicList
 *
 * Returns to Startup afterwards
 */

public class DeleteArea
{
    private DeleteAreaUI _ui;
    private TopicList _topicList;
    
    public DeleteArea()
    {
        _topicList = TopicList.getInstance();
        _ui = new DeleteAreaUI(_topicList);
        addListener();
    }
    
    /**
     * Adds the listeners of the components of DeleteAreaUI:
     * List.ListSelectionListener: Disables the DeleteButton iff nothing in the list is selected
     * AddButton.actionlistener: adds a new blank topic and goes back to Weekplan
     * DeleteButton.actionlistener: opens a new menu with topics of which the chosen ones can be deleted
     * BackButton.actionlistener: goes back to Weekplan
     */
    private void addListener()
    {
        //Checks number of selected entries, cause you can only delete if something is selected
        _ui.getList().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (_ui.getList().getMinSelectionIndex() == -1)
                {
                    _ui.disableDeleteButton();
                }
                else
                {
                    _ui.enableDeleteButton();
                }
            }
        });
        
        //Actual deletion of entries: Delete, save progress, back to mainscreen
        _ui.getDeleteButton().addActionListener(event ->
        {
            int[] list = _ui.getList().getSelectedIndices(); //returns a list of indeces in increasing order
            _topicList.remove(list);
//            for (int i = list.length-1;i>=0;i--) //going the list backwards and deleting them from the _topicList
//            {
//                _topicList.remove(list[i]);
//            }
            _topicList.save(TopicList.FILENAME);
            _ui.getBackButton().doClick();
        });
        
        //Back to mainscreen
        _ui.getBackButton().addActionListener(event ->
        {
            _ui.close();
            Startup.main(null);
        });
    }
}
