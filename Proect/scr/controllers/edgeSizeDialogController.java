package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import objects.Edge;
import objects.Vertex;

import java.io.IOException;

import static java.lang.Math.sqrt;

/**
 * Created by User on 02.07.2016.
 */
public class edgeSizeDialogController {
    @FXML
    public static TextField edgeSize;
    public static double edgeWeight = -1;

    private boolean isDigit(String text)
    {
        try{
            int number = Integer.parseInt(text);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public void edgeSizeOK(ActionEvent actionEvent) {
        System.out.println("Ok was pressed");
        mainController.appendText("Ok was pressed\n");
        System.out.println("Text " + edgeSize.getText() + " was entered.");
        mainController.appendText("Text " + edgeSize.getText() + " was entered.\n");
        String text = edgeSize.getText();
        int weight;
        if (isDigit(text)) {
            weight = Integer.parseInt(text);
            // can work with weights < 0
            if (weight > -1000000) {
                edgeWeight = (int) weight;
                createEdgeFixedSize();
                mainController.shouldGenerate = false;
                mainController.generationType = true;
                if (mainController.DelBut.isDisable())
                    mainController.DelBut.setDisable(false);
                if (mainController.ClearBut.isDisable())
                    mainController.ClearBut.setDisable(false);
                if (!mainController.AddArmBut.isDisable())
                    mainController.AddArmBut.setDisable(true);
                if (!mainController.RandGenBut.isDisable())
                    mainController.RandGenBut.setDisable(true);
                if (mainController.GotovoBut.isDisable())
                    mainController.GotovoBut.setDisable(false);
                if (!mainController.FatherBut.isDisable())
                    mainController.FatherBut.setDisable(true);
                if (!mainController.StepBut.isDisable())
                    mainController.StepBut.setDisable(true);
                mainController.activePane.getChildren().remove(mainController.edges.get(mainController.edges.size() - 1).txtWeight);
                mainController.shouldBuild = true;
                mainController.activePane.getChildren().add(mainController.edges.get(mainController.edges.size() - 1).txtWeight);
                ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
                //return n;
            }
            else {
                edgeSize.clear();
                edgeSize.setPromptText(" incorrect value ");
            }
            }
            else
            {

                edgeSize.clear();
                edgeSize.setPromptText(" incorrect value ");
            }
    }

    public void edgeSizeCancel(ActionEvent actionEvent) {
        System.out.println("Cancel was pressed");
        mainController.appendText("Cancel was pressed\n");
        mainController.edges.get(mainController.edges.size() - 1).isChecked = true;
        mainController.deleteEdge();
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }

    private void createEdgeFixedSize()  {
        System.out.println("Create edge");
        mainController.appendText("Create edge\n");
        double weight = edgeWeight;
        mainController.activePane.getChildren().remove(mainController.edges.get(mainController.edges.size() - 1).txtWeight);
        mainController.edges.get(mainController.edges.size() - 1).setWeight(weight);
        mainController.activePane.getChildren().add(mainController.edges.get(mainController.edges.size() - 1).txtWeight);
        // Media sound = new Media(new File("src/content/Edge.wav").toURI().toString());
        //MediaPlayer mediaPlayer = new MediaPlayer(sound);
        // mediaPlayer.play();
    }

}
