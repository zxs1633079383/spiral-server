package io.spiralserver.schema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Result of schema validation.
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Immutable</li>
 *   <li>If valid, error list is empty</li>
 *   <li>If invalid, error list contains at least one error</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public final class ValidationResult {
    
    private final boolean valid;
    private final List<ValidationError> errors;
    
    private ValidationResult(boolean valid, List<ValidationError> errors) {
        this.valid = valid;
        this.errors = Collections.unmodifiableList(new ArrayList<>(errors));
    }
    
    /**
     * Creates a valid validation result.
     * 
     * @return valid result
     */
    public static ValidationResult valid() {
        return new ValidationResult(true, Collections.emptyList());
    }
    
    /**
     * Creates an invalid validation result with errors.
     * 
     * @param errors validation errors
     * @return invalid result
     * @throws IllegalArgumentException if errors is empty
     */
    public static ValidationResult invalid(List<ValidationError> errors) {
        if (errors == null || errors.isEmpty()) {
            throw new IllegalArgumentException("Invalid result must have at least one error");
        }
        return new ValidationResult(false, errors);
    }
    
    /**
     * Creates an invalid validation result with a single error.
     * 
     * @param error validation error
     * @return invalid result
     */
    public static ValidationResult invalid(ValidationError error) {
        return invalid(List.of(error));
    }
    
    public boolean isValid() {
        return valid;
    }
    
    public List<ValidationError> errors() {
        return errors;
    }
    
    /**
     * Validation error with context.
     */
    public static final class ValidationError {
        private final String code;
        private final String message;
        private final String path; // JSON path or field path
        
        public ValidationError(String code, String message, String path) {
            this.code = code;
            this.message = message;
            this.path = path;
        }
        
        public String code() {
            return code;
        }
        
        public String message() {
            return message;
        }
        
        public String path() {
            return path;
        }
        
        @Override
        public String toString() {
            return String.format("[%s] %s (path: %s)", code, message, path);
        }
    }
}