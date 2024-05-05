package sh.alex.onlineTesting.model.exeptions;

/**
 * Исключение, которое возникает при попытке создания пользователя, который уже существует в системе.
 */
public class UserAlreadyExistsException extends RuntimeException {
    /**
     * Конструктор, который принимает сообщение об ошибке.
     *
     * @param message сообщение об ошибке
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

