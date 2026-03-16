package sanchez.bankingapi.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import sanchez.bankingapi.dto.user.CreateUserRequestDto;
import sanchez.bankingapi.dto.user.UserResponseDto;
import sanchez.bankingapi.role.RoleEntity;
import sanchez.bankingapi.role.RoleEnum;
import sanchez.bankingapi.role.RoleRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;


    @Test
    void createUser_ShouldReturnUserResponseDto_WhenDataIsValid()
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
                password);
        UserResponseDto expectedResponseDto = new UserResponseDto(
                1L,
                firstName,
                secondName,
                thirdName,
                email);
        UserEntity userEntity = new UserEntity(firstName, secondName, thirdName, email, password);
        UserEntity savedEntity = new UserEntity(firstName, secondName, thirdName, email, password);
        savedEntity.setId(1L);
        RoleEntity roleEntity = new RoleEntity(RoleEnum.ROLE_USER);


        when(userRepository.findByEmail(email))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode(password))
                .thenReturn("password");
        when(roleRepository.findByName(RoleEnum.ROLE_USER))
                .thenReturn(Optional.of(roleEntity));
        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(savedEntity);
        UserResponseDto responseDto = userService.createUser(requestDto);


        assertEquals(expectedResponseDto, responseDto);
        verify(userRepository).findByEmail(email);
        verify(userRepository).save(any(UserEntity.class));
        verify(roleRepository).findByName(RoleEnum.ROLE_USER);
        verify(passwordEncoder).encode(password);

    }
}
