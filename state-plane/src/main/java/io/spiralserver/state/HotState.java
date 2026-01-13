package io.spiralserver.state;

import java.time.Instant;
import java.util.Optional;

/**
 * Hot state management abstraction.
 * 
 * <p>Hot state provides:
 * <ul>
 *   <li>Fast read/write access to agent execution state</li>
 *   <li>State versioning and conflict detection</li>
 *   <li>State checkpointing</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>State operations are isolated per agent instance</li>
 *   <li>State updates are versioned</li>
 *   <li>State can be checkpointed for recovery</li>
 *   <li>State schema is validated</li>
 * </ul>
 * 
 * <p><strong>Implementation Notes:</strong>
 * <ul>
 *   <li>This is an interface-only definition</li>
 *   <li>Concrete implementations will be storage-agnostic</li>
 *   <li>No storage engine is assumed</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface HotState {
    
    /**
     * Reads the current state for an agent instance.
     * 
     * @param agentInstanceId agent instance identifier
     * @return current state, empty if not found
     */
    Optional<State> read(String agentInstanceId);
    
    /**
     * Updates state with optimistic locking.
     * 
     * <p>Update succeeds only if the current version matches the expected version.
     * 
     * @param agentInstanceId agent instance identifier
     * @param expectedVersion expected current version (for optimistic locking)
     * @param newState new state value
     * @return true if update succeeded, false if version mismatch
     * @throws IllegalArgumentException if state is invalid
     */
    boolean update(String agentInstanceId, long expectedVersion, State newState);
    
    /**
     * Creates or updates state (upsert).
     * 
     * @param agentInstanceId agent instance identifier
     * @param state state value
     * @return new version number
     * @throws IllegalArgumentException if state is invalid
     */
    long upsert(String agentInstanceId, State state);
    
    /**
     * Creates a checkpoint of the current state.
     * 
     * @param agentInstanceId agent instance identifier
     * @return checkpoint identifier
     */
    Checkpoint checkpoint(String agentInstanceId);
    
    /**
     * Restores state from a checkpoint.
     * 
     * @param agentInstanceId agent instance identifier
     * @param checkpoint checkpoint to restore from
     * @return true if restore succeeded
     */
    boolean restore(String agentInstanceId, Checkpoint checkpoint);
    
    /**
     * State representation.
     */
    interface State {
        long version(); // monotonically increasing
        Object data(); // schema-validated state data
        Instant lastModified();
    }
    
    /**
     * Checkpoint representation.
     */
    interface Checkpoint {
        String checkpointId();
        long stateVersion();
        Instant timestamp();
        
        /**
         * Returns checkpoint metadata.
         * 
         * @return metadata map
         */
        java.util.Map<String, String> metadata();
        
        /**
         * Returns snapshot reference if checkpoint has associated snapshot.
         * 
         * @return snapshot reference (nullable)
         */
        String snapshotRef();
    }
}