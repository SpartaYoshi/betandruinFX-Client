package uicontrollers;

import java.net.URL;
import java.util.ResourceBundle;

import businessLogic.BlFacade;
import configuration.ConfigXML;
import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ui.MainGUI;


public class EditProfileController implements Controller{
    private MainGUI mainGUI;
    private final BlFacade businessLogic;

    public EditProfileController(BlFacade bl) {
        this.businessLogic = bl;
    }


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label birthDateLabel;

    @FXML
    private Button editProfileButton;

    @FXML
    private Label messageLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private PasswordField passwdField;

    @FXML
    private Label surnameLabel;

    @FXML
    private TextField usernameField;

    @FXML
    void onEditProfileButton() {

    }

    @FXML
    void selectBack(ActionEvent event) {
        mainGUI.showMyProfile();
    }

    @FXML
    void initialize() {

        ConfigXML config = ConfigXML.getInstance();

        if (businessLogic.getCurrentUser() == null)
            return;


        switch (config.getLocale()) {
            case "en":
                birthDateLabel.setText("Birth Date: " + this.businessLogic.getCurrentUser().getBirthdate().toString());
                nameLabel.setText("Name: " +this.businessLogic.getCurrentUser().getName());
                surnameLabel.setText("Surname: " +this.businessLogic.getCurrentUser().getSurname());
                break;

            case "es":
                birthDateLabel.setText("Fecha de nacimiento: " + this.businessLogic.getCurrentUser().getBirthdate().toString());
                nameLabel.setText("Nombre: " +this.businessLogic.getCurrentUser().getName());
                surnameLabel.setText("Apellido: " +this.businessLogic.getCurrentUser().getSurname());
                break;

            case "eus":
                birthDateLabel.setText("Jaiotze data: " + this.businessLogic.getCurrentUser().getBirthdate().toString());
                nameLabel.setText("Izena: " +this.businessLogic.getCurrentUser().getName());
                surnameLabel.setText("Abizena: " +this.businessLogic.getCurrentUser().getSurname());
                break;

            default:
        }
    }


    public void editProfile() {
        User u = businessLogic.getCurrentUser();
        if(this.usernameField!=null) {
            String oldUserName = u.getUsername();
            String newUserName = usernameField.getText();
            if (oldUserName.toLowerCase().trim().equals(newUserName.toLowerCase().trim())) {
                messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                ConfigXML config = ConfigXML.getInstance();
                switch (config.getLocale()) {
                    case "en" -> messageLabel.setText("The new username is the same as the old one");
                    case "es" -> messageLabel.setText("El nuevo nombre de usuario es el mismo que el antiguo");
                    case "eus" -> messageLabel.setText("Erabiltzaile-izen berria aurrekoaren berdina da");
                }

            }else{
                businessLogic.editProfileUsername(u);
            }
        }

        if(this.passwdField!=null) {
            String oldPassword = u.getUsername();
            String newPassword = passwdField.getText();
            if (oldPassword.toLowerCase().trim().equals(newPassword.toLowerCase().trim())) {
                messageLabel.getStyleClass().setAll("lbl", "lbl-danger");
                ConfigXML config = ConfigXML.getInstance();
                switch (config.getLocale()) {
                    case "en" -> messageLabel.setText("The new password is the same as the old one");
                    case "es" -> messageLabel.setText("La nueva contraseña es la misma que la antigua");
                    case "eus" -> messageLabel.setText("Pasahitz berria aurrekoaren berdina da");
                }

            }else{
                businessLogic.editProfileUsername(u);
                ConfigXML config = ConfigXML.getInstance();
                switch (config.getLocale()) {
                    case "en" -> messageLabel.setText("The password has been successfully changed");
                    case "es" -> messageLabel.setText("La contraseña se ha cambiado exitosamente");
                    case "eus" -> messageLabel.setText("Pasahitz berria aurrekoaren berdina da");
                }
            }
        }
    }

    @Override public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

}

