package application.ManageQuestionsScreen;

import client.ClientUI;
import util.*;
import entity.Question;
import common.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class UpdateQuestionController {

    @FXML
    private AnchorPane header;

    @FXML
    private TextField idField;

    @FXML
    private TextField subjectField;

    @FXML
    private TextField courseNameField;

    @FXML
    private TextArea questionTextField;

    @FXML
    private TextField questionNumberField;

    @FXML
    private TextField lecturerField;

    @FXML
    private Button saveButton;

    private Question question;
    private Stage manageQuestions;

    public void initialize() {
        ScreenManager.dragAndDrop(header);
    }

    public void setManage(Stage manageQuestions) {
        this.manageQuestions = manageQuestions;
    }


    @FXML
    void onSaveButtonClicked() {
        saveData();
        reloadPage();
    }

    private void saveData() {
        ArrayList<Question> arr = new ArrayList<>();

        Question updatedQuestion = new Question(
                questionNumberField.getText(),
                question.getId(),
                questionTextField.getText(),
                lecturerField.getText(),
                subjectField.getText(),
                courseNameField.getText()
        );
        arr.add(updatedQuestion);
        MsgHandler editQ = new MsgHandler(TypeMsg.EditQuestion, arr);
        ClientUI.chat.accept(editQ);
    }

    private void reloadPage() {
        Stage currentStage = (Stage) saveButton.getScene().getWindow();
        currentStage.close();

        manageQuestions.close();
        ScreenManager.showStage("ManageQuestionsScreen/ManageQuestions.fxml", "/application/images/Icon.png");
    }

    public void setQuestion(Question question) {
        this.question = question;
        subjectField.setText(question.getSubject());
        courseNameField.setText(question.getCourse_name());
        questionTextField.setText(question.getQuestion_text());
        questionNumberField.setText(question.getQuestion_number());
        lecturerField.setText(question.getLecturer());
        idField.setText(question.getId());
    }

    public void closePopUp(ActionEvent event) {
        ExitButton.closePopUp(event);
    }

    public void minimizeWindow(ActionEvent event) {
        MinimizeButton.minimizeWindow(event);
    }
}
