package io.spiralserver.state;

import java.util.Objects;

/**
 * Cursor for event log position tracking.
 * 
 * <p>Cursor represents a position in the event log, used for:
 * <ul>
 *   <li>Reading events from a specific position</li>
 *   <li>Tracking processing progress</li>
 *   <li>Replay from a specific point</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Immutable</li>
 *   <li>Sequence number is non-negative</li>
 *   <li>Cursors are comparable (ordering)</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public final class Cursor implements Comparable<Cursor> {
    
    private final long sequence;
    
    /**
     * Creates a cursor at the given sequence position.
     * 
     * @param sequence sequence number (non-negative)
     * @throws IllegalArgumentException if sequence is negative
     */
    public Cursor(long sequence) {
        if (sequence < 0) {
            throw new IllegalArgumentException("Sequence must be non-negative");
        }
        this.sequence = sequence;
    }
    
    /**
     * Creates a cursor at the beginning (sequence 0).
     * 
     * @return beginning cursor
     */
    public static Cursor beginning() {
        return new Cursor(0);
    }
    
    public long sequence() {
        return sequence;
    }
    
    /**
     * Returns the next cursor (sequence + 1).
     * 
     * @return next cursor
     */
    public Cursor next() {
        return new Cursor(sequence + 1);
    }
    
    /**
     * Returns true if this cursor is before the other cursor.
     * 
     * @param other other cursor
     * @return true if before
     */
    public boolean isBefore(Cursor other) {
        return this.sequence < other.sequence;
    }
    
    /**
     * Returns true if this cursor is after the other cursor.
     * 
     * @param other other cursor
     * @return true if after
     */
    public boolean isAfter(Cursor other) {
        return this.sequence > other.sequence;
    }
    
    @Override
    public int compareTo(Cursor o) {
        return Long.compare(sequence, o.sequence);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cursor cursor = (Cursor) o;
        return sequence == cursor.sequence;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(sequence);
    }
    
    @Override
    public String toString() {
        return "Cursor{sequence=" + sequence + "}";
    }
}