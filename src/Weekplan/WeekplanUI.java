package Weekplan;

import TopicList.Topic.Topic;
import TopicList.TopicList;

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
    private TopicList _topicList;
    
    private List<JButton> _topicTitles;
    private List<JProgressBar> _topicProgressbars;
    private List<JButton> _addButtons;
    
    private JLabel _totalLabel;
    private JProgressBar _totalProgress;
    private JButton _saveButton;
    private JButton _optionButton;
    private JButton _closeButton;
    
    private JFrame _mainframe;
    
    /**
     * Initializing the UI
     *
     * @param list the list of topics
     */
    public WeekplanUI(TopicList list)
    {
        _topicList = list;
        createTitles();
        createProgressbars();
        createButtons();
        createBottomElements();
        
        createWindow();
        initializeWindow();
    }
    
    /**
     * Creates the titles as borderless JButtons with the names of the topics
     * as well as the _totalLabel
     */
    private void createTitles()
    {
        _topicTitles = new ArrayList<>();
        
        for (Topic t : _topicList.getList())
        {
            JButton tmp = new JButton(t.getTitle());
            tmp.setBorderPainted(false);
            tmp.setContentAreaFilled(false);
            _topicTitles.add(tmp);
        }
    }
    
    /**
     * Creates the JProgressbars with the data of the topics
     */
    private void createProgressbars()
    {
        _topicProgressbars = new ArrayList<>();
        
        for (Topic t : _topicList.getList())
        {
            JProgressBar bar = new JProgressBar(0, t.getGoalTime());
            bar.setValue(t.getProgress());
            bar.setStringPainted(true);
            _topicProgressbars.add(bar);
        }
    }
    
    /**
     * Creates the remaining JButtons of the UI
     * AddButton: A JButton for adding a value for every topic
     * SaveButton: A JButton to save the progress
     * OptionButton: A JButton to go to the options
     */
    private void createButtons()
    {
        _addButtons = new ArrayList<>();
        
        for (Topic t : _topicList.getList())
        {
            JButton button = new JButton("add");
            _addButtons.add(button);
        }
        _saveButton = new JButton("Save");
        
        _optionButton = new JButton("Options");
        _closeButton = new JButton("Close");
    }
    
    /**
     * Creates the elements used in the bottom part of the JDialog
     */
    private void createBottomElements()
    {
        _totalLabel = new JLabel("Error");
        
        _totalProgress = new JProgressBar(0, _topicList.size() * 100);
        _totalProgress.setStringPainted(true);
    }
    
    /**
     * Builds the JFrame
     */
    private void createWindow()
    {
        _mainframe = new JFrame();
        _mainframe.setTitle("Weekplan");
        _mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //closes window
        _mainframe.setSize(400, 350);
        _mainframe.setLayout(new BorderLayout());
        
        _mainframe.setLocationRelativeTo(null);
        //_mainframe.setResizable(false);
        _mainframe.setVisible(true);
    }
    
    private void initializeWindow()
    {
        JPanel centerPanel = buildCenterPanel();
        JScrollPane scrollpane = new JScrollPane(centerPanel);
        scrollpane.getVerticalScrollBar().setUnitIncrement(15); //increases vertical scrollspeed
        _mainframe.add(scrollpane);
        _mainframe.add(buildBotPanel(), BorderLayout.PAGE_END);
    }
    
    /**
     * Builds the central JPanel of the _mainframe
     * consisting of rows consisting of a JLabel, a JTextfield and a JButton representing a Topic
     *
     * @return central JPanel
     */
    private JPanel buildCenterPanel()
    {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        centerPanel.add(buildTitlePanel());
        centerPanel.add(buildProgressbarPanel());
        centerPanel.add(buildButtonPanel());
        
        return centerPanel;
    }
    
    /**
     * Builds vertical JPanel with all titles
     *
     * @return Left part of centerPanel
     */
    private JPanel buildTitlePanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        
        for (int i = 0; i < _topicList.size(); i++)
        {
            JPanel tmp = new JPanel();
            tmp.setLayout(new GridBagLayout()); //makes sure that the labels are in the center
            tmp.setPreferredSize(new Dimension(120, 40)); //makes sure all panels have the same height
            tmp.add(_topicTitles.get(i));
            panel.add(tmp);
        }
        return panel;
    }
    
    /**
     * Builds vertical JPanel with all JLabels
     *
     * @return Left part of centerPanel
     */
    private JPanel buildProgressbarPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        
        for (int i = 0; i < _topicList.size(); i++)
        {
            JPanel tmp = new JPanel();
            tmp.setLayout(new GridBagLayout()); //makes sure that the progressbars are in the center
            tmp.setPreferredSize(new Dimension(150, 40)); //makes sure all panels have the same height
            tmp.add(_topicProgressbars.get(i));
            panel.add(tmp);
        }
        return panel;
    }
    
    /**
     * Builds vertical JPanel with all JLabels
     *
     * @return Left part of centerPanel
     */
    private JPanel buildButtonPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        
        for (int i = 0; i < _topicList.size(); i++)
        {
            JPanel tmp = new JPanel();
            tmp.setLayout(new GridBagLayout()); //makes sure that the buttons are in the center
            tmp.setPreferredSize(new Dimension(70, 40)); //makes sure all panels have the same height
            tmp.add(_addButtons.get(i));
            panel.add(tmp);
        }
        return panel;
    }
    
    
    
    /**
     * Builds the bottom JPanel of the _mainframe
     * consisting of one row consisting of a JLabel, a JTextfield and a JButton for the total
     * and a row of additional JButtons: for now just the _optionButton
     *
     * @return bottom JPanel
     */
    private JPanel buildBotPanel()
    {
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new GridLayout(2, 1));
        //upper half
        mainpanel.add(buildUpperBotPanel());
        //lower half
        mainpanel.add(buildLowerBotPanel());
    
        updateTotalLabel();
        colorTotalBar();
        
        return mainpanel;
    }
    
    /**
     * Builds the upper part of the BotPanel consisting of the total info
     * @return upper part of bottom JPanel
     */
    private JPanel buildUpperBotPanel()
    {
        JPanel toppanel = new JPanel();
        toppanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(120, 40));
        panel1.add(_totalLabel);
        JPanel panel2 = new JPanel();
        panel2.add(_totalProgress);
        JPanel panel3 = new JPanel();
        panel3.add(_saveButton);
        toppanel.add(panel1);
        toppanel.add(panel2);
        toppanel.add(panel3);
        return toppanel;
    }
    
    /**
     * Builds the lower part of the BotPanel consisting of remaining JButtons
     * @return lower part of the bottom JPanel
     */
    private JPanel buildLowerBotPanel()
    {
        JPanel botpanel = new JPanel();
        botpanel.setLayout(new GridLayout(1, 3));
        JPanel panel4 = new JPanel();
        panel4.add(_optionButton);
        botpanel.add(panel4);
        JPanel panel5 = new JPanel();
        panel5.add(_closeButton);
        botpanel.add(panel5);
        return botpanel;
    }
    
    /**
     * Updates the text on the _totalLabel
     */
    private void updateTotalLabel()
    {
        String sign = "";
        int progressHours = _topicList.getTotalProgress() / 60;
        int progressMinutes = (_topicList.getTotalProgress() / 6) % 10;
        
        if (progressHours < 0 || progressMinutes < 0) //If the progress is negative, create an extra sign to display properly
        {
            progressMinutes = Math.abs(progressMinutes);
            progressHours = Math.abs(progressHours);
            sign = "-";
        }
        
        int goalHours = _topicList.getTotalGoaltime() / 60;
        int goalMinutes = (_topicList.getTotalGoaltime() / 6) % 10;
        if (progressMinutes == 0) //if progress is ",0" then ignore that
        {
            _totalLabel.setText(sign + progressHours + " of " + goalHours + "," + goalMinutes + " hours");
        }
        else
        {
            _totalLabel.setText(sign + progressHours + "," + progressMinutes + " of " + goalHours + "," + goalMinutes + " hours");
        }
    }
    
    /**
     * Colors the final JProgressbar, that takes data from all topics
     */
    private void colorTotalBar()
    {
        _totalProgress.setValue(_topicList.getTotalPercentProgress());
    }
    

    /**
     * Colors the JProgressbars with the data provided from the topic
     *
     * @param topic topic corresponding the JProgressbar
     */
    public void colorBar(Topic topic)
    {
        JProgressBar bar = _topicProgressbars.get(_topicList.indexOf(topic));
        bar.setValue(topic.getProgress());
        updateTotal();
    }
    
    /**
     * Updates the total area consisting of the JLabel and the JProgressbar
     */
    private void updateTotal()
    {
        updateTotalLabel();
        colorTotalBar();
    }
    
    /**
     * Returns the list of JLabels
     *
     * @return _topicLabels
     */
    public List<JButton> getTitleButtonList()
    {
        return _topicTitles;
    }
    
    /**
     * Returns the list of JButtons for adding values
     *
     * @return _topicButtons
     */
    public List<JButton> getAddButtonlist()
    {
        return _addButtons;
    }
    
    /**
     * Returns the JButton for saving
     *
     * @return _saveButton
     */
    public JButton getSaveButton()
    {
        return _saveButton;
    }
    
    /**
     * Updates the displayed title of the given topic
     *
     * @param topic: the topic which title might have changed
     */
    public void updateTopicName(Topic topic)
    {
        int topicNumber = _topicList.indexOf(topic);
        _topicTitles.get(topicNumber).setText(topic.getTitle());
    }
    
    /**
     * Updates the goal of the given topic
     *
     * @param topic: the topic which goal might have changed
     */
    public void updateGoal(Topic topic)
    {
        int topicNumber = _topicList.indexOf(topic);
        _topicProgressbars.get(topicNumber).setMaximum(topic.getGoalTime());
    }
    
    /**
     * GetA for _optionButton
     *
     * @return _optionButton
     */
    public JButton getOptionButton()
    {
        return _optionButton;
    }
    
    /**
     * Closes the UI
     */
    public void close()
    {
        _mainframe.dispose();
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
     * GetA for the _mainframe
     *
     * @return _mainframe
     */
    public JFrame getMainframe()
    {
        return _mainframe;
    }
    
    
    /**
     * Opens a newly created topic standardized with the title "New"
     * by opening the first entry of "New" in the _topicList
     */
    public void openNewTopicMenu()
    {
        int index = _topicList.indexOf("New");
        _topicTitles.get(index).doClick();
    }
}