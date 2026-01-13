package io.spiralserver.runtime;

import io.spiralserver.schema.AgentSchema;
import io.spiralserver.state.Cursor;
import io.spiralserver.state.EventLog;
import io.spiralserver.state.HotState;
import io.spiralserver.state.Snapshot;

import java.util.List;
import java.util.Optional;

/**
 * Replay engine for deterministic execution replay.
 * 
 * <p>Replay engine provides:
 * <ul>
 *   <li>Replay execution from any point</li>
 *   <li>Deterministic replay (bit-for-bit identical)</li>
 *   <li>Replay optimization (start from snapshot)</li>
 *   <li>Debugging and audit support</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Replay is deterministic (same events = same execution)</li>
 *   <li>Replay uses recorded tool results (no actual tool invocations)</li>
 *   <li>Replay can start from any cursor or snapshot</li>
 * </ul>
 * 
 * <p><strong>Design Notes:</strong>
 * <ul>
 *   <li>Inspired by Temporal's workflow replay</li>
 *   <li>Replay is a production feature, not just for debugging</li>
 *   <li>Replay enables recovery and audit</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface ReplayEngine {
    
    /**
     * Replays agent execution from a cursor.
     * 
     * <p>Replay is deterministic: given the same events and state, execution is identical.
     * Tool results are taken from recorded history, not actual tool invocations.
     * 
     * @param agentInstanceId agent instance identifier
     * @param agentSchema agent schema
     * @param fromCursor starting cursor (inclusive)
     * @param toCursor ending cursor (exclusive, null means to end)
     * @param initialState initial state (null means load from snapshot or beginning)
     * @return replay result
     */
    ReplayResult replay(
        String agentInstanceId,
        AgentSchema agentSchema,
        Cursor fromCursor,
        Optional<Cursor> toCursor,
        Optional<HotState.State> initialState
    );
    
    /**
     * Replays agent execution from a snapshot.
     * 
     * <p>Optimized replay starting from a known good state.
     * 
     * @param agentInstanceId agent instance identifier
     * @param agentSchema agent schema
     * @param snapshot snapshot to start from
     * @param toCursor ending cursor (exclusive, null means to end)
     * @return replay result
     */
    ReplayResult replayFromSnapshot(
        String agentInstanceId,
        AgentSchema agentSchema,
        Snapshot snapshot,
        Optional<Cursor> toCursor
    );
    
    /**
     * Replay result.
     */
    interface ReplayResult {
        ReplayStatus status();
        HotState.State finalState(); // state after replay
        Cursor finalCursor(); // cursor after replay
        List<ReplayEvent> events(); // events processed during replay
        
        enum ReplayStatus {
            SUCCESS, // replay completed successfully
            FAILED, // replay failed (inconsistent state, missing events, etc.)
            PARTIAL // replay partially completed (some events missing)
        }
        
        /**
         * Event processed during replay.
         */
        interface ReplayEvent {
            Cursor cursor();
            EventLog.Event event();
            Planner.Plan plan(); // plan generated for this event
            Executor.ExecutionResult executionResult(); // execution result
        }
    }
}