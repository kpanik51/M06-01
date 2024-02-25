import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.*;

public class StaffManagementSystem extends Application {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    private TextField idField, lastNameField, firstNameField, miField, addressField, cityField, stateField,
            telephoneField, emailField;
    private Label resultLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Staff Information Management");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        idField = new TextField();
        lastNameField = new TextField();
        firstNameField = new TextField();
        miField = new TextField();
        addressField = new TextField();
        cityField = new TextField();
        stateField = new TextField();
        telephoneField = new TextField();
        emailField = new TextField();

        resultLabel = new Label();

        Button viewButton = new Button("View");
        viewButton.setOnAction(e -> viewRecord());

        Button insertButton = new Button("Insert");
        insertButton.setOnAction(e -> insertRecord());

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> updateRecord());

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("First Name:"), 0, 2);
        grid.add(firstNameField, 1, 2);
        grid.add(new Label("MI:"), 0, 3);
        grid.add(miField, 1, 3);
        grid.add(new Label("Address:"), 0, 4);
        grid.add(addressField, 1, 4);
        grid.add(new Label("City:"), 0, 5);
        grid.add(cityField, 1, 5);
        grid.add(new Label("State:"), 0, 6);
        grid.add(stateField, 1, 6);
        grid.add(new Label("Telephone:"), 0, 7);
        grid.add(telephoneField, 1, 7);
        grid.add(new Label("Email:"), 0, 8);
        grid.add(emailField, 1, 8);
        grid.add(viewButton, 0, 9);
        grid.add(insertButton, 1, 9);
        grid.add(updateButton, 2, 9);
        grid.add(resultLabel, 0, 10, 3, 1);
        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void viewRecord() {
        String id = idField.getText();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Staff WHERE id = ?")) {
            preparedStatement.setString(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    resultLabel.setText("Record found:\n" +
                            "ID: " + resultSet.getString("id") + "\n" +
                            "Last Name: " + resultSet.getString("lastName") + "\n" +
                            "First Name: " + resultSet.getString("firstName") + "\n" +
                            "MI: " + resultSet.getString("mi") + "\n" +
                            "Address: " + resultSet.getString("address") + "\n" +
                            "City: " + resultSet.getString("city") + "\n" +
                            "State: " + resultSet.getString("state") + "\n" +
                            "Telephone: " + resultSet.getString("telephone") + "\n" +
                            "Email: " + resultSet.getString("email"));
                } else {
                    resultLabel.setText("Record not found");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertRecord() {
        String id = idField.getText();
        String lastName = lastNameField.getText();
        String firstName = firstNameField.getText();
        String mi = miField.getText();
        String address = addressField.getText();
        String city = cityField.getText();
        String state = stateField.getText();
        String telephone = telephoneField.getText();
        String email = emailField.getText();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Staff VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, mi);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, city);
            preparedStatement.setString(7, state);
            preparedStatement.setString(8, telephone);
            preparedStatement.setString(9, email);

            int rowsAffected = preparedStatement.executeUpdate();
            resultLabel.setText(rowsAffected + " row(s) inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateRecord() {
        String id = idField.getText();
        String lastName = lastNameField.getText();
        String firstName = firstNameField.getText();
        String mi = miField.getText();
        String address = addressField.getText();
        String city = cityField.getText();
        String state = stateField.getText();
        String telephone = telephoneField.getText();
        String email = emailField.getText();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE Staff SET lastName=?, firstName=?, mi=?, address=?, city=?, state=?, telephone=?, email=? WHERE id=?")) {
            preparedStatement.setString(1, lastName);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, mi);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, city);
            preparedStatement.setString(6, state);
            preparedStatement.setString(7, telephone);
            preparedStatement.setString(8, email);
            preparedStatement.setString(9, id);

            int rowsAffected = preparedStatement.executeUpdate();
            resultLabel.setText(rowsAffected + " row(s) updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
