package io.spiralserver.control;

import io.spiralserver.schema.AgentSchema;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Scheduler for agent execution scheduling.
 * 
 * <p>Scheduler provides:
 * <ul>
 *   <li>Time-based scheduling (cron, interval)</li>
 *   <li>Event-driven scheduling</li>
 *   <li>Schedule management</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Schedules are deterministic</li>
 *   <li>Schedule execution is auditable</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface Scheduler {
    
    /**
     * Schedules an agent instance for execution.
     * 
     * @param agentInstanceId agent instance identifier
     * @param schedule schedule definition
     * @return schedule identifier
     */
    String schedule(String agentInstanceId, Schedule schedule);
    
    /**
     * Cancels a schedule.
     * 
     * @param scheduleId schedule identifier
     * @return true if cancelled
     */
    boolean cancel(String scheduleId);
    
    /**
     * Lists schedules for an agent instance.
     * 
     * @param agentInstanceId agent instance identifier
     * @return list of schedules
     */
    List<Schedule> listSchedules(String agentInstanceId);
    
    /**
     * Schedule definition.
     */
    interface Schedule {
        String scheduleId();
        String agentInstanceId();
        ScheduleType type();
        String expression(); // cron expression, interval, etc.
        Instant startTime(); // nullable
        Instant endTime(); // nullable
        boolean enabled();
        
        enum ScheduleType {
            CRON, // cron expression
            INTERVAL, // fixed interval
            ONCE // execute once at specific time
        }
    }
}