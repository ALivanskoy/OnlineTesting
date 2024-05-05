package sh.alex.onlineTesting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sh.alex.onlineTesting.model.services.implementation.UserEntityServiceImp;


/**
 * Главный класс приложения, содержащий точку входа (main).
 */
@SpringBootApplication
public class OnlineTestingApplication {

	/**
	 * Точка входа в приложение.
	 *
	 * @param args аргументы командной строки
	 */
	public static void main(String[] args) {
		SpringApplication.run(OnlineTestingApplication.class, args);
	}
}

