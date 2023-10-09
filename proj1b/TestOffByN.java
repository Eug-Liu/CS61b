import static org.junit.Assert.*;
import org.junit.Test;


public class TestOffByN {
    
    CharacterComparator cmp = new OffByN(5);

    @Test
    public void OffByN(){
        assertTrue(cmp.equalChars('a', 'f'));
        assertTrue(cmp.equalChars('f', 'a'));
        assertFalse(cmp.equalChars('f', 'h'));

    }

}
