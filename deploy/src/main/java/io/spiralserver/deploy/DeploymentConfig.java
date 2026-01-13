package io.spiralserver.deploy;

import java.util.Map;

/**
 * Deployment configuration abstraction.
 * 
 * <p>Deployment configuration provides:
 * <ul>
 *   <li>Environment-specific configuration</li>
 *   <li>Resource allocation</li>
 *   <li>Scaling configuration</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Configuration is environment-specific</li>
 *   <li>Configuration is validated before deployment</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface DeploymentConfig {
    
    /**
     * Returns the deployment environment.
     * 
     * @return environment name
     */
    String getEnvironment();
    
    /**
     * Returns configuration properties.
     * 
     * @return configuration map
     */
    Map<String, String> getProperties();
    
    /**
     * Returns resource allocation configuration.
     * 
     * @return resource configuration
     */
    ResourceConfig getResourceConfig();

    /**
     * Returns rollout strategy configuration.
     * 
     * @return rollout strategy
     */
    RolloutStrategy getRolloutStrategy();
    
    /**
     * Resource configuration.
     */
    interface ResourceConfig {
        int minInstances();
        int maxInstances();
        int cpuMillis(); // CPU allocation in millicores
        long memoryBytes(); // Memory allocation in bytes
    }
}