package com.dpardo.ulid.hibernate;

import com.dpardo.ulid.ULID;
import org.hibernate.internal.util.BytesHelper;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractJavaType;

import java.io.Serializable;

public class ULIDTypeDescriptor extends AbstractJavaType<ULID> {
    public static final ULIDTypeDescriptor INSTANCE = new ULIDTypeDescriptor();

    public ULIDTypeDescriptor() {
        super(ULID.class);
    }

    @Override
    public String toString(ULID value) {
        return value == null ? null : value.toString();
    }

    @Override
    public ULID fromString(CharSequence sequence) {
        return sequence == null ? null : ULID.fromString(sequence.toString());
    }

    @Override
    public <T> T unwrap(ULID value, Class<T> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (String.class.isAssignableFrom(type)) {
            return (T) value.toString();
        }
        throw unknownUnwrap(type);
    }

    @Override
    public <T> ULID wrap(T value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return ULID.fromString((String) value);
        }
        throw unknownWrap(value.getClass());
    }

    public interface ValueTransformer {
        Serializable transform(ULID ulid);

        ULID parse(Object value);
    }

    public static class PassThroughTransformer implements ValueTransformer {
        public static final PassThroughTransformer INSTANCE = new PassThroughTransformer();

        public ULID transform(ULID ulid) {
            return ulid;
        }

        public ULID parse(Object value) {
            return (ULID) value;
        }
    }

    public static class StringTransformer implements ValueTransformer {
        public static final StringTransformer INSTANCE = new StringTransformer();

        public String transform(ULID ulid) {
            return ulid.toString();
        }

        public ULID parse(Object value) {
            return ULID.fromString((String) value);
        }
    }

    public static class ToBytesTransformer implements ValueTransformer {
        public static final ToBytesTransformer INSTANCE = new ToBytesTransformer();

        public byte[] transform(ULID ulid) {
            byte[] bytes = new byte[16];
            BytesHelper.fromLong(ulid.getMostSignificantBits(), bytes, 0);
            BytesHelper.fromLong(ulid.getLeastSignificantBits(), bytes, 8);
            return bytes;
        }

        public ULID parse(Object value) {
            byte[] bytea = (byte[]) value;
            return ULID.of(BytesHelper.asLong(bytea, 0), BytesHelper.asLong(bytea, 8));
        }
    }
}
