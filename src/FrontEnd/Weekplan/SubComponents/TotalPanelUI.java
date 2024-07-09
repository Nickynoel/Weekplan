package FrontEnd.Weekplan.SubComponents;

import BackEnd.TaskList.TaskList;
import FrontEnd.Weekplan.WeekPlanUI;

import javax.swing.*;
import java.awt.*;

public class TotalPanelUI
{
    private JPanel _totalPanel;
    private JLabel _totalLabel;
    private JProgressBar _totalProgress;
    private JButton _closeButton;

    private TaskList _taskList;

    public TotalPanelUI(TaskList list){
        initializeVariables(list);
        createTotalProgressBar();
        initializePanel();
    }

    private void initializeVariables(TaskList list){
        _totalPanel = new JPanel();
        _totalLabel = new JLabel("Error");
        _totalProgress = new JProgressBar();
        _closeButton = new JButton("Close");

        _taskList = list;
    }

    /**
     * Creates the elements used in the bottom part of the JDialog
     * Maximum cant be 100, cause the progress has to be of type int in JProgressBar
     */
    private void createTotalProgressBar() {
        _totalProgress.setMinimum(0);
        _totalProgress.setMaximum(100);
        _totalProgress.setStringPainted(true);
    }
    /**
     * Builds the bottom JPanel of the _mainframe which consists of one row consisting of a JLabel,
     * a JTextField for the total progress and a JButton to close the program
     */
    private void initializePanel() {
        _totalPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel subPanel1 = new JPanel();
        subPanel1.setLayout(new GridBagLayout());
        subPanel1.setPreferredSize(new Dimension(120, WeekPlanUI.TASKHEIGHT));
        subPanel1.add(_totalLabel);

        JPanel subPanel2 = new JPanel();
        subPanel2.setLayout(new GridBagLayout());
        subPanel2.setPreferredSize(new Dimension(150, WeekPlanUI.TASKHEIGHT));
        subPanel2.add(_totalProgress);

        JPanel subPanel3 = new JPanel();
        subPanel3.setLayout(new GridBagLayout());
        subPanel3.setPreferredSize(new Dimension(70, WeekPlanUI.TASKHEIGHT));
        subPanel3.add(_closeButton);

        _totalPanel.add(subPanel1);
        _totalPanel.add(subPanel2);
        _totalPanel.add(subPanel3);

        updateTotalPanel();
    }

    // -------- Creation of UI: End ----------- Getters: Start ---------------------

    public JPanel getPanel(){
        return _totalPanel;
    }

    public JButton getCloseButton(){
        return _closeButton;
    }

    // ---------------- Getters: End ------------- Setters/Updaters: Start --------------------

    /**
     * Updates the total area consisting of the JLabel and the JProgressbar
     */
    public void updateTotalPanel() {
        updateTotalLabel();
        updateTotalProgressBar();
    }

    /**
     * Updates the text on the _totalLabel
     */
    private void updateTotalLabel() {
        _totalLabel.setText(_taskList.getTotalProgressInText());
    }

    /**
     * Colors the final JProgressbar, that takes data from all tasks
     */
    private void updateTotalProgressBar() {
        _totalProgress.setValue(_taskList.getTotalProgressInPercent());
    }

}
