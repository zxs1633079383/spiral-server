package io.spiralserver.governance;

import io.spiralserver.schema.PolicySchema;

/**
 * Enforcement hook for policy enforcement points.
 * 
 * <p>Enforcement hooks provide integration points for:
 * <ul>
 *   <li>Pre-execution policy checks</li>
 *   <li>Post-execution policy validation</li>
 *   <li>Runtime policy enforcement</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Enforcement is mandatory, not optional</li>
 *   <li>All enforcement actions are auditable</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface EnforcementHook {
    
    /**
     * Hook point before agent execution.
     * 
     * @param context enforcement context
     * @return enforcement result (true to allow, false to deny)
     */
    boolean beforeExecution(EnforcementContext context);
    
    /**
     * Hook point after agent execution.
     * 
     * @param context enforcement context
     * @param result execution result
     */
    void afterExecution(EnforcementContext context, Object result);
    
    /**
     * Enforcement context.
     */
    interface EnforcementContext {
        String agentInstanceId();
        String action();
        Object data();
        
        /**
         * Returns tenant identifier.
         * 
         * @return tenant ID
         */
        String tenantId();
        
        /**
         * Returns user identifier.
         * 
         * @return user ID (nullable)
         */
        String userId();
        
        /**
         * Returns applicable policy schema references.
         * 
         * @return list of policy refs
         */
        java.util.List<io.spiralserver.schema.SchemaRef> policies();
    }
}