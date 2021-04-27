package com.example.demo.user;

import com.example.demo.user.dto.UserMinimalDTO;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setup(){
        userRepository.deleteAll();
    }

    @Test
    void allUsersMinimal() {
        int nrUsers = 10;
        List<User> users = new ArrayList<>();
        for(int i = 0; i < nrUsers; i++){
            User user = User.builder()
                    .username("User" + i)
                    .password(UUID.randomUUID().toString())
                    .email("user" + i + "@gmail.com")
                    .build();
            users.add(user);
            //userRepository.save(user);
            //better all at at ime, because we do not open and close the connection too many times
        }
        userRepository.saveAll(users);

        List<UserMinimalDTO> userMinimalDTOS = userService.allUsersMinimal();

        for(int i = 0; i < nrUsers; i++) {
            assertEquals(users.get(i).getId(),userMinimalDTOS.get(i).getId());
            assertEquals(users.get(i).getUsername(),userMinimalDTOS.get(i).getName());

        }
    }

    @Test
    void allUsersForList() {

    }
}