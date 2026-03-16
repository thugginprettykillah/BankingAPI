package sanchez.bankingapi.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sanchez.bankingapi.dto.user.CreateUserRequestDto;
import sanchez.bankingapi.dto.user.UserResponseDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerUnitTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void createUserTest()
    {
        String firstName = "testName";
        String secondName = "testName";
        String thirdName = "testName";
        String  email = "email";
        String password = "password";

        CreateUserRequestDto requestDto = new CreateUserRequestDto(
                firstName,
                secondName,
                thirdName,
                email,
                password
        );

        UserResponseDto responseDto = new UserResponseDto(
                1L,
                firstName,
                secondName,
                thirdName,
                email
        );


        when(userService.createUser(requestDto)).thenReturn(responseDto);

        ResponseEntity<UserResponseDto> response = userController.createUser(requestDto);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().id(), responseDto.id());
        assertEquals(response.getBody().firstName(), responseDto.firstName());
        assertEquals(response.getBody().secondName(), responseDto.secondName());
        assertEquals(response.getBody().thirdName(), responseDto.thirdName());
        assertEquals(response.getBody().email(), responseDto.email());
        verify(userService, times(1)).createUser(requestDto);

    }
}