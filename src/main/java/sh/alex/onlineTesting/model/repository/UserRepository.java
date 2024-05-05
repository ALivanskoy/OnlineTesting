package sh.alex.onlineTesting.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sh.alex.onlineTesting.model.entities.UserEntity;

import java.util.Optional;

/**
 * Репозиторий для управления сущностями UserEntity (Пользователь) в базе данных.
 * Наследуется от интерфейса JpaRepository, предоставляющего базовые CRUD-операции.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Метод для получения пользователя по имени пользователя.
     *
     * @param username имя пользователя
     * @return Optional, содержащий сущность UserEntity, если пользователь найден, иначе Optional.empty()
     */
    Optional<UserEntity> getUserByUsername(String username);

    /**
     * Метод для удаления пользователя по имени пользователя.
     *
     * @param username имя пользователя
     */
    void deleteByUsername(String username);
}
