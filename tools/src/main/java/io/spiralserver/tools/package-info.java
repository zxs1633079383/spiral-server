/**
 * Tools module - Tool SPI and adapters.
 * 
 * <p>This module provides:
 * <ul>
 *   <li>Tool Service Provider Interface (SPI)</li>
 *   <li>Built-in tool implementations</li>
 *   <li>External adapters (HTTP, gRPC, MCP)</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Tools are non-deterministic and isolated</li>
 *   <li>Tools define side-effect boundaries</li>
 * </ul>
 * 
 * @author SpiralServer Team
 * @version 1.0
 */
package io.spiralserver.tools;