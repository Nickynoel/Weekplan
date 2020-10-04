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
    
    private JFrame _mainframe;
    private List<JButton> _topicTitels;
    private List<JProgressBar> _topicProgressbars;
    private List<JButton> _topicButtons;
    
    private JLabel _totalLabel;
    private JProgressBar _totalProgress;
    private JButton _saveButton;
    private JButton _optionButton;
    private JButton _closeButton;
    
    /**
     * Initializing the UI
     *
     * @param list the list of topics
     */
    public WeekplanUI(TopicList list)
    {
        _topicList = list;
        buildTitles();
        buildProgressbars();
        buildButtons();
        generateUI();
    }
    
    /**
     * Creates the titles as borderless JButtons with the names of the topics
     * as well as the _totalLabel
     */
    private void buildTitles()
    {
        _topicTitels = new ArrayList<>();
        
        for (Topic t : _topicList.getList())
        {
            JButton tmp = new JButton(t.getTitel());
            tmp.setBorderPainted(false);
            tmp.setContentAreaFilled(false);
            _topicTitels.add(tmp);
        }
        _totalLabel = new JLabel("Error");
        updateTotalLabel();
    }
    
    /**
     * Creates the JProgressbars with the data of the topics
     */
    private void buildProgressbars()
    {
        _topicProgressbars = new ArrayList<>();
        
        for (Topic t : _topicList.getList())
        {
            JProgressBar bar = new JProgressBar(0, t.getLength());
            bar.setValue(t.getProgress());
            bar.setStringPainted(true);
            _topicProgressbars.add(bar);
        }
        
        _totalProgress = new JProgressBar(0, _topicList.size() * 100);
        _totalProgress.setStringPainted(true);
        colorTotalBar();
    }
    
    /**
     * Creates the remaining JButtons of the UI
     * AddButton: A JButton for adding a value for every topic
     * SaveButton: A JButton to save the progress
     * OptionButton: A JButton to go to the options
     */
    private void buildButtons()
    {
        _topicButtons = new ArrayList<>();
        
        for (Topic t : _topicList.getList())
        {
            JButton button = new JButton("add");
            _topicButtons.add(button);
        }
        _saveButton = new JButton("Save");
        
        _optionButton = new JButton("Options");
        _closeButton = new JButton("Close");
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
     * Colors the final JProgressbar, that takes data from all topics
     */
    private void colorTotalBar()
    {
        _totalProgress.setValue(_topicList.getTotalPercentProgress());
    }
    
    /**
     * Builds the JFrame
     */
    private void generateUI()
    {
        _mainframe = new JFrame();
        _mainframe.setTitle("Weekplan");
        _mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //closes window
        _mainframe.setSize(400, 350);
        _mainframe.setLayout(new BorderLayout());
        
        JPanel centerPanel = buildCenterPanel();
        JScrollPane scrollpane = new JScrollPane(centerPanel);
        scrollpane.getVerticalScrollBar().setUnitIncrement(15); //increases vertical scrollspeed
        _mainframe.add(scrollpane);
        _mainframe.add(buildBotPanel(), BorderLayout.PAGE_END);
        
        _mainframe.setLocationRelativeTo(null);
        //_mainframe.setResizable(false);
        _mainframe.setVisible(true);
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
            tmp.add(_topicTitels.get(i));
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
            tmp.add(_topicButtons.get(i));
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
        mainpanel.add(toppanel);
        //lower half
        JPanel botpanel = new JPanel();
        botpanel.setLayout(new GridLayout(1, 3));
        JPanel panel4 = new JPanel();
        panel4.add(_optionButton);
        botpanel.add(panel4);
        JPanel panel5 = new JPanel();
        panel5.add(_closeButton);
        botpanel.add(panel5);
        mainpanel.add(botpanel);
        
        return mainpanel;
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
     * Returns the list of JLabels
     *
     * @return _topicLabels
     */
    public List<JButton> getTitleButtonList()
    {
        return _topicTitels;
    }
    
    /**
     * Returns the list of JButtons for adding values
     *
     * @return _topicButtons
     */
    public List<JButton> getButtonlist()
    {
        return _topicButtons;
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
        _topicTitels.get(topicNumber).setText(topic.getTitel());
    }
    
    /**
     * Updates the goal of the given topic
     *
     * @param topic: the topic which goal might have changed
     */
    public void updateGoal(Topic topic)
    {
        int topicNumber = _topicList.indexOf(topic);
        _topicProgressbars.get(topicNumber).setMaximum(topic.getLength());
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
    
    public void openNewTopicMenu()
    {
        int index = _topicList.indexOf("New");
        _topicTitels.get(index).doClick();
    }
}