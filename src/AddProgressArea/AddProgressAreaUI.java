package AddProgressArea;

import javax.swing.*;
import java.awt.*;

/**
 * UI of AddArea
 */

public class AddProgressAreaUI
{
    private final int WINDOWWIDTH = 200;
    private final int WINDOWHEIGHT = 140;
    private final String WINDOWTITLE = "Add";

    private JLabel _label;
    private JTextField _textField;
    private JButton _confirmButton;
    private JButton _backButton;

    private JDialog _dialog;
    
    /**
     * Initializing the UI
     */
    public AddProgressAreaUI()
    {
        initializeVariables();
        createWindow();
        initializeWindow();
    }

    private void initializeVariables()
    {
        _label = new JLabel("Time in minutes");
        _textField = new JTextField("", 5); //number of columns or dimension
        _confirmButton = new JButton("confirm");
        _confirmButton.setEnabled(false);

        _backButton = new JButton("back");
        _dialog = new JDialog();
    }

    /**
     * Builds the JDialog
     */
    private void createWindow()
    {
        _dialog.setTitle(WINDOWTITLE);
        _dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        _dialog.setSize(WINDOWWIDTH,WINDOWHEIGHT);
        _dialog.setLayout(new BorderLayout());

        _dialog.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
        _dialog.setResizable(false);
    }

    /**
     * insert the panels onto the _mainFrame
     */
    private void initializeWindow()
    {
        _dialog.add(generateTopPanel(),BorderLayout.PAGE_START);
        _dialog.add(generateCenterPanel());
        _dialog.add(generateBotPanel(),BorderLayout.PAGE_END);
    }

    /**
     * Initialization of the _topPanel, containing the Label describing the action
     * @return the _topPanel
     */
    private JPanel generateTopPanel()
    {
        JPanel panel = new JPanel();
        panel.add(_label);

        return panel;
    }

    /**
     * Initialization of the _centerPanel, containing the textField for the input
     * @return the _centerPanel
     */
    private JPanel generateCenterPanel()
    {
        JPanel panel = new JPanel();
        panel.add(_textField);

        return panel;
    }

    /**
     * Initialization of the _botPanel, containing the two buttons for confirming and declining
     * @return the _botPanel
     */
    private JPanel generateBotPanel()
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
     * Sets the UI's title
     */
    public void setTitle(String s)
    {
        _dialog.setTitle(s);
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
     * Returns the textField of the UI
     * @return _textField
     */
    public JTextField getTextField()
    {
        return _textField;
    }
    
    /**
     * Returns the confirmationButton
     * @return _confirmButton
     */
    public JButton getConfirmButton()
    {
        return _confirmButton;
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
     * Returns the declining _backButton
     * @return _backButton
     */
    public JButton getBackButton()
    {
        return _backButton;
    }
    
    /**
     * Sets the UI's visibility to true
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
