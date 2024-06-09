package TaskList.Task;

import java.util.Comparator;

/**
 * A comparator that sorts tasks by their percentual progress
 */

public class TaskComparatorByProgressInPercent implements Comparator<Task>
{
    /**
     * The actual comparison method
     * @param t1: the first task
     * @param t2: the second task
     * @return -1,0,1 to show how the two tasks compare via getPercentProgress
     */
    @Override
    public int compare(Task t1, Task t2)
    {
        if (t1.getProgressInPercent()>t2.getProgressInPercent())
        {
            return 1;
        }
        else if (t1.getProgressInPercent()<t2.getProgressInPercent())
        {
            return -1;
        }
        return 0;
    }
}
