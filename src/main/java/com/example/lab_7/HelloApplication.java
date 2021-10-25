package com.example.lab_7;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Graphiques");
        stage.setMaximized(true);
        BorderPane borderPane = new BorderPane();

        //Menu en haut
        MenuBar menuBar = new MenuBar();
        Menu importer = new Menu("Importer");
        Menu exporter = new Menu("Exporter");
        MenuItem lignes = new MenuItem("Lignes");
        MenuItem regions = new MenuItem("Régions");
        MenuItem barres = new MenuItem("Barres");
        MenuItem exporter1 = new MenuItem("Exporter");

        menuBar.getMenus().addAll(importer, exporter);
        exporter.getItems().addAll(exporter1);
        importer.getItems().addAll(lignes, regions, barres);

        ContextMenu contextMenu = new ContextMenu(lignes, regions, barres);
        borderPane.setOnContextMenuRequested((n) -> contextMenu.show(borderPane.getCenter(), n.getSceneX(), n.getSceneY()));

        //Choisir fichiers
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichiers data", "*dat"),
                new FileChooser.ExtensionFilter(".png" , "*.png"),
                new FileChooser.ExtensionFilter(".jpg", "*.jpg"),
                new FileChooser.ExtensionFilter(".gif", "*.gif"));

        //Axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Mois");
        xAxis.setLabel("Température (℃)");

        //Actions sur les boutons
        lignes.setOnAction((n) -> {
            File tableauligne = fileChooser.showOpenDialog(stage);
            List<String> dataligne = null;
            try {
                dataligne = Files.readAllLines(tableauligne.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            final LineChart<String, Number> graphiqueligne = new LineChart<>(xAxis, yAxis);
            graphiqueligne.setTitle("Température moyenne par mois");
            XYChart.Series seriesligne = new XYChart.Series();
            seriesligne.setName("Moyenne");
            String[] list1ligne = dataligne.get(0).split(",");
            String[] list2ligne = dataligne.get(1).split(",");

            for (int i = 0; i < list1ligne.length; i++) {

                seriesligne.getData().add(new XYChart.Data(list1ligne[i], Double.parseDouble(list2ligne[i])));
            }
            graphiqueligne.getData().addAll(seriesligne);
            borderPane.setCenter(graphiqueligne);

        });

        regions.setOnAction((n) -> {
            File tableauregions = fileChooser.showOpenDialog(stage);
            List<String> dataregion = null;
            try {
                dataregion = Files.readAllLines(tableauregions.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            final AreaChart<String, Number> graphiqueregions = new AreaChart<>(xAxis, yAxis);
            graphiqueregions.setTitle("Température moyenne par mois");
            XYChart.Series seriesregions = new XYChart.Series();
            seriesregions.setName("Moyenne");
            String[] list1regions = dataregion.get(0).split(",");
            String[] list2regions = dataregion.get(1).split(",");

            for (int i = 0; i < list1regions.length; i++) {

                seriesregions.getData().add(new XYChart.Data(list1regions[i], Double.parseDouble(list2regions[i])));
            }
            graphiqueregions.getData().addAll(seriesregions);
            borderPane.setCenter(graphiqueregions);


        });

        barres.setOnAction((n) -> {
            File tableaubarres = fileChooser.showOpenDialog(stage);
            List<String> databarres = null;
            try {
                databarres = Files.readAllLines(tableaubarres.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            final BarChart<String, Number> graphiquebarres = new BarChart<>(xAxis, yAxis);
            graphiquebarres.setTitle("Température moyenne par mois");
            XYChart.Series seriesbarres = new XYChart.Series();
            seriesbarres.setName("Moyenne");
            String[] list1barres = databarres.get(0).split(",");
            String[] list2barres = databarres.get(1).split(",");

            for (int i = 0; i < list1barres.length; i++) {

                seriesbarres.getData().add(new XYChart.Data(list1barres[i], Double.parseDouble(list2barres[i])));
            }
            graphiquebarres.getData().addAll(seriesbarres);
            borderPane.setCenter(graphiquebarres);


        });
        exporter1.setOnAction((ae) -> {
            WritableImage image = borderPane.getCenter().snapshot(null, null);
            File file = fileChooser.showSaveDialog(stage);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //BorderPane
        borderPane.setTop(menuBar);

        stage.setScene(new Scene(borderPane));
        stage.show();
        System.out.println("After show");
    }

    public static void main(String[] args) {
        launch();
    }
}