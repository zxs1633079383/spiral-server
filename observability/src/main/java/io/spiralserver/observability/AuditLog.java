package io.spiralserver.observability;

import java.time.Instant;
import java.util.Map;

/**
 * Audit log for security and compliance auditing.
 * 
 * <p>Audit log provides:
 * <ul>
 *   <li>Audit event recording</li>
 *   <li>Audit event querying</li>
 *   <li>Compliance reporting</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>All audit events are immutable</li>
 *   <li>Audit log is append-only</li>
 *   <li>Audit events are tamper-proof</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface AuditLog {
    
    /**
     * Records an audit event.
     * 
     * @param event audit event
     */
    void record(AuditEvent event);
    
    /**
     * Queries audit events.
     * 
     * @param query audit query
     * @return list of audit events
     */
    java.util.List<AuditEvent> query(AuditQuery query);
    
    /**
     * Audit event.
     */
    interface AuditEvent {
        String eventId();
        AuditEventType type();
        String actor(); // user, agent, system
        String action();
        String resource(); // resource being acted upon
        Instant timestamp();
        Map<String, String> attributes();
        AuditResult result(); // success, failure, etc.
    }
    
    /**
     * Audit event type.
     */
    enum AuditEventType {
        ACCESS, // resource access
        EXECUTION, // agent execution
        POLICY_DECISION, // policy evaluation
        DATA_ACCESS, // data access
        CONFIGURATION_CHANGE // configuration change
    }
    
    /**
     * Audit result.
     */
    enum AuditResult {
        SUCCESS,
        FAILURE,
        DENIED
    }
    
    /**
     * Audit query.
     */
    interface AuditQuery {
        String actor(); // nullable
        AuditEventType type(); // nullable
        String resource(); // nullable
        Instant fromTime(); // nullable
        Instant toTime(); // nullable
        Map<String, String> filters(); // custom filters (field -> value/pattern)
    }

    /**
     * Audit policy configuration.
     */
    interface AuditPolicy {
        boolean enabled();
        double samplingRate(); // 0.0 - 1.0
        Map<String, String> rules(); // rule-based audit controls
    }
}