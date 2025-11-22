package ch.lenglet.core;

import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class FormImplTest {

    @Test
    void testSerializeDeserialize() {
        final var json = """
                {
                    "caseId": 123,
                    "version": 1,
                    "answers": [
                        {
                            "questionId": "123",
                            "answerId": "123",
                            "rating": "SUFFICIENT"
                        }
                    ]
                }
                """;
        assertThatJson(FormImpl.fromJson(json).toJson())
                .isEqualTo(json);
    }

}