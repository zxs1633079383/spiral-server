package io.spiralserver.control;

import io.spiralserver.schema.AgentSchema;
import io.spiralserver.state.EventLog;

import java.util.List;

/**
 * Event router for routing events to agent instances based on CEL filters.
 * 
 * <p>Event router provides:
 * <ul>
 *   <li>Event subscription matching (CEL filter evaluation)</li>
 *   <li>Correlation key routing</li>
 *   <li>Idempotency handling</li>
 *   <li>Event delivery to agent instances</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Event routing is deterministic</li>
 *   <li>CEL filters are evaluated safely</li>
 *   <li>Idempotency is enforced</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface EventRouter {
    
    /**
     * Routes an event to matching agent instances.
     * 
     * <p>Event is matched against agent subscriptions using CEL filters.
     * Correlation keys are used to route to specific instances.
     * 
     * @param event event to route
     * @return list of routing results (which instances received the event)
     */
    List<RoutingResult> route(EventLog.Event event);
    
    /**
     * Subscribes an agent instance to events.
     * 
     * @param agentInstance agent instance
     * @param agentSchema agent schema (contains event subscriptions)
     */
    void subscribe(AgentRegistry.AgentInstance agentInstance, AgentSchema agentSchema);
    
    /**
     * Unsubscribes an agent instance from events.
     * 
     * @param agentInstance agent instance
     */
    void unsubscribe(AgentRegistry.AgentInstance agentInstance);
    
    /**
     * Routing result.
     */
    interface RoutingResult {
        String agentInstanceId();
        boolean matched(); // true if event matched subscription
        String reason(); // reason if not matched
    }
}