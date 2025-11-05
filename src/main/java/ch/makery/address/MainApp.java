package ch.makery.address;

import java.io.File;

import ch.makery.address.util.FileDataManager;
import ch.makery.address.util.ViewManager;
import ch.makery.address.view.*;
import javafx.application.Application;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.collections.ObservableList;

import ch.makery.address.model.Person;
import ch.makery.address.repository.PersonRepository;

public class MainApp extends Application {

    private Stage primaryStage;

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
     * View Manager
    */
    private final ViewManager viewManager;

    /**
     * Constructor
     */
    public MainApp() {

        this.personRepository = new PersonRepository();
        this.personData = personRepository.getPersonData();
//        this.personData = FXCollections.observableArrayList();

        this.fileDataManager = new FileDataManager(MainApp.class);

        this.viewManager = new ViewManager(this);

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

//        initRootLayout();
//        showPersonOverview();

        initRootLayoutAndLoadData();

        viewManager.showPersonOverview();
    }

    /**
     * Initialize root layout and load last opened person file.
     */
    public void initRootLayoutAndLoadData() {
        viewManager.initRootLayout();

        File file = fileDataManager.getPersonFilePath();
        if (file != null) {
            fileDataManager.loadPersonDataFromFile(file, personData);
        }
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
     * View Manager methods
     * @return
     */
    public void showPersonOverview() {
        viewManager.showPersonOverview();
    }

    public boolean showPersonEditDialog(Person person) {
        return viewManager.showPersonEditDialog(person);
    }

    public void showBirthdayStatistics() {
        viewManager.showBirthdayStatistics();
    }

    public void showCityStatistics() {
        viewManager.showCityStatistics();
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