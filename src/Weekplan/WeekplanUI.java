package Weekplan;

import TaskList.Task.Task;
import TaskList.TaskList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UI of the class Weekplan
 * Renewed Version of WeekplanUI
 */
public class WeekplanUI
{
    private final int VERTICALSCROLLSPEED = 30;
    private final int WINDOWWIDTH = 400;
    private final int WINDOWHEIGHT = 370;
    private final String WINDOWTITLE = "Weekplan";
    private final int TASKHEIGHT = 40;

    private TaskList _taskList;

    private List<JButton> _taskTitleButtons;
    private List<JProgressBar> _taskProgressBars;
    private List<JButton> _addTimeButtons;

    private JMenuBar _menuBar;
    private JMenuItem _createItem;
    private JMenuItem _deleteItem;
    private JMenuItem _optionsItem;
    private JMenuItem _closeItem;

    private JLabel _totalLabel;
    private JProgressBar _totalProgress;
//    private JButton _saveButton;
//    private JButton _optionButton;
    private JButton _closeButton;

    private JFrame _mainframe;

    /**
     * Initializing the UI
     *
     * @param list the list of tasks
     */
    public WeekplanUI(TaskList list)
    {
        initializeVariables(list);
        createElements();
        createWindow();
        initializeWindow();
    }

    /**
     * Initializes variables
     */
    private void initializeVariables(TaskList list)
    {
        _taskList = list;
        _taskTitleButtons = new ArrayList<>();
        _taskProgressBars = new ArrayList<>();
        _addTimeButtons = new ArrayList<>();

        _menuBar = new JMenuBar();
        _createItem = new JMenuItem("New Task", 'N');
        _deleteItem = new JMenuItem("Delete Tasks", 'D');
        _optionsItem = new JMenuItem("Options", 'O');
        _closeItem = new JMenuItem("Close", 'E');

//        _saveButton = new JButton("Save");
//        _optionButton = new JButton("Options");
        _closeButton = new JButton("Close");

        _totalLabel = new JLabel("Error");
        _mainframe = new JFrame();
        _totalProgress = new JProgressBar();
    }

    /**
     * Creates the elements within the UI
     */
    private void createElements()
    {
        createMenuBar();
        createTaskElements();
        createTotalProgressBar();
    }

    private void createMenuBar()
    {
        JMenu menu = new JMenu("File");
        menu.setMnemonic('F');
        menu.add(_createItem);
        menu.add(_deleteItem);
        menu.add(_optionsItem);
        menu.add(_closeItem);
        _menuBar.add(menu);
    }

    /**
     * Creates the three elements for each task and adds them to their list
     */
    private void createTaskElements()
    {
        for (Task t:  _taskList.getList())
        {
            addTaskTitleButton(t);
            addTaskProgressBar(t);
            addAddButton();
        }
    }

    /**
     * Creates a title as borderless JButton with the name of the task
     */
    private void addTaskTitleButton(Task task)
    {
        JButton tmp = new JButton(task.getTitle());
        tmp.setBorderPainted(false);
        tmp.setContentAreaFilled(false);
        _taskTitleButtons.add(tmp);
    }
    /**
     * Creates the JProgressBar with the data of the task
     */
    private void addTaskProgressBar(Task task)
    {
        JProgressBar bar = new JProgressBar(0, task.getTargetTime());
        bar.setValue(task.getProgress());
        bar.setStringPainted(true);
        _taskProgressBars.add(bar);
    }

    /**
     * Creates the remaining JButton used to add progress to the task in the UI
     */
    private void addAddButton()
    {
        JButton button = new JButton("add");
        _addTimeButtons.add(button);
    }

    /**
     * Creates the elements used in the bottom part of the JDialog
     * Maximum cant be 100, cause the progress has to be of type int in JProgressBar
     */
    private void createTotalProgressBar()
    {
        _totalProgress.setMinimum(0);
        _totalProgress.setMaximum(100);
        _totalProgress.setStringPainted(true);
    }

    /**
     * Builds the JFrame
     */
    private void createWindow()
    {
        _mainframe.setTitle(WINDOWTITLE);
        _mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //closes window
        _mainframe.setSize(WINDOWWIDTH, WINDOWHEIGHT);
        _mainframe.setJMenuBar(_menuBar);
        _mainframe.setLayout(new BorderLayout());

        _mainframe.setLocationRelativeTo(null);

    }

    private void initializeWindow()
    {
        JPanel centerPanel = buildCenterPanel();
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(VERTICALSCROLLSPEED);
        _mainframe.add(scrollPane);

        _mainframe.add(buildBotPanel(), BorderLayout.PAGE_END);
        //_mainframe.setResizable(false);
        _mainframe.setVisible(true);
    }

    /**
     * Builds the central JPanel of the _mainframe
     * consisting of x rows consisting of a JLabel, a JTextField and a JButton representing a Task
     *
     * @return central JPanel
     */
    private JPanel buildCenterPanel()
    {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 1));

        for (int i = 0; i < _taskList.getSize(); i++)
        {
            JPanel taskPanel = new JPanel();
            //for vertical space in between
            taskPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            taskPanel.add(buildTitlePanel(i));
            taskPanel.add(buildProgressbarPanel(i));
            taskPanel.add(buildAddButtonPanel(i));
            centerPanel.add(taskPanel);
        }

        return centerPanel;
    }

    /**
     * Builds a JPanel with a title given by the Task's parameter i
     *
     * @param i Index of Task for which the panel is built
     * @return Left part of centerPanel
     */
    private JPanel buildTitlePanel(int i)
    {
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
    private JPanel buildProgressbarPanel(int i)
    {
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
    private JPanel buildAddButtonPanel(int i)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); //makes sure that the buttons are in the center
        panel.setPreferredSize(new Dimension(70, TASKHEIGHT));
        panel.add(_addTimeButtons.get(i));
        return panel;
    }


    /**
     * Builds the bottom JPanel of the _mainframe
     * consisting of one row consisting of a JLabel, a JTextField and a JButton for the total
     * and a row of additional JButtons: for now just the _optionButton
     *
     * @return bottom JPanel
     */
    private JPanel buildBotPanel()
    {
//        JPanel botPanel = new JPanel();
//        botPanel.setLayout(new GridLayout(1, 1));
        //upper half
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setPreferredSize(new Dimension(120, TASKHEIGHT));
        panel1.add(_totalLabel);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel2.setPreferredSize(new Dimension(150, TASKHEIGHT));
        panel2.add(_totalProgress);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        panel3.setPreferredSize(new Dimension(70, TASKHEIGHT));
        panel3.add(_closeButton);

        topPanel.add(panel1);
        topPanel.add(panel2);
        topPanel.add(panel3);

        updateTotal();

        return topPanel;
    }

    public JMenuItem getCreateItem()
    {
        return _createItem;
    }

    public JMenuItem getDeleteItem()
    {
        return _deleteItem;
    }

    public JMenuItem getOptionsItem()
    {
        return _optionsItem;
    }

    public JMenuItem getCloseItem()
    {
        return _closeItem;
    }

    /**
     * Returns the list of Title-Buttons
     *
     * @return a list of all Title-Buttons
     */
    public List<JButton> getTitleButtonList()
    {
        return _taskTitleButtons;
    }

    /**
     * Returns the list of JButtons for adding progress
     *
     * @return the list of JButtons for adding progress
     */
    public List<JButton> getAddButtonlist()
    {
        return _addTimeButtons;
    }

    /**
     * Updates the UI when a certain task is changed
     *
     * @param task: task which was edited
     */
    public void updateTask(Task task)
    {
        updateTaskName(task);
        updateTargetTime(task);
        colorProgressBar(task);
        updateTotal();
    }

    /**
     * A change of progress is signaled to the UI and shown appropriately
     *
     * @param task: task in which the progress changed
     */
    public void updateProgress(Task task)
    {
        colorProgressBar(task);
        updateTotal();
    }

    /**
     * Updates the displayed title of the given task
     *
     * @param task: the task which title might have changed
     */
    private void updateTaskName(Task task)
    {
        int taskNumber = _taskList.indexOf(task);
        _taskTitleButtons.get(taskNumber).setText(task.getTitle());
    }

    /**
     * Updates the target time of the given task
     *
     * @param task: the task which goal might have changed
     */
    private void updateTargetTime(Task task)
    {
        int taskNumber = _taskList.indexOf(task);
        _taskProgressBars.get(taskNumber).setMaximum(task.getTargetTime());
    }

    /**
     * Colors the JProgressBars with the data provided from the task
     *
     * @param task task corresponding the JProgressBar
     */
    private void colorProgressBar(Task task)
    {
        JProgressBar bar = _taskProgressBars.get(_taskList.indexOf(task));
        bar.setValue(task.getProgress());
    }

    /**
     * Updates the total area consisting of the JLabel and the JProgressbar
     */
    private void updateTotal()
    {
        updateTotalLabel();
        updateTotalBar();
    }

    /**
     * Updates the text on the _totalLabel
     */
    private void updateTotalLabel()
    {
        _totalLabel.setText(_taskList.getTotalProgressInText());
    }

    /**
     * Colors the final JProgressbar, that takes data from all tasks
     */
    private void updateTotalBar()
    {
        _totalProgress.setValue(_taskList.getTotalProgressInPercent());
    }

    /**
     * GetA for _closeButton
     *
     * @return _closeButton
     */
    public JButton getCloseButton()
    {
        return _closeButton;
    }

    /**
     * Closes the UI
     */
    public void close()
    {
        _mainframe.dispose();
    }

    /**
     * GetA for the _mainframe
     *
     * @return _mainframe
     */
    public JFrame getMainframe()
    {
        return _mainframe;
    }


    /**
     * Opens a newly created task standardized with the title "New"
     * by opening the first entry of Task.DEFAULTNAME in the _taskList
     */
    public void openNewTaskMenu()
    {
        int index = _taskList.indexOf(Task.DEFAULTNAME);
        _taskTitleButtons.get(index).doClick();
    }
}