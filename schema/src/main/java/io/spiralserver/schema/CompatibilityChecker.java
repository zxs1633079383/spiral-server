package io.spiralserver.schema;

import java.util.List;

/**
 * Checks compatibility between schema versions.
 * 
 * <p>Compatibility checks ensure:
 * <ul>
 *   <li>Backward compatibility (new version can replace old version)</li>
 *   <li>Forward compatibility (old version can work with new runtime)</li>
 *   <li>Breaking change detection</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Compatibility checking is deterministic</li>
 *   <li>Same major version implies backward compatibility</li>
 *   <li>Different major versions imply breaking changes</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface CompatibilityChecker {
    
    /**
     * Checks if newSchema is backward compatible with oldSchema.
     * 
     * <p>Backward compatibility means newSchema can be used where oldSchema is expected.
     * 
     * @param oldSchema old schema version
     * @param newSchema new schema version
     * @return compatibility result
     */
    CompatibilityResult checkBackwardCompatibility(Schema oldSchema, Schema newSchema);
    
    /**
     * Checks if oldSchema is forward compatible with newSchema.
     * 
     * <p>Forward compatibility means oldSchema can work with a runtime expecting newSchema.
     * 
     * @param oldSchema old schema version
     * @param newSchema new schema version
     * @return compatibility result
     */
    CompatibilityResult checkForwardCompatibility(Schema oldSchema, Schema newSchema);
    
    /**
     * Compatibility check result.
     */
    interface CompatibilityResult {
        boolean isCompatible();
        List<String> breakingChanges(); // empty if compatible
        List<String> warnings(); // non-breaking changes
        
        static CompatibilityResult compatible() {
            return new CompatibilityResult() {
                @Override
                public boolean isCompatible() {
                    return true;
                }
                
                @Override
                public List<String> breakingChanges() {
                    return List.of();
                }
                
                @Override
                public List<String> warnings() {
                    return List.of();
                }
            };
        }
        
        static CompatibilityResult incompatible(List<String> breakingChanges, List<String> warnings) {
            return new CompatibilityResult() {
                @Override
                public boolean isCompatible() {
                    return false;
                }
                
                @Override
                public List<String> breakingChanges() {
                    return breakingChanges;
                }
                
                @Override
                public List<String> warnings() {
                    return warnings;
                }
            };
        }
    }
}