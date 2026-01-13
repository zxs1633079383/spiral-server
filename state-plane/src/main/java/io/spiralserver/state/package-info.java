/**
 * State Plane module - State management abstraction.
 * 
 * <p>This module provides:
 * <ul>
 *   <li>Hot state management</li>
 *   <li>Event log abstraction</li>
 *   <li>Snapshot & cursor management</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>State Plane is independent of storage implementation</li>
 *   <li>State operations are isolated from runtime</li>
 * </ul>
 * 
 * @author SpiralServer Team
 * @version 1.0
 */
package io.spiralserver.state;