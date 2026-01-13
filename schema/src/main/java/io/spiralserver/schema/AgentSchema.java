package io.spiralserver.schema;

import java.util.List;

/**
 * Schema definition for an Agent.
 * 
 * <p>An agent schema defines:
 * <ul>
 *   <li>Agent identity and versioning</li>
 *   <li>Event subscriptions (with CEL filters)</li>
 *   <li>Tool references (skills the agent can use)</li>
 *   <li>Policy bindings (governance rules)</li>
 *   <li>State schema (persistent state structure)</li>
 *   <li>Planner configuration</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Immutable</li>
 *   <li>All tool references must resolve</li>
 *   <li>All event subscriptions must reference valid event schemas</li>
 *   <li>Policy bindings must reference valid policy schemas</li>
 *   <li>State schema must be valid</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface AgentSchema extends Schema {
    
    /**
     * Returns the agent name (unique identifier).
     * 
     * @return agent name
     */
    String name();
    
    /**
     * Returns event subscriptions with CEL filter expressions.
     * 
     * <p>Each subscription defines:
     * <ul>
     *   <li>Event schema reference</li>
     *   <li>CEL filter expression (optional)</li>
     *   <li>Correlation key expression</li>
     *   <li>Idempotency key expression</li>
     * </ul>
     * 
     * @return list of event subscriptions
     */
    List<EventSubscription> eventSubscriptions();
    
    /**
     * Returns tool references (skills the agent can use).
     * 
     * @return list of tool schema references
     */
    List<SchemaRef> toolRefs();
    
    /**
     * Returns policy bindings (governance rules).
     * 
     * @return list of policy schema references
     */
    List<SchemaRef> policyRefs();
    
    /**
     * Returns the state schema reference (structure of persistent state).
     * 
     * @return state schema reference (nullable if no persistent state)
     */
    // TODO: Define StateSchema type
    // StateSchemaRef stateSchema();
    
    /**
     * Event subscription definition.
     */
    interface EventSubscription {
        SchemaRef eventRef();
        String celFilter(); // CEL expression, empty string means no filter
        String correlationKeyExpr(); // CEL expression for correlation key
        String idempotencyKeyExpr(); // CEL expression for idempotency key
    }
}