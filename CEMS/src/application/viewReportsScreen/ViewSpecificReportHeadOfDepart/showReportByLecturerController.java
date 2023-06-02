package application.viewReportsScreen.ViewSpecificReportHeadOfDepart;

import client.Client;
import client.ClientUI;
import common.MsgHandler;
import common.TypeMsg;
import entity.Course;
import entity.Subject;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import util.ExitButton;
import util.MinimizeButton;
import util.PathConstants;
import util.ScreenManager;

import java.util.ArrayList;
import java.util.List;

public class showReportByLecturerController {

    @FXML
    private ComboBox<String> LecturerCombo;

    @FXML
    private AnchorPane header;

    @FXML
    private Text usernameText;
    private static ObservableList<String> LecturerNames;
    private static ArrayList<String> LecturersID;
    public static String chosenLecturer;
    public void initialize() {
        ScreenManager.dragAndDrop(header);
        usernameText.setText(Client.user.getFullName());
        MsgHandler getLecutrers = new MsgHandler(TypeMsg.GetUser,Client.user.getId());
        ClientUI.chat.accept(getLecutrers);
        LecturerCombo.setItems(LecturerNames);

    }

    @FXML
    void LogOut(ActionEvent event) {
        ScreenManager.goToNewScreen(event, PathConstants.loginPath);
    }


    @FXML
    void closeClient(ActionEvent event) {
        ExitButton.closeClient(event);

    }
    public void setLecturerCombo(ArrayList<Object> Lecturers){
        ObservableList lectureList = FXCollections.observableArrayList((List) Lecturers);
        LecturerNames = FXCollections.observableArrayList();
        for (Object user : lectureList) {
            if (user instanceof User) {
                    String lecturerName = ((User) user).getFullName();
                    LecturerNames.add(lecturerName);
            }
        }
    }

    @FXML
    public void goBackToPreviousScreen(ActionEvent event) {
        ScreenManager.goToNewScreen(event, PathConstants.ViewReportHeadOfDepartmentPath);
    }

    @FXML
    public void minimizeWindow(ActionEvent event) {
        MinimizeButton.minimizeWindow(event);
    }

    @FXML
    void showReportForSpecificLecturer(ActionEvent event) {
        chosenLecturer = LecturerCombo.getSelectionModel().getSelectedItem().toString();
        MsgHandler getLecutrers = new MsgHandler(TypeMsg.GetTestsByLecutrer,chosenLecturer);
        ClientUI.chat.accept(getLecutrers);
        ScreenManager.goToNewScreen(event, PathConstants.EnterReportByLecturerPath);
    }

}
