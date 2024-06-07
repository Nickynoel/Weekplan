package OptionArea;

import javax.swing.*;
import java.awt.*;
import Settings.Settings;



/**
 * UI of OptionArea
 */

public class OptionAreaUI
{
    private final int WINDOWWIDTH = 300;
    private final int WINDOWHEIGHT = 200;
    private final String WINDOWTITLE = "Options";

    private JTextField _totalTargetInput;
    private JComboBox<String> _resetComboBox;
    private JButton _addButton;
    private JButton _deleteButton;
    private JButton _backButton;
    
    private JFrame _mainframe;
    
    /**
     * Initializing the UI
     */
    public OptionAreaUI()
    {
        initializeVariables();
        createWindow();
        initializeWindow();
        showUI();
    }

    private void initializeVariables()
    {
        _totalTargetInput = new JTextField("",5);
        _resetComboBox = new JComboBox<>((Settings.RESETPROGRAMS).toArray(new String[0]));

        _addButton = new JButton("Add Topic");
        _deleteButton = new JButton("Delete Topic");
        _backButton = new JButton("Back");

        _mainframe = new JFrame();
    }

    
    /**
     * Builds the JFrame
     */
    private void createWindow()
    {
        _mainframe.setTitle(WINDOWTITLE);
        _mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        _mainframe.setSize(WINDOWWIDTH,WINDOWHEIGHT);
        _mainframe.setLayout(new FlowLayout(FlowLayout.CENTER));

        _mainframe.setLocationRelativeTo(null);
        //_mainframe.setResizable(false);
    }
    
    /**
     * Puts JButtons onto the JFrame
     */
    private void initializeWindow()
    {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("<html><span style='font-size:12px'>Total Target: (in h)</span></html>");
        panel.add(label);
        _mainframe.add(panel);
        
        JPanel panel0 = new JPanel();
        panel0.add(_totalTargetInput);
        _mainframe.add(panel0);
        
        JPanel panel01 = new JPanel();
        JLabel resetLabel = new JLabel("<html><span style='font-size:12px'>Reset Type:</span></html>");
        panel01.add(resetLabel);
        _mainframe.add(panel01);
        
        JPanel panel02 = new JPanel();
        panel02.add(_resetComboBox);
        _mainframe.add(panel02);
        
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
     * GetA for the _totalTargetInput JTextField
     * @return _totalTargetInput
     */
    public JTextField getTotalTargetInput()
    {
        return _totalTargetInput;
    }

    public void clearTotalTargetInput()
    {
        _totalTargetInput.setText("");
    }

    /**
     * Returns the addButton
     * @return _addButton
     */
    public JButton getAddButton()
    {
        return _addButton;
    }

    /**
     * Returns the deleteButton
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

    /**
     * Defaults the comboBox-index to the current index
     * @param resetProgram: the reset-program given from the settings
     */
    public void setResetProgram(String resetProgram)
    {
        _resetComboBox.setSelectedItem(resetProgram);
    }

    /**
     * GetA for the _resetJComboBox
     * @return _resetComboBox
     */
    public JComboBox<String> getResetComboBox()
    {
        return _resetComboBox;
    }

    /**
     * Sets the UI's visibility to true
     */
    private void showUI()
    {
        _mainframe.setVisible(true);
    }

    public void loadSettings(Settings settingList)
    {
        setResetProgram(settingList.getResetProgram());
    }
}
