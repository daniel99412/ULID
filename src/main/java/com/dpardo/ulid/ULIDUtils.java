package com.dpardo.ulid;

/**
 * Utility class for encoding and decoding ULIDs using a modified Base32 encoding (Crockford's Base32).
 * <p>
 * This class provides methods to:
 * <ul>
 *     <li>Convert a ULID (most and least significant bits) into a 26-character ULID string.</li>
 *     <li>Parse a ULID string back into its 128-bit representation.</li>
 * </ul>
 * <p>
 * The encoding follows Crockford's Base32 alphabet, which is designed for better readability and avoids ambiguous characters.
 * </p>
 */
public final class ULIDUtils {
    /** Crockford's Base32 alphabet for encoding ULIDs */
    private final static String BASE32_ALPHABET = "0123456789ABCDEFGHJKMNPQRSTVWXYZ";

    /** Private constructor to prevent instantiation */
    private ULIDUtils() {}

    /**
     * Encodes a ULID from its most and least significant bits into a 26-character string.
     *
     * @param msb The most significant bits (timestamp part).
     * @param lsb The least significant bits (random part).
     * @return The encoded ULID string.
     */
    public static String encodeUlid(long msb, long lsb) {
        return encodeCrockford(msb, 10) + encodeCrockford(lsb, 16);
    }

    /**
     * Parses a ULID string and converts it back into a {@link ULID} object.
     *
     * @param ulidString The 26-character ULID string to parse.
     * @return A {@link ULID} instance representing the given ULID string.
     * @throws IllegalArgumentException If the input string is null or not exactly 26 characters long.
     */
    public static ULID parseUlid(String ulidString) {
        if (ulidString == null || ulidString.length() != 26) {
            throw new IllegalArgumentException("ULID string must be 26 characters long.");
        }
        if (ulidString.charAt(0) > '7') {
            throw new IllegalArgumentException("Invalid ULID string: The first character must be between 0 and 7.");
        }
        if (!ulidString.matches("^[0-9A-HJKMNP-TV-Z]{26}$")) {
            throw new IllegalArgumentException("ULID contains invalid characters.");
        }

        long msb = 0;
        long lsb = 0;

        // Decode the first 10 characters into the most significant bits
        for (int i = 0; i < 10; i++) {
            msb <<= 5;
            msb += BASE32_ALPHABET.indexOf(Character.toUpperCase(ulidString.charAt(i)));
        }
        // Decode the last 16 characters into the least significant bits
        for (int i = 10; i < 26; i++) {
            lsb <<= 5;
            lsb += BASE32_ALPHABET.indexOf(Character.toUpperCase(ulidString.charAt(i)));
        }
        return ULID.of(msb, lsb);
    }

    /**
     * Encodes a long value into a Crockford's Base32 string.
     *
     * @param value  The numeric value to encode.
     * @param length The desired output length.
     * @return The encoded Base32 string.
     */
    private static String encodeCrockford(long value, int length) {
        char[] buffer = new char[length];
        for (int i = length - 1; i >= 0; i--) {
            buffer[i] = BASE32_ALPHABET.charAt((int) (value & 0x1F));
            value >>>= 5;
        }
        return new String(buffer);
    }
}
