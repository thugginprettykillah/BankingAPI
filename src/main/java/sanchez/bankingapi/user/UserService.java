package sanchez.bankingapi.user;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sanchez.bankingapi.exception.EmailAlreadyExistsException;
import sanchez.bankingapi.role.RoleEntity;
import sanchez.bankingapi.role.RoleEnum;
import sanchez.bankingapi.role.RoleRepository;
import sanchez.bankingapi.security.RegistrationRequestDto;
import sanchez.bankingapi.security.RegistrationResponseDto;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;


    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder,  RoleRepository roleRepository)
    {
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }



    public List<UserResponseDto> getAllUsers()
    {
        log.info("Called getAllUsers from UserService");
        List<UserEntity> list = userRepository.findAll();
        return list.stream().map(this::toDto).toList();
    }

    public UserResponseDto getUserById(Long id)
    {
        log.info("Called getUserById from UserService");

        if (!getUserByAuth().getId().equals(id)) {
            throw new AuthorizationDeniedException("You are not allowed to access this resource");
        }

        return toDto(userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id)));

    }
    @Transactional
    public UserResponseDto createUser(CreateUserRequestDto request)
    {
        log.debug("Creating user method by request {}", request);

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        UserEntity userEntity = toEntity(request);
        userEntity.setPassword(passwordEncoder.encode(request.password()));

        RoleEntity userRole = roleRepository
                .findByName(RoleEnum.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        userEntity.setRoles(Set.of(userRole));

        userRepository.save(userEntity);
        return toDto(userEntity);
    }

    @Transactional
    public RegistrationResponseDto createUser(RegistrationRequestDto request)
    {
        log.debug("Registration user method {}", request);

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        UserEntity userEntity = toEntity(request);
        userEntity.setPassword(passwordEncoder.encode(request.password()));

        RoleEntity userRole = roleRepository
                .findByName(RoleEnum.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        userEntity.setRoles(Set.of(userRole));

        var saved = userRepository.save(userEntity);
        return new RegistrationResponseDto(saved.getId());
    }

    @Transactional
    public void deleteUser(Long id)
    {
        log.info("Deleting user with id {}", id);

        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id " + id);
        }

        userRepository.deleteById(id);
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

        return user;

    }

    private UserEntity toEntity(RegistrationRequestDto request)
    {
        UserEntity user = new UserEntity();
        user.setFirstName(request.firstName());
        user.setSecondName(request.secondName());
        user.setThirdName(request.thirdName());
        user.setEmail(request.email());
        return user;
    }

    private UserEntity getUserByAuth()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthorizationDeniedException("User is not authenticated");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return userRepository
                .findByEmail(userDetails.getUsername())
                .orElseThrow(
                        () -> new EntityNotFoundException("User with email="
                                + authentication.getName()
                                + " not found")
                );
    };

}
