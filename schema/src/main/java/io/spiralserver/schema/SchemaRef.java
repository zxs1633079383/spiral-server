package io.spiralserver.schema;

import java.util.Objects;

/**
 * Reference to a schema using URI-like syntax.
 * 
 * <p>Schema references follow the pattern: ref://schemas/{type}/{name}/{version}?digest={digest}
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Immutable</li>
 *   <li>Reference format is validated</li>
 *   <li>Digest is optional but recommended for pinning</li>
 * </ul>
 * 
 * <p>Examples:
 * <ul>
 *   <li>ref://schemas/agent/my-agent/1.0.0</li>
 *   <li>ref://schemas/event/user-action/2.1.0?digest=SHA-256:abc123...</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public final class SchemaRef {
    
    private final String type; // "agent", "event", "tool", "policy"
    private final String name;
    private final Version version;
    private final Digest digest; // optional, for pinning
    
    /**
     * Creates a schema reference.
     * 
     * @param type schema type (agent, event, tool, policy)
     * @param name schema name
     * @param version schema version
     * @param digest optional digest for pinning
     * @throws IllegalArgumentException if type, name, or version is invalid
     */
    public SchemaRef(String type, String name, Version version, Digest digest) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Schema type cannot be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Schema name cannot be null or blank");
        }
        if (version == null) {
            throw new IllegalArgumentException("Schema version cannot be null");
        }
        this.type = type;
        this.name = name;
        this.version = version;
        this.digest = digest;
    }
    
    /**
     * Creates a schema reference without digest.
     */
    public SchemaRef(String type, String name, Version version) {
        this(type, name, version, null);
    }
    
    /**
     * Parses a schema reference from URI string.
     * 
     * <p>Supports formats:
     * <ul>
     *   <li>ref://schemas/{type}/{name}/{version}</li>
     *   <li>ref://schemas/{type}/{name}/{version}?digest={algorithm}:{value}</li>
     * </ul>
     * 
     * @param refString reference string (e.g., "ref://schemas/agent/my-agent/1.0.0")
     * @return SchemaRef instance
     * @throws IllegalArgumentException if format is invalid
     */
    public static SchemaRef parse(String refString) {
        if (refString == null || refString.isBlank()) {
            throw new IllegalArgumentException("Schema reference string cannot be null or blank");
        }
        
        String trimmed = refString.trim();
        if (!trimmed.startsWith("ref://schemas/")) {
            throw new IllegalArgumentException(
                "Schema reference must start with 'ref://schemas/', got: " + refString
            );
        }
        
        String rest = trimmed.substring("ref://schemas/".length());
        
        // Split query parameters
        String[] parts = rest.split("\\?", 2);
        String path = parts[0];
        String query = parts.length > 1 ? parts[1] : null;
        
        // Parse path: type/name/version
        String[] pathParts = path.split("/");
        if (pathParts.length != 3) {
            throw new IllegalArgumentException(
                "Schema reference path must be 'type/name/version', got: " + path
            );
        }
        
        String type = pathParts[0];
        String name = pathParts[1];
        Version version = Version.parse(pathParts[2]);
        
        // Parse digest from query string
        Digest digest = null;
        if (query != null && !query.isBlank()) {
            if (query.startsWith("digest=")) {
                String digestValue = query.substring("digest=".length());
                digest = parseDigest(digestValue);
            }
        }
        
        return new SchemaRef(type, name, version, digest);
    }
    
    private static Digest parseDigest(String digestString) {
        int colonIndex = digestString.indexOf(':');
        if (colonIndex < 0) {
            throw new IllegalArgumentException(
                "Digest must be in format 'algorithm:value', got: " + digestString
            );
        }
        String algorithm = digestString.substring(0, colonIndex);
        String value = digestString.substring(colonIndex + 1);
        return new Digest(algorithm, value);
    }
    
    public String type() {
        return type;
    }
    
    public String name() {
        return name;
    }
    
    public Version version() {
        return version;
    }
    
    public Digest digest() {
        return digest;
    }
    
    public boolean hasDigest() {
        return digest != null;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchemaRef schemaRef = (SchemaRef) o;
        return type.equals(schemaRef.type) 
            && name.equals(schemaRef.name) 
            && version.equals(schemaRef.version)
            && Objects.equals(digest, schemaRef.digest);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(type, name, version, digest);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ref://schemas/")
            .append(type).append("/").append(name).append("/").append(version);
        if (digest != null) {
            sb.append("?digest=").append(digest);
        }
        return sb.toString();
    }
}