package OptionArea;

import javax.swing.*;
import java.awt.*;

/**
 * UI of OptionArea
 */

public class OptionAreaUI
{
    private JTextField _totalGoalInput;
    private JButton _addButton;
    private JButton _deleteButton;
    private JButton _backButton;
    
    private JFrame _mainframe;
    
    /**
     * Initializing the UI
     */
    public OptionAreaUI()
    {
        createTextFields();
        createButtons();
        createWindow();
        
        initializeWindow();
        
        _mainframe.setVisible(true);
    }
    
    /**
     * Creates the JTextfields of the OptionGui:
     * _totalGoalInput: JTextfield for accepting the new total goaltime
     */
    private void createTextFields()
    {
        _totalGoalInput = new JTextField("",5);
    }
    
    /**
     * Creates the buttons
     */
    private void createButtons()
    {
        _addButton = new JButton("Add Topic");
        _deleteButton = new JButton("Delete Topic");
        _backButton = new JButton("Back");
    }
    
    /**
     * Builds the JFrame
     */
    private void createWindow()
    {
        _mainframe = new JFrame();
        _mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Schließt Fenster
        _mainframe.setLocationRelativeTo(null);
        _mainframe.setTitle("Options");
        _mainframe.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        _mainframe.setSize(300,200);
        
        _mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //_mainframe.setResizable(false);
    }
    
    /**
     * Puts JButtons onto the JFrame
     */
    private void initializeWindow()
    {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("<html><span style='font-size:12px'>Total Goaltime:</span></html>");
        panel.add(label);
        _mainframe.add(panel);
        
        JPanel panel0 = new JPanel();
        panel0.add(_totalGoalInput);
        _mainframe.add(panel0);
        
        JPanel panel1 = new JPanel();
        panel1.add(_addButton);
        _mainframe.add(panel1);
        
        JPanel panel2 = new JPanel();
        panel2.add(_deleteButton);
        _mainframe.add(panel2);
        
        JPanel panel3 = new JPanel();
        panel3.add(_backButton);
        _mainframe.add(panel3);
    }
    
    /**
     * GetA for the _totalGoalInput JTextField
     * @return _totalGoalInput
     */
    public JTextField getTotalGoalInput()
    {
        return _totalGoalInput;
    }
    
    /**
     * Returns the addbutton
     * @return _addButton
     */
    public JButton getAddButton()
    {
        return _addButton;
    }
    
    /**
     * Returns the deletebutton
     * @return _deleteButton
     */
    public JButton getDeleteButton()
    {
        return _deleteButton;
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
