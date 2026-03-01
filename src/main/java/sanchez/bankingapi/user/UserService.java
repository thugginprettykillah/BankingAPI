package sanchez.bankingapi.user;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    private final PasswordEncoder passwordEncoder;

    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder)
    {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }



    public List<UserResponseDto> getAllUsers()
    {
        log.info("Called getAllUsers from UserService");
        List<UserEntity> list = repository.findAll();
        return list.stream().map(this::toDto).toList();
    }

    public UserResponseDto getUserById(Long id)
    {
        log.info("Called getUserById from UserService");
        return toDto(repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id)));

    }
    @Transactional
    public UserResponseDto createUser(CreateUserRequestDto request)
    {
        log.debug("Creating user {}", request);
        UserEntity userEntity = repository
                .save(toEntity(request));
        return toDto(userEntity);

    }

    @Transactional
    public void deleteUser(Long id)
    {
        log.info("Deleting user with id " + id);
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id " + id);
        }
        repository.deleteById(id);
    }




    private UserResponseDto toDto(UserEntity user)
    {
        return new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getSecondName(),
                user.getThirdName(),
                user.getEmail()
        );
    }

    private UserEntity toEntity(CreateUserRequestDto request)
    {
        UserEntity user = new UserEntity();
        user.setFirstName(request.firstName());
        user.setSecondName(request.secondName());
        user.setThirdName(request.thirdName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        return user;
    }



}
