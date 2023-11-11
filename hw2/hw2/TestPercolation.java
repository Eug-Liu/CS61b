package hw2;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Test.*;
import org.junit.Assert.*;

public class TestPercolation {

    @Test
    public void TestxyTo1D() {
        Percolation p = new Percolation(4);
        assertEquals(9, p.xyTo1D(2, 1));
        assertEquals(15, p.xyTo1D(3, 3));
        assertEquals(7, p.xyTo1D(1, 3));
        assertEquals(1, p.xyTo1D(0, 1));
        int index = p.xyTo1D(0, 1);
        for (int i = 0; i < 4; i++)
            if (p.grid.connected(index, i))
                p.isOpen(0, i);

    }

    @Test
    public void TestOpen() {
        Percolation p = new Percolation(4);
        p.open(2, 2);
        p.open(1, 1);
        assertTrue(p.isOpen(2, 2));
        assertTrue(p.isOpen(1, 1));
        assertFalse(p.isOpen(1, 2));
        assertFalse(p.isOpen(3, 3));
        int index11 = p.xyTo1D(1, 1);
        int index22 = p.xyTo1D(2, 2);
        int index12 = p.xyTo1D(1, 2);
        assertFalse(p.grid.connected(index11, index22));
        assertFalse(p.grid.connected(index11, index12));
        p.open(1, 2);
        assertTrue(p.grid.connected(index11, index12));
        assertTrue(p.grid.connected(index11, index22));
        assertEquals(3, p.numberOfOpenSites());
        p.open(0, 1);
        p.open(0, 0);
        assertTrue(p.grid.connected(0, 0));
        assertTrue(p.grid.connected(1, 1));
    }

    @Test
    public void TestFullAndPercolates() {
        Percolation p = new Percolation(4);
        p.open(0, 2);
        int index02 = p.xyTo1D(0, 2);
        System.out.println(index02);
        assertTrue(p.grid.connected(0, index02));
    }

}
