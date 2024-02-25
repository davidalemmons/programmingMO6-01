import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StaffDatabaseApp extends Application {

    private Connection connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/staff_database";
        String user = "root";
        String password = "Look2the1!";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void viewRecord(String id, TextField idField, TextField lastNameField, TextField firstNameField,
            TextField miField, TextField addressField, TextField cityField, TextField stateField,
            TextField telephoneField, TextField emailField) {
        String query = "SELECT * FROM Staff WHERE id = ?";
        try (Connection conn = connectToDatabase();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                final String finalId = rs.getString("id");
                final String finalLastName = rs.getString("lastName");
                final String finalFirstName = rs.getString("firstName");
                final String finalmi = rs.getString("mi");
                final String finaladdress = rs.getString("address");
                final String finalcity = rs.getString("city");
                final String finalstate = rs.getString("state");
                final String finaltelephone = rs.getString("telephone");
                final String finalemail = rs.getString("email");

                Platform.runLater(() -> {
                    idField.setText(finalId);
                    lastNameField.setText(finalLastName);
                    firstNameField.setText(finalFirstName);
                    miField.setText(finalmi);
                    addressField.setText(finaladdress);
                    cityField.setText(finalcity);
                    stateField.setText(finalstate);
                    telephoneField.setText(finaltelephone);
                    emailField.setText(finalemail);
                });
            } else {
                System.out.println("Record not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertRecord(String id, String lastName, String firstName, String mi, String address, String city,
            String state, String telephone, String email) {
        String query = "INSERT INTO Staff (id, lastName, firstName, mi, address, city, state, telephone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connectToDatabase();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, id);
            pstmt.setString(2, lastName);
            pstmt.setString(3, firstName);
            pstmt.setString(4, mi);
            pstmt.setString(5, address);
            pstmt.setString(6, city);
            pstmt.setString(7, state);
            pstmt.setString(8, telephone);
            pstmt.setString(9, email);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Record inserted successfully.");
            } else {
                System.out.println("Record insert failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateRecord(String id, String lastName, String firstName, String mi, String address, String city,
            String state, String telephone, String email) {
        String query = "UPDATE Staff SET lastName = ?, firstName = ?, mi = ?, address = ?, city = ?, state = ?, telephone = ?, email = ? WHERE id = ?";
        try (Connection conn = connectToDatabase();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, lastName);
            pstmt.setString(2, firstName);
            pstmt.setString(3, mi);
            pstmt.setString(4, address);
            pstmt.setString(5, city);
            pstmt.setString(6, state);
            pstmt.setString(7, telephone);
            pstmt.setString(8, email);
            pstmt.setString(9, id);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Record updated successfully.");
            } else {
                System.out.println("Record update failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        TextField idField = new TextField();
        idField.setPromptText("ID");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        TextField miField = new TextField();
        miField.setPromptText("MI");
        TextField addressField = new TextField();
        addressField.setPromptText("Address");
        TextField cityField = new TextField();
        cityField.setPromptText("City");
        TextField stateField = new TextField();
        stateField.setPromptText("State");
        TextField telephoneField = new TextField();
        telephoneField.setPromptText("Telephone");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        Button viewButton = new Button("View");
        viewButton.setOnAction(e -> viewRecord(idField.getText(), idField, lastNameField, firstNameField, miField,
                addressField, cityField, stateField, telephoneField, emailField));

        Button insertButton = new Button("Insert");
        insertButton.setOnAction(e -> insertRecord(
                idField.getText(),
                lastNameField.getText(),
                firstNameField.getText(),
                miField.getText(),
                addressField.getText(),
                cityField.getText(),
                stateField.getText(),
                telephoneField.getText(),
                emailField.getText()));

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> updateRecord(
                idField.getText(),
                lastNameField.getText(),
                firstNameField.getText(),
                miField.getText(),
                addressField.getText(),
                cityField.getText(),
                stateField.getText(),
                telephoneField.getText(),
                emailField.getText()));

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> {
            idField.clear();
            lastNameField.clear();
            firstNameField.clear();
            miField.clear();
            addressField.clear();
            cityField.clear();
            stateField.clear();
            telephoneField.clear();
            emailField.clear();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                idField,
                lastNameField,
                firstNameField,
                miField,
                addressField,
                cityField,
                stateField,
                telephoneField,
                emailField,
                viewButton,
                insertButton,
                updateButton,
                clearButton);

        Scene scene = new Scene(layout, 500, 600);
        primaryStage.setTitle("Staff Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
