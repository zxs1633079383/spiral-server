package io.spiralserver.tools;

import io.spiralserver.schema.SchemaRef;
import io.spiralserver.schema.ToolSchema;

import java.util.concurrent.CompletableFuture;

/**
 * Tool Service Provider Interface (SPI) for tool implementations.
 * 
 * <p>Tools provide:
 * <ul>
 *   <li>Side-effect boundaries</li>
 *   <li>Protocol-agnostic tool execution</li>
 *   <li>Tool result validation</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Tools are non-deterministic (may call external systems)</li>
 *   <li>Tool results are schema-validated</li>
 *   <li>Tools are isolated (failures don't crash runtime)</li>
 * </ul>
 * 
 * <p><strong>Implementation Notes:</strong>
 * <ul>
 *   <li>This is an SPI interface</li>
 *   <li>Tool implementations are discovered via Java SPI</li>
 *   <li>Tools are protocol-agnostic (HTTP, gRPC, MCP adapters handle protocol)</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface Tool {
    
    /**
     * Returns the tool schema reference this tool implements.
     * 
     * @return tool schema reference
     */
    SchemaRef getSchemaRef();
    
    /**
     * Executes the tool.
     * 
     * @param parameters tool input parameters (schema-validated)
     * @param context tool execution context
     * @return tool result
     */
    CompletableFuture<ToolResult> execute(Object parameters, ToolContext context);
    
    /**
     * Tool execution result.
     */
    interface ToolResult {
        boolean success();
        Object result(); // schema-validated output
        String errorMessage(); // null if success
        long durationMs();
    }
    
    /**
     * Tool execution context.
     */
    interface ToolContext {
        String agentInstanceId();
        String correlationKey();
        
        /**
         * Returns budget allocated for this tool invocation.
         * 
         * @return budget in tokens/cost units (0 means no limit)
         */
        long budget();
        
        /**
         * Returns applicable policy schema references.
         * 
         * @return list of policy refs
         */
        java.util.List<io.spiralserver.schema.SchemaRef> policies();
        
        /**
         * Returns tenant identifier.
         * 
         * @return tenant ID
         */
        String tenantId();
    }
}