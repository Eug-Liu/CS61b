import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void TestOffByOne(){
        assertTrue(offByOne.equalChars('C', 'B'));
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('y', 'x'));
        assertFalse(offByOne.equalChars('i', 'A'));
        assertFalse(offByOne.equalChars('w', 'z'));
        assertFalse(offByOne.equalChars('}', 'a'));

    }
    // Your tests go here.
}
