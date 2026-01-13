package io.spiralserver.observability;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Tracing abstraction for distributed tracing.
 * 
 * <p>Tracing provides:
 * <ul>
 *   <li>Span creation and management</li>
 *   <li>Trace context propagation</li>
 *   <li>Trace export (OpenTelemetry compatible)</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Tracing does not affect execution determinism</li>
 *   <li>Traces are immutable once completed</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface Trace {
    
    /**
     * Creates a new span.
     * 
     * @param name span name
     * @param parentSpanId parent span ID (nullable for root span)
     * @param attributes span attributes
     * @return span
     */
    Span createSpan(String name, String parentSpanId, Map<String, String> attributes);
    
    /**
     * Completes a span.
     * 
     * @param spanId span identifier
     * @param status span status
     */
    void completeSpan(String spanId, SpanStatus status);
    
    /**
     * Span representation.
     */
    interface Span {
        String spanId();
        String traceId();
        String parentSpanId(); // nullable
        String name();
        Instant startTime();
        Instant endTime(); // nullable until completed
        SpanStatus status();
        Map<String, String> attributes();
        List<SpanEvent> events();
        Sampling sampling(); // sampling configuration for this span
        
        /**
         * Adds an event to the span.
         */
        void addEvent(String name, Instant timestamp, Map<String, String> attributes);
    }
    
    /**
     * Span status.
     */
    enum SpanStatus {
        OK,
        ERROR
    }
    
    /**
     * Span event.
     */
    interface SpanEvent {
        String name();
        Instant timestamp();
        Map<String, String> attributes();
    }

    /**
     * Sampling configuration for traces/spans.
     */
    interface Sampling {
        double rate(); // 0.0 - 1.0
        Map<String, String> rules(); // optional rule-based sampling
    }
}