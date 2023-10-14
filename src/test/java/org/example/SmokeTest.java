package org.example;

import static org.assertj.core.api.Assertions.assertThat;

import org.example.controllers.AddressBookController;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private AddressBookController addyController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(addyController).isNotNull();
    }
}