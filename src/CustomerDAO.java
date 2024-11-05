import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private final Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    public int insert(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (surname, name, fathername, address, phoneNumber, cardNumber, bonusBalance) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getSurname());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getFathername());
            statement.setString(4, customer.getAddress());
            statement.setLong(5, customer.getPhoneNumber());
            statement.setLong(6, customer.getCardNumber());
            statement.setDouble(7, customer.getBonusBalance());
            return statement.executeUpdate();
        }
    }

    public List<Customer> select() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("surname"),
                        resultSet.getString("name"),
                        resultSet.getString("fathername"),
                        resultSet.getString("address"),
                        resultSet.getLong("phoneNumber"),
                        resultSet.getLong("cardNumber"),
                        resultSet.getDouble("bonusBalance")
                );
                customers.add(customer);
            }
        }
        return customers;
    }

    public Customer findById(int id) throws SQLException {
        String sql = "SELECT * FROM customers WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Customer(
                            resultSet.getInt("id"),
                            resultSet.getString("surname"),
                            resultSet.getString("name"),
                            resultSet.getString("fathername"),
                            resultSet.getString("address"),
                            resultSet.getLong("phoneNumber"),
                            resultSet.getLong("cardNumber"),
                            resultSet.getDouble("bonusBalance")
                    );
                }
            }
        }
        return null;
    }

    public int update(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET surname = ?, name = ?, fathername = ?, address = ?, phoneNumber = ?, cardNumber = ?, bonusBalance = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getSurname());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getFathername());
            statement.setString(4, customer.getAddress());
            statement.setLong(5, customer.getPhoneNumber());
            statement.setLong(6, customer.getCardNumber());
            statement.setDouble(7, customer.getBonusBalance());
            statement.setInt(8, customer.getId());
            return statement.executeUpdate();
        }
    }

    public int deleteByID(int id) throws SQLException {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        }
    }

    public List<Customer> selectByName(String name) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    customers.add(new Customer(
                            resultSet.getInt("id"),
                            resultSet.getString("surname"),
                            resultSet.getString("name"),
                            resultSet.getString("fathername"),
                            resultSet.getString("address"),
                            resultSet.getLong("phoneNumber"),
                            resultSet.getLong("cardNumber"),
                            resultSet.getDouble("bonusBalance")
                    ));
                }
            }
        }
        return customers;
    }

    public List<Customer> selectByCardNumberRange(long lowerBound, long upperBound) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers WHERE cardNumber BETWEEN ? AND ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, lowerBound);
            statement.setLong(2, upperBound);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    customers.add(new Customer(
                            resultSet.getInt("id"),
                            resultSet.getString("surname"),
                            resultSet.getString("name"),
                            resultSet.getString("fathername"),
                            resultSet.getString("address"),
                            resultSet.getLong("phoneNumber"),
                            resultSet.getLong("cardNumber"),
                            resultSet.getDouble("bonusBalance")
                    ));
                }
            }
        }
        return customers;
    }

    public List<Customer> selectByZeroBonusBalance() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers WHERE bonusBalance = 0";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                customers.add(new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("surname"),
                        resultSet.getString("name"),
                        resultSet.getString("fathername"),
                        resultSet.getString("address"),
                        resultSet.getLong("phoneNumber"),
                        resultSet.getLong("cardNumber"),
                        resultSet.getDouble("bonusBalance")
                ));
            }
        }
        return customers;
    }
}
