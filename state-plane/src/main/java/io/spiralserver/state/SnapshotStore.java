package io.spiralserver.state;

import java.util.Optional;

/**
 * Snapshot storage abstraction.
 * 
 * <p>Snapshot store provides:
 * <ul>
 *   <li>Snapshot creation and retrieval</li>
 *   <li>Snapshot querying by agent instance and cursor</li>
 *   <li>Snapshot cleanup and retention</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Snapshots are immutable once created</li>
 *   <li>Snapshots are indexed by agent instance and cursor</li>
 *   <li>Snapshot state is schema-validated</li>
 * </ul>
 * 
 * <p><strong>Implementation Notes:</strong>
 * <ul>
 *   <li>This is an interface-only definition</li>
 *   <li>Concrete implementations will be storage-agnostic</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface SnapshotStore {
    
    /**
     * Saves a snapshot.
     * 
     * @param snapshot snapshot to save
     * @throws IllegalArgumentException if snapshot is invalid
     */
    void save(Snapshot snapshot);
    
    /**
     * Retrieves a snapshot by ID.
     * 
     * @param snapshotId snapshot identifier
     * @return snapshot if found
     */
    Optional<Snapshot> findById(String snapshotId);
    
    /**
     * Finds the latest snapshot for an agent instance at or before a cursor.
     * 
     * <p>Used for replay optimization (start from snapshot instead of beginning).
     * 
     * @param agentInstanceId agent instance identifier
     * @param maxCursor maximum cursor (inclusive)
     * @return latest snapshot at or before cursor, empty if none found
     */
    Optional<Snapshot> findLatestBefore(String agentInstanceId, Cursor maxCursor);
    
    /**
     * Deletes snapshots older than the specified cursor for an agent instance.
     * 
     * <p>Used for cleanup and retention management.
     * 
     * @param agentInstanceId agent instance identifier
     * @param beforeCursor delete snapshots before this cursor
     * @return number of snapshots deleted
     */
    int deleteBefore(String agentInstanceId, Cursor beforeCursor);
}