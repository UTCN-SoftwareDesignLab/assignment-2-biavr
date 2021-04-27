package com.example.demo.user;

import com.example.demo.BaseControllerTest;
import com.example.demo.user.dto.UpdatePasswordUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.demo.TestCreationFactory.*;
import static com.example.demo.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @BeforeEach
    void setup() {
        super.setUp();
        controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();

        doNothing().when(userService).deleteUser(id);

        ResultActions result = performDeleteWithPathVariable(USERS + ENTITY, id);
        result.andExpect(status().isOk());

        verify(userService,times(1)).deleteUser(id);
    }

    @Test
    void changePassword() throws Exception {
        long id = randomLong();
        String newPassword = randomString();
        UpdatePasswordUser reqUser= UpdatePasswordUser.builder()
                .password(newPassword)
                .id(id)
                .build();

        when(userService.updatePassword(id, newPassword)).thenReturn(reqUser);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(USERS + ENTITY, newPassword, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqUser));
    }
}