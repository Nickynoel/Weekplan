package FrontEnd.Weekplan;

import BackEnd.TaskList.Task.Task;
import BackEnd.TaskList.TaskList;
import FrontEnd.Weekplan.SubComponents.MenuBarUI;
import FrontEnd.Weekplan.SubComponents.TaskPanelUI;
import FrontEnd.Weekplan.SubComponents.TotalPanelUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * UI of the class WeekPlan
 * Renewed Version of WeekPlanUI
 */
public class WeekPlanUI
{
    public static final int TASKHEIGHT = 40;

    private final int VERTICALSCROLLSPEED = 30;
    private final int WINDOWWIDTH = 400;
    private final int WINDOWHEIGHT = 370;
    private final String WINDOWTITLE = "Nicky's Java Program";

    private MenuBarUI _menuBar;
    private TaskPanelUI _itemPanel;
    private TotalPanelUI _totalPanel;

    private JFrame _mainframe;

    /**
     * Initializing the UI
     *
     * @param list the list of tasks
     */
    public WeekPlanUI(TaskList list) {
        initializeVariables(list);
        createWindow();
        initializeWindow();
    }

    /**
     * Initializes variables
     */
    private void initializeVariables(TaskList list) {
        _menuBar = new MenuBarUI();
        _itemPanel = new TaskPanelUI(list);
        _totalPanel = new TotalPanelUI(list);

        _mainframe = new JFrame();
    }

    // ----------------------- Creation of UI -----------------------------------

    /**
     * Builds the JFrame
     */
    private void createWindow() {
        _mainframe.setTitle(WINDOWTITLE);
        _mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //closes window
        _mainframe.setSize(WINDOWWIDTH, WINDOWHEIGHT);
        _mainframe.setJMenuBar(_menuBar.getMenuBar());
        _mainframe.setLayout(new BorderLayout());

        _mainframe.setLocationRelativeTo(null);

    }

    private void initializeWindow() {
        JScrollPane scrollPane = new JScrollPane(_itemPanel.getPanel());
        scrollPane.getVerticalScrollBar().setUnitIncrement(VERTICALSCROLLSPEED);
        _mainframe.add(scrollPane);

        _mainframe.add(_totalPanel.getPanel(), BorderLayout.PAGE_END);
        //_mainframe.setResizable(false);
        _mainframe.setVisible(true);
    }

    // -------- Creation of UI: End ----------- Getters: Start ---------------------

    public JMenuItem getCreateItem() {
        return _menuBar.getCreateItem();
    }

    public JMenuItem getDeleteItem() {
        return _menuBar.getDeleteItem();
    }

    public JMenuItem getOptionsItem() {
        return _menuBar.getOptionsItem();
    }

    public JMenuItem getCloseItem() {
        return _menuBar.getCloseItem();
    }

    public JMenuItem getUndoItem() {
        return _menuBar.getUndoItem();
    }

    public JMenuItem getRedoItem() {
        return _menuBar.getRedoItem();
    }

    /**
     * Returns the list of Title-Buttons
     *
     * @return a list of all Title-Buttons
     */
    public List<JButton> getTitleButtonList() {
        return _itemPanel.getTitleButtonList();
    }

    /**
     * Returns the list of JButtons for adding progress
     *
     * @return the list of JButtons for adding progress
     */
    public List<JButton> getAddButtonlist() {
        return _itemPanel.getAddButtonlist();
    }

    /**
     * Opens the task menu of a given (usually newly generated) task
     *
     * @param task Task of which the menu is opened
     */
    public void openTaskMenu(Task task) {
        _itemPanel.openTaskMenu(task);
    }

    /**
     * GetA for _closeButton
     *
     * @return _closeButton
     */
    public JButton getCloseButton() {
        return _totalPanel.getCloseButton();
    }

    /**
     * GetA for the _mainframe
     *
     * @return _mainframe
     */
    public JFrame getMainframe() {
        return _mainframe;
    }

    // ---------------- Getters: End ------------- Setters/Updaters: Start --------------------

    /**
     * A change of progress is signaled to the UI and shown appropriately
     *
     * @param task: task in which the progress changed
     */
    public void updateProgress(Task task) {
        colorProgressBar(task);
        updateTotalPanel();
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
        updateTotalPanel();
    }

    /**
     * Updates the displayed title of the given task
     *
     * @param task: the task which title might have changed
     */
    private void updateTaskName(Task task) {
        _itemPanel.updateTaskName(task);
    }

    /**
     * Updates the target time of the given task
     *
     * @param task: the task which goal might have changed
     */
    private void updateTargetTime(Task task) {
        _itemPanel.updateTargetTime(task);
    }

    /**
     * Colors the JProgressBars with the data provided from the task
     *
     * @param task task corresponding the JProgressBar
     */
    private void colorProgressBar(Task task) {
        _itemPanel.colorProgressBar(task);
    }

    /**
     * Updates the total area consisting of the JLabel and the JProgressbar
     */
    private void updateTotalPanel() {
        _totalPanel.updateTotalPanel();
    }

    public void enableUndoButton() {
        _menuBar.enableUndoButton();
    }

    public void enableRedoButton() {
        _menuBar.enableRedoButton();
    }

    public void disableUndoButton() {
        _menuBar.disableUndoButton();
    }

    public void disableRedoButton() {
        _menuBar.disableRedoButton();
    }

    /**
     * Reloads the tasks shown in the UI
     */
    public void refreshTaskDisplay() {
        _itemPanel.refreshTaskDisplay();
    }

    /**
     * Closes the UI
     */
    public void close() {
        _mainframe.dispose();
    }
}