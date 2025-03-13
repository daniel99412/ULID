import com.dpardo.ulid.ULID;
import com.dpardo.ulid.ULIDUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ULIDUtilsTest {

    @Test
    void codificaULIDCorrectamente() {
        String codificado = ULIDUtils.encodeUlid(12345L, 67890L);
        assertThat(codificado).hasSize(26).matches("[0-9A-HJKMNP-TV-Z]{26}");
    }

    @Test
    void parseaULIDCorrectamente() {
        ULID ulid = ULID.of(12345L, 67890L);
        String codificado = ulid.toString();
        ULID parseado = ULIDUtils.parseUlid(codificado);
        assertThat(parseado).isEqualTo(ulid);
    }

    @Test
    void fallaConULIDInvalido() {
        assertThatThrownBy(() -> ULIDUtils.parseUlid("01IINVALIDO12345678901234"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
