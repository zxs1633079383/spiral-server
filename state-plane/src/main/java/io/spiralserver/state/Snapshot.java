package io.spiralserver.state;

import java.time.Instant;
import java.util.Objects;

/**
 * Snapshot of agent execution state at a specific point in time.
 * 
 * <p>Snapshots enable:
 * <ul>
 *   <li>Fast recovery from a known good state</li>
 *   <li>Replay optimization (start from snapshot instead of beginning)</li>
 *   <li>State migration and evolution</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Immutable</li>
 *   <li>Snapshot corresponds to a specific event log cursor</li>
 *   <li>Snapshot state is schema-validated</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public final class Snapshot {
    
    private final String snapshotId;
    private final String agentInstanceId;
    private final Cursor cursor; // event log position at snapshot time
    private final Object stateData; // schema-validated state
    private final Instant timestamp;
    private final long stateVersion;
    
    /**
     * Creates a snapshot.
     * 
     * @param snapshotId unique snapshot identifier
     * @param agentInstanceId agent instance identifier
     * @param cursor event log cursor at snapshot time
     * @param stateData state data (must be schema-validated)
     * @param timestamp snapshot creation time
     * @param stateVersion state version at snapshot time
     */
    public Snapshot(
        String snapshotId,
        String agentInstanceId,
        Cursor cursor,
        Object stateData,
        Instant timestamp,
        long stateVersion
    ) {
        if (snapshotId == null || snapshotId.isBlank()) {
            throw new IllegalArgumentException("Snapshot ID cannot be null or blank");
        }
        if (agentInstanceId == null || agentInstanceId.isBlank()) {
            throw new IllegalArgumentException("Agent instance ID cannot be null or blank");
        }
        if (cursor == null) {
            throw new IllegalArgumentException("Cursor cannot be null");
        }
        if (stateData == null) {
            throw new IllegalArgumentException("State data cannot be null");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
        this.snapshotId = snapshotId;
        this.agentInstanceId = agentInstanceId;
        this.cursor = cursor;
        this.stateData = stateData;
        this.timestamp = timestamp;
        this.stateVersion = stateVersion;
    }
    
    public String snapshotId() {
        return snapshotId;
    }
    
    public String agentInstanceId() {
        return agentInstanceId;
    }
    
    public Cursor cursor() {
        return cursor;
    }
    
    public Object stateData() {
        return stateData;
    }
    
    public Instant timestamp() {
        return timestamp;
    }
    
    public long stateVersion() {
        return stateVersion;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Snapshot snapshot = (Snapshot) o;
        return stateVersion == snapshot.stateVersion
            && snapshotId.equals(snapshot.snapshotId)
            && agentInstanceId.equals(snapshot.agentInstanceId)
            && cursor.equals(snapshot.cursor)
            && stateData.equals(snapshot.stateData)
            && timestamp.equals(snapshot.timestamp);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(snapshotId, agentInstanceId, cursor, stateData, timestamp, stateVersion);
    }
    
    @Override
    public String toString() {
        return "Snapshot{snapshotId='" + snapshotId + "', agentInstanceId='" + agentInstanceId 
            + "', cursor=" + cursor + ", timestamp=" + timestamp + ", stateVersion=" + stateVersion + "}";
    }
}