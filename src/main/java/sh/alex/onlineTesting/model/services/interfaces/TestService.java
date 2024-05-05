package sh.alex.onlineTesting.model.services.interfaces;

import sh.alex.onlineTesting.model.entities.Test;
import sh.alex.onlineTesting.model.entities.TestResults;

import java.util.List;

/**
 * Сервис для работы с тестами и результатами тестирования.
 */
public interface TestService {

    /**
     * Метод для создания нового теста.
     *
     * @return созданный объект теста
     */
    Test create();

    /**
     * Метод для получения списка всех результатов тестирования.
     *
     * @return список результатов тестирования
     */
    List<TestResults> getResults();

    /**
     * Метод для получения списка результатов тестирования по имени пользователя.
     *
     * @param username имя пользователя
     * @return список результатов тестирования для указанного пользователя
     */
    List<TestResults> getResultsByUsername(String username);

    /**
     * Метод для сохранения результатов тестирования.
     *
     * @param testResults объект с результатами тестирования
     */
    void saveResults(TestResults testResults);
}
