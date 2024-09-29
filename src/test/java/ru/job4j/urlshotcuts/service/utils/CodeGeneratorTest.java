package ru.job4j.urlshotcuts.service.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CodeGeneratorTest {

    @Test
    public void whenGenerateEightSymCodeThenLengthIsEight() {
        var expected = 8;
        String code = new CodeGenerator().generate(8);
        assertThat(code.length()).isEqualTo(expected);
    }

    @Test
    public void whenGeneratedCodeIsValidThenTrue() {
        String code = new CodeGenerator().generate(8);
        assertThat(isValid(code)).isTrue();
    }

    public static boolean isValid(String s) {
        return s != null && s.matches("^[a-zA-Z0-9]*$");
    }

}