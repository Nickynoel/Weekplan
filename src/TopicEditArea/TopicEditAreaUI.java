package TopicEditArea;

import javax.swing.*;
import java.awt.*;

/**
 * UI of TopicArea
 */

public class TopicEditAreaUI
{
    private JLabel _topicLabel;
    private JTextField _topicField;
    
    private JLabel _lengthLabel;
    private JTextField _lengthField;
    
    private JButton _confirmButton;
    private JButton _backButton;
    
    private JPanel _centerPanel;
    private JPanel _botPanel;
    
    private JDialog _dialog;
    
    /**
     * Initializing the UI
     */
    public TopicEditAreaUI()
    {
        createLabels();
        createTextfields();
        createButtons();
        createPanels();
    
        createWindow();
        initializeWindow();
    }
    
    /**
     * Initialization of the Labels
     */
    private void createLabels()
    {
        _topicLabel = new JLabel("Error");
        _lengthLabel = new JLabel("Error");
    }
    
    /**
     * Creates the textfield
     */
    private void createTextfields()
    {
        _topicField = new JTextField("", 5); //number of colomns or dimension
        _lengthField = new JTextField("", 5);
    }
    
    /**
     * Creates the buttons
     */
    private void createButtons()
    {
        _confirmButton = new JButton("confirm");
        _backButton = new JButton("back");
    }
    
    /**
     * Creates the panels
     */
    private void createPanels()
    {
        _centerPanel = new JPanel();
        _centerPanel = generateCenterpanel();
        
        _botPanel = new JPanel();
        _botPanel = generateBotpanel();
    }
    
    /**
     * Initialization of the _centerPanel, containing JLabels and JTextfields for topicname and topiclength
     *
     * @return the _centerPanel
     */
    private JPanel generateCenterpanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        
        JPanel panel1 = new JPanel();
        panel1.add(_topicLabel);
        panel.add(panel1);
        
        JPanel panel2 = new JPanel();
        panel2.add(_topicField);
        panel.add(panel2);
        
        JPanel panel3 = new JPanel();
        panel3.add(_lengthLabel);
        panel.add(panel3);
        
        JPanel panel4 = new JPanel();
        panel4.add(_lengthField);
        panel.add(panel4);
        
        return panel;
    }
    
    /**
     * Initialization of the _botPanel, containing the two buttons for confirming and declining
     *
     * @return the _botPanel
     */
    private JPanel generateBotpanel()
    {
        JPanel panel1 = new JPanel();
        panel1.add(_confirmButton);
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
        _dialog = new JDialog();
        _dialog.setLayout(new BorderLayout());
        
        _dialog.setSize(300, 200);
        
        _dialog.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
        _dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        _dialog.setResizable(false);
    }
    
    /**
     * Initialization of the Panels, which is devided into its 3 subpanels top, center and bot
     */
    private void initializeWindow()
    {
        _dialog.add(_centerPanel);
        _dialog.add(_botPanel, BorderLayout.PAGE_END);
    }
    
    /**
     * Sets the title of the window according to the topic at hand
     *
     * @param titel: name of the topic
     */
    public void setTitle(String titel)
    {
        _dialog.setTitle(titel);
    }
    
    /**
     * Sets the UI to a certain point
     * @param p: Point for positioning the JDialog
     */
    public void setPosition(Point p)
    {
        _dialog.setLocation(p);
    }
    
    /**
     * Sets the text of the _topicLabel to the given String
     * @param s: text on _topicLabel
     */
    public void setTopicLabel(String s)
    {
        _topicLabel.setText(s);
    }
    
    /**
     * GetA for _topicField
     * @return _topicField
     */
    public JTextField getTopicField()
    {
        return _topicField;
    }
    
    /**
     * Sets the text of the _lengthLabel to the given String
     * @param s: text on _lengthLabel
     */
    public void setLengthLabel(String s)
    {
        _lengthLabel.setText(s);
    }
    
    /**
     * GetA for _lengthField
     * @return _lengthField
     */
    public JTextField getLengthField()
    {
        return _lengthField;
    }
    
    /**
     * Returns the confirmationbutton
     *
     * @return _confirmButton
     */
    public JButton getConfirmButton()
    {
        return _confirmButton;
    }
    
    /**
     * Returns the declining _backButton
     *
     * @return _backButton
     */
    public JButton getBackButton()
    {
        return _backButton;
    }
    
    /**
     * Shows the UI
     */
    public void showUI()
    {
        _dialog.setVisible(true);
    }
    
    /**
     * Closes the UI
     */
    public void close()
    {
        _dialog.dispose();
    }
}
