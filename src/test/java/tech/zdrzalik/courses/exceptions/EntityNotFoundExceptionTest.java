package tech.zdrzalik.courses.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.zdrzalik.courses.common.I18nCodes;

@SpringBootTest
class EntityNotFoundExceptionTest {

    @Test
    void throwEntityNotFoundExceptionEntityNotFound() {
        var x = Assertions.assertThrows(EntityNotFoundException.class, (() -> {
            throw EntityNotFoundException.entityNotFound(0L);
        }), I18nCodes.ENTITY_NOT_FOUND);
        Assertions.assertEquals(0L, x.getId());
    }
}
