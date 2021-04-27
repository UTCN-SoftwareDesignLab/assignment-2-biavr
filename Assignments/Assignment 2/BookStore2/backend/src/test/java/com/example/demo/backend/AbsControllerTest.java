package com.example.demo.backend;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public abstract class AbsControllerTest {

    protected MockMvc mockMvc;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    protected void initController(Object controller){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    protected ResultActions performGet(String path) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(path));
    }
}
