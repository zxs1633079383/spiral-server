package io.spiralserver.schema;

/**
 * Validates schemas for structural correctness and reference integrity.
 * 
 * <p>Validation checks include:
 * <ul>
 *   <li>Required fields are present</li>
 *   <li>Reference integrity (all refs resolve via registry)</li>
 *   <li>Security constraints (no plaintext secrets)</li>
 *   <li>Data classification and PII handling</li>
 *   <li>Idempotency and correlation key validation</li>
 *   <li>Budget and limit validation</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Validation is deterministic</li>
 *   <li>Validation does not mutate schemas</li>
 *   <li>All validation errors are reported</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface SchemaValidator {
    
    /**
     * Validates a schema.
     * 
     * @param schema schema to validate
     * @param registry schema registry for reference resolution
     * @return validation result
     */
    ValidationResult validate(Schema schema, SchemaRegistry registry);
    
    /**
     * Validates an agent schema.
     * 
     * @param agentSchema agent schema
     * @param registry schema registry
     * @return validation result
     */
    ValidationResult validateAgent(AgentSchema agentSchema, SchemaRegistry registry);
    
    /**
     * Validates an event schema.
     * 
     * @param eventSchema event schema
     * @param registry schema registry
     * @return validation result
     */
    ValidationResult validateEvent(EventSchema eventSchema, SchemaRegistry registry);
    
    /**
     * Validates a tool schema.
     * 
     * @param toolSchema tool schema
     * @param registry schema registry
     * @return validation result
     */
    ValidationResult validateTool(ToolSchema toolSchema, SchemaRegistry registry);
    
    /**
     * Validates a policy schema.
     * 
     * @param policySchema policy schema
     * @param registry schema registry
     * @return validation result
     */
    ValidationResult validatePolicy(PolicySchema policySchema, SchemaRegistry registry);
}