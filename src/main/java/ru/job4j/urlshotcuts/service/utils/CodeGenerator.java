package ru.job4j.urlshotcuts.service.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@AllArgsConstructor
public class CodeGenerator {
    private static final int[][] ALLOWED_BYTES = {
            {48, 57},  /* 0-9 */
            {65, 90},  /* A-Z */
            {97, 122}  /* a-z */
    };
    private static final SecureRandom RANDOM = new SecureRandom();

    public String generate(int codeLength) {
        StringBuilder code = new StringBuilder(codeLength);
        for (int i = 0; i < codeLength; i++) {
            code.append((char) getRandomByte());
        }
        return code.toString();
    }

    private int getRandomByte() {
        int range = RANDOM.nextInt(ALLOWED_BYTES.length);
        int min = ALLOWED_BYTES[range][0];
        int max = ALLOWED_BYTES[range][1];
        return RANDOM.nextInt(max - min + 1) + min;
    }

}
