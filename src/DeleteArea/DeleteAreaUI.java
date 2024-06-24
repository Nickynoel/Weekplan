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

    private JLabel _descriptionLabel;
    private JList<String> _jListOfTasks;
    private JButton _deleteButton;
    private JButton _backButton;
    
    private JDialog _dialogWindow;
    
    /**
     * Initializing the UI
     */
    public DeleteAreaUI()
    {
        initializeElements();
        createWindow();
        initializeWindow();
    }

    private void initializeElements()
    {
        _descriptionLabel = new JLabel("List of current tasks");
        _jListOfTasks = new JList<>();
        _deleteButton = new JButton("Delete");
        _deleteButton.setEnabled(false); //assumption that initial selection of the JList is empty

        _backButton = new JButton("Back");
        _dialogWindow = new JDialog();
    }
    
    /**
     * Builds the JDialog
     */
    private void createWindow()
    {
        _dialogWindow.setTitle(WINDOWTITLE);
        _dialogWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        _dialogWindow.setSize(WINDOWWIDTH,WINDOWHEIGHT);
        _dialogWindow.setLayout(new BorderLayout());

        _dialogWindow.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
        _dialogWindow.setResizable(false);
    }
    
    /**
     * insert the panels onto the _dialogWindow
     */
    private void initializeWindow()
    {
        _dialogWindow.add(generateTopPanel(),BorderLayout.PAGE_START);
        
        JScrollPane scrollPane = new JScrollPane(generateCenterPanel());
        scrollPane.getVerticalScrollBar().setUnitIncrement(VERTICALSCROLLSPEED);
        _dialogWindow.add(scrollPane);
        
        _dialogWindow.add(generateBotPanel(),BorderLayout.PAGE_END);
    }

    /**
     * Initialization of the upper panel, containing the label that describes the action
     * @return the upper panel
     */
    private JPanel generateTopPanel()
    {
        JPanel panel = new JPanel();
        panel.add(_descriptionLabel);

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
        _jListOfTasks.setListData(taskList.getListOfTaskTitles().toArray(new String[0]));
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
        _dialogWindow.dispose();
    }


    public void setPositionRelativeToMainFrame(JFrame frame)
    {
        _dialogWindow.setLocation(new Point
                (frame.getLocation().x + 80, frame.getLocation().y + 40));
    }

    public void showUI()
    {
        _dialogWindow.setVisible(true);
    }
}
