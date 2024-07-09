package FrontEnd.Weekplan.SubComponents;

import BackEnd.TaskList.Task.Task;
import BackEnd.TaskList.TaskList;
import FrontEnd.Weekplan.WeekPlanUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TaskPanelUI
{
    private JPanel _itemPanel;
    private List<JButton> _taskTitleButtons;
    private List<JProgressBar> _taskProgressBars;
    private List<JButton> _addTimeButtons;

    private TaskList _taskList;

    public TaskPanelUI(TaskList list){
        initializeVariables(list);
        addTaskElements();
        initializePanel();
    }

    private void initializeVariables(TaskList list){
        _taskTitleButtons = new ArrayList<>();
        _taskProgressBars = new ArrayList<>();
        _addTimeButtons = new ArrayList<>();
        _itemPanel = new JPanel();
        _taskList = list;
    }

    // ----------------------- Creation of UI -----------------------------------

    /**
     * Creates the three elements for each task and adds them to their list
     */
    private void addTaskElements() {
        for (Task t : TaskList.getInstance().getList()) {
            addTaskTitleButton(t);
            addTaskProgressBar(t);
            addAddButton();
        }
    }

    private void removeTaskElements() {
        _taskTitleButtons.clear();
        _taskProgressBars.clear();
        _addTimeButtons.clear();
    }

    /**
     * Creates a title as borderless JButton with the name of the task
     */
    private void addTaskTitleButton(Task task) {
        JButton tmp = new JButton(task.getTitle());
        tmp.setBorderPainted(false);
        tmp.setContentAreaFilled(false);
        _taskTitleButtons.add(tmp);
    }

    /**
     * Creates the JProgressBar with the data of the task
     */
    private void addTaskProgressBar(Task task) {
        JProgressBar bar = new JProgressBar(0, task.getTargetTime());
        bar.setValue(task.getProgress());
        bar.setStringPainted(true);
        _taskProgressBars.add(bar);
    }

    /**
     * Creates the remaining JButton used to add progress to the task in the UI
     */
    private void addAddButton() {
        JButton button = new JButton("add");
        _addTimeButtons.add(button);
    }

    private void initializePanel() {
        _itemPanel.setLayout(new GridLayout(0,1));
        buildItemPanel();
//        JScrollPane scrollPane = new JScrollPane(_itemPanel);
//        scrollPane.getVerticalScrollBar().setUnitIncrement(VERTICALSCROLLSPEED);
    }

    private void buildItemPanel() {
        for (int i = 0; i < _taskList.getSize(); i++) {
            JPanel taskPanel = new JPanel();
            //for vertical space in between
            taskPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            taskPanel.add(buildTitlePanel(i));
            taskPanel.add(buildProgressbarPanel(i));
            taskPanel.add(buildAddButtonPanel(i));
            _itemPanel.add(taskPanel);
        }
    }

    /**
     * Builds a JPanel with a title given by the Task's parameter i
     *
     * @param i Index of Task for which the panel is built
     * @return Left part of centerPanel
     */
    private JPanel buildTitlePanel(int i) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); //makes sure that the labels are in the center
        panel.setPreferredSize(new Dimension(120, WeekPlanUI.TASKHEIGHT));
        panel.add(_taskTitleButtons.get(i));
        return panel;
    }

    /**
     * Builds a JPanel with a JLabel given by the Task's parameter i
     *
     * @param i Index of Task for which the panel is built
     * @return Left part of centerPanel
     */
    private JPanel buildProgressbarPanel(int i) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); //makes sure that the progressBars are in the center
        panel.setPreferredSize(new Dimension(150, WeekPlanUI.TASKHEIGHT));
        panel.add(_taskProgressBars.get(i));
        return panel;
    }

    /**
     * Builds a JPanel with a JLabel given by the Task's parameter i
     *
     * @param i Index of Task for which the panel is built
     * @return Left part of centerPanel
     */
    private JPanel buildAddButtonPanel(int i) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); //makes sure that the buttons are in the center
        panel.setPreferredSize(new Dimension(70, WeekPlanUI.TASKHEIGHT));
        panel.add(_addTimeButtons.get(i));
        return panel;
    }

    // -------- Creation of UI: End ----------- Getters: Start ---------------------

    public JPanel getPanel(){
        return _itemPanel;
    }

    /**
     * Returns the list of Title-Buttons
     *
     * @return a list of all Title-Buttons
     */
    public List<JButton> getTitleButtonList() {
        return _taskTitleButtons;
    }

    /**
     * Returns the list of JButtons for adding progress
     *
     * @return the list of JButtons for adding progress
     */
    public List<JButton> getAddButtonlist() {
        return _addTimeButtons;
    }

    /**
     * Opens the task menu of a given (usually newly generated) task
     *
     * @param task Task of which the menu is opened
     */
    public void openTaskMenu(Task task) {
        int index = _taskList.indexOf(task);
        _taskTitleButtons.get(index).doClick();
    }

    // ---------------- Getters: End ------------- Setters/Updaters: Start --------------------

    /**
     * Updates the displayed title of the given task
     *
     * @param task: the task which title might have changed
     */
    public void updateTaskName(Task task) {
        int taskNumber = _taskList.indexOf(task);
        _taskTitleButtons.get(taskNumber).setText(task.getTitle());
    }

    /**
     * Updates the target time of the given task
     *
     * @param task: the task which goal might have changed
     */
    public void updateTargetTime(Task task) {
        int taskNumber = _taskList.indexOf(task);
        _taskProgressBars.get(taskNumber).setMaximum(task.getTargetTime());
    }

    /**
     * Colors the JProgressBars with the data provided from the task
     *
     * @param task task corresponding the JProgressBar
     */
    public void colorProgressBar(Task task) {
        JProgressBar bar = _taskProgressBars.get(_taskList.indexOf(task));
        bar.setValue(task.getProgress());
    }

    /**
     * Reloads the tasks shown in the UI
     */
    public void refreshTaskDisplay() {
        removeTaskElements();
        addTaskElements();
        _itemPanel.removeAll();
        buildItemPanel();
        _itemPanel.validate();
        _itemPanel.repaint();
    }
}
