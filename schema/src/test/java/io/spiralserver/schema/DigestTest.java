package io.spiralserver.schema;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Digest}.
 * 
 * @author SpiralServer Team
 */
class DigestTest {
    
    @Test
    void testDigestCreation() {
        Digest d = new Digest("SHA-256", "abc123");
        assertEquals("SHA-256", d.algorithm());
        assertEquals("abc123", d.value());
    }
    
    @Test
    void testInvalidDigest() {
        assertThrows(IllegalArgumentException.class, () -> new Digest(null, "value"));
        assertThrows(IllegalArgumentException.class, () -> new Digest("", "value"));
        assertThrows(IllegalArgumentException.class, () -> new Digest("SHA-256", null));
        assertThrows(IllegalArgumentException.class, () -> new Digest("SHA-256", ""));
    }
}