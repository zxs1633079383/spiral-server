package io.spiralserver.schema;

import java.util.List;
import java.util.Map;

/**
 * Schema definition for a Tool (skill).
 * 
 * <p>A tool schema defines:
 * <ul>
 *   <li>Tool interface (input/output schema)</li>
 *   <li>Side-effect boundaries</li>
 *   <li>Protocol binding (HTTP, gRPC, MCP, etc.)</li>
 *   <li>Timeout and retry configuration</li>
 *   <li>Resource requirements</li>
 *   <li>Guardrails (rate limits, cost limits)</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Immutable</li>
 *   <li>Tools are side-effect boundaries</li>
 *   <li>Input/output schemas are well-defined</li>
 *   <li>Protocol bindings are declarative</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface ToolSchema extends Schema {
    
    /**
     * Returns the tool name (unique identifier).
     * 
     * @return tool name
     */
    String name();
    
    /**
     * Returns the input schema (tool parameters).
     * 
     * @return input data schema
     */
    DataSchema inputSchema();
    
    /**
     * Returns the output schema (tool result).
     * 
     * @return output data schema
     */
    DataSchema outputSchema();
    
    /**
     * Returns protocol bindings.
     * 
     * <p>Each tool may have multiple protocol bindings (HTTP endpoint, gRPC service, etc.)
     * 
     * @return list of protocol bindings
     */
    List<ProtocolBinding> protocolBindings();
    
    /**
     * Returns timeout configuration in milliseconds.
     * 
     * @return timeout in ms (0 means no timeout)
     */
    long timeoutMs();
    
    /**
     * Returns retry configuration.
     * 
     * @return retry configuration (nullable if no retries)
     */
    RetryConfig retryConfig();
    
    /**
     * Returns resource requirements (budget, rate limits).
     * 
     * @return resource requirements map
     */
    Map<String, ResourceRequirement> resourceRequirements();

    /**
     * Returns authorization requirements for this tool.
     * 
     * @return authorization configuration (nullable means default)
     */
    Authorization authorization();

    /**
     * Returns whether this tool is read-only (no side effects).
     * 
     * <p>Important for DIRECT_DB bindings to enforce read-only access.
     * 
     * @return true if read-only
     */
    boolean readOnly();
    
    /**
     * Protocol binding definition.
     */
    interface ProtocolBinding {
        ProtocolType protocol();
        Map<String, String> configuration(); // protocol-specific config
        RlsPolicy rlsPolicy(); // Row-level security configuration (nullable)
        
        enum ProtocolType {
            HTTP,
            GRPC,
            MCP,
            DIRECT_DB,
            DICTIONARY
        }
    }
    
    /**
     * Resource requirement (rate limit, budget, etc.).
     */
    interface ResourceRequirement {
        String name();
        String value(); // human-readable value
        // TODO: Define structured requirement types
    }

    /**
     * Authorization configuration for tools.
     */
    interface Authorization {
        List<String> allowedRoles(); // empty means all
        List<String> deniedRoles(); // optional deny list
    }

    /**
     * Row-level security policy.
     */
    interface RlsPolicy {
        String policyName();
        String condition(); // expression applied per row
    }
}