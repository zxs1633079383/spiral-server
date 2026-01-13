package io.spiralserver.control;

import io.spiralserver.schema.AgentSchema;
import io.spiralserver.schema.SchemaRef;

import java.util.List;

/**
 * Admission control for agent execution.
 * 
 * <p>Admission control provides:
 * <ul>
 *   <li>Pre-execution validation</li>
 *   <li>Resource quota checking</li>
 *   <li>Policy enforcement</li>
 *   <li>Rate limiting</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Admission decisions are auditable</li>
 *   <li>Admission is enforced before execution</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface AdmissionControl {
    
    /**
     * Checks if an agent instance can be created.
     * 
     * @param agentRef agent schema reference
     * @param context admission context
     * @return admission result
     */
    AdmissionResult checkCreate(SchemaRef agentRef, AdmissionContext context);
    
    /**
     * Checks if an agent instance can execute.
     * 
     * @param agentInstanceId agent instance identifier
     * @param context admission context
     * @return admission result
     */
    AdmissionResult checkExecute(String agentInstanceId, AdmissionContext context);
    
    /**
     * Admission result.
     */
    interface AdmissionResult {
        boolean allowed();
        String reason(); // reason if not allowed
        List<String> violations(); // policy violations
    }   
    
    /**
     * Admission context.
     */
    interface AdmissionContext {
        String tenantId(); // multi-tenant support
        
        /**
         * Returns user identifier requesting admission.
         * 
         * @return user ID (nullable)
         */
        String userId();
        
        /**
         * Returns resource limits (CPU, memory, etc.).
         * 
         * @return resource limits map
         */
        java.util.Map<String, String> resourceLimits();
        
        /**
         * Returns current resource usage.
         * 
         * @return resource usage map
         */
        java.util.Map<String, String> resourceUsage();
    }
}