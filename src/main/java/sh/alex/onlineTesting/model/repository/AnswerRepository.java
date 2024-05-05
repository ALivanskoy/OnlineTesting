package sh.alex.onlineTesting.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sh.alex.onlineTesting.model.entities.Answer;

import java.util.List;

/**
 * Репозиторий для управления сущностями Answer (Ответ) в базе данных.
 * Наследуется от интерфейса JpaRepository, предоставляющего базовые CRUD-операции.
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    /**
     * Метод для получения списка ответов по идентификатору вопроса.
     *
     * @param questionId идентификатор вопроса
     * @return список ответов, относящихся к указанному вопросу
     */
    @Query("SELECT a FROM Answer a WHERE a.questionId = :questionId")
    List<Answer> getByQuestionId(@Param("questionId") Long questionId);
}
