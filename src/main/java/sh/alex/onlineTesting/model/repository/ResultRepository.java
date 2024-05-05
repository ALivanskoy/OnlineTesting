package sh.alex.onlineTesting.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sh.alex.onlineTesting.model.entities.TestResults;
import java.util.List;

/**
 * Репозиторий для управления сущностями TestResults (Результаты теста) в базе данных.
 * Наследуется от интерфейса JpaRepository, предоставляющего базовые CRUD-операции.
 */
public interface ResultRepository extends JpaRepository<TestResults, Long> {

  /**
   * Метод для поиска всех результатов теста по имени пользователя.
   *
   * @param username имя пользователя
   * @return список результатов теста для указанного пользователя
   */
  List<TestResults> findAllByUsername(String username);
}
