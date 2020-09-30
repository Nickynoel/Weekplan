package OptionArea;

import javax.swing.*;
import java.awt.*;

/**
 * UI of OptionArea
 */

public class OptionAreaUI
{
    private JFrame _mainframe;
    private JButton _addButton;
    private JButton _deleteButton;
    private JButton _backButton;
    
    /**
     * Initializing the UI
     */
    public OptionAreaUI()
    {
        createWindow();
        createButtons();
        
        initializeWindow();
        
        _mainframe.setVisible(true);
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
        
        _mainframe.setSize(300,130);
        
        _mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        _mainframe.setResizable(false);
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
     * Puts JButtons onto the JFrame
     */
    private void initializeWindow()
    {
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
     * Returns the declining _backButton
     * @return _backButton
     */
    public JButton getBackButton()
    {
        return _backButton;
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
     * Closes the UI
     */
    public void close()
    {
        _mainframe.dispose();
    }
    
}
