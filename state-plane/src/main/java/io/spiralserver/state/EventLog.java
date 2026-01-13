package io.spiralserver.state;

import io.spiralserver.schema.EventSchema;
import io.spiralserver.schema.SchemaRef;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Event log abstraction for immutable event storage.
 * 
 * <p>Event log provides:
 * <ul>
 *   <li>Append-only event storage</li>
 *   <li>Event retrieval by cursor/offset</li>
 *   <li>Event filtering and correlation</li>
 *   <li>Idempotency checking</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Events are immutable once written</li>
 *   <li>Events are ordered by sequence</li>
 *   <li>Event log is append-only (no updates/deletes)</li>
 *   <li>All events are schema-validated</li>
 * </ul>
 * 
 * <p><strong>Implementation Notes:</strong>
 * <ul>
 *   <li>This is an interface-only definition</li>
 *   <li>Concrete implementations will be storage-agnostic</li>
 *   <li>No storage engine is assumed</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface EventLog {
    
    /**
     * Appends an event to the log.
     * 
     * <p>Event must be validated against its schema before appending.
     * Idempotency is checked if idempotency key is present.
     * 
     * @param event event to append
     * @return event sequence number
     * @throws IllegalArgumentException if event is invalid or duplicate
     */
    long append(Event event);
    
    /**
     * Retrieves events starting from a cursor.
     * 
     * @param cursor starting cursor (exclusive)
     * @param limit maximum number of events to retrieve
     * @return list of events
     */
    List<Event> read(Cursor cursor, int limit);
    
    /**
     * Retrieves events by correlation key.
     * 
     * @param correlationKey correlation key value
     * @param eventSchemaRef optional event schema filter
     * @param limit maximum number of events
     * @return list of correlated events
     */
    List<Event> readByCorrelation(String correlationKey, Optional<SchemaRef> eventSchemaRef, int limit);
    
    /**
     * Checks if an event with the given idempotency key already exists.
     * 
     * @param idempotencyKey idempotency key
     * @param dedupeWindowMs deduplication window in milliseconds
     * @return existing event if found, empty otherwise
     */
    Optional<Event> findByIdempotencyKey(String idempotencyKey, long dedupeWindowMs);
    
    /**
     * Returns the current cursor (highest sequence number).
     * 
     * @return current cursor
     */
    Cursor currentCursor();
    
    /**
     * Event representation.
     */
    interface Event {
        long sequence(); // unique, monotonically increasing
        SchemaRef eventSchemaRef();
        Instant timestamp();
        String correlationKey(); // nullable
        String idempotencyKey(); // nullable
        Object payload(); // schema-validated event data
        String source(); // event source identifier
    }
}