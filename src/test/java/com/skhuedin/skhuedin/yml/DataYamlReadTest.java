package com.skhuedin.skhuedin.yml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataYamlReadTest {

    @Autowired
    DataYamlRead dataYamlRead;

    @DisplayName("yml 에서 데이터를 잘 불러오는지 테스트")
    @Test
    void name() {
        String age = dataYamlRead.getAge();

        System.out.println("My age is " + age);
        System.out.println(dataYamlRead.getKakao());
        assertThat(age).isEqualTo("300");
    }


}