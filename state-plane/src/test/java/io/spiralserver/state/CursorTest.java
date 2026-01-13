package io.spiralserver.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Cursor}.
 * 
 * @author SpiralServer Team
 */
class CursorTest {
    
    @Test
    void testCursorCreation() {
        Cursor c = new Cursor(100);
        assertEquals(100, c.sequence());
    }
    
    @Test
    void testBeginningCursor() {
        Cursor c = Cursor.beginning();
        assertEquals(0, c.sequence());
    }
    
    @Test
    void testCursorComparison() {
        Cursor c1 = new Cursor(10);
        Cursor c2 = new Cursor(20);
        
        assertTrue(c1.isBefore(c2));
        assertTrue(c2.isAfter(c1));
        assertTrue(c1.compareTo(c2) < 0);
    }
    
    @Test
    void testNextCursor() {
        Cursor c1 = new Cursor(10);
        Cursor c2 = c1.next();
        assertEquals(11, c2.sequence());
    }
    
    @Test
    void testInvalidCursor() {
        assertThrows(IllegalArgumentException.class, () -> new Cursor(-1));
    }
}