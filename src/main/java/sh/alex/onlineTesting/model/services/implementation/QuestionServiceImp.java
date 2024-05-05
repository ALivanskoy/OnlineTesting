package sh.alex.onlineTesting.model.services.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sh.alex.onlineTesting.model.repository.QuestionRepository;
import sh.alex.onlineTesting.model.services.interfaces.QuestionService;
import sh.alex.onlineTesting.model.entities.Question;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для работы с сущностями Question (Вопрос).
 */
@Service
@RequiredArgsConstructor
public class QuestionServiceImp implements QuestionService {

    private final QuestionRepository repository;

    /**
     * Метод для получения списка всех вопросов.
     *
     * @return список всех вопросов
     */
    @Override
    public List<Question> getAll() {
        return repository.findAll().stream().toList();
    }

    /**
     * Метод для создания нового вопроса.
     *
     * @param text текст вопроса
     * @return созданная сущность вопроса
     */
    @Override
    @Transactional
    public Question create(String text) {
        Question question = new Question(text);
        return repository.saveAndFlush(question);
    }

    /**
     * Метод для получения вопроса по идентификатору.
     *
     * @param id идентификатор вопроса
     * @return сущность вопроса
     */
    @Override
    public Question getById(Long id) {
        Optional<Question> questionEntityOptional = repository.findById(id);
        return questionEntityOptional.orElse(null);
    }

    /**
     * Метод для проверки уникальности текста вопроса.
     *
     * @param text текст вопроса
     * @return true, если текст уникален, false в противном случае
     */
    @Override
    public boolean isQuestionTextUnique(String text) {
        Optional<Question> questionOptional = repository.findByText(text);
        return questionOptional.isEmpty();
    }

    /**
     * Метод для обновления существующего вопроса.
     *
     * @param id          идентификатор вопроса для обновления
     * @param newQuestion обновленная сущность вопроса
     * @return обновленная сущность вопроса
     */
    @Override
    @Transactional
    public Question update(Long id, Question newQuestion) {
        Optional<Question> questionOptional = repository.findById(id);

        if (questionOptional.isPresent()) {
            Question question = questionOptional.get();
            question.setText(newQuestion.getText());
            return repository.saveAndFlush(question);
        } else return null;
    }

    /**
     * Метод для удаления вопроса по идентификатору.
     *
     * @param id идентификатор вопроса для удаления
     */
    @Override
    @Transactional
    public void delete(Long id) {
        if (repository.existsById(id)) repository.deleteById(id);
    }

    /**
     * Метод для получения случайных вопросов.
     *
     * @param limit максимальное количество вопросов для получения
     * @return список случайных вопросов
     */
    @Override
    public List<Question> getRandomQuestions(int limit) {
        return repository.findRandomQuestions(limit);
    }

    /**
     * Метод для получения количества вопросов в базе данных.
     *
     * @return количество вопросов в базе данных
     */
    @Override
    public long getDbSize() {
        return repository.count();
    }
}

