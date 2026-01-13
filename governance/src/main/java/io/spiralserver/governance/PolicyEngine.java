package io.spiralserver.governance;

import io.spiralserver.schema.PolicySchema;
import io.spiralserver.schema.SchemaRef;

import java.util.List;

/**
 * Policy engine for policy evaluation and enforcement.
 * 
 * <p>Policy engine provides:
 * <ul>
 *   <li>Policy evaluation</li>
 *   <li>Policy enforcement</li>
 *   <li>Policy decision auditing</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Policy evaluation is deterministic</li>
 *   <li>All policy decisions are auditable</li>
 *   <li>Policies are enforced, not advisory</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface PolicyEngine {
    
    /**
     * Evaluates policies for a given context.
     * 
     * @param context policy evaluation context
     * @return policy evaluation result
     */
    PolicyEvaluationResult evaluate(PolicyContext context);
    
    /**
     * Registers a policy.
     * 
     * @param policySchema policy schema
     */
    void register(PolicySchema policySchema);
    
    /**
     * Finds applicable policies for a context.
     * 
     * @param context policy context
     * @return list of applicable policy references
     */
    List<SchemaRef> findApplicablePolicies(PolicyContext context);
    
    /**
     * Policy evaluation context.
     */
    interface PolicyContext {
        String agentInstanceId(); // nullable
        SchemaRef agentRef(); // nullable
        SchemaRef toolRef(); // nullable
        SchemaRef eventRef(); // nullable
        String action(); // action being evaluated
        Object data(); // context data
    }
    
    /**
     * Policy evaluation result.
     */
    interface PolicyEvaluationResult {
        boolean allowed();
        List<PolicyDecision> decisions(); // individual policy decisions
        List<String> violations(); // policy violations
        
        /**
         * Individual policy decision.
         */
        interface PolicyDecision {
            SchemaRef policyRef();
            boolean allowed();
            String reason();
        }
    }
}