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
    }

    private void initializeVariables()
    {
        _totalTargetInput = new JTextField("",5);
        _resetComboBox = new JComboBox<>((Settings.RESETPROGRAMS).toArray(new String[0]));

        _addButton = new JButton("Add Task");
        _deleteButton = new JButton("Delete Tasks");
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
        _mainframe.setLayout(new GridLayout(4,1));

        _mainframe.setLocationRelativeTo(null);
    }
    
    /**
     * Puts JButtons onto the JFrame
     */
    private void initializeWindow()
    {
        _mainframe.add(generateTotalTargetTimePanel());
        _mainframe.add(generateResetTypePanel());
        _mainframe.add(generateTaskButtonsPanel());
        _mainframe.add(generateBackButtonPanel());

        //_mainframe.setResizable(false);
        _mainframe.setVisible(true);
    }

    private JPanel generateTotalTargetTimePanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("<html><span style='font-size:12px'>Total Target: (in h)</span></html>");
        labelPanel.add(label);
        panel.add(labelPanel);

        JPanel textFieldPanel = new JPanel();
        textFieldPanel.add(_totalTargetInput);
        panel.add(textFieldPanel);

        return panel;
    }

    private JPanel generateResetTypePanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JPanel labelPanel = new JPanel();
        JLabel resetLabel = new JLabel("<html><span style='font-size:12px'>Reset Type:</span></html>");
        labelPanel.add(resetLabel);
        panel.add(labelPanel);

        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.add(_resetComboBox);
        panel.add(comboBoxPanel);

        return panel;
    }

    private JPanel generateTaskButtonsPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JPanel addButtonPanel = new JPanel();
        addButtonPanel.add(_addButton);
        panel.add(addButtonPanel);

        JPanel deleteButtonPanel = new JPanel();
        deleteButtonPanel.add(_deleteButton);
        panel.add(deleteButtonPanel);

        return panel;
    }

    private JPanel generateBackButtonPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(_backButton);
        panel.add(backButtonPanel);

        return panel;
    }

    public void loadSettings(Settings settingList)
    {
        setResetProgram(settingList.getResetProgram());
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
     * GetA for the _resetJComboBox
     * @return _resetComboBox
     */
    public JComboBox<String> getResetComboBox()
    {
        return _resetComboBox;
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
}
