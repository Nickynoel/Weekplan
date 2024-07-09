package FrontEnd.Weekplan;

import BackEnd.TaskList.Task.Task;
import BackEnd.TaskList.TaskList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UI of the class WeekPlan
 * Renewed Version of WeekPlanUI
 */
public class WeekPlanUI
{
    private final int VERTICALSCROLLSPEED = 30;
    private final int WINDOWWIDTH = 400;
    private final int WINDOWHEIGHT = 370;
    private final String WINDOWTITLE = "Nicky's Java Program";
    private final int TASKHEIGHT = 40;

    private TaskList _taskList;

    private JPanel _itemPanel;
    private List<JButton> _taskTitleButtons;
    private List<JProgressBar> _taskProgressBars;
    private List<JButton> _addTimeButtons;

    private JMenuBar _menuBar;
    private JMenuItem _createItem;
    private JMenuItem _deleteItem;
    private JMenuItem _optionsItem;
    private JMenuItem _closeItem;
    private JMenuItem _undoItem;
    private JMenuItem _redoItem;

    private JLabel _totalLabel;
    private JProgressBar _totalProgress;
    private JButton _closeButton;

    private JFrame _mainframe;

    /**
     * Initializing the UI
     *
     * @param list the list of tasks
     */
    public WeekPlanUI(TaskList list) {
        initializeVariables(list);
        createElements();
        createWindow();
        initializeWindow();
    }

    /**
     * Initializes variables
     */
    private void initializeVariables(TaskList list) {
        _taskList = list;
        _taskTitleButtons = new ArrayList<>();
        _taskProgressBars = new ArrayList<>();
        _addTimeButtons = new ArrayList<>();

        _menuBar = new JMenuBar();
        _createItem = new JMenuItem("New Task", 'N');
        _deleteItem = new JMenuItem("Delete Tasks", 'D');
        _optionsItem = new JMenuItem("Options", 'P');
        _closeItem = new JMenuItem("Close", 'E');

        _undoItem = new JMenuItem("Undo", 'U');
        _undoItem.setEnabled(false);
        _redoItem = new JMenuItem("Redo", 'R');
        _redoItem.setEnabled(false);

        _itemPanel = new JPanel();

        _totalLabel = new JLabel("Error");
        _closeButton = new JButton("Close");

        _mainframe = new JFrame();
        _totalProgress = new JProgressBar();
    }

    // ----------------------- Creation of UI -----------------------------------

    /**
     * Creates the elements within the UI
     */
    private void createElements() {
        createMenuBar();
        addTaskElements();
        createTotalProgressBar();
    }

    private void createMenuBar() {
        JMenu menu = new JMenu("File");
        menu.setMnemonic('F');
        menu.add(_createItem);
        menu.add(_deleteItem);
        menu.add(_optionsItem);
        menu.add(_closeItem);
        _menuBar.add(menu);

        JMenu options = new JMenu("Options");
        options.setMnemonic('O');
        options.add(_undoItem);
        options.add(_redoItem);
        _menuBar.add(options);
    }

    /**
     * Creates the three elements for each task and adds them to their list
     */
    private void addTaskElements() {
        for (Task t : _taskList.getList()) {
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
     * Builds the JFrame
     */
    private void createWindow() {
        _mainframe.setTitle(WINDOWTITLE);
        _mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //closes window
        _mainframe.setSize(WINDOWWIDTH, WINDOWHEIGHT);
        _mainframe.setJMenuBar(_menuBar);
        _mainframe.setLayout(new BorderLayout());

        _mainframe.setLocationRelativeTo(null);

    }

    private void initializeWindow() {
        _itemPanel.setLayout(new GridLayout(0, 1));
        buildItemPanel();
        JScrollPane scrollPane = new JScrollPane(_itemPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(VERTICALSCROLLSPEED);
        _mainframe.add(scrollPane);

        _mainframe.add(buildBotPanel(), BorderLayout.PAGE_END);
        //_mainframe.setResizable(false);
        _mainframe.setVisible(true);
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
        panel.setPreferredSize(new Dimension(120, TASKHEIGHT));
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
        panel.setPreferredSize(new Dimension(150, TASKHEIGHT));
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
        panel.setPreferredSize(new Dimension(70, TASKHEIGHT));
        panel.add(_addTimeButtons.get(i));
        return panel;
    }


    /**
     * Builds the bottom JPanel of the _mainframe which consists of one row consisting of a JLabel,
     * a JTextField for the total progress and a JButton to close the program
     *
     * @return bottom JPanel
     */
    private JPanel buildBotPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel subPanel1 = new JPanel();
        subPanel1.setLayout(new GridBagLayout());
        subPanel1.setPreferredSize(new Dimension(120, TASKHEIGHT));
        subPanel1.add(_totalLabel);

        JPanel subPanel2 = new JPanel();
        subPanel2.setLayout(new GridBagLayout());
        subPanel2.setPreferredSize(new Dimension(150, TASKHEIGHT));
        subPanel2.add(_totalProgress);

        JPanel subPanel3 = new JPanel();
        subPanel3.setLayout(new GridBagLayout());
        subPanel3.setPreferredSize(new Dimension(70, TASKHEIGHT));
        subPanel3.add(_closeButton);

        panel.add(subPanel1);
        panel.add(subPanel2);
        panel.add(subPanel3);

        updateTotal();
        return panel;
    }

    // -------- Creation of UI: End ----------- Getters: Start ---------------------

    public JMenuItem getCreateItem() {
        return _createItem;
    }

    public JMenuItem getDeleteItem() {
        return _deleteItem;
    }

    public JMenuItem getOptionsItem() {
        return _optionsItem;
    }

    public JMenuItem getCloseItem() {
        return _closeItem;
    }

    public JMenuItem getUndoItem() {
        return _undoItem;
    }

    public JMenuItem getRedoItem() {
        return _redoItem;
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
     * GetA for _closeButton
     *
     * @return _closeButton
     */
    public JButton getCloseButton() {
        return _closeButton;
    }

    /**
     * GetA for the _mainframe
     *
     * @return _mainframe
     */
    public JFrame getMainframe() {
        return _mainframe;
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
     * A change of progress is signaled to the UI and shown appropriately
     *
     * @param task: task in which the progress changed
     */
    public void updateProgress(Task task) {
        colorProgressBar(task);
        updateTotal();
    }

    /**
     * Updates the UI when a certain task is changed
     *
     * @param task: task which was edited
     */
    public void updateTask(Task task) {
        updateTaskName(task);
        updateTargetTime(task);
        colorProgressBar(task);
        updateTotal();
    }

    /**
     * Updates the displayed title of the given task
     *
     * @param task: the task which title might have changed
     */
    private void updateTaskName(Task task) {
        int taskNumber = _taskList.indexOf(task);
        _taskTitleButtons.get(taskNumber).setText(task.getTitle());
    }

    /**
     * Updates the target time of the given task
     *
     * @param task: the task which goal might have changed
     */
    private void updateTargetTime(Task task) {
        int taskNumber = _taskList.indexOf(task);
        _taskProgressBars.get(taskNumber).setMaximum(task.getTargetTime());
    }

    /**
     * Colors the JProgressBars with the data provided from the task
     *
     * @param task task corresponding the JProgressBar
     */
    private void colorProgressBar(Task task) {
        JProgressBar bar = _taskProgressBars.get(_taskList.indexOf(task));
        bar.setValue(task.getProgress());
    }

    /**
     * Updates the total area consisting of the JLabel and the JProgressbar
     */
    private void updateTotal() {
        updateTotalLabel();
        updateTotalBar();
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
    private void updateTotalBar() {
        _totalProgress.setValue(_taskList.getTotalProgressInPercent());
    }

    public void enableUndoButton() {
        _undoItem.setEnabled(true);
    }

    public void enableRedoButton() {
        _redoItem.setEnabled(true);
    }

    public void disableUndoButton() {
        _undoItem.setEnabled(false);
    }

    public void disableRedoButton() {
        _redoItem.setEnabled(false);
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

    /**
     * Closes the UI
     */
    public void close() {
        _mainframe.dispose();
    }
}