package FrontEnd.DialogWindows.TaskEditArea;

import BackEnd.TaskList.Task.Task;

import javax.swing.*;
import java.awt.*;

/**
 * UI of FrontEnd.DialogWindows.TaskEditArea
 */

public class TaskEditAreaUI
{
    private final int MAXTASKDISPLAYLENGTH = 13;
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

        _taskField = new JTextField("", 5); //number of columns or dimension
        _targetTimeField = new JTextField("", 5);

        _confirmButton = new JButton("confirm");
        _backButton = new JButton("back");

        _dialog = new JDialog();
    }

    // ----------------------- Creation of UI -----------------------------------

    /**
     * Builds the JDialog
     */
    private void createWindow()
    {
        _dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        _dialog.setSize(WINDOWWIDTH,WINDOWHEIGHT);
        _dialog.setLayout(new BorderLayout());

        _dialog.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
    }

    /**
     * Initialization of the Panels, which is divided into its 3 subPanels top, center and bot
     */
    private void initializeWindow()
    {
        _dialog.add(generateCenterPanel());
        _dialog.add(generateBotPanel(), BorderLayout.PAGE_END);

        _dialog.setResizable(false);
    }

    /**
     * Initialization of the central panel, containing JLabels and JTextFields for name and targetLength
     *
     * @return the central panel
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

    // ----- Internal Creation of UI: End ----------- External Creation of UI: Start --------------

    public void loadTaskData(Task task)
    {
        setTitle(task.getTitle() + ": " + task.getProgress() + " Min.");
        setTaskLabel(task.getTitle());
        setTargetTimeLabel(task.getTargetTime());
    }

    /**
     * Sets the title of the window according to the task at hand
     *
     * @param title: name of the task
     */
    private void setTitle(String title)
    {
        _dialog.setTitle(title);
    }

    /**
     * Sets the text on the taskLabel with the current temporary taskName
     */
    public void setTaskLabel(String newName)
    {
        if (newName.length() > MAXTASKDISPLAYLENGTH)
            _taskLabel.setText("Name: " + newName.substring(0, MAXTASKDISPLAYLENGTH)+".");
        else
            _taskLabel.setText("Name: " + newName);
    }

    public void setPositionRelativeToMainFrame(JFrame frame)
    {
        _dialog.setLocation(new Point
                (frame.getLocation().x + 50, frame.getLocation().y + 100));
    }

    // ----------- External Creation of UI: End -------------- Getters: Start -------------------

    /**
     * GetA for _taskField
     * @return _taskField
     */
    public JTextField getTaskField()
    {
        return _taskField;
    }

    /**
     * GetA for _lengthField
     * @return _lengthField
     */
    public JTextField getTargetTimeField()
    {
        return _targetTimeField;
    }

    /**
     * Returns the confirmation button
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

    // ---------------- Getters: End ------------- Setters/Updaters: Start --------------------

    public void clearTaskField()
    {
        _taskField.setText("");
    }

    /**
     * Updates the text on the targetTimeLabel with the current temporary targetTime value
     */
    public void setTargetTimeLabel(int newTargetTime)
    {
        _targetTimeLabel.setText("Goal: " + newTargetTime + " Minutes");
    }

    public void clearTargetTimeField()
    {
        _targetTimeField.setText("");
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