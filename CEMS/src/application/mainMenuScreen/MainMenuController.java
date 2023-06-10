package application.mainMenuScreen;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import util.ExitButton;
import util.MinimizeButton;
import util.PathConstants;
import util.ScreenManager;

import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MainMenuController {
    @FXML
    private Text usernameText;
    @FXML
    private AnchorPane header;
    @FXML
    private Pane backPane;


    /**
     * Initializes the main menu screen.
     * Gets the authenticated user from the Client and sets the username.
     * Enables dragging and dropping of the application window using the header pane.
     */
    public void initialize() {
        usernameText.setText(Client.user.getFullName());
        detectCopying();
        ScreenManager.dragAndDrop(header);
        backPane.setVisible(false);
        if (Client.user.getRole().equals("Head of Department/Lecturer")) {
            backPane.setVisible(true);
        }
    }
        public void backToPickaRole(ActionEvent event) {
            ScreenManager.goToNewScreen(event, PathConstants.PickRolePath);
        }

    /**
     * Logs out the user and navigates back to the login screen.
     * @param event The event triggered by the logout button click.
     */
    public void LogOut(ActionEvent event) {
        ScreenManager.goToNewScreen(event, PathConstants.loginPath);
    }

    /**
     * Navigates to the manage questions screen.
     * @param event The event triggered by the manage questions button click.
     */
    public void manageQuestions(ActionEvent event) {
        ScreenManager.goToNewScreen(event, PathConstants.manageQuestions);
    }

    /**
     * Navigates to the view reports screen.
     * @param event The event triggered by the view reports button click.
     */
    public void ViewReports(ActionEvent event) {
        ScreenManager.goToNewScreen(event, PathConstants.viewReportsPath);
    }

    /**
     * Navigates to the create new test screen.
     * @param event The event triggered by the create new test button click.
     */
    public void CreateNewTest(ActionEvent event) {
        ScreenManager.goToNewScreen(event, PathConstants.createNewTestPath);
    }

    public void manageTests(ActionEvent event) {
        ScreenManager.goToNewScreen(event,PathConstants.manageTestsPath);
    }

    public void addQuestion(ActionEvent event) {
        ScreenManager.goToNewScreen(event, PathConstants.addQuestionPath);
    }

    public void detectCopying() {
        String url = "jdbc:mysql://localhost:3306/cems?serverTimezone=UTC&useSSL=false";
        String username = "root";
        String password = "Aa123456";

        String testID = "010203";

        try {
            // Connect to the database
            Connection connection = DriverManager.getConnection(url, username, password);

            // Create the SQL query
            String query = "UPDATE studentstest st " +
                    "SET st.suspicionOfCheating = 'YES' " +
                    "WHERE st.testID = '" + testID + "' " +
                    "AND st.score != '100' " +
                    "AND EXISTS (" +
                    "SELECT as1.studentID " +
                    "FROM answersofstudent as1 " +
                    "JOIN answersofstudent as2 ON as1.questionID = as2.questionID " +
                    "AND as1.studentsAnswer != as2.studentsAnswer " +
                    "WHERE as1.studentID = st.studentID " +
                    "AND as1.testID = st.testID" +
                    ")";

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute the query
            int rowsAffected = statement.executeUpdate(query);

            System.out.println("Rows affected: " + rowsAffected);

            // Close the statement and connection
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Closes the application.
     * @param event The event triggered by the close button click.
     */
    @FXML
    private void closeClient(ActionEvent event) {
        ExitButton.closeClient(event);
    }


    /*
      Minimizes the application window.
      @param event The event triggered by the minimize button click.
     */
    @FXML
    public void minimizeWindow(ActionEvent event) {
        MinimizeButton.minimizeWindow(event);
    }
}