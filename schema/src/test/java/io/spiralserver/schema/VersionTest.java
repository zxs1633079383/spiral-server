package io.spiralserver.schema;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Version}.
 * 
 * @author SpiralServer Team
 */
class VersionTest {
    
    @Test
    void testVersionCreation() {
        Version v = new Version(1, 2, 3);
        assertEquals(1, v.major());
        assertEquals(2, v.minor());
        assertEquals(3, v.patch());
    }
    
    @Test
    void testVersionComparison() {
        Version v1 = new Version(1, 0, 0);
        Version v2 = new Version(1, 0, 1);
        Version v3 = new Version(1, 1, 0);
        
        assertTrue(v1.compareTo(v2) < 0);
        assertTrue(v2.compareTo(v3) < 0);
        assertTrue(v1.compareTo(v1) == 0);
    }
    
    @Test
    void testBackwardCompatibility() {
        Version v1 = new Version(1, 0, 0);
        Version v2 = new Version(1, 1, 0);
        Version v3 = new Version(2, 0, 0);
        
        assertTrue(v2.isBackwardCompatibleWith(v1));
        assertFalse(v3.isBackwardCompatibleWith(v1));
    }
    
    @Test
    void testInvalidVersion() {
        assertThrows(IllegalArgumentException.class, () -> new Version(-1, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new Version(0, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> new Version(0, 0, -1));
    }
}