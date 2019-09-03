import java.util.*;

class Task{
  static int currentTime;
  static int PID = 0;
  private int aT;
  public int eT;  
  private int period;  
  private int relativeDeadline;
  public int pid;
  public int jobNo;

  Task(int aT, int eT, int period, int relativeDeadline){
    this.aT = aT;
    this.eT = eT;
    this.period = period;
    this.relativeDeadline = relativeDeadline;
    this.pid = ++ Task.PID;
    this.jobNo = 0;
  }

  int execute(){
    return this.eT;
  }

  int getAbsoluteDeadline(){
    return this.relativeDeadline + Task.currentTime;
  }

  static int majorCycle(ArrayList<Task> tasks){
    int[] arr = new int[tasks.size()];
    for(int i = 0; i < tasks.size(); i++){
      arr[i] = tasks.get(i).period;
    }
    return Utils.lcm(arr);
  }

  static int minorCycle(int majorCycle, ArrayList<Task> tasks){
    int lowerLimit = 0, upperLimit;
    ArrayList<Integer> factors = new ArrayList<Integer>();
    ArrayList<Integer> validFactors = new ArrayList<Integer>();
    ArrayList<Integer> validFrames = new ArrayList<Integer>();
    int[] executionTimes = new int[tasks.size()];
    int[] deadlines = new int[tasks.size()];
    for(int i = 0; i < tasks.size(); i++){
      executionTimes[i] = tasks.get(i).eT;
      deadlines[i] = tasks.get(i).relativeDeadline;
    }
    lowerLimit = Utils.max(executionTimes);
    upperLimit = Utils.min(deadlines);
    factors = Utils.factors(majorCycle);
    for(int i=0; i < factors.size(); i++) if (factors.get(i) >= lowerLimit && factors.get(i) <= upperLimit) validFactors.add(factors.get(i));
    for(int i=0; i < validFactors.size(); i++){
      boolean ok = true;
      int f = validFactors.get(i);
      for(int j = 0; j < tasks.size(); j++){
        Task t = tasks.get(j);
        if(ok && (2*f - Utils.gcd(f, t.period)) > t.relativeDeadline) ok = false;
      }
      if(ok) validFrames.add(f);
    }
    if(validFrames.size() == 0) return -1;
    return validFrames.get(validFrames.size() - 1);
  }

  static void checkArrivalTime(ArrayList<Task> tasks, ArrayList<Task> queue, int majorCycle){
    for(int i = 0, len = tasks.size(); i < len; i++){
      Task t = tasks.get(i);
      if(Task.currentTime >= (t.jobNo * t.period + t.aT) && majorCycle / t.period >= (t.jobNo + 1)){
        queue.add(t);
        t.jobNo ++;
      }
    }
  }
}

class Utils{
  static ArrayList<Integer> factors(int number){
    ArrayList<Integer> s = new ArrayList<Integer>();
    for(int i = 1; i < number/2; i++){
      if(number%i == 0){
        s.add(i);
      }
    }
    return s;
  }
  
  static int max(int []numbers){
    int max = -999;
    for(int i =0; i < numbers.length; i++){
      max = max < numbers[i] ? numbers[i] : max;
    }
    return max;
  }

  static int min(int []numbers){
    int min = 1000000;
    for(int i =0; i < numbers.length; i++){
      min = min > numbers[i] ? numbers[i] : min;
    }
    return min;
  }

  static int gcd(int a, int b){
    if(b == 0) return a;
    else return gcd(b, a % b);
  }

  static int lcm(int []element_array){
    int lcm_of_array_elements = 1;
    int divisor = 2;         
    while (true) { 
      int counter = 0; 
      boolean divisible = false;        
      for (int i = 0; i < element_array.length; i++) {
        if (element_array[i] == 0) { 
          return 0; 
        } 
        else if (element_array[i] < 0) { 
          element_array[i] = element_array[i] * (-1); 
        } 
        if (element_array[i] == 1) { 
          counter++; 
        }
        if (element_array[i] % divisor == 0) { 
          divisible = true; 
          element_array[i] = element_array[i] / divisor; 
        } 
      }

      if (divisible) { 
          lcm_of_array_elements = lcm_of_array_elements * divisor; 
      } 
      else { 
          divisor++; 
      }
      
      if (counter == element_array.length) { 
          return lcm_of_array_elements; 
      } 
    } 
  }
}

class TaskCompare implements Comparator<Task>{
  @Override
  public int compare(Task t1, Task t2) {
    if(t1.getAbsoluteDeadline() > t2.getAbsoluteDeadline()){
      return 1;
    } else {
      return -1;
    }
  }
}

public class CyclicSchedule{
  private static ArrayList<Task> tasks = new ArrayList<Task>();
  private static ArrayList<Task> queue = new ArrayList<Task>();
  private static int majorCycle;
  private static int minorCycle;
  public static void main(String []args){
    Task.currentTime = 0;
    Task t1 = new Task(0, 1, 4, 4);
    Task t2 = new Task(0, 2, 5, 7);
    Task t3 = new Task(0, 3, 20, 20);
    tasks.add(t1);
    tasks.add(t2);
    tasks.add(t3);
    //get major and minor cycles
    majorCycle = Task.majorCycle(tasks);
    minorCycle = Task.minorCycle(majorCycle, tasks);
    // if tasks can not be rescheduled
    if(minorCycle == -1){
      System.out.println("Can't be scheduled");
      return;
    }

    // if tasks can be rescheduled
    while(Task.currentTime < majorCycle){
      int frameStartingTime = Task.currentTime;
      Task.currentTime += minorCycle;
      Task.checkArrivalTime(tasks, queue, majorCycle);
      if(queue.isEmpty()){
        continue;
      }
      // - sort acc to absolute deadlines
      Collections.sort(queue, new TaskCompare());

      /***********************
      for(int i = 0, len = queue.size(); i < len; i++){
        System.out.println(queue.get(i).pid);
      }
      System.out.println("end");
      /********************** */

      // - scheduling
      System.out.println("Frame: " + Task.currentTime / minorCycle);
      while(frameStartingTime < Task.currentTime){
        frameStartingTime += queue.get(0).eT;
        if(frameStartingTime <= Task.currentTime){
          System.out.println("Executing: " + queue.get(0).pid + " job : " + queue.get(0).jobNo);
          queue.remove(0);
        }
      }
    }
    
  }
}
