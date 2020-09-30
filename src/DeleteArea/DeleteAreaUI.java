package DeleteArea;

import TopicList.TopicList;

import javax.swing.*;
import java.awt.*;

/**
 * UI of DeleteArea
 */

public class DeleteAreaUI
{
    private TopicList _topicList;
    
    private JFrame _mainframe;
    private JList _list;
    private JButton _deleteButton;
    private JButton _backButton;
    
    private JPanel _topPanel;
    private JPanel _centerPanel;
    private JPanel _botPanel;
    
    private JLabel _label;
    
    /**
     * Initializing the UI
     */
    public DeleteAreaUI(TopicList list)
    {
        _topicList = list;
        
        createWindow();
        createList();
        createButtons();
        createPanels();
        
        initializeLabels();
        initializePanels();
        
        _mainframe.setVisible(true);
    }
    
    /**
     * Builds the JDialog
     */
    private void createWindow()
    {
        _mainframe = new JFrame();
        _mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Schließt Fenster
        _mainframe.setLocationRelativeTo(null);
        _mainframe.setTitle("Delete");
        _mainframe.setLayout(new BorderLayout());
        
        _mainframe.setSize(250,300);
        
        _mainframe.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        //_mainframe.pack();
        //_mainframe.setResizable(false);
    }
    
    /**
     * Creates the JList consisting of the topics in the TopicList
     */
    private void createList()
    {
        _list = new JList(_topicList.getTitleArray());
    }
    
    /**
     * Creates the buttons
     */
    private void createButtons()
    {
        _deleteButton = new JButton("Delete");
        _deleteButton.setEnabled(false); //assumption that initial selection of the JList is empty
        
        _backButton = new JButton("Back");
    }
    
    /**
     * Creates the panels
     */
    private void createPanels()
    {
        _topPanel = new JPanel();
        _centerPanel = new JPanel();
        _botPanel = new JPanel();
    }
    
    /**
     * Initialization of the Labels
     */
    private void initializeLabels()
    {
        _label = new JLabel("List of current topics");
    }
    
    /**
     * Initialization of the Panels, which is divided into its 3 subpanels: top, center and bot
     */
    private void initializePanels()
    {
        _topPanel = generateToppanel();
        _mainframe.add(_topPanel,BorderLayout.PAGE_START);
        
        _centerPanel = generateCenterpanel();
        JScrollPane scrollPane = new JScrollPane(_centerPanel);
        _mainframe.add(scrollPane);
        
        _botPanel = generateBotpanel();
        _mainframe.add(_botPanel,BorderLayout.PAGE_END);
    }
    
    /**
     * Initialization of the _topPanel, containing the Label describing the action
     * @return the _toppanel
     */
    private JPanel generateToppanel()
    {
        JPanel panel = new JPanel();
        panel.add(_label);
        
        return panel;
    }
    
    /**
     * Initialization of the _centerPanel, containing the textfield for the input
     * @return the _centerPanel
     */
    private JPanel generateCenterpanel()
    {
        JPanel panel = new JPanel();
        panel.add(_list);
        return panel;
    }
    
    /**
     * Initialization of the _botPanel, containing the two buttons for confirming and declining
     * @return the _botPanel
     */
    private JPanel generateBotpanel()
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
    
    /**
     * Returns the declining _backButton
     * @return _backButton
     */
    public JButton getBackButton()
    {
        return _backButton;
    }
    
    /**
     * Returns the confirmationbutton
     * @return _confirmButton
     */
    public JButton getDeleteButton()
    {
        return _deleteButton;
    }
    
    /**
     * Closes the UI
     */
    public void close()
    {
        _mainframe.dispose();
    }
    
    /**
     * Returns the list
     * @return _list
     */
    public JList<String> getList()
    {
        return _list;
    }
    
    /**
     * Disables the _deleteButton
     */
    public void disableDeleteButton()
    {
        _deleteButton.setEnabled(false);
    }
    
    /**
     * Enables the _deleteButton
     */
    public void enableDeleteButton()
    {
        _deleteButton.setEnabled(true);
    }
}
