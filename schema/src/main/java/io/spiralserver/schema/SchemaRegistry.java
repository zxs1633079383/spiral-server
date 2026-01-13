package io.spiralserver.schema;

import java.util.Optional;

/**
 * Service Provider Interface (SPI) for schema registry.
 * 
 * <p>Schema registry provides:
 * <ul>
 *   <li>Schema storage and retrieval</li>
 *   <li>Schema resolution by reference</li>
 *   <li>Version management</li>
 *   <li>Digest verification</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Registry implementations are pluggable (SPI)</li>
 *   <li>Registry operations are thread-safe</li>
 *   <li>Resolved schemas are immutable</li>
 *   <li>Digest verification is mandatory when digest is present</li>
 * </ul>
 * 
 * <p><strong>Implementation Notes:</strong>
 * <ul>
 *   <li>This is an interface-only definition</li>
 *   <li>Concrete implementations will be in control-plane module</li>
 *   <li>No storage engine is assumed</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface SchemaRegistry {
    
    /**
     * Registers a schema.
     * 
     * @param schema schema to register
     * @throws IllegalArgumentException if schema is invalid or duplicate
     */
    void register(Schema schema);
    
    /**
     * Resolves a schema by reference.
     * 
     * <p>If digest is present in the reference, it must match the resolved schema.
     * 
     * @param ref schema reference
     * @return resolved schema, empty if not found
     * @throws IllegalArgumentException if digest mismatch
     */
    Optional<Schema> resolve(SchemaRef ref);
    
    /**
     * Resolves a schema by reference, throwing exception if not found.
     * 
     * @param ref schema reference
     * @return resolved schema
     * @throws SchemaNotFoundException if schema not found
     * @throws IllegalArgumentException if digest mismatch
     */
    default Schema resolveRequired(SchemaRef ref) {
        return resolve(ref).orElseThrow(() -> new SchemaNotFoundException(ref));
    }
    
    /**
     * Finds all versions of a schema by type and name.
     * 
     * @param type schema type (agent, event, tool, policy)
     * @param name schema name
     * @return list of schema references (all versions)
     */
    java.util.List<SchemaRef> findVersions(String type, String name);
    
    /**
     * Finds the latest version of a schema.
     * 
     * @param type schema type
     * @param name schema name
     * @return latest schema reference, empty if not found
     */
    Optional<SchemaRef> findLatest(String type, String name);
    
    /**
     * Exception thrown when schema is not found.
     */
    class SchemaNotFoundException extends RuntimeException {
        private final SchemaRef ref;
        
        public SchemaNotFoundException(SchemaRef ref) {
            super("Schema not found: " + ref);
            this.ref = ref;
        }
        
        public SchemaRef ref() {
            return ref;
        }
    }
}