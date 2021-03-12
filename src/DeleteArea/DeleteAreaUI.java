package DeleteArea;

import javax.swing.*;
import java.awt.*;

/**
 * UI of DeleteArea
 */

public class DeleteAreaUI
{
    private JLabel _label;
    private JList _list;
    private JButton _deleteButton;
    private JButton _backButton;
    
    private JPanel _topPanel;
    private JPanel _centerPanel;
    private JPanel _botPanel;
    
    private JFrame _mainframe;
    
    /**
     * Initializing the UI
     */
    public DeleteAreaUI()
    {
        createLabels();
        createList();
        createButtons();
        createPanels();
        createWindow();
        
        initializeWindow();
        
        _mainframe.setVisible(true);
    }
    
    /**
     * Initialization of the Labels
     */
    private void createLabels()
    {
        _label = new JLabel("List of current topics");
    }
    
    /**
     * Creates the JList consisting of the topics in the TopicList
     */
    private void createList()
    {
        _list = new JList();
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
        _topPanel = generateToppanel();
        
        _centerPanel = new JPanel();
        _centerPanel = generateCenterpanel();
        
        _botPanel = new JPanel();
        _botPanel = generateBotpanel();
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
        //_mainframe.setResizable(false);
    }
    
    /**
     * insert the panels onto the _mainFrame
     */
    private void initializeWindow()
    {
        _mainframe.add(_topPanel,BorderLayout.PAGE_START);
        
        JScrollPane scrollPane = new JScrollPane(_centerPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        _mainframe.add(scrollPane);
        
        _mainframe.add(_botPanel,BorderLayout.PAGE_END);
    }
    
    /**
     * Returns the list
     * @return _list
     */
    public JList<String> getJList()
    {
        return _list;
    }
    
    /**
     * Sets the list with a given String-array
     * @param list: List of Topic-titles
     */
    public void setList(String[] list)
    {
        _list.setListData(list);
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
     * Closes the UI
     */
    public void close()
    {
        _mainframe.dispose();
    }
}
