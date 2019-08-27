import java.util.ArrayList;

class Task{
  private int aT;  
  private int eT;  
  private int period;  
  private int relativeDeadline;

  Task(int aT, int eT, int period, int relativeDeadline){
    this.aT = aT;
    this.eT = eT;
    this.period = period;
    this.relativeDeadline = relativeDeadline;
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
    ArrayList<Integer> factors = new ArrayList();
    ArrayList<Integer> validFactors = new ArrayList();
    ArrayList<Integer> validFrames = new ArrayList();
    int[] excutionTimes = new int[tasks.size()];
    int[] deadlines = new int[tasks.size()];
    for(int i = 0; i < tasks.size(); i++){
      excutionTimes[i] = tasks.get(i).eT;
      deadlines[i] = tasks.get(i).relativeDeadline;
    }
    lowerLimit = Utils.max(excutionTimes);
    upperLimit = Utils.min(deadlines);
    factors = Utils.factors(majorCycle);
    System.out.println(lowerLimit + " " + upperLimit);
    for(int i=0; i < factors.size(); i++) if (factors.get(i) >= lowerLimit && factors.get(i) <= upperLimit) validFactors.add(factors.get(i));
    for(int i=0; i < validFactors.size(); i++){
      boolean ok = true;
      int f = validFactors.get(i);
      for(int j = 0; j < tasks.size(); j++){
        Task t = tasks.get(j);
        if(ok && (2*f - Utils.gcd(f, t.period)) > t.relativeDeadline) ok = false;
      }
      System.out.println(f + " " + ok);
      if(ok) validFrames.add(f);
      System.out.println(validFactors.get(i));
    }
    return 0;
  }
}

class Utils{
  static ArrayList factors(int number){
    ArrayList s = new ArrayList();
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

public class CyclicSchedule{
  private static ArrayList<Task> tasks = new ArrayList<Task>();
  private static int majorCycle;
  private static int minorCycle;
  public static void main(String []args){
    Task t1 = new Task(0, 2, 5, 7);
    Task t2 = new Task(0, 5, 6, 10);
    Task t3 = new Task(1, 1, 4, 6);
    tasks.add(t1);
    tasks.add(t2);
    tasks.add(t3);
    majorCycle = Task.majorCycle(tasks);
    minorCycle = Task.minorCycle(majorCycle, tasks);
    if(minorCycle == -1){
      System.out.println("Can't be scheduled");
      return;
    }
  }
}