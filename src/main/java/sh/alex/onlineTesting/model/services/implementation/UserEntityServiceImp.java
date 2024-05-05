package sh.alex.onlineTesting.model.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sh.alex.onlineTesting.model.entities.UserEntity;
import sh.alex.onlineTesting.model.exeptions.UserAlreadyExistsException;
import sh.alex.onlineTesting.model.exeptions.UserNotFoundException;
import sh.alex.onlineTesting.model.repository.UserRepository;
import sh.alex.onlineTesting.model.services.interfaces.UserEntityService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для работы с сущностями UserEntity (Пользователь).
 */
@Service
public class UserEntityServiceImp implements UserEntityService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Конструктор для внедрения зависимостей.
     *
     * @param repository      репозиторий для работы с пользователями
     * @param passwordEncoder кодировщик паролей
     */
    @Autowired
    public UserEntityServiceImp(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Метод для получения списка всех пользователей.
     *
     * @return список всех пользователей
     */
    @Override
    public List<UserEntity> getAll() {
        return repository.findAll().stream().toList();
    }

    /**
     * Метод для создания нового пользователя.
     *
     * @param username    имя пользователя
     * @param rawPassword пароль в открытом виде
     * @return созданная сущность пользователя
     * @throws UserAlreadyExistsException если пользователь с таким именем уже существует
     */
    @Override
    public UserEntity create(String username, String rawPassword) {
        if (repository.getUserByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует");
        }

        UserEntity user = new UserEntity(username, passwordEncoder.encode(rawPassword));

        if (user.getUsername().equals("admin")) {
            user.getAuthority().addAll(Set.of(
                    "ROLE_MODERATOR",
                    "ROLE_ADMIN"
            ));
        }

        return repository.saveAndFlush(user);
    }

    /**
     * Метод для получения пользователя по имени.
     *
     * @param username имя пользователя
     * @return сущность пользователя
     */
    @Override
    public UserEntity getByUsername(String username) {
        Optional<UserEntity> userEntityOptional = repository.getUserByUsername(username);
        return userEntityOptional.orElse(null);
    }

    /**
     * Метод для обновления пользователя.
     *
     * @param username    имя пользователя для обновления
     * @param refreshUser обновленная сущность пользователя
     * @return обновленная сущность пользователя
     */
    @Override
    @Transactional
    public UserEntity update(String username, UserEntity refreshUser) {
        Optional<UserEntity> userEntityOptional = repository.getUserByUsername(username);

        if (userEntityOptional.isPresent()) {
            UserEntity user = userEntityOptional.get();

            if (refreshUser.getPassword() != null && !refreshUser.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(refreshUser.getPassword()));
            }
            return user;
        } else {
            return null;
        }
    }

    /**
     * Метод для удаления пользователя по имени.
     *
     * @param username имя пользователя для удаления
     * @throws UserNotFoundException если пользователь не найден
     */
    @Override
    @Transactional
    public void delete(String username) {
        Optional<UserEntity> userEntityOptional = repository.getUserByUsername(username);

        if (userEntityOptional.isPresent()) {
            repository.deleteByUsername(username);
            return;
        }
        throw new UserNotFoundException("User not found");
    }

/**
 * Метод для обновления ролей пользователя.
 *
 * @param username имя пользователя
 * @param roles    новые роли пользователя

 * @throws UserNotFoundException если пользователь не найден
 */
@Override
public void updateRoles(String username, Collection<SimpleGrantedAuthority> roles) {
    Optional<UserEntity> userEntityOptional = repository.getUserByUsername(username);
    if (userEntityOptional.isPresent()) {
        UserEntity user = userEntityOptional.get();
        Set<String> authorities = roles.stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        user.setAuthority(authorities);
        repository.save(user);
        return;
    }
    throw new UserNotFoundException("User not found");
}
}

