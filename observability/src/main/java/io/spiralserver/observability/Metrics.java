package io.spiralserver.observability;

import java.util.Map;

/**
 * Metrics abstraction for telemetry collection.
 * 
 * <p>Metrics provides:
 * <ul>
 *   <li>Counter metrics</li>
 *   <li>Gauge metrics</li>
 *   <li>Histogram metrics</li>
 *   <li>Metrics export (OpenTelemetry compatible)</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Metrics collection does not affect execution determinism</li>
 *   <li>Metrics are aggregated asynchronously</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface Metrics {
    
    /**
     * Increments a counter metric.
     * 
     * @param name metric name
     * @param value increment value
     * @param attributes metric attributes (labels)
     */
    void incrementCounter(String name, long value, Map<String, String> attributes);
    
    /**
     * Records a gauge value.
     * 
     * @param name metric name
     * @param value gauge value
     * @param attributes metric attributes
     */
    void recordGauge(String name, double value, Map<String, String> attributes);
    
    /**
     * Records a histogram value.
     * 
     * @param name metric name
     * @param value histogram value
     * @param attributes metric attributes
     */
    void recordHistogram(String name, double value, Map<String, String> attributes);

    /**
     * Metrics field mapping configuration.
     * 
     * @return mapping config (nullable means default)
     */
    default Map<String, String> fieldMappings() {
        return Map.of();
    }
}