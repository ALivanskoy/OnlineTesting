package sh.alex.onlineTesting.model.exeptions;

/**
 * Исключение, которое возникает, когда пользователь не найден в системе.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Конструктор, который принимает сообщение об ошибке.
     *
     * @param message сообщение об ошибке
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
