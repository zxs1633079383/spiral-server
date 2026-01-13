package io.spiralserver.schema;

import java.util.Objects;

/**
 * Cryptographic digest for schema content pinning.
 * 
 * <p>Digests ensure immutable execution by pinning exact schema content.
 * Used for deterministic replay and security validation.
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Immutable</li>
 *   <li>Digest algorithm is always specified</li>
 *   <li>Digest value is hex-encoded</li>
 *   <li>Two schemas with same digest are byte-for-byte identical</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public final class Digest {
    
    private final String algorithm; // e.g., "SHA-256"
    private final String value; // hex-encoded digest
    
    /**
     * Creates a new digest.
     * 
     * @param algorithm hash algorithm (e.g., "SHA-256")
     * @param value hex-encoded digest value
     * @throws IllegalArgumentException if algorithm or value is null/empty
     */
    public Digest(String algorithm, String value) {
        if (algorithm == null || algorithm.isBlank()) {
            throw new IllegalArgumentException("Algorithm cannot be null or blank");
        }
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Digest value cannot be null or blank");
        }
        this.algorithm = algorithm;
        this.value = value;
    }
    
    public String algorithm() {
        return algorithm;
    }
    
    public String value() {
        return value;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Digest digest = (Digest) o;
        return algorithm.equals(digest.algorithm) && value.equals(digest.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(algorithm, value);
    }
    
    @Override
    public String toString() {
        return algorithm + ":" + value;
    }
}