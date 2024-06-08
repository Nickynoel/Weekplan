package TaskList.Task;

import java.util.Comparator;

/**
 * A comparator that sorts tasks by their percentual progress
 */

public class TaskComparatorByProgressInPercent implements Comparator
{
    /**
     * The actual comparison method
     * @param o1: the first task
     * @param o2: the second task
     * @return -1,0,1 to show how the two tasks compare via getPercentProgress
     */
    @Override
    public int compare(Object o1, Object o2)
    {
        try
        {
            Task t1 = (Task) o1;
            Task t2 = (Task) o2;
            if (t1.getProgressInPercent()>t2.getProgressInPercent())
            {
                return 1;
            }
            else if (t1.getProgressInPercent()<t2.getProgressInPercent())
            {
                return -1;
            }
            
        }
        catch (ClassCastException e)
        {
            javax.swing.JOptionPane.showMessageDialog
                    (new javax.swing.JFrame(),"This comparator is only meant to be used " +
                            "for comparison between two elements of the class Task");
        }
        return 0;
    }
}
