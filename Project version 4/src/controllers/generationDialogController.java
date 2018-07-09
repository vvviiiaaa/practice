package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import objects.Edge;
import objects.Vertex;


public class generationDialogController {

    public static int vertexNumber = 0;

    @FXML
    TextField generationText;

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

    public void gdOK(ActionEvent actionEvent) {
        /*System.out.println("Ok was pressed");
        mainController.appendText("Ok was pressed\n");
        System.out.println("Text " + generationText.getText() + " was entered.");
        mainController.appendText("Text " + generationText.getText() + " was entered.\n");*/
        String text = generationText.getText();
        int n;
        if (isDigit(text)) {
            n = Integer.parseInt(text);
            if (n > 0 && n < 8) {
                vertexNumber = n;
                mainController.shouldGenerate = false;
                mainController.graphGenerationRandom();
                mainController.generationType = true;
                if (mainController.DelBut.isDisable())
                    mainController.DelBut.setDisable(false);
                if (mainController.ClearBut.isDisable())
                    mainController.ClearBut.setDisable(false);
                if (!mainController.AddArmBut.isDisable())
                    mainController.AddArmBut.setDisable(true);
                if (!mainController.RandGenBut.isDisable())
                    mainController.RandGenBut.setDisable(true);
                if (!mainController.FixedEdgesBut.isDisable())
                    mainController.FixedEdgesBut.setDisable(true);
                if (mainController.GotovoBut.isDisable())
                    mainController.GotovoBut.setDisable(false);
                if (!mainController.FatherBut.isDisable())
                    mainController.FatherBut.setDisable(true);
                if (!mainController.StepBut.isDisable())
                    mainController.StepBut.setDisable(true);
                mainController.shouldBuild = true;
                ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
                //return n;
            }
            else
            {

                generationText.clear();
                generationText.setPromptText(" 1 < n < 8 ");
                //return 0;
            }
        }
        else
        {
            generationText.clear();
            generationText.setPromptText(" 1 < n < 8 ");
           //return 0;
        }
    }

    public void gdCancel(ActionEvent actionEvent) {
       /* System.out.println("Cancel was pressed");
        mainController.appendText("Cancel was pressed\n");*/
        mainController.shouldGenerate = false;
        mainController.generationType = false;
                ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }

    public static Vertex vertGeneration () {
        ImageView image = new ImageView("content/drt1.png");
        Vertex vertex = new Vertex(image);
        double x;
        double y;
        boolean OK;
        do {
            OK = true;
            x = Math.random() * 450;
            y = Math.random() * 450;
            for (Vertex item : mainController.buttons) {
                double path = Math.sqrt((item.circle.getX() - x) * (item.circle.getX() - x)
                        + (item.circle.getY() - y) * (item.circle.getY() - y));
                if (path < 65)
                    OK = false;
            }
        }
        while (OK == false);
        vertex.circle.setX(x);
        vertex.circle.setY(y);
        vertex.setNumber();
        //mainController.buttons.add(vertex);
        return vertex;
    }

}
