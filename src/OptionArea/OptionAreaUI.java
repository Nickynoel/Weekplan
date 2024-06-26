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
    private final int WINDOWHEIGHT = 170;
    private final String WINDOWTITLE = "Options";

    private JTextField _totalTargetInput;
    private JComboBox<String> _resetComboBox;
    private JButton _backButton;
    
    private JFrame _dialogWindow;
    
    /**
     * Initializing the UI
     */
    public OptionAreaUI()
    {
        initializeElements();
        createWindow();
        initializeWindow();
    }

    private void initializeElements()
    {
        _totalTargetInput = new JTextField("",5);
        _resetComboBox = new JComboBox<>((Settings.RESETPROGRAMS).toArray(new String[0]));

        _backButton = new JButton("Back");
        _dialogWindow = new JFrame();
    }

    /**
     * Builds the JDialog
     */
    private void createWindow()
    {
        _dialogWindow.setTitle(WINDOWTITLE);
        _dialogWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        _dialogWindow.setSize(WINDOWWIDTH,WINDOWHEIGHT);
        _dialogWindow.setLayout(new GridLayout(3,1));
    }
    
    /**
     * Puts JButtons onto the Dialog window
     */
    private void initializeWindow()
    {
        _dialogWindow.add(generateTotalTargetTimePanel());
        _dialogWindow.add(generateResetTypePanel());
        _dialogWindow.add(generateBackButtonPanel());
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
                (frame.getLocation().x + 60, frame.getLocation().y + 80));
    }

    public void showUI()
    {
        _dialogWindow.setVisible(true);
    }
}
