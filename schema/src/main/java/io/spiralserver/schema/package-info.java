/**
 * Schema module - Core schema definitions and validation.
 * 
 * <p>This module defines the contract for:
 * <ul>
 *   <li>Agent schemas</li>
 *   <li>Event schemas</li>
 *   <li>Tool schemas</li>
 *   <li>Policy schemas</li>
 *   <li>Versioning and digest pinning</li>
 *   <li>Backward compatibility checks</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>All schemas are immutable</li>
 *   <li>All schemas are versioned</li>
 *   <li>No execution without validation</li>
 * </ul>
 * 
 * @author SpiralServer Team
 * @version 1.0
 */
package io.spiralserver.schema;