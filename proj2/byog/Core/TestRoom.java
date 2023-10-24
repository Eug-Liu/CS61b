package byog.Core;
import java.util.Random;
public class TestRoom {
    public static void main(String[] args) {
        RoomList list = new RoomList();
        list.add(10, 10, 10,10);
        list.add(29, 48, 5,5);
        list.add(25, 11, 7,5);
        list.add(91, 100, 4,3);
        list.add(38, 50, 5,3);
        Random rand = new Random(1);


        list.visitNext();

        int[] array = list.nextPath(rand);
        System.out.println(list.current.x + " " + list.current.y);
        System.out.println(list.nextRoom.x + " " + list.nextRoom.y);

        for (int i: array){
            System.out.print(i + " ");
        }

        System.out.println();

        list.view();

    }
}
