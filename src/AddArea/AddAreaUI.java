package AddArea;

import javax.swing.*;
import java.awt.*;

/**
 * UI of AddArea
 */

public class AddAreaUI
{
    private JDialog _dialog;
    private JButton _confirmButton;
    private JButton _backButton;
    
    private JPanel _topPanel;
    private JPanel _centerPanel;
    private JPanel _botPanel;
    
    private JTextField _textField;
    private JLabel _label;
    
    /**
     * Initializing the UI
     */
    public AddAreaUI(JFrame frame)
    {
        createWindow(frame);
        createButtons();
        createPanels();
        
        initializeTextField();
        initializeLabels();
        initializePanels();
    }
    
    /**
     * Builds the JDialog
     */
    private void createWindow(JFrame frame)
    {
        _dialog = new JDialog();
        _dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //disables upper right X
        _dialog.setLocation(new Point(frame.getLocation().x+100,frame.getLocation().y+100)); //location relative to the frame in the background
        _dialog.setTitle("Add");
        _dialog.setLayout(new BorderLayout());
        
        _dialog.setSize(200,140);
        
        _dialog.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
        _dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        _dialog.setResizable(false);
    }
    
    /**
     * Creates the buttons
     */
    private void createButtons()
    {
        _confirmButton = new JButton("confirm");
        _confirmButton.setEnabled(false);
        
        _backButton = new JButton("back");
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
     * Creates the textfield
     */
    private void initializeTextField()
    {
        _textField = new JTextField("", 5); //number of colomns or dimension
    }
    
    /**
     * Initialization of the Labels
     */
    private void initializeLabels()
    {
        _label = new JLabel("Time in minutes");
    }
    
    /**
     * Initialization of the Panels, which is devided into its 3 subpanels: top, center and bot
     */
    private void initializePanels()
    {
        _topPanel = generateToppanel();
        _dialog.add(_topPanel,BorderLayout.PAGE_START);
        
        _centerPanel = generateCenterpanel();
        _dialog.add(_centerPanel);
        
        _botPanel = generateBotpanel();
        _dialog.add(_botPanel,BorderLayout.PAGE_END);
    }
    
    /**
     * Initialization of the _topPanel, containing the Label describing the action
     * @return the _topPanel
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
        panel.add(_textField);
        
        return panel;
    }
    
    /**
     * Initialization of the _botPanel, containing the two buttons for confirming and declining
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
    public JButton getConfirmButton()
    {
        return _confirmButton;
    }
    
    /**
     * Closes the UI
     */
    public void close()
    {
        _dialog.dispose();
    }
    
    /**
     * Returns the textfield of the UI
     * @return _textField
     */
    public JTextField getTextfield()
    {
        return _textField;
    }
    
    /**
     * Enables the _confirmButton
     */
    public void enableConfirmButton()
    {
        _confirmButton.setEnabled(true);
    }
    
    /**
     * Disables the _confirmButton
     */
    public void disableConfirmButton()
    {
        _confirmButton.setEnabled(false);
    }
    
    /**
     * Sets the UI's visibility to true
     */
    public void showUI()
    {
        _dialog.setVisible(true);
    }
    
    /**
     * Sets the UI's title
     */
    public void setTitle(String s)
    {
        _dialog.setTitle(s);
    }
}
