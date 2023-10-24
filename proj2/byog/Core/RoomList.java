package byog.Core;

import java.util.Random;
public class RoomList {

    private int size;

    protected class Room{
        Room next;
        int x;
        int y;
        int width;
        int height;
        boolean visited = false;

        Room(int xx, int yy, int w, int h){
            next = null;
            x = xx;
            y = yy;
            width = w;
            height = h;
        }
    }



    Room first;
    Room last;
    Room current;
    Room nextRoom;

    public RoomList(){
        first = new Room(-1, -1, -1, -1);
        first.visited = true;
        current = first;
        last = first;
        size = 0;
    }

    public void add(int x, int y, int w, int h){
        last.next = new Room(x, y, w, h);
        last = last.next;
        size += 1;
    }

    public int size(){
        return size();
    }

    // If given space available return true
    public boolean checkAvailable(int x, int y, int w, int h){
        Room ptr = first;
        Room pend = new Room(x, y, w, h);
        while(ptr != null){
            if(checkConflict(ptr, pend))
                return false;
            ptr = ptr.next;
        }

        return true;
    }

    // If the location of two rooms conflict return true
    private boolean checkConflict(Room r1, Room r2){
        Room high, low;
        if(r1.y > r2.y) {
            high = r1;
            low = r2;
        } else{
            high = r2;
            low = r1;
        }
        if(low.y + low.height <= high.y)
            return false;

        Room front, back;
        if(r1.x > r2.x) {
            front = r1;
            back = r2;
        } else{
            front = r2;
            back = r1;
        }
        if(back.x + back.width <= front.x)
            return false;

        return true;
    }

    public void view(){
        Room fp = first;
        int id = 1;
        while(fp != null){
            System.out.print("(Room " + id + ": " + fp.x + " " + fp.y + ") ");
            fp = fp.next;
            id += 1;
        }
        System.out.println();
    }

    public Room get(int index){
        return getHelper(index, first.next);
    }

    private Room getHelper(int index, Room start){
        if(index == 0)
            return start;
        return getHelper(index - 1, start.next);
    }

    /** return the shortest room that is not connected */
    protected Room shortest(Room start){
        Room ptr = first.next;
        int index = 0;
        int minIndex = -1;
        int minDS = -1;
        while(ptr != null){
            if (!ptr.visited) {
                int ds = distanceSquare(start, ptr);
                if(minDS == -1 || ds < minDS){
                    minDS = ds;
                    minIndex = index;
                }
            }
            index++;
            ptr = ptr.next;
        }
        if (minIndex == -1)
            return null;
        return get(minIndex);
    }

    private int distanceSquare(Room r1, Room r2){
        return (r1.x - r2.x)*(r1.x - r2.x) + (r1.y - r2.y) * (r1.y - r2.y);
    }

    public boolean visitNext(){
        if(current == first){
            current = first.next;
            current.visited = true;
            nextRoom = shortest(current);
        } else {
            current = nextRoom;
            current.visited = true;
            nextRoom = shortest(current);
        }
        if (nextRoom == null)
            return false;
        return true;
    }

    protected int currX = -1;
    protected int currY = -1;
    protected int distX = -1;
    protected int distY = -1;
    private final int UP = 1;
    private final int DOWN = -1;
    private final int LEFT = -1;
    private final int RIGHT = 1;
    public int[] nextPath(Random rand){
        if (currX == -1){
            currX = current.x + 1 + rand.nextInt(current.width - 2);
            currY = current.y + 1 + rand.nextInt(current.height - 2);
        }
        distX = nextRoom.x + 1 + rand.nextInt(nextRoom.width - 2);
        distY = nextRoom.y + 1 + rand.nextInt(nextRoom.height - 2);
        int totalDistance = abs(distX - currX) + abs(distY - currY);
        int[] path = new int[totalDistance * 2];
        int hor = 1;
        int ver = 1;
        if (distX < currX)
            hor = -1;
        if (distY < currY)
            ver = -1;
        int i = 0;
        while (i < totalDistance * 2){
            if (currX == distX) {
                path[i] = currX;
                path[i + 1] = currY + ver;
                currY += ver;
            } else if(currY == distY) {
                path[i] = currX + hor;
                path[i + 1] = currY;
                currX += hor;
            } else {
                if (rand.nextBoolean()){
                    path[i] = currX;
                    path[i + 1] = currY + ver;
                    currY += ver;
                } else {
                    path[i] = currX + hor;
                    path[i + 1] = currY;
                    currX += hor;
                }
            }

            i += 2;
        }
        return path;
    }

    protected int pow(int x){
        return x*x;
    }
    protected int abs(int x) {
        if (x < 0)
            return -x;
        return x;
    }
}
