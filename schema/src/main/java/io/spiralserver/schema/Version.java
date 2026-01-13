package io.spiralserver.schema;

import java.util.Objects;

/**
 * Semantic version for schemas.
 * 
 * <p>Follows semantic versioning (major.minor.patch) for schema compatibility checks.
 * 
 * <p><strong>Invariants:</strong>
 * <ul>
 *   <li>Immutable</li>
 *   <li>All components are non-negative integers</li>
 *   <li>Major version changes indicate breaking changes</li>
 *   <li>Minor version changes indicate backward-compatible additions</li>
 *   <li>Patch version changes indicate backward-compatible fixes</li>
 * </ul>
 * 
 * @author SpiralServer Team
 */
public final class Version implements Comparable<Version> {
    
    private final int major;
    private final int minor;
    private final int patch;
    
    /**
     * Creates a new version.
     * 
     * @param major major version (non-negative)
     * @param minor minor version (non-negative)
     * @param patch patch version (non-negative)
     * @throws IllegalArgumentException if any component is negative
     */
    public Version(int major, int minor, int patch) {
        if (major < 0 || minor < 0 || patch < 0) {
            throw new IllegalArgumentException("Version components must be non-negative");
        }
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }
    
    /**
     * Parses a version string in format "major.minor.patch".
     * 
     * @param versionString version string
     * @return Version instance
     * @throws IllegalArgumentException if format is invalid
     */
    public static Version parse(String versionString) {
        // TODO: Implement parsing
        throw new UnsupportedOperationException("Version parsing not yet implemented");
    }
    
    public int major() {
        return major;
    }
    
    public int minor() {
        return minor;
    }
    
    public int patch() {
        return patch;
    }
    
    /**
     * Checks if this version is backward compatible with the other version.
     * 
     * <p>Backward compatibility means this version can be used where the other version is expected.
     * Same major version implies backward compatibility.
     * 
     * @param other the version to check compatibility with
     * @return true if backward compatible
     */
    public boolean isBackwardCompatibleWith(Version other) {
        return this.major == other.major;
    }
    
    @Override
    public int compareTo(Version o) {
        int cmp = Integer.compare(major, o.major);
        if (cmp != 0) return cmp;
        cmp = Integer.compare(minor, o.minor);
        if (cmp != 0) return cmp;
        return Integer.compare(patch, o.patch);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version = (Version) o;
        return major == version.major && minor == version.minor && patch == version.patch;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch);
    }
    
    @Override
    public String toString() {
        return major + "." + minor + "." + patch;
    }
}