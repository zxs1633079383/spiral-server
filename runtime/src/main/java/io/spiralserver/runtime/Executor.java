package io.spiralserver.runtime;

import io.spiralserver.schema.AgentSchema;
import io.spiralserver.state.EventLog;
import io.spiralserver.state.HotState;

import java.util.List;

/**
 * Deterministic executor interface (state machine).
 * 
 * <p>Executor is a state machine that:
 * <ul>
 *   <li>Executes plans deterministically</li>
 *   <li>Manages agent execution state</li>
 *   <li>Coordinates tool invocations</li>
 *   <li>Handles failures and compensation</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Executor is deterministic (same state + plan = same execution)</li>
 *   <li>Executor does not directly access IO</li>
 *   <li>All side effects go through tool boundary</li>
 *   <li>Executor state transitions are replayable</li>
 * </ul>
 * 
 * <p><strong>Design Notes:</strong>
 * <ul>
 *   <li>Inspired by Temporal's workflow executor</li>
 *   <li>Executor is a state machine, not a workflow DSL</li>
 *   <li>Tool invocations are isolated and non-deterministic</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface Executor {
    
    /**
     * Executes a plan for an agent instance.
     * 
     * <p>Execution is deterministic: given the same state and plan, execution is identical.
     * Tool invocations are the only non-deterministic parts (handled via tool boundary).
     * 
     * @param agentInstanceId agent instance identifier
     * @param agentSchema agent schema
     * @param plan plan to execute
     * @param currentState current agent state
     * @param toolBoundary tool boundary for side effects
     * @return execution result
     */
    ExecutionResult execute(
        String agentInstanceId,
        AgentSchema agentSchema,
        Planner.Plan plan,
        HotState.State currentState,
        ToolBoundary toolBoundary
    );
    
    /**
     * Execution result.
     */
    interface ExecutionResult {
        ExecutionStatus status();
        HotState.State newState(); // updated state after execution
        List<ExecutionEvent> events(); // events produced during execution
        
        enum ExecutionStatus {
            SUCCESS, // plan executed successfully
            PARTIAL, // plan partially executed (some actions failed)
            FAILED, // plan execution failed
            WAITING // waiting for external event
        }
        
        /**
         * Event produced during execution.
         */
        interface ExecutionEvent {
            String eventType();
            Object payload();
        }
    }
}