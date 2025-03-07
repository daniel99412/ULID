package com.dpardo.ulid;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Locale;
import java.util.Objects;

/**
 * ULID (Universally Unique Lexicographically Sortable Identifier).
 * This class represents a ULID, which is a 128-bit identifier composed of:
 * <ul>
 *     <li>A timestamp (most significant bits)</li>
 *     <li>A randomly generated component (least significant bits)</li>
 * </ul>
 * ULIDs are lexicographically sortable and can be used as unique identifiers in distributed systems.
 *
 * This class provides methods for:
 * <ul>
 *     <li>Generating new ULIDs</li>
 *     <li>Parsing ULIDs from strings</li>
 *     <li>Extracting timestamp and random components</li>
 *     <li>Comparing, encoding, and formatting ULIDs</li>
 * </ul>
 */
public class ULID implements Serializable, Comparable<ULID> {
    /** SecureRandom instance for generating the random component of the ULID */
    private static final SecureRandom RANDOM = new SecureRandom();
    /** Most significant bits (timestamp) */
    private final long msb;
    /** Least significant bits (random part) */
    private final long lsb;

    /**
     * Private constructor to create a ULID from its components.
     *
     * @param msb The most significant bits (timestamp).
     * @param lsb The least significant bits (random component).
     */
    private ULID(long msb, long lsb) {
        this.msb = msb;
        this.lsb = lsb;
    }

    /**
     * Creates a new ULID from the given most and least significant bits.
     *
     * @param msb The most significant bits (timestamp).
     * @param lsb The least significant bits (random component).
     * @return A new ULID instance.
     */
    public static ULID of(long msb, long lsb) {
        return new ULID(msb, lsb);
    }

    /**
     * Generates a new ULID using the current timestamp and a randomly generated component.
     *
     * @return A new ULID instance.
     */
    public static ULID getUlid() {
        long timestamp = Instant.now().toEpochMilli();
        long randomPart = RANDOM.nextLong();
        return new ULID(timestamp, randomPart);
    }

    /**
     * Parses a ULID from its string representation.
     *
     * @param ulidString The ULID string to parse.
     * @return A ULID instance representing the given string.
     * @throws IllegalArgumentException If the string format is invalid.
     */
    public static ULID fromString(String ulidString) {
        return ULIDUtils.parseUlid(ulidString);
    }

    /**
     * Generates a ULID from a given {@link Instant}.
     *
     * @param instant The timestamp to use for the ULID.
     * @return A new ULID instance.
     */
    public static ULID fromInstant(Instant instant) {
        long timestamp = instant.toEpochMilli();
        long randomPart = RANDOM.nextLong();
        return new ULID(timestamp, randomPart);
    }

    /**
     * Retrieves the most significant bits (timestamp component) of the ULID.
     *
     * @return The most significant bits.
     */
    public long getMostSignificantBits() {
        return msb;
    }

    /**
     * Retrieves the least significant bits (random component) of the ULID.
     *
     * @return The least significant bits.
     */
    public long getLeastSignificantBits() {
        return lsb;
    }

    /**
     * Returns the string representation of the ULID in lowercase.
     *
     * @return The ULID string in lowercase.
     */
    public String toLowerCase() {
        return toString().toLowerCase(Locale.ROOT);
    }

    /**
     * Converts the ULID to a 16-byte array.
     *
     * @return A byte array representation of the ULID.
     */
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.putLong(msb);
        buffer.putLong(lsb);
        return buffer.array();
    }

    /**
     * Compares this ULID to another ULID.
     * ULIDs are sorted based on their timestamp component first, followed by the random component.
     *
     * @param other The ULID to compare with.
     * @return A negative integer if this ULID is less than the other, zero if equal, and a positive integer if greater.
     */
    @Override
    public int compareTo(ULID other) {
        int cmp = Long.compare(this.msb, other.msb);
        return (cmp != 0) ? cmp : Long.compare(this.lsb, other.lsb);
    }

    /**
     * Checks if this ULID is equal to another object.
     *
     * @param obj The object to compare.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ULID ulid = (ULID) obj;
        return msb == ulid.msb && lsb == ulid.lsb;
    }

    /**
     * Returns the string representation of this ULID.
     *
     * @return The ULID as a string.
     */
    @Override
    public String toString() {
        return ULIDUtils.encodeUlid(msb, lsb);
    }

    /**
     * Computes the hash code for this ULID.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(msb, lsb);
    }
}
