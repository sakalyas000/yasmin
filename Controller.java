/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yasmin Sakalla
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Controller {
    
    private final static double BUTTON_WIDTH = 120;

    private Controller controller;

    private TextField pathField;
    private TextField courseField;
    private TextField semesterField;

    Button createEditButton;
    Button editTemplateButton;
    Button mergeButton;
    Button touchUpButton;
    Button viewButton;
    Button exportHTMLButton;

    private String path;
    private String course;
    private String semester;
    
    //@Override
    public void start(Stage primaryStage) {
        
        controller = new Controller();

        primaryStage.setTitle("Schedule Builder");

        // Configuration Section

        Label pathLabel     = new Label("Path");
        Label courseLabel   = new Label("Course");
        Label semesterLabel = new Label("Semester");
        
        // fill in values for early test
        // TBD: remove
        pathField     = new TextField("/Users/dgreen/Dropbox");
        courseField   = new TextField("333");
        semesterField = new TextField("2018-4Fallx");
        
        GridPane configGrid = new GridPane();
        configGrid.add(pathLabel,     0, 0);
        configGrid.add(pathField,     1, 0);
        configGrid.add(courseLabel,   0, 1);
        configGrid.add(courseField,   1, 1);
        configGrid.add(semesterLabel, 0, 2);
        configGrid.add(semesterField, 1, 2);
        GridPane.setHalignment(pathLabel,     HPos.RIGHT);
        GridPane.setHalignment(courseLabel,   HPos.RIGHT);
        GridPane.setHalignment(semesterLabel, HPos.RIGHT);
        configGrid.setPadding(new Insets(25, 25, 25, 25));
        configGrid.setHgap(10);
        configGrid.setVgap(10);
       
        // Work Flow Section
        
        createEditButton           = new Button("Create/Edit");
        editTemplateButton         = new Button("Edit Template");
        mergeButton                = new Button("Merge");
        touchUpButton              = new Button("Touch Up");
        viewButton                 = new Button("View");
        exportHTMLButton           = new Button("Export HTML");
        
        Label createEditLabel      = new Label("Create or modify schedule in \r\n" +
                                               "in Spreadsheet.  Export CSV");
        Label editTemplateLabel    = new Label("Edit Markdown template that\r\n" +
                                               "will hold schedule");
        Label mergeLabel           = new Label("Merge CSV infomration into\r\n" +
                                               "Template replacing old information");
        Label touchUpLabel         = new Label("Optionally edit results if\r\n" +
                                               "needed to apply any touch ups");
        Label viewLabel            = new Label("View results in final form \r\n" +
                                               "after formatting");
        Label exportHTMLLabel      = new Label("Create HTML version of schedule");

        createEditButton.setMinWidth(BUTTON_WIDTH);
        editTemplateButton.setMinWidth(BUTTON_WIDTH);
        mergeButton.setMinWidth(BUTTON_WIDTH);
        touchUpButton.setMinWidth(BUTTON_WIDTH);
        viewButton.setMinWidth(BUTTON_WIDTH);
        exportHTMLButton.setMinWidth(BUTTON_WIDTH);
 
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("1."),    0, 0);
        grid.add(createEditButton,   1, 0);
        grid.add(createEditLabel,    2, 0);
        grid.add(new Label("2."),    0, 1);
        grid.add(editTemplateButton, 1, 1);
        grid.add(editTemplateLabel,  2, 1);
        grid.add(new Label("3."),    0, 2);
        grid.add(mergeButton,        1, 2);
        grid.add(mergeLabel,         2, 2);
        grid.add(new Label("4."),    0, 3);
        grid.add(touchUpButton,      1, 3);
        grid.add(touchUpLabel,       2, 3);
        grid.add(new Label("5."),    0, 4);
        grid.add(viewButton,         1, 4);
        grid.add(viewLabel,          2, 4);
        grid.add(new Label("6."),    0, 5);
        grid.add(exportHTMLButton,   1, 5);
        grid.add(exportHTMLLabel,    2, 5);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setHgap(10);
        grid.setVgap(10);

        // Map button actions to GUI methods (which generally dispatch to
        // Dispatch behaviors
        
        createEditButton.setOnAction(event -> createEditClicked());
        editTemplateButton.setOnAction(event -> editTemplateClicked());
        mergeButton.setOnAction(event -> mergeClicked());
        touchUpButton.setOnAction(event -> touchUpClicked());
        viewButton.setOnAction(event -> viewClicked());
        exportHTMLButton.setOnAction(event -> exportHTMLClicked());

        enableReadyButtons();
        
        BorderPane borderPane = new BorderPane();
        
        borderPane.setTop(configGrid);
        borderPane.setCenter(grid);
        borderPane.setBottom(new Label("  Version 1.0 (20181124)         David G. Green <DGreen@uab.edu>  "));
        
        Scene scene = new Scene(borderPane);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);       // don't allow resize
        primaryStage.focusedProperty().addListener(
                (observable, oldValue, newValue) -> { enableReadyButtons(); } );
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Update which buttons are enabled based on the state of the system
     */
    public void enableReadyButtons() {
        copyConfigInfo();
        createEditButton.setDisable(  ! controller.readyForCreateEdit());
        editTemplateButton.setDisable(! controller.readyForEditTemplate());
        mergeButton.setDisable(       ! controller.readyForMerge());
        touchUpButton.setDisable(     ! controller.readyForTouchUp());
        viewButton.setDisable(        ! controller.readyForView());
        exportHTMLButton.setDisable(  ! controller.readyForExport());
    }
    
    /**
     * GUI side of action associated with Create/Edit button click
     * Set up config info and call code in dispatch.
     * TBD: update GUI State
     */
    public void createEditClicked() {
        copyConfigInfo();                           // ensure it is current
        controller.createEditSchedule();
        enableReadyButtons();
    }

    /**
     * GUI side of action associated with Edit Template button click
     * Set up config info and call code in dispatch.
     * TBD: update GUI State
     */
    public void editTemplateClicked() {
        copyConfigInfo();                           // ensure it is current
        controller.editTemplate();
        enableReadyButtons();
    }

    /**
     * GUI side of action associated with Merge button click
     * Set up config info and call code in dispatch.
     * TBD: update GUI State
     */
    public void mergeClicked() {
        copyConfigInfo();                           // ensure it is current
        controller.mergeSchedule();
        enableReadyButtons();
    }
    
    /**
     * GUI side of action associated with Touch Up button click
     * Set up config info and call code in dispatch.
     * TBD: update GUI State
     */
    public void touchUpClicked() {
        copyConfigInfo();                           // ensure it is current
        controller.touchUpResult();        
        enableReadyButtons();
    }
    
    /**
     * GUI side of action associated with View button click
     * Set up config info and call code in dispatch.
     * TBD: update GUI State
     */
    public void viewClicked() {
        copyConfigInfo();                           // ensure it is current
        controller.viewResult();        
        enableReadyButtons();
    }
    
    /**
     * GUI side of action associated with Export HTML button click
     * Set up config info and call code in dispatch.
     * TBD: update GUI State
     */
    public void exportHTMLClicked() {
        copyConfigInfo();                           // ensure it is current
        controller.xportResult();
        enableReadyButtons();
    }

    // Copy the config out of text fields into private String variables
    // and convey this info to the dispatch object
    private void copyConfigInfo() {
        path     = pathField.getText();
        course   = courseField.getText();
        semester = semesterField.getText();
        controller.setFullPath(path, course, semester);
    }
}
