package com.example.demo.backend;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public abstract class AbsUnitTest {
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }
}
