package TopicList.Topic;

import java.util.Comparator;

/**
 * A comparator that sorts topics given their percentual progress
 */

public class TopicPercentComperator implements Comparator
{
    /**
     * The actual comparation method
     * @param o1: the first topic
     * @param o2: the second topic
     * @return -1,0,1 to show how the two topics compare via getPercentProgress
     */
    @Override
    public int compare(Object o1, Object o2)
    {
        try
        {
            Task t1 = (Task) o1;
            Task t2 = (Task) o2;
            if (t1.getPercentProgress()>t2.getPercentProgress())
            {
                return 1;
            }
            else if (t1.getPercentProgress()<t2.getPercentProgress())
            {
                return -1;
            }
            
        }
        catch (ClassCastException e)
        {
            javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(),"This comparator is only meant to be used for comparison between two elements of the class Topic");
        }
        return 0;
    }
}
