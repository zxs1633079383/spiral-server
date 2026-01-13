/**
 * Control Plane module - Agent orchestration and routing.
 * 
 * <p>This module provides:
 * <ul>
 *   <li>Agent registry</li>
 *   <li>Event router with CEL filtering</li>
 *   <li>Scheduler</li>
 *   <li>Admission control</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Control Plane manages schemas and policies</li>
 *   <li>Runtime is blind to control decisions</li>
 * </ul>
 * 
 * @author SpiralServer Team
 * @version 1.0
 */
package io.spiralserver.control;