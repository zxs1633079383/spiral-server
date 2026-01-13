package io.spiralserver.tools;

import io.spiralserver.schema.SchemaRef;

import java.util.List;
import java.util.Optional;

/**
 * Tool registry for tool discovery and management.
 * 
 * <p>Tool registry provides:
 * <ul>
 *   <li>Tool registration and discovery</li>
 *   <li>Tool lookup by schema reference</li>
 *   <li>Tool adapter management</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Tools are registered with their schema references</li>
 *   <li>Tool registry is thread-safe</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface ToolRegistry {
    
    /**
     * Registers a tool.
     * 
     * @param tool tool to register
     */
    void register(Tool tool);
    
    /**
     * Registers a tool adapter.
     * 
     * @param adapter tool adapter
     */
    void registerAdapter(ToolAdapter adapter);
    
    /**
     * Looks up a tool by schema reference.
     * 
     * @param toolRef tool schema reference
     * @return tool if found
     */
    Optional<Tool> lookup(SchemaRef toolRef);
    
    /**
     * Lists all registered tools.
     * 
     * @return list of tools
     */
    List<Tool> listTools();
}