/**
 * Runtime module - Deterministic execution engine.
 * 
 * <p>This module provides:
 * <ul>
 *   <li>Deterministic executor</li>
 *   <li>Pure planner</li>
 *   <li>Saga engine for compensation</li>
 *   <li>Tool boundary (side-effect isolation)</li>
 *   <li>Replay engine</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Runtime cannot access IO directly</li>
 *   <li>All side-effects via events</li>
 *   <li>Replay must be identical</li>
 *   <li>Planner is pure (no side-effects)</li>
 *   <li>Executor is a state machine</li>
 * </ul>
 * 
 * @author SpiralServer Team
 * @version 1.0
 */
package io.spiralserver.runtime;