package sh.alex.onlineTesting.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sh.alex.onlineTesting.model.entities.Question;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для управления сущностями Question (Вопрос) в базе данных.
 * Наследуется от интерфейса JpaRepository, предоставляющего базовые CRUD-операции.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    /**
     * Метод для поиска вопроса по его тексту.
     *
     * @param text текст вопроса
     * @return Optional, содержащий вопрос, если он найден, иначе Optional.empty()
     */
    Optional<Question> findByText(String text);

    /**
     * Метод для получения количества вопросов в базе данных.
     *
     * @return количество вопросов
     */
    long count();

    /**
     * Метод для получения случайного списка вопросов с ограничением по количеству.
     *
     * @param limit максимальное количество вопросов в списке
     * @return список случайных вопросов с указанным ограничением
     */
    @Query(value = "SELECT q FROM Question q ORDER BY RAND()", nativeQuery = false)
    List<Question> findRandomQuestions(@Param("limit") int limit);
}
