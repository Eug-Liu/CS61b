public class OffByN implements CharacterComparator{
    static private int N;
    public OffByN(int n){
        N = n;
    }
    public boolean equalChars(char x, char y){
        return (x - y == N || x - y == -N);
    }
}
