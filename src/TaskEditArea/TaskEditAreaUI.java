package TaskEditArea;

import javax.swing.*;
import java.awt.*;

/**
 * UI of TaskEditArea
 */

public class TaskEditAreaUI
{
    private final int WINDOWWIDTH = 300;
    private final int WINDOWHEIGHT = 200;

    private JLabel _taskLabel;
    private JTextField _taskField;
    
    private JLabel _targetTimeLabel;
    private JTextField _targetTimeField;
    
    private JButton _confirmButton;
    private JButton _backButton;

    private JDialog _dialog;
    
    /**
     * Initializing the UI
     */
    public TaskEditAreaUI()
    {
        initializeVariables();
        createWindow();
        initializeWindow();
    }

    private void initializeVariables()
    {
        _taskLabel = new JLabel("Error");
        _targetTimeLabel = new JLabel("Error");

        _taskField = new JTextField("", 5); //number of colomns or dimension
        _targetTimeField = new JTextField("", 5);

        _confirmButton = new JButton("confirm");
        _backButton = new JButton("back");

        _dialog = new JDialog();
    }

    /**
     * Builds the JDialog
     */
    private void createWindow()
    {
        _dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        _dialog.setSize(WINDOWWIDTH,WINDOWHEIGHT);
        _dialog.setLayout(new BorderLayout());

        _dialog.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
        _dialog.setResizable(false);
    }

    /**
     * Initialization of the Panels, which is devided into its 3 subpanels top, center and bot
     */
    private void initializeWindow()
    {
        _dialog.add(generateCenterPanel());
        _dialog.add(generateBotPanel(), BorderLayout.PAGE_END);
    }

    /**
     * Initialization of the central Panel, containing JLabels and JTextFields for name and targetLength
     *
     * @return the central Panel
     */
    private JPanel generateCenterPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JPanel panel1 = new JPanel();
        panel1.add(_taskLabel);
        panel.add(panel1);

        JPanel panel2 = new JPanel();
        panel2.add(_taskField);
        panel.add(panel2);

        JPanel panel3 = new JPanel();
        panel3.add(_targetTimeLabel);
        panel.add(panel3);

        JPanel panel4 = new JPanel();
        panel4.add(_targetTimeField);
        panel.add(panel4);

        return panel;
    }

    /**
     * Initialization of the bottom Panel, containing the two buttons for confirming and declining
     *
     * @return the bottom Panel
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
        _taskLabel.setText(s);
    }
    
    /**
     * GetA for _topicField
     * @return _topicField
     */
    public JTextField getTaskField()
    {
        return _taskField;
    }

    public void clearTaskField()
    {
        _taskField.setText("");
    }
    /**
     * Sets the text of the _lengthLabel to the given String
     * @param s: text on _lengthLabel
     */
    public void setLengthLabel(String s)
    {
        _targetTimeLabel.setText(s);
    }
    
    /**
     * GetA for _lengthField
     * @return _lengthField
     */
    public JTextField getTargetTimeField()
    {
        return _targetTimeField;
    }

    public void clearTargetTimeField()
    {
        _targetTimeField.setText("");
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
