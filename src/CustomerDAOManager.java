import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CustomerDAOManager {
    private final CustomerDAO customerDAO;

    public CustomerDAOManager(Connection connection) {
        this.customerDAO = new CustomerDAO(connection);
    }

    private void handleSQLException(Runnable action) {
        try {
            action.run();
        } catch (SQLException e) {
            System.out.println("Виникла помилка: " + e.getMessage());
        }
    }

    public void addCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть прізвище: ");
        String surname = scanner.nextLine();
        System.out.print("Введіть ім'я: ");
        String name = scanner.nextLine();
        System.out.print("Введіть по батькові: ");
        String fathername = scanner.nextLine();
        System.out.print("Введіть адресу: ");
        String address = scanner.nextLine();
        System.out.print("Введіть номер телефону: ");
        long phoneNumber = scanner.nextLong();
        System.out.print("Введіть номер картки: ");
        long cardNumber = scanner.nextLong();
        System.out.print("Введіть бонусний баланс: ");
        double bonusBalance = scanner.nextDouble();

        Customer customer = new Customer(0, surname, name, fathername, address, phoneNumber, cardNumber, bonusBalance);
        handleSQLException(() -> {
            customerDAO.insert(customer);
            System.out.println("Покупця додано успішно.");
        });
    }

    public void updateCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть ID покупця для оновлення: ");
        int id = scanner.nextInt();

        System.out.print("Введіть нове прізвище: ");
        String surname = scanner.next();
        System.out.print("Введіть нове ім'я: ");
        String name = scanner.next();
        System.out.print("Введіть нове по батькові: ");
        String fathername = scanner.next();
        System.out.print("Введіть нову адресу: ");
        String address = scanner.next();
        System.out.print("Введіть новий номер телефону: ");
        long phoneNumber = scanner.nextLong();
        System.out.print("Введіть новий номер картки: ");
        long cardNumber = scanner.nextLong();
        System.out.print("Введіть новий бонусний баланс: ");
        double bonusBalance = scanner.nextDouble();

        Customer customer = new Customer(id, surname, name, fathername, address, phoneNumber, cardNumber, bonusBalance);
        handleSQLException(() -> {
            customerDAO.update(customer);
            System.out.println("Покупця оновлено успішно.");
        });
    }

    public void deleteCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть ID покупця для видалення: ");
        int id = scanner.nextInt();
        handleSQLException(() -> {
            customerDAO.deleteByID(id);
            System.out.println("Покупця видалено успішно.");
        });
    }

    public void findCustomerById() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть ID покупця: ");
        int id = scanner.nextInt();
        handleSQLException(() -> {
            Customer customer = customerDAO.findById(id);
            if (customer != null) {
                System.out.println(customer);
            } else {
                System.out.println("Покупця не знайдено.");
            }
        });
    }

    public void showCustomersByName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть ім'я: ");
        String name = scanner.nextLine();
        handleSQLException(() -> {
            List<Customer> customers = customerDAO.selectByName(name);
            customers.forEach(System.out::println);
        });
    }

    public void showCustomersByCardNumberRange() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть нижню межу номера картки: ");
        long lowerBound = scanner.nextLong();
        System.out.print("Введіть верхню межу номера картки: ");
        long upperBound = scanner.nextLong();
        handleSQLException(() -> {
            List<Customer> customers = customerDAO.selectByCardNumberRange(lowerBound, upperBound);
            customers.forEach(System.out::println);
        });
    }

    public void showCustomersWithZeroBonusBalance() {
        handleSQLException(() -> {
            List<Customer> customers = customerDAO.selectByZeroBonusBalance();
            customers.forEach(System.out::println);
        });
    }

    public void showAllCustomers() {
        handleSQLException(() -> {
            List<Customer> customers = customerDAO.select();
            customers.forEach(System.out::println);
        });
    }
}
