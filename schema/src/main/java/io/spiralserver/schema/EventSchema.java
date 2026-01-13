package io.spiralserver.schema;

import java.util.List;
import java.util.Map;

/**
 * Schema definition for an Event.
 * 
 * <p>An event schema defines:
 * <ul>
 *   <li>Event structure (data fields)</li>
 *   <li>Data classification (PII, sensitive, public)</li>
 *   <li>Idempotency and correlation fields</li>
 *   <li>Validation rules</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Immutable</li>
 *   <li>Event structure is well-defined</li>
 *   <li>Data classification is mandatory</li>
 *   <li>Idempotency fields are identified</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface EventSchema extends Schema {
    
    /**
     * Returns the event name (type identifier).
     * 
     * @return event name
     */
    String name();
    
    /**
     * Returns the event data schema (field definitions).
     * 
     * <p>TODO: Define structured schema representation (JSON Schema, Avro, etc.)
     * 
     * @return data schema definition
     */
    DataSchema dataSchema();
    
    /**
     * Returns data classification metadata.
     * 
     * <p>Maps field paths to classification levels (PII, sensitive, public, etc.)
     * 
     * @return classification map (field path -> classification)
     */
    Map<String, DataClassification> dataClassification();
    
    /**
     * Returns the field path(s) used for correlation.
     * 
     * <p>Correlation keys identify related events (e.g., user_id, session_id).
     * 
     * @return correlation field paths (may be multiple for composite keys)
     */
    List<String> correlationFields();
    
    /**
     * Returns the field path(s) used for idempotency.
     * 
     * <p>Idempotency keys ensure duplicate events are handled safely.
     * 
     * @return idempotency field paths
     */
    List<String> idempotencyFields();

    /**
     * Returns default deduplication window in milliseconds.
     * 
     * <p>Used when idempotency key is present to prevent duplicate processing
     * within the specified time window.
     * 
     * @return deduplication window in ms (0 means no window)
     */
    long dedupeWindowMs();
    
    /**
     * Data classification levels.
     */
    enum DataClassification {
        PUBLIC,
        INTERNAL,
        CONFIDENTIAL,
        PII, // Personally Identifiable Information
        RESTRICTED
    }
}