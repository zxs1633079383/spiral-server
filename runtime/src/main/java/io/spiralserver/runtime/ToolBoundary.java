package io.spiralserver.runtime;

import io.spiralserver.schema.SchemaRef;
import io.spiralserver.schema.ToolSchema;

import java.util.concurrent.CompletableFuture;

/**
 * Tool boundary interface for isolated side effects.
 * 
 * <p>Tool boundary provides:
 * <ul>
 *   <li>Isolated tool invocations (side-effect boundary)</li>
 *   <li>Non-deterministic operations (IO, external APIs)</li>
 *   <li>Tool result recording for replay</li>
 *   <li>Tool guardrail enforcement</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>All side effects go through tool boundary</li>
 *   <li>Tool invocations are recorded for replay</li>
 *   <li>Tool results are deterministic in replay (from recorded results)</li>
 *   <li>Tool guardrails are enforced</li>
 * </ul>
 * 
 * <p><strong>Design Notes:</strong>
 * <ul>
 *   <li>Inspired by Temporal's activity boundary</li>
 *   <li>Tools are the only non-deterministic part of execution</li>
 *   <li>Tool results are stored and replayed deterministically</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface ToolBoundary {
    
    /**
     * Invokes a tool asynchronously.
     * 
     * <p>Tool invocation is non-deterministic (may call external APIs, databases, etc.).
     * Results are recorded for deterministic replay.
     * 
     * @param toolRef tool schema reference
     * @param parameters tool input parameters
     * @param context invocation context (agent instance, correlation, etc.)
     * @return future tool result
     */
    CompletableFuture<ToolResult> invoke(
        SchemaRef toolRef,
        Object parameters,
        InvocationContext context
    );
    
    /**
     * Invokes a tool synchronously (blocking).
     * 
     * <p>Use for deterministic replay or when async is not needed.
     * 
     * @param toolRef tool schema reference
     * @param parameters tool input parameters
     * @param context invocation context
     * @return tool result
     */
    ToolResult invokeSync(
        SchemaRef toolRef,
        Object parameters,
        InvocationContext context
    );
    
    /**
     * Tool invocation result.
     */
    interface ToolResult {
        boolean success();
        Object result(); // tool output (schema-validated)
        String errorMessage(); // null if success
        long durationMs(); // invocation duration
        
        /**
         * Returns cost consumed by this tool invocation.
         * 
         * @return cost in tokens/cost units (0 if not tracked)
         */
        long cost();
        
        /**
         * Returns rate limit information.
         * 
         * @return rate limit status (remaining requests, reset time, etc.)
         */
        java.util.Map<String, String> rateLimitInfo();
    }
    
    /**
     * Tool invocation context.
     */
    interface InvocationContext {
        String agentInstanceId();
        String correlationKey();
        boolean isReplay(); // true if this is a replay
        
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