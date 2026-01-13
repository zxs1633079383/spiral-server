package io.spiralserver.runtime;

import io.spiralserver.schema.AgentSchema;
import io.spiralserver.state.EventLog;
import io.spiralserver.state.HotState;

import java.util.List;

/**
 * Pure planner interface for deterministic planning.
 * 
 * <p>Planner is a pure function that:
 * <ul>
 *   <li>Takes current state and events as input</li>
 *   <li>Produces a plan (sequence of actions) as output</li>
 *   <li>Has no side effects</li>
 *   <li>Is deterministic (same input = same output)</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Planner is pure (no IO, no side effects)</li>
 *   <li>Planner is deterministic</li>
 *   <li>Planner output is replayable</li>
 *   <li>Planner does not mutate input state</li>
 * </ul>
 * 
 * <p><strong>Design Notes:</strong>
 * <ul>
 *   <li>Inspired by Temporal's deterministic workflow model</li>
 *   <li>Planner logic is defined by agent schema</li>
 *   <li>Planner may use LLM, but LLM calls must be deterministic (via seed/temperature=0)</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface Planner {
    
    /**
     * Plans the next actions for an agent instance.
     * 
     * <p>This is a pure function: given the same inputs, it produces the same plan.
     * 
     * @param agentSchema agent schema (defines planning logic)
     * @param currentState current agent state
     * @param newEvents new events since last planning
     * @param context planning context (tool availability, policies, etc.)
     * @return plan (sequence of actions)
     */
    Plan plan(
        AgentSchema agentSchema,
        HotState.State currentState,
        List<EventLog.Event> newEvents,
        PlanningContext context
    );
    
    /**
     * Planning result (sequence of actions).
     */
    interface Plan {
        List<Action> actions();
        String planId(); // unique plan identifier
        long planVersion(); // version for optimistic locking
        
        /**
         * Action to execute.
         */
        interface Action {
            String actionId();
            ActionType type();
            Object parameters(); // action-specific parameters
            
            enum ActionType {
                TOOL_INVOCATION, // invoke a tool
                WAIT, // wait for event
                COMPLETE, // agent execution complete
                FAIL // agent execution failed
            }
        }
    }
    
    /**
     * Planning context (tool availability, policies, etc.).
     */
    interface PlanningContext {
        // TODO: Define context structure (available tools, policies, budget, etc.)
    }
}