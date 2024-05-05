package sh.alex.onlineTesting.model.services.interfaces;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import sh.alex.onlineTesting.model.entities.UserEntity;

import java.util.Collection;
import java.util.List;

/**
 * Сервис для работы с сущностями UserEntity (Пользователь).
 */
public interface UserEntityService {

    /**
     * Метод для получения списка всех пользователей.
     *
     * @return список всех пользователей
     */
    List<UserEntity> getAll();

    /**
     * Метод для создания нового пользователя.
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     * @return созданная сущность пользователя
     */
    UserEntity create(String username, String password);

    /**
     * Метод для получения пользователя по имени пользователя.
     *
     * @param username имя пользователя
     * @return сущность пользователя
     */
    UserEntity getByUsername(String username);

    /**
     * Метод для обновления существующего пользователя.
     *
     * @param username имя пользователя для обновления
     * @param user     обновленная сущность пользователя
     * @return обновленная сущность пользователя
     */
    UserEntity update(String username, UserEntity user);

    /**
     * Метод для удаления пользователя по имени пользователя.
     *
     * @param username имя пользователя для удаления
     */
    void delete(String username);

    /**
     * Метод для обновления ролей пользователя.
     *
     * @param username имя пользователя
     * @param roles    коллекция ролей пользователя
     */
    void updateRoles(String username, Collection<SimpleGrantedAuthority> roles);
}
