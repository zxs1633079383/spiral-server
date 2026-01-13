package io.spiralserver.schema;

import java.util.List;
import java.util.Map;

/**
 * Schema definition for a Policy (governance rule).
 * 
 * <p>A policy schema defines:
 * <ul>
 *   <li>Policy scope (which agents/tools/events it applies to)</li>
 *   <li>Policy rules (RBAC, data classification, tool guardrails, budgets)</li>
 *   <li>Enforcement level (advisory, mandatory)</li>
 *   <li>Policy evaluation logic</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Immutable</li>
 *   <li>Policies are enforced, not advisory</li>
 *   <li>All policy decisions are auditable</li>
 *   <li>Policy scope is explicit</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface PolicySchema extends Schema {
    
    /**
     * Returns the policy name (unique identifier).
     * 
     * @return policy name
     */
    String name();
    
    /**
     * Returns policy scope (what this policy applies to).
     * 
     * @return policy scope
     */
    PolicyScope scope();
    
    /**
     * Returns policy type (RBAC, data classification, tool guardrail, budget, etc.).
     * 
     * @return policy type
     */
    PolicyType type();
    
    /**
     * Returns policy rules (structured rule definitions).
     * 
     * <p>TODO: Define structured rule representation (CEL expressions, rule DSL, etc.)
     * 
     * @return policy rules
     */
    // TODO: Define PolicyRule type
    // List<PolicyRule> rules();
    
    /**
     * Returns enforcement level.
     * 
     * @return enforcement level
     */
    EnforcementLevel enforcementLevel();
    
    /**
     * Policy scope definition.
     */
    interface PolicyScope {
        List<SchemaRef> agentRefs(); // empty means all agents
        List<SchemaRef> toolRefs(); // empty means all tools
        List<SchemaRef> eventRefs(); // empty means all events
    }
    
    /**
     * Policy type enumeration.
     */
    enum PolicyType {
        RBAC, // Role-Based Access Control
        DATA_CLASSIFICATION,
        TOOL_GUARDRAIL,
        BUDGET,
        RATE_LIMIT,
        RLS, // Row Level Security
        CUSTOM
    }
    
    /**
     * Enforcement level.
     */
    enum EnforcementLevel {
        ADVISORY, // Log but allow
        MANDATORY // Enforce and reject if violated
    }
}