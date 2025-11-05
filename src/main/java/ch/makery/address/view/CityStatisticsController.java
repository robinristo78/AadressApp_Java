package ch.makery.address.view;

import ch.makery.address.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityStatisticsController {

    @FXML
    private PieChart pieChart;

    @FXML
    private void initialize() {
    }

    public void setPersonData(List<Person> persons) {
        Map<String, Integer> cityCounts = new HashMap<>();
        for (Person person : persons) {
            String city = person.getCity();
            cityCounts.put(city, cityCounts.getOrDefault(city, 0) + 1);
        }

        ObservableList<Data> pieChartData = FXCollections.observableArrayList();

        for (Map.Entry<String, Integer> entry : cityCounts.entrySet()) {
            String cityName = entry.getKey();
            int cityCount = entry.getValue();

            pieChartData.add(new Data(cityName, cityCount));
        }

        pieChart.setData(pieChartData);
        pieChart.setTitle("City Statistics");

        addTooltipToSlices();
    }

    private void addTooltipToSlices() {
        for (final PieChart.Data pieChartData : pieChart.getData()) {
            Node slice = pieChartData.getNode();

            String tooltipText = String.format("%.0f",
                    pieChartData.getPieValue());

            Tooltip tooltip = new Tooltip(tooltipText);
            Tooltip.install(slice, tooltip);
        }
    }
}
