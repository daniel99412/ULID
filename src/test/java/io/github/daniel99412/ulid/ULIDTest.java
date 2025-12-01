package io.github.daniel99412.ulid;

import io.github.daniel99412.ulid.ULID;
import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ULIDTest {

    @Test
    void generaULIDValido() {
        ULID ulid = ULID.getUlid();
        assertThat(ulid).isNotNull();
        assertThat(ulid.toString()).hasSize(26);
    }

    @Test
    void creaDesdeInstant() {
        Instant ahora = Instant.now();
        ULID ulid = ULID.fromTimestamp(ahora);
        assertThat(ulid.getMostSignificantBits()).isEqualTo(ahora.toEpochMilli());
    }

    @Test
    void convierteAStringYVuelve() {
        ULID ulid = ULID.getUlid();
        String texto = ulid.toString();
        ULID parseado = ULID.parse(texto);
        assertThat(parseado).isEqualTo(ulid);
    }

    @Test
    void fallaConStringInvalido() {
        assertThatThrownBy(() -> ULID.parse("INVALIDO"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void comparaCorrectamente() {
        ULID ulid1 = ULID.fromTimestamp(Instant.ofEpochMilli(1000));
        ULID ulid2 = ULID.fromTimestamp(Instant.ofEpochMilli(2000));
        assertThat(ulid1.compareTo(ulid2)).isLessThan(0);
        assertThat(ulid2.compareTo(ulid1)).isGreaterThan(0);
    }

    @Test
    void verificaIgualdadYHash() {
        ULID ulid1 = ULID.of(12345L, 67890L);
        ULID ulid2 = ULID.of(12345L, 67890L);
        assertThat(ulid1).isEqualTo(ulid2);
        assertThat(ulid1.hashCode()).isEqualTo(ulid2.hashCode());
    }

    @Test
    void convierteABytes() {
        ULID ulid = ULID.of(12345L, 67890L);
        byte[] bytes = ulid.toBytes();
        assertThat(bytes).hasSize(16);
    }

    @Test
    void convierteAMinusculas() {
        ULID ulid = ULID.getUlid();
        String minusculas = ulid.toLowerCase();
        assertThat(minusculas).isEqualTo(ulid.toString().toLowerCase());
    }

}
