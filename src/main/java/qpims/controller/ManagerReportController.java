/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package qpims.controller;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import qpims.model.JobType;
import qpims.model.QPropertyDAO;

public class ManagerReportController implements Initializable {
    @FXML
    private BarChart<?,?> bcRepairJobs;
    @FXML
    private BarChart<?,?> bcCompletedJobs;
    
    private XYChart.Series series1;
    private XYChart.Series series2;
    private XYChart.Series series3;
    private XYChart.Series series4;
    private QPropertyDAO dao;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = QPropertyDAO.getInstance();
        //Bar chart showing completed jobs distribution by type for all houses
        bcCompletedJobs.categoryGapProperty().setValue(10);
        bcCompletedJobs.maxWidth(500);
        
        
        series1 = new XYChart.Series();
        series1.setName("Completed Jobs by Type");
        series1.getData().add(new XYChart.Data("ELECTRICAL", dao.getAllCompletedBookingsByType(JobType.ELECTRICAL).size()));
        series1.getData().add(new XYChart.Data("PLUMBING", dao.getAllCompletedBookingsByType(JobType.PLUMBING).size()));
        series1.getData().add(new XYChart.Data("STRUCTURAL", dao.getAllCompletedBookingsByType(JobType.STRUCTURAL).size()));
        series1.getData().add(new XYChart.Data("CLEANING", dao.getAllCompletedBookingsByType(JobType.CLEANING).size()));
        series1.getData().add(new XYChart.Data("GARDENING", dao.getAllCompletedBookingsByType(JobType.GARDENING).size()));
        series1.getData().add(new XYChart.Data("PEST CONTROL", dao.getAllCompletedBookingsByType(JobType.PEST_CONTROL).size()));
        bcCompletedJobs.getData().addAll(series1);

        //display minimum, average and maximum repair job cost
        bcRepairJobs.categoryGapProperty().setValue(0);
        bcRepairJobs.categoryGapProperty().setValue(0);
        
        //minimum
        series2 = new XYChart.Series();
        series2.setName("Minimum");
        List<Double> allBookingCharges = dao.getAllBookingCharges();
        double min = (allBookingCharges.isEmpty())? 0 : Collections.min(allBookingCharges);
        series2.getData().add(new XYChart.Data("Minimum", min));
        
        
        //average
        series3 = new XYChart.Series();
        series3.setName("Average");
        series3.getData().add(new XYChart.Data("Average", allBookingCharges.stream().mapToDouble(Double::doubleValue).average().orElse(0.0)));
        
        //maximum
        series4 = new XYChart.Series();
        series4.setName("Maximum");
        double max = (allBookingCharges.isEmpty())? 0 : Collections.max(allBookingCharges);
        series4.getData().add(new XYChart.Data("Maximum", max));
        
        //add all series to the bar chart
        bcRepairJobs.getData().addAll(series2, series3, series4);
        
        
        
        
        
        
        
        
    }    
    
}
