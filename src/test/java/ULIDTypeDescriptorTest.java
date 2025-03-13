import com.dpardo.ulid.ULID;
import com.dpardo.ulid.hibernate.ULIDTypeDescriptor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ULIDTypeDescriptorTest {

    @Test
    void transformaAStringYVuelve() {
        ULID ulid = ULID.getUlid();
        String transformado = ULIDTypeDescriptor.StringTransformer.INSTANCE.transform(ulid);
        ULID parseado = ULIDTypeDescriptor.StringTransformer.INSTANCE.parse(transformado);
        assertThat(parseado).isEqualTo(ulid);
    }

    @Test
    void transformaABytesYVuelve() {
        ULID ulid = ULID.getUlid();
        byte[] transformado = ULIDTypeDescriptor.ToBytesTransformer.INSTANCE.transform(ulid);
        ULID parseado = ULIDTypeDescriptor.ToBytesTransformer.INSTANCE.parse(transformado);
        assertThat(parseado).isEqualTo(ulid);
    }

    @Test
    void pasaSinCambios() {
        ULID ulid = ULID.getUlid();
        ULID transformado = ULIDTypeDescriptor.PassThroughTransformer.INSTANCE.transform(ulid);
        assertThat(transformado).isEqualTo(ulid);
    }
}