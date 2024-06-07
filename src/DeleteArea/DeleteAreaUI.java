package DeleteArea;

import TaskList.TaskList;

import javax.swing.*;
import java.awt.*;

/**
 * UI of DeleteArea
 */

public class DeleteAreaUI
{
    private final int VERTICALSCROLLSPEED = 15;
    private final int WINDOWWIDTH = 250;
    private final int WINDOWHEIGHT = 300;
    private final String WINDOWTITLE = "Delete";

    private JLabel _titleLabel;
    private JList _jListOfTasks;
    private JButton _deleteButton;
    private JButton _backButton;
    
    private JFrame _mainframe;
    
    /**
     * Initializing the UI
     */
    public DeleteAreaUI()
    {
        initializeVariables();
        createWindow();
        initializeWindow();
    }

    private void initializeVariables()
    {
        _titleLabel = new JLabel("List of current tasks");
        _jListOfTasks = new JList();
        _deleteButton = new JButton("Delete");
        _deleteButton.setEnabled(false); //assumption that initial selection of the JList is empty

        _backButton = new JButton("Back");
        _mainframe = new JFrame();
    }
    
    /**
     * Builds the JDialog
     */
    private void createWindow()
    {
        _mainframe.setTitle(WINDOWTITLE);
        _mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Schließt Fenster
        _mainframe.setSize(WINDOWWIDTH,WINDOWHEIGHT);
        _mainframe.setLayout(new BorderLayout());

        _mainframe.setLocationRelativeTo(null);
    }
    
    /**
     * insert the panels onto the _mainFrame
     */
    private void initializeWindow()
    {
        _mainframe.add(generateTopPanel(),BorderLayout.PAGE_START);
        
        JScrollPane scrollPane = new JScrollPane(generateCenterPanel());
        scrollPane.getVerticalScrollBar().setUnitIncrement(VERTICALSCROLLSPEED);
        _mainframe.add(scrollPane);
        
        _mainframe.add(generateBotPanel(),BorderLayout.PAGE_END);
        //_mainframe.setResizable(false);
        _mainframe.setVisible(true);
    }

    /**
     * Initialization of the upper panel, containing the label that describes the action
     * @return the upper panel
     */
    private JPanel generateTopPanel()
    {
        JPanel panel = new JPanel();
        panel.add(_titleLabel);

        return panel;
    }

    /**
     * Initialization of the central panel, containing the JList for the input
     * @return the central panel
     */
    private JPanel generateCenterPanel()
    {
        JPanel panel = new JPanel();
        panel.add(_jListOfTasks);
        return panel;
    }

    /**
     * Initialization of the bottom panel, containing the two buttons for confirming and declining
     * @return the bottom panel
     */
    private JPanel generateBotPanel()
    {
        JPanel panel1 = new JPanel();
        panel1.add(_deleteButton);
        JPanel panel2 = new JPanel();
        panel2.add(_backButton);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(panel1);
        panel.add(panel2);

        return panel;
    }

    public void loadTaskList(TaskList taskList)
    {
        _jListOfTasks.setListData(taskList.getArrayOfTaskTitles());
    }

    /**
     * Returns the JList
     * @return _jListOfTasks
     */
    public JList<String> getJList()
    {
        return _jListOfTasks;
    }

    
    /**
     * Returns the _deleteButton
     * @return _deleteButton
     */
    public JButton getDeleteButton()
    {
        return _deleteButton;
    }

    /**
     * Enables the _deleteButton
     */
    public void enableDeleteButton()
    {
        _deleteButton.setEnabled(true);
    }

    /**
     * Disables the _deleteButton
     */
    public void disableDeleteButton()
    {
        _deleteButton.setEnabled(false);
    }

    /**
     * Returns the declining _backButton
     * @return _backButton
     */
    public JButton getBackButton()
    {
        return _backButton;
    }

    /**
     * Closes the UI
     */
    public void close()
    {
        _mainframe.dispose();
    }


}
