package io.spiralserver.control;

import io.spiralserver.schema.AgentSchema;
import io.spiralserver.schema.SchemaRef;
import io.spiralserver.schema.SchemaRegistry;

import java.util.List;
import java.util.Optional;

/**
 * Agent registry for managing agent schemas and instances.
 * 
 * <p>Agent registry provides:
 * <ul>
 *   <li>Agent schema registration and retrieval</li>
 *   <li>Agent instance lifecycle management</li>
 *   <li>Agent instance querying</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>All agent schemas must be validated before registration</li>
 *   <li>Agent instances are tracked per schema version</li>
 *   <li>Registry operations are thread-safe</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface AgentRegistry {
    
    /**
     * Registers an agent schema.
     * 
     * @param agentSchema agent schema to register
     * @param schemaRegistry schema registry for validation
     * @throws IllegalArgumentException if schema is invalid
     */
    void register(AgentSchema agentSchema, SchemaRegistry schemaRegistry);
    
    /**
     * Retrieves an agent schema by reference.
     * 
     * @param agentRef agent schema reference
     * @return agent schema if found
     */
    Optional<AgentSchema> getSchema(SchemaRef agentRef);
    
    /**
     * Creates a new agent instance.
     * 
     * @param agentRef agent schema reference
     * @param instanceId instance identifier (null for auto-generated)
     * @param initialState initial state (nullable)
     * @return agent instance
     * @throws IllegalArgumentException if agent schema not found
     */
    AgentInstance createInstance(SchemaRef agentRef, String instanceId, Object initialState);
    
    /**
     * Retrieves an agent instance by ID.
     * 
     * @param instanceId instance identifier
     * @return agent instance if found
     */
    Optional<AgentInstance> getInstance(String instanceId);
    
    /**
     * Lists all instances of an agent schema.
     * 
     * @param agentRef agent schema reference
     * @return list of agent instances
     */
    List<AgentInstance> listInstances(SchemaRef agentRef);
    
    /**
     * Agent instance representation.
     */
    interface AgentInstance {
        String instanceId();
        SchemaRef agentRef();
        InstanceStatus status();
        long createdAt();
        long lastUpdatedAt();
        
        enum InstanceStatus {
            CREATED,
            RUNNING,
            PAUSED,
            COMPLETED,
            FAILED
        }
    }
}