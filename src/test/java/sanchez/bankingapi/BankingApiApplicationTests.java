package sanchez.bankingapi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BankingApiApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void concatTest() {

        String str1 = "abc";
        String str2 = "qwe";

        assertEquals("abcqwe", str1 + str2);
    }

}
