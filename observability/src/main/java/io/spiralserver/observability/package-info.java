/**
 * Observability module - Tracing, metrics, and audit.
 * 
 * <p>This module provides:
 * <ul>
 *   <li>OpenTelemetry integration (abstraction)</li>
 *   <li>Trace models</li>
 *   <li>Metrics models</li>
 *   <li>Audit log models</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Observability does not affect execution determinism</li>
 * </ul>
 * 
 * @author SpiralServer Team
 * @version 1.0
 */
package io.spiralserver.observability;