package io.spiralserver.governance;

import java.time.Instant;
import java.util.List;

/**
 * Lineage tracker for data lineage and audit trails.
 * 
 * <p>Lineage tracker provides:
 * <ul>
 *   <li>Data lineage tracking</li>
 *   <li>Audit trail recording</li>
 *   <li>Lineage querying</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>All lineage records are immutable</li>
 *   <li>Lineage is complete and auditable</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface LineageTracker {
    
    /**
     * Records a lineage entry.
     * 
     * @param lineage lineage entry
     */
    void record(LineageEntry lineage);
    
    /**
     * Queries lineage for a data item.
     * 
     * @param dataId data identifier
     * @return lineage chain
     */
    List<LineageEntry> queryLineage(String dataId);
    
    /**
     * Lineage entry.
     */
    interface LineageEntry {
        String entryId();
        String dataId();
        String source(); // source of data (agent, tool, etc.)
        String operation(); // operation performed
        Instant timestamp();
        List<String> upstreamDataIds(); // upstream data dependencies
    }
}