package sh.alex.onlineTesting.model.services.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sh.alex.onlineTesting.model.entities.Answer;
import sh.alex.onlineTesting.model.repository.AnswerRepository;
import sh.alex.onlineTesting.model.services.interfaces.AnswerService;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для работы с сущностями Answer (Ответ).
 */
@Service
@RequiredArgsConstructor
public class AnswerServiceImp implements AnswerService {

    private final AnswerRepository repository;

    /**
     * Метод для получения списка всех ответов.
     *
     * @return список всех ответов
     */
    @Override
    public List<Answer> getAll() {
        return repository.findAll().stream().toList();
    }

    /**
     * Метод для получения списка ответов по идентификатору вопроса.
     *
     * @param id идентификатор вопроса
     * @return список ответов для указанного вопроса
     */
    @Override
    public List<Answer> getByQuestionId(Long id) {
        return repository.getByQuestionId(id).stream().toList();
    }

    /**
     * Метод для создания нового ответа.
     *
     * @param answer     сущность ответа
     * @param questionId идентификатор вопроса
     */
    @Override
    public void create(Answer answer, Long questionId) {
        answer.setQuestionId(questionId);
        repository.saveAndFlush(answer);
    }

    /**
     * Метод для получения ответа по идентификатору.
     *
     * @param id идентификатор ответа
     * @return сущность ответа
     */
    @Override
    public Answer getById(Long id) {
        Optional<Answer> optionalAnswer = repository.findById(id);
        return optionalAnswer.orElse(null);
    }

    /**
     * Метод для обновления существующего ответа.
     *
     * @param id        идентификатор ответа для обновления
     * @param newAnswer обновленная сущность ответа
     * @return обновленная сущность ответа
     */
    @Override
    @Transactional
    public Answer update(Long id, Answer newAnswer) {
        Optional<Answer> answerOptional = repository.findById(id);

        if (answerOptional.isPresent()) {
            Answer answer = answerOptional.get();
            answer.setText(newAnswer.getText());
            answer.setCorrect(answer.getCorrect());
            return repository.saveAndFlush(answer);
        } else return null;
    }

    /**
     * Метод для удаления ответа по идентификатору.
     *
     * @param id идентификатор ответа для удаления
     */
    @Override
    @Transactional
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }
}
