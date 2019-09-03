import java.util.*;
class Task{
  static int PID = 0;
  public int pid;
  public int jobNo;
  private double arrivalTime;
  private double executionTime;
  private double period;
  private double relativeDeadline;
  private boolean scheduled;
  private double remainingTime;
  Task(double at, double period, double et, double rD){
    this.arrivalTime = at;
    this.executionTime = et;
    this.period = period;
    this.relativeDeadline = rD;
    this.pid = ++Task.PID;
    this.scheduled = false;
    this.jobNo = 0;
  }

  public boolean execute(){
    this.remainingTime -= 0.5;
    if(this.remainingTime == 0 ) this.scheduled = false;
    return this.scheduled;
  }

  public double getRelativeDeadline(){
    return this.relativeDeadline;
  }
  static void checkArivalTime(ArrayList<Task> tasks, double currentTime, ArrayList<Task> queue){
    for(int i = 0; i < tasks.size(); i++){
      Task t = tasks.get(i);
      if(currentTime >= t.arrivalTime && (currentTime - t.arrivalTime) % t.period == 0 && !t.scheduled){
        System.out.println(currentTime + " : " +  t.pid);
        t.scheduled = true;
        t.remainingTime = t.executionTime;
        t.jobNo ++; 
        queue.add(t);
      }
    }
  }
}

class TaskCompare implements Comparator<Task>{
  @Override
  public int compare(Task t1, Task t2) {
    if(t1.getRelativeDeadline() > t2.getRelativeDeadline()){
      return 1;
    } else {
      return -1;
    }
  }
}

public class AbsoluteDeadline{
  private static double currentTime = -0.5;
  public static void main(String[] args){
    ArrayList<Task> tasks = new ArrayList<Task>();
    ArrayList<Task> queue = new ArrayList<Task>();
    tasks.add(new Task(50, 50, 75, 100));
    tasks.add(new Task(0, 62.5, 10, 20));
    tasks.add(new Task(0, 125, 25, 50));
    while (currentTime <= 100 - 0.5) {
      currentTime+= .5;
      Task.checkArivalTime(tasks, currentTime, queue);
      if(queue.isEmpty()) continue;
      Collections.sort(queue, new TaskCompare());
      System.out.println("Executing " + queue.get(0).pid + " job " + queue.get(0).jobNo + " at time " + currentTime);
      boolean pending = queue.get(0).execute();
      if(!pending) queue.remove(0);
    }
  }

  public static void printQueue(ArrayList<Task> queue, double currentTime){
    for(int i = 0; i < queue.size(); i++){
      System.out.println("Queue : " + queue.get(i).pid + " : currentTime : " + currentTime);
    }
  }
} 
