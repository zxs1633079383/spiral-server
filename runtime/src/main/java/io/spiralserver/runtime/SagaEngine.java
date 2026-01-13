package io.spiralserver.runtime;

import io.spiralserver.schema.RetryConfig;

import java.util.List;

/**
 * Saga engine for distributed transaction compensation.
 * 
 * <p>Saga engine provides:
 * <ul>
 *   <li>Compensation actions for failed transactions</li>
 *   <li>Long-running transaction management</li>
 *   <li>Compensation orchestration</li>
 * </ul>
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Compensation is deterministic</li>
 *   <li>Compensation actions are idempotent</li>
 *   <li>Compensation is replayable</li>
 * </ul>
 * 
 * <p><strong>Design Notes:</strong>
 * <ul>
 *   <li>Inspired by Saga pattern for distributed transactions</li>
 *   <li>Compensation actions are defined in agent schema</li>
 *   <li>Compensation is part of deterministic execution</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public interface SagaEngine {
    
    /**
     * Executes a saga (sequence of actions with compensation).
     * 
     * <p>If any action fails, compensation actions are executed in reverse order.
     * 
     * @param saga saga definition
     * @param toolBoundary tool boundary for action execution
     * @return saga execution result
     */
    SagaResult execute(Saga saga, ToolBoundary toolBoundary);
    
    /**
     * Saga definition.
     */
    interface Saga {
        String sagaId();
        List<SagaStep> steps();
        
        /**
         * Saga step (action + compensation).
         */
        interface SagaStep {
            String stepId();
            Planner.Plan.Action action(); // action to execute
            Planner.Plan.Action compensation(); // compensation action (nullable)
            RetryConfig retryConfig(); // retry/backoff configuration (nullable)
        }
    }
    
    /**
     * Saga execution result.
     */
    interface SagaResult {
        SagaStatus status();
        List<CompensatedStep> compensatedSteps(); // steps that were compensated
        
        enum SagaStatus {
            COMPLETED, // all steps succeeded
            COMPENSATED, // some steps failed and were compensated
            FAILED // compensation failed
        }
        
        /**
         * Step that was compensated.
         */
        interface CompensatedStep {
            String stepId();
            String reason(); // why compensation was needed
        }
    }
}