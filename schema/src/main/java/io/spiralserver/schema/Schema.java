package io.spiralserver.schema;

/**
 * Base interface for all schema types.
 * 
 * <p>All schemas (Agent, Event, Tool, Policy) must implement this interface.
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>All schemas are immutable</li>
 *   <li>All schemas have a reference (type, name, version)</li>
 *   <li>All schemas can have a digest for content pinning</li>
 *   <li>Schemas are validated before use</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface Schema {
    
    /**
     * Returns the schema reference.
     * 
     * @return schema reference
     */
    SchemaRef ref();
    
    /**
     * Returns the schema version.
     * 
     * @return version
     */
    default Version version() {
        return ref().version();
    }
    
    /**
     * Returns the content digest if pinned, null otherwise.
     * 
     * @return digest or null
     */
    default Digest digest() {
        return ref().digest();
    }
    
    /**
     * Validates the schema structure and references.
     * 
     * <p>This method checks:
     * <ul>
     *   <li>Required fields are present</li>
     *   <li>Reference integrity (all refs resolve)</li>
     *   <li>Security constraints (no plaintext secrets)</li>
     *   <li>Data classification and PII handling</li>
     * </ul>
     * 
     * @param registry schema registry for reference resolution
     * @return validation result
     */
    ValidationResult validate(SchemaRegistry registry);
}