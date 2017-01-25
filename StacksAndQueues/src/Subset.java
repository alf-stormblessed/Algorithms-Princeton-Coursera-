import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {

    public static void main (String[] args) {
        
        RandomizedQueue<String> myRandomQueue = new RandomizedQueue<String>();
        
        int k = Integer.parseInt(args[0]);
        
        while (!StdIn.isEmpty()) {
            
            String s = StdIn.readString();
            myRandomQueue.enqueue(s);
            
        }
        if (k > 0) {
            
            for (String s : myRandomQueue) {
                
                StdOut.println(s);
                k--;
                if (k == 0) break;
            }
        }   
    }
}
