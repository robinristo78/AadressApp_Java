package ch.makery.address;

import java.io.File;
import java.io.IOException;

import ch.makery.address.util.FileDataManager;
import ch.makery.address.view.*;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.ObservableList;

import ch.makery.address.model.Person;
import ch.makery.address.repository.PersonRepository;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * The data as an observable list of Persons.
     */
    private final ObservableList<Person> personData;
    private final PersonRepository personRepository;

    /**
     * File data
     */
    private final FileDataManager fileDataManager;

    /**
     * Constructor
     */
    public MainApp() {

        this.personRepository = new PersonRepository();
        this.personData = personRepository.getPersonData();
//        this.personData = FXCollections.observableArrayList();

        this.fileDataManager = new FileDataManager(MainApp.class);

    }

    /**
     * Returns the data as an observable list of Persons.
     * @return
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");
//        this.primaryStage.getIcons().add(new Image("file:src/main/resources/ch/makery/address/images/address_book_32.png"));
        this.primaryStage.getIcons().add(new Image(getClass().getResource("images/address_book_32.png").toExternalForm()));

        this.fileDataManager.setPrimaryStage(primaryStage);

        initRootLayout();
        showPersonOverview();
    }

    /**
     * Enables other classes that import MainApp to use FileDataManager functions.
     * @return
     */

    public File getPersonFilePath() {
        return fileDataManager.getPersonFilePath();
    }

    public void setPersonFilePath(File file) {
        fileDataManager.setPersonFilePath(file);
    }

    public void loadPersonDataFromFile(File file) {
        fileDataManager.loadPersonDataFromFile(file, personData);
    }

    public void savePersonDataToFile(File file) {
        fileDataManager.savePersonDataToFile(file, personData);
    }

    /**
     * Initializes the root layout and tries to load the last opened
     * person file.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.
        File file = fileDataManager.getPersonFilePath();
        if (file != null) {
            fileDataManager.loadPersonDataFromFile(file, personData);
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();

            // 'Add New Person' kui eesnimi oli tühi
            if (person.getFirstName() == null || person.getFirstName().isEmpty()) {
                dialogStage.setTitle("Add New Person");
            } else {
                dialogStage.setTitle("Edit Person");
            }

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.getIcons().add(new Image(getClass().getResource("images/address_book_32.png").toExternalForm()));

            // Set the person into the controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }



    /**
     * Opens a dialog to show birthday statistics.
     */
    public void showBirthdayStatistics() {
        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/BirthdayStatistics.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Birthday Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.getIcons().add(new Image(getClass().getResource("images/address_book_32.png").toExternalForm()));

            // Set the persons into the controller.
            BirthdayStatisticsController controller = loader.getController();
            controller.setPersonData(personData);

            dialogStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to show city statistics.
     */
    public void showCityStatistics() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/CityStatistics.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("City Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.getIcons().add(new Image(getClass().getResource("images/address_book_32.png").toExternalForm()));

            CityStatisticsController controller = loader.getController();
            controller.setPersonData(personData);

            dialogStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}