import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    
    @Test
    public void checker(){
        StudentArrayDeque<Integer> student = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solution = new ArrayDequeSolution<>();
        double method;
        String log = "";
        for(int times = 10000; times > 0; times--){
            method = StdRandom.uniform();
            if (method <= 0.25){
                student.addFirst(times);
                solution.addFirst(times);
                log += "addFirst(" + times + ")\n";
            } else if (method <= 0.5) {
                student.addLast(times);
                solution.addLast(times);
                log += "addLast(" + times + ")\n";
            } else if (method <= 0.75) {
                if (!student.isEmpty()){
                    int get = student.removeFirst();
                    int exp = solution.removeFirst();
                    log += "removeFirst(): " + get + "\n";
                    assertEquals(log, exp, get);
                }
            } else{
                if (!student.isEmpty()){
                    int get = student.removeLast();
                    int exp = solution.removeLast();
                    log += "removeLast(): " + get + "\n";
                    assertEquals(log, exp, get);
                }
            }

        }
    }
}
