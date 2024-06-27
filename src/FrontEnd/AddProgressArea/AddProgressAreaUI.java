package FrontEnd.AddProgressArea;

import BackEnd.TaskList.Task.Task;

import javax.swing.*;
import java.awt.*;

/**
 * UI of FrontEnd.AddProgressArea
 */

public class AddProgressAreaUI
{
    private final int WINDOWWIDTH = 200;
    private final int WINDOWHEIGHT = 140;
    private final String WINDOWTITLE = "Add";

    private JLabel _descriptionLabel;
    private JTextField _inputField;
    private JButton _confirmButton;
    private JButton _backButton;

    private JDialog _dialogWindow;
    
    /**
     * Initializing the UI
     */
    public AddProgressAreaUI()
    {
        initializeElements();
        createWindow();
        initializeWindow();
    }

    private void initializeElements()
    {
        _descriptionLabel = new JLabel("Time in minutes");
        _inputField = new JTextField("", 5); //number of columns or dimension
        _confirmButton = new JButton("confirm");
        _confirmButton.setEnabled(false);

        _backButton = new JButton("back");
        _dialogWindow = new JDialog();
    }

    private void createWindow()
    {
        _dialogWindow.setTitle(WINDOWTITLE);
        _dialogWindow.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        _dialogWindow.setSize(WINDOWWIDTH,WINDOWHEIGHT);
        _dialogWindow.setLayout(new BorderLayout());

        _dialogWindow.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
        _dialogWindow.setResizable(false);
    }

    private void initializeWindow()
    {
        _dialogWindow.add(generateTopPanel(),BorderLayout.PAGE_START);
        _dialogWindow.add(generateCenterPanel());
        _dialogWindow.add(generateBotPanel(),BorderLayout.PAGE_END);
    }

    private JPanel generateTopPanel()
    {
        JPanel panel = new JPanel();
        panel.add(_descriptionLabel);

        return panel;
    }

    private JPanel generateCenterPanel()
    {
        JPanel panel = new JPanel();
        panel.add(_inputField);

        return panel;
    }

    private JPanel generateBotPanel()
    {
        JPanel panel1 = new JPanel();
        panel1.add(_confirmButton);
        JPanel panel2 = new JPanel();
        panel2.add(_backButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        mainPanel.add(panel1);
        mainPanel.add(panel2);

        return mainPanel;
    }


    public void loadTaskData(Task task)
    {
        setTitle(task.getTitle());
    }

    /**
     * Sets the position of the UI to a certain point
     * @param frame: Underlying base frame
     */
    public void setPositionRelativeToMainFrame(JFrame frame)
    {
        _dialogWindow.setLocation(new Point
                (frame.getLocation().x + 100, frame.getLocation().y + 100));
    }

    /**
     * Sets the title of the UI
     */
    private void setTitle(String s)
    {
        _dialogWindow.setTitle(s);
    }

    /**
     * Returns the textField of the UI
     */
    public JTextField getTextField()
    {
        return _inputField;
    }

    /**
     * Returns the text within the textField of the UI
     */
    public String getUserInput()
    {
        return _inputField.getText();
    }

    /**
     * Returns the confirmationButton
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
     * Returns the _backButton
     */
    public JButton getBackButton()
    {
        return _backButton;
    }
    
    /**
     * Sets the visibility of the UI to true
     */
    public void showUI()
    {
        _dialogWindow.setVisible(true);
    }
    
    /**
     * Closes the UI
     */
    public void close()
    {
        _dialogWindow.dispose();
    }
}
