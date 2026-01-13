package io.spiralserver.tools;

import io.spiralserver.schema.ToolSchema;

/**
 * Tool adapter for protocol-specific tool implementations.
 * 
 * <p>Tool adapters provide:
 * <ul>
 *   <li>Protocol binding (HTTP, gRPC, MCP, etc.)</li>
 *   <li>Protocol-to-tool translation</li>
 *   <li>Tool result translation</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Adapters are protocol-specific</li>
 *   <li>Adapters validate input/output against tool schema</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface ToolAdapter {
    
    /**
     * Returns the protocol type this adapter handles.
     * 
     * @return protocol type
     */
    ToolSchema.ProtocolBinding.ProtocolType getProtocolType();
    
    /**
     * Creates a tool instance from a tool schema.
     * 
     * @param toolSchema tool schema
     * @return tool instance
     * @throws IllegalArgumentException if tool schema is not compatible
     */
    Tool createTool(ToolSchema toolSchema);
    
    /**
     * Validates that a tool schema is compatible with this adapter.
     * 
     * @param toolSchema tool schema
     * @return true if compatible
     */
    boolean isCompatible(ToolSchema toolSchema);
}