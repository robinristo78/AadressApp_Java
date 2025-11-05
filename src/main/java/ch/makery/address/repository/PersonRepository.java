package ch.makery.address.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ch.makery.address.model.Person;

public class PersonRepository {

    private final ObservableList<Person> personData = FXCollections.observableArrayList();

    public PersonRepository() {
        // Add some sample data
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
    }

    /**
     * @return ObservableList<Person>
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }
}
