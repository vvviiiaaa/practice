package controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import static java.lang.Math.sqrt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javafx.event.ActionEvent;

import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import objects.Vertex;
import objects.Edge;

//import fxml.*;

public class mainController {

    public static ArrayList<Vertex> buttons = new ArrayList<Vertex>();

    public static ArrayList<Edge> edges = new ArrayList<Edge>();

    public static ArrayList<Double> vec = new ArrayList<Double>(Vertex.i);

    public static ArrayList<Integer> p = new ArrayList<Integer>();

    public static boolean shouldBuild = false;
    public static boolean shouldGenerate = false;
    public static boolean fordInWork = false;
    public static boolean nextStep = false;

    public static int shpEdgesCounter = 0;
    public static int relaxCounter = 0;

    public static boolean last = false;
    public static int counter = 0;
    public static  int cycleCounter = 0;
    public static Vertex startVert;
    public  static  boolean cycle = false;

    public static boolean fixedSizeType = false;

    public static boolean pressed;

    public static boolean generationType = false;



    @FXML
    public static Pane activePane;

    @FXML
    public static Button StepBut;

    @FXML
    public static Button DelBut;

    @FXML
    public static Button ClearBut;


    @FXML
    public static Button RandGenBut;

    @FXML
    public static Button AddArmBut;

    @FXML
    public static Button GotovoBut;

    @FXML
    public static Button FatherBut;

    @FXML
    public static Button ResultBut;

    @FXML
    public static Button FixedEdgesBut;
    public void createButton (MouseEvent event) throws IOException  {
        if (!pressed) {
            if (event.getClickCount() == 2)
                twoMouseClick(event);
            else
                oneMouseClick(event);
            }
        else
            pressed = false;
    }

    private void twoMouseClick(MouseEvent event)
    {
        if (createOnCorrectPlace(event)) { //если тыкнул в правильном месте
            if (Vertex.i == 0)
                GotovoBut.setDisable(false);
            ImageView image = new ImageView("content/drt1.png");
            Vertex vertex = new Vertex(image);
            vertex.circle.setX(event.getX() - 25);
            vertex.circle.setY(event.getY() - 25);
            vertex.setNumber();
            buttons.add(vertex);
            activePane.getChildren().addAll(vertex.circle, vertex.txt);//добавил вершину
            vertex.circle.toFront();
        }

    }

    private boolean createOnCorrectPlace(MouseEvent event)
    {
        for (Vertex vertex: buttons) {
            double fromCenterToEvent = sqrt((event.getX() - vertex.circle.getX()-25) * (event.getX() - vertex.circle.getX()-25)
                    + (event.getY() - vertex.circle.getY()-25) * (event.getY() - vertex.circle.getY()-25));
            if (fromCenterToEvent <= 40) {
                return false;
            }
        }
        return  true;
    }
    ////////////////////////////////////////////////
    public static Vertex[] vertexes = new Vertex[2];
    public static int countOfCheckedVertex = 0;
///////////////////////////////////////////////////
    public void oneMouseClick(MouseEvent event) throws IOException {
        boolean shouldContinue = true;
        checkButton(event);
        for (Vertex vertex : buttons)
        {
            if (vertex.isChecked)
            {
                if (countOfCheckedVertex != 0){
                    if(vertex.getNumber() != vertexes[0].getNumber()) {
                        vertexes[countOfCheckedVertex] = vertex;
                        countOfCheckedVertex++;
                    }
                }
                else{
                    vertexes[countOfCheckedVertex] = vertex;
                    countOfCheckedVertex++;
                }
            }
            if (countOfCheckedVertex == 2)
            {
                shouldContinue = checkEdge(event, vertexes);
                countOfCheckedVertex = setNormalView();
                if(shouldContinue) {
                    createEdge(vertexes, event);
                    vertexes[0]=null;
                    vertexes[1]=null;
                    vertexes = new Vertex[2];
                    break;
                }
            }
        }
    }

    private void createEdge(Vertex[] vertexes, MouseEvent event) throws IOException {
        System.out.println("Create edge");
        double weight = 0;
        if (fixedSizeType)
            callEdgeSizeWindow(event);
            weight = sqrt(((vertexes[0].circle.getX() - vertexes[1].circle.getX()) * (vertexes[0].circle.getX() - vertexes[1].circle.getX()))
                    + ((vertexes[0].circle.getY() - vertexes[1].circle.getY()) * (vertexes[0].circle.getY() - vertexes[1].circle.getY())));
            Edge edge = new Edge(vertexes[0], vertexes[1], weight);
            edges.add(edge);
            // Media sound = new Media(new File("src/content/Edge.wav").toURI().toString());
            //MediaPlayer mediaPlayer = new MediaPlayer(sound);
            // mediaPlayer.play();
            for (int i = 0; i < 4; i++)
                activePane.getChildren().addAll(edge.allLine[i]);
            activePane.getChildren().add(edges.get(edges.size() - 1).txtWeight);
        if (fixedSizeType)
             activePane.getChildren().remove(edges.get(edges.size() - 1).txtWeight);
            edge.line.toBack();
            System.out.println(edge.getWeight());

    }

    private static int setNormalView()
    {
        for (Vertex vertex : buttons)
        {
            vertex.circle.setStyle("-fx-image:url('content/drt1.png');");
            vertex.isChecked = false;
            countOfCheckedVertex = 0;
        }
        return 0;
    }

    private void checkButton (MouseEvent event) {
        for (Vertex vertex : buttons)
        {
            if (onCorrectPlace(event,vertex))
            {
                if (vertex.isChecked)
                {
                    vertex.circle.setStyle("-fx-image:url('content/drt1.png');");
                    vertex.isChecked = false;
                }
                else
                {
                    vertex.circle.setStyle("-fx-image:url('content/drt2.png');");
                    vertex.isChecked = true;
                }
            }

        }
    }

    private boolean onCorrectPlace(MouseEvent event,Vertex vertex)
    {
        boolean inX = event.getX() < vertex.circle.getX() + 40 && event.getX() > vertex.circle.getX() + 8;
        boolean inY = event.getY() < vertex.circle.getY() + 40 && event.getY() > vertex.circle.getY() + 8;
        return inX&&inY;
    }

    private boolean checkEdge(MouseEvent event, Vertex[] vertexes) {
        boolean shouldContinue = true;
        for (int i = 0; i < edges.size(); i++)
        {
            if (twoVertexChecked(i,vertexes))
            {
                if ((!edges.get(i).isChecked) && shpEdgesCounter == 0)
                {
                    edges.get(i).line.setStyle("-fx-stroke-width:4;-fx-stroke:red");
                    edges.get(i).drawAllLines("red",3);
                    vertexesToFront();
                    //edges.get(i).line.setStyle("-fx-stroke:red");
                    System.out.println("Weight of this edge = " + edges.get(i).getWeight());
                    shpEdgesCounter++;
                    System.out.println(shpEdgesCounter);
                    edges.get(i).isChecked = true;
                }
                else
                {
                    edges.get(i).line.setStyle("-fx-stroke-width:2;-fx-stroke:black");
                    edges.get(i).drawAllLines("black", 2);
                    shpEdgesCounter--;
                    vertexesToFront();
                    edges.get(i).isChecked = false;
                }
                shouldContinue = false;
            }
        }
        return shouldContinue;
    }

    private boolean twoVertexChecked(int i,Vertex[] vertexes)
    {
        return (edges.get(i).getV1().equals(vertexes[0])&&edges.get(i).getV2().equals(vertexes[1]));
    }

    public void buildGraph(ActionEvent actionEvent) {
        fixedSizeType = false;
        generationType = false;
        if (!shouldGenerate)
        shouldBuild = true;
        if (DelBut.isDisable())
        DelBut.setDisable(false);
        if (ClearBut.isDisable())
        ClearBut.setDisable(false);
        if (!AddArmBut.isDisable())
        AddArmBut.setDisable(true);
        if (!RandGenBut.isDisable())
        RandGenBut.setDisable(true);
        if (!FixedEdgesBut.isDisable())
            FixedEdgesBut.setDisable(true);
        if (Vertex.i > 0) {
            if (GotovoBut.isDisable())
                GotovoBut.setDisable(false);
        }
        if (!ResultBut.isDisable())
            ResultBut.setDisable(true);
        if (!FatherBut.isDisable())
        FatherBut.setDisable(true);
        if (!StepBut.isDisable())
        StepBut.setDisable(true);
        for (int i = 0; i < edges.size(); i++) {
            edges.get(i).line.setStyle("-fx-stroke:black;-fx-stroke-width:2");
            edges.get(i).drawAllLines("black", 2);
        }
        if (generationType) {
            refreshBut(actionEvent);
            generationType = false;
            shouldBuild = true;
            if (!AddArmBut.isDisable())
                AddArmBut.setDisable(true);
            if (!RandGenBut.isDisable())
                RandGenBut.setDisable(true);
            if (!FixedEdgesBut.isDisable())
                FixedEdgesBut.setDisable(true);
        }
    }

    public void tryToBuild(MouseEvent event) throws IOException {
        if (shouldBuild && !nextStep) {
            if (Vertex.i > 0) {
                if (GotovoBut.isDisable())
                    GotovoBut.setDisable(false);
            }
            else
                GotovoBut.setDisable(true);
            createButton(event);
            System.out.println("shouldBuild = true!");
        }
        else
        if (fordInWork)
        {
            if (!DelBut.isDisable())
                DelBut.setDisable(true);
            if (!ClearBut.isDisable())
                ClearBut.setDisable(true);
            if (!AddArmBut.isDisable())
                AddArmBut.setDisable(true);
            if (!RandGenBut.isDisable())
                RandGenBut.setDisable(true);
            if (!FixedEdgesBut.isDisable())
                FixedEdgesBut.setDisable(true);
            if (Vertex.i > 0) {
                if (!GotovoBut.isDisable())
                    GotovoBut.setDisable(true);
            }
            if (!ResultBut.isDisable())
                ResultBut.setDisable(true);
            if (!FatherBut.isDisable())
                FatherBut.setDisable(true);
            if (StepBut.isDisable())
                StepBut.setDisable(false);
            if (ResultBut.isDisable())
                ResultBut.setDisable(false);
            checkStartVert(event);

        }
        else
            System.out.println("shouldBuild = false!");
    }

    public void endBuilding(ActionEvent actionEvent) {
        shouldBuild = false;
        fixedSizeType = false;
        if (!DelBut.isDisable())
            DelBut.setDisable(true);
        if (!ClearBut.isDisable())
            ClearBut.setDisable(true);
        if (!AddArmBut.isDisable())
            AddArmBut.setDisable(true);
        if (!RandGenBut.isDisable())
            RandGenBut.setDisable(true);
        if (!FixedEdgesBut.isDisable())
            FixedEdgesBut.setDisable(true);
        if (!GotovoBut.isDisable())
            GotovoBut.setDisable(true);
        if (FatherBut.isDisable())
            FatherBut.setDisable(false);
        if (!StepBut.isDisable())
            StepBut.setDisable(true);
        if (!ResultBut.isDisable())
            ResultBut.setDisable(true);
    }


    public void generateGraph(ActionEvent actionEvent) throws Exception {

        if (!(shouldBuild || fordInWork))
        shouldGenerate = true;
        if (shouldGenerate) {
                generationDialogController.vertexNumber = 0;
                refreshBut(actionEvent);
            generation(actionEvent);
            shouldGenerate = false;
            if (generationDialogController.vertexNumber != 0)
            {
                if (!DelBut.isDisable())
                    DelBut.setDisable(true);
                if (!ClearBut.isDisable())
                    ClearBut.setDisable(true);
                if (!AddArmBut.isDisable())
                    AddArmBut.setDisable(true);
                if (!RandGenBut.isDisable())
                    RandGenBut.setDisable(true);
                if (!FixedEdgesBut.isDisable())
                    FixedEdgesBut.setDisable(true);
                if (!GotovoBut.isDisable())
                    GotovoBut.setDisable(true);
                if (!FatherBut.isDisable())
                    FatherBut.setDisable(true);
                if (!StepBut.isDisable())
                    StepBut.setDisable(true);
                if (!ResultBut.isDisable())
                    ResultBut.setDisable(true);
            }
            System.out.println("shouldGenerate = true!");
        }
        else
            System.out.println("shouldGenerate = false!");
    }

    public void generation(ActionEvent actionEvent) throws Exception{
        Stage stage = new Stage();
        Parent panel = FXMLLoader.load(getClass().getResource("../fxml/generationDialog.fxml"));
        stage.setTitle("—лучайна€ генераци€");
        stage.setMinWidth(200);
        stage.setMinHeight(150);
        stage.setResizable(false);
        stage.setScene(new Scene(panel));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.show();
        //graphGenerationRandom();
    }


    public void fordInWork(ActionEvent actionEvent) {
        if (!shouldGenerate && !shouldBuild && !nextStep) {
            setNormalView();
            fordInWork = true;
            //generationType = false;
            if (!ResultBut.isDisable())
                ResultBut.setDisable(true);
            FatherBut.setDisable(true);
        }
    }
    public void checkStartVert(MouseEvent event) {
        if (fordInWork && !nextStep && buttons.size() > 0) {

             StepBut.setDisable(true);
            ResultBut.setDisable(true);
            checkButton(event);
            for (Vertex vertex : buttons) {
                if (vertex.isChecked) {
                    StepBut.setDisable(false);
                    ResultBut.setDisable(false);
                    double INFINITE = 10000;
                    startVert = vertex;

                    for (int i = 0; i < Vertex.i; i++) {
                        vec.add(i, INFINITE);
                        p.add(i,-1);
                    }
                    vec.set(vertex.getNumber(), 0.0);
                    //cycleFord(vertex.getNumber(), Vertex.i, vec);
                    //System.out.println("Ford for " + vertex.getNumber() + " was done!");
                    break;
                }
            }
            DelBut.setDisable(true);
            ClearBut.setDisable(true);
            AddArmBut.setDisable(true);
            RandGenBut.setDisable(true);
            FixedEdgesBut.setDisable(true);
            GotovoBut.setDisable(true);
            FatherBut.setDisable(true);
            //ResultBut.setDisable(false);
           // StepBut.setDisable(true);
        }
        //fordInWork=false;
    }


    public void nextStep(ActionEvent actionEvent) throws IOException {

        if (fordInWork) {
            nextStep = true;
           // StepBut.setDisable(true);
            System.out.println("calling cycleFord");
            for (int i = 0; i < edges.size(); i++) {
                edges.get(i).line.setStyle("-fx-stroke:black");
                edges.get(i).drawAllLines("black",2);
            }
            cycleFord(startVert.getNumber(), Vertex.i, vec);
            if (last) {
                for (int i = 0; i < edges.size(); i++) {
                    edges.get(i).line.setStyle("-fx-stroke:black");
                    edges.get(i).drawAllLines("black",2);
                }
                ArrayList<Integer> path = new ArrayList<Integer>();
                for (int i = 0; i < Vertex.i; i++) {
                    if (startVert.getNumber() != i) {
                        for (int cur = i; cur != -1; cur = p.get(cur)) {
                            path.add(cur);
                        }
                        reverse(path);
                        for (int j = 0; j < path.size() - 1; j++){
                            edges.get(findEdge(path.get(j),path.get(j+1))).line.setStyle("-fx-stroke:red;-fx-stroke-width:4");
                            edges.get(findEdge(path.get(j),path.get(j+1))).line.toFront();
                            vertexesToFront();
                            edges.get(findEdge(path.get(j), path.get(j+1))).drawAllLines("red",2);
                        }
                        path.clear();
                    }
                }
                path.clear();
                p.clear();
                nextStep = false;
                fordInWork = false;
                last = false;
                counter = 0;
                AddArmBut.setDisable(false);
                RandGenBut.setDisable(false);
                FixedEdgesBut.setDisable(false);
                ResultBut.setDisable(true);
                StepBut.setDisable(true);
                startVert.circle.setStyle("-fx-image:url('content/drt1.png');");
                startVert.isChecked = false;
                cycleCounter = 0;
                vec.clear();
            }
        }
    }

    public void vertexesToFront () {
        for (Vertex vertex: buttons) {
            vertex.circle.toFront();
        }
    }

    public int findEdge (int v1, int v2) {
        for (int i = 0; i < edges.size(); i++) {
            if ((edges.get(i).v1.getNumber() == v1) && (edges.get(i).v2.getNumber() == v2)) {
                return i;
            }

        }
        return 0;
    }
    public void cycleFord (int s, int n, ArrayList<Double> vec) throws IOException
    {
        if(fordInWork && !cycle)
        {
            if (!last) {
                if (counter < edges.size())
                    algFord(s, n, counter++, vec);
                else {
                    relaxCounter = 0;
                    counter = 0;
                    cycleCounter++;
                    System.out.println(cycleCounter + " - cycleCounter");
                    cycle = true;
                }
            }
        }
        if (fordInWork && cycle) {
                last = true;
                if (counter < edges.size()) {
                    algFord(s, n, counter++, vec);
                    cycle = true;
                    last = false;
                }
                else {
                    if (relaxCounter != 0)
                        last = false;
                    else
                    last = true;
                    relaxCounter = 0;
                    counter = 0;
                    cycleCounter++;
                    cycle = false;
                }
        }
    }

    public void reverse(ArrayList<Integer> list) {
        for (int i = 0; i < list.size() / 2; i++) {
            int tmp = list.get(i);
            list.set(i,list.get(list.size() - i - 1));
            list.set(list.size() - i - 1, tmp);
        }
    }

    public void algFord(int s,int n, int i, ArrayList<Double> vec) throws IOException {
        double INFINITE = 10000;
                if (vec.get(mainController.edges.get(i).v1.getNumber()) < INFINITE) {

                    if (vec.get(mainController.edges.get(i).v1.getNumber()) + mainController.edges.get(i).getWeight() < vec.get(mainController.edges.get(i).v2.getNumber())) {
                        edges.get(i).line.setStyle("-fx-stroke:green");
                        edges.get(i).drawAllLines("green", 2);
                        edges.get(i).line.toFront();
                        vertexesToFront();
                        vec.set((mainController.edges.get(i).v2.getNumber()), vec.get(mainController.edges.get(i).v1.getNumber()) + mainController.edges.get(i).getWeight());
                        p.set(edges.get(i).v2.getNumber(), edges.get(i).v1.getNumber());
                        last = false;
                        relaxCounter++;
                        cycle = false;
                    }
                    else {
                        edges.get(i).line.setStyle("-fx-stroke:blue");
                        edges.get(i).line.toFront();
                        vertexesToFront();
                        edges.get(i).drawAllLines("blue", 2);
                        cycle = false;
                    }

                    for (int y = 0; y < n; y++)
                    {
                        System.out.println(s + "->" + y + ":" + (vec.get(y)!= INFINITE ?(vec.get(y)): " none"));
                    }
                }
        else {
                    edges.get(i).line.setStyle("-fx-stroke:orange");
                    edges.get(i).line.toFront();
                    vertexesToFront();
                    edges.get(i).drawAllLines("orange", 2);
                    //last = false;
                    cycle = false;
                }

    }

    public void deleteButton(ActionEvent actionEvent) {
        if(buttons.size()==0){
            System.out.println("No Vertexes!");
            return;
        }
        deleteVertex();
        deleteEdge();
    }

    private void deleteVertex()
    {
        int deletedNumb = 0;
        for(int i=0; i<buttons.size();i++)
        {
            deletedNumb = 0;
            if(buttons.get(i).isChecked)
            {
                setNormalView();
                deletedNumb = i;
                activePane.getChildren().remove(buttons.get(i).circle);
                buttons.get(i).isChecked = false;
                for (int j = edges.size()-1; j >= 0; j--)
                {
                    if (edges.get(j).getV1().equals(buttons.get(i)) || edges.get(j).getV2().equals(buttons.get(i)))
                    {
                        activePane.getChildren().removeAll(edges.get(j).allLine);
                        activePane.getChildren().remove(buttons.get(i).txt);
                        activePane.getChildren().removeAll(edges.get(j).txtWeight);
                        edges.remove(edges.get(j));

                    }
                }
                activePane.getChildren().remove(buttons.get(i).txt);
                setNormalView();
                buttons.remove(buttons.get(i));
            }
        }
        Vertex.i--;
        if (Vertex.i > 0) {
            for (int i = buttons.size(); i > deletedNumb; i--) {
                activePane.getChildren().remove(buttons.get(i-1).txt);
                buttons.get(i-1).setNumberAfterDelete(i-1);
                buttons.get(i-1).setNumber();
                activePane.getChildren().remove(buttons.get(i-1).txt);
                activePane.getChildren().add(buttons.get(i-1).txt);
            }
        }
    }

    public static void deleteEdge() {
        for(int i=edges.size()-1; i>=0;i--)
        {
            if(edges.get(i).isChecked)
            {
                activePane.getChildren().removeAll(edges.get(i).allLine);
                activePane.getChildren().removeAll(edges.get(i).txtWeight);
                edges.remove(edges.get(i));
                setNormalView();
            }
        }
        shpEdgesCounter=0;
    }

    public void refreshBut(ActionEvent actionEvent) {
        activePane.getChildren().clear();
        GotovoBut.setDisable(true);
        ResultBut.setDisable(true);
        if (!mainController.DelBut.isDisable())
            mainController.DelBut.setDisable(true);
        if (!mainController.ClearBut.isDisable())
            mainController.ClearBut.setDisable(true);
        if (mainController.AddArmBut.isDisable())
            mainController.AddArmBut.setDisable(false);
        if (mainController.RandGenBut.isDisable())
            mainController.RandGenBut.setDisable(false);
        if (FixedEdgesBut.isDisable())
            FixedEdgesBut.setDisable(false);
        if (!mainController.GotovoBut.isDisable())
            mainController.GotovoBut.setDisable(true);
        if (!mainController.FatherBut.isDisable())
            mainController.FatherBut.setDisable(true);
        if (!mainController.StepBut.isDisable())
            mainController.StepBut.setDisable(true);
        if (!ResultBut.isDisable())
            ResultBut.setDisable(true);
        shouldBuild = false;
        shouldGenerate = false;
        nextStep = false;
        //myGridPane.getChildren().remove(imageSadCat);
        edges.clear();
        buttons.clear();
        shpEdgesCounter=0;
        countOfCheckedVertex = 0;
        generationType = false;
        fixedSizeType = false;
        Vertex.i = 0;
    }


    private Vertex checkButtonToReplace (MouseEvent event) {
        for (Vertex vertex : buttons)
        {
            if (onCorrectPlace(event,vertex)) {

                pressed = true;
                return vertex;
            }

        }
        return null;
    }


    public void changeVertPlace(Event event) throws IOException {
        if (shouldBuild) {
            MouseEvent mouseEvent = (MouseEvent) event;
            Vertex currentVertex = checkButtonToReplace(mouseEvent);
            if (currentVertex != null) {
                currentVertex.circle.setX(mouseEvent.getX() - 25);
                currentVertex.circle.setY(mouseEvent.getY() - 25);
                currentVertex.syncronize();

                for (int i = 0; i < edges.size(); i++) {
                    if (edges.get(i).getV1().circle.getX() == currentVertex.circle.getX() && edges.get(i).getV1().circle.getY() == currentVertex.circle.getY()) {
                        edges.get(i).syncronize(currentVertex, edges.get(i).getV2(),generationType);
                        System.out.println("Edge" + i + "was syncronized");
                        System.out.println(edges.get(i).getV1().circle.getX() + " = x1, " + edges.get(i).getV2().circle.getX() + " = x2"
                        + " "+ edges.get(i).getWeight());
                    }
                    else {
                        if (edges.get(i).getV2().circle.getX() == currentVertex.circle.getX() && edges.get(i).getV2().circle.getY() == currentVertex.circle.getY()) {
                            edges.get(i).syncronize(edges.get(i).getV1(), currentVertex, generationType);
                            System.out.println("Edge" + i + "was syncronized");
                            System.out.println(edges.get(i).getV1().circle.getX() + " = x1, " + edges.get(i).getV2().circle.getX() + " = x2"
                                    + " " + edges.get(i).getWeight());
                        }
                    }
                    System.out.println("numb of edges = " + edges.size());
                }
            }
        }
    }

    public static void graphGenerationRandom () {
        int vertexNumber = generationDialogController.vertexNumber;
        double[][] genMatrix = new double[vertexNumber][vertexNumber];
        for (int i = 0; i < vertexNumber; i++) {
            for (int j = 0; j < vertexNumber; j++) {
                int resolution = (int) (Math.random() * 8);
                if (resolution > 5) {
                    genMatrix[i][j] = Math.random()*99 + 1;
                }
                else
                    genMatrix[i][j] = 0;
            }
            Vertex vertex = generationDialogController.vertGeneration();
            activePane.getChildren().addAll(vertex.circle,vertex.txt);
            vertex.circle.toFront();
            buttons.add(vertex);
        }
        int currentEdge = 0;
        for (int i = 0; i < vertexNumber; i++) {
            for (int j = 0; j < vertexNumber; j++) {
                if (genMatrix[i][j] != 0 && i != j) {
                    Edge edge = new Edge(buttons.get(i), buttons.get(j),genMatrix[i][j]);
                    //Edge edge = generationDialogController.edgeGeneration(buttons.get(i), buttons.get(j),genMatrix[i][j]);
                    edges.add(edge);
                    currentEdge++;
                    //activePane.getChildren().add(edge.line);
                    for (int k = 0; k < 4; k++)
                    activePane.getChildren().add(edge.allLine[k]);
                    activePane.getChildren().add(edges.get(currentEdge - 1).txtWeight);
                    edge.line.toBack();
                }
            }
        }

    }

    public void fastResult(ActionEvent actionEvent) {
        algorithFastResult(startVert.getNumber(), Vertex.i);

    }

    public void algorithFastResult (int s, int n) {
        double INFINITE = 10000;
        while (true) {
            boolean last = true;
            for (int i = 0; i < edges.size(); i++) {
                if (vec.get(mainController.edges.get(i).v1.getNumber()) < INFINITE) {

                    if (vec.get(mainController.edges.get(i).v1.getNumber()) + mainController.edges.get(i).getWeight() < vec.get(mainController.edges.get(i).v2.getNumber())) {
                        vec.set((mainController.edges.get(i).v2.getNumber()), vec.get(mainController.edges.get(i).v1.getNumber()) + mainController.edges.get(i).getWeight());
                        p.set(edges.get(i).v2.getNumber(), edges.get(i).v1.getNumber());
                        last = false;
                    }
                }
            }
            if (last)
                break;
        }
        for (int y = 0; y < n; y++)
        {
            System.out.println(s + "->" + y + ":" + (vec.get(y)!= INFINITE ?(vec.get(y)): " none"));
        }

        for (int i = 0; i < edges.size(); i++) {
            edges.get(i).line.setStyle("-fx-stroke:black");
            edges.get(i).drawAllLines("black",2);
        }
        ArrayList<Integer> path = new ArrayList<Integer>();
        for (int i = 0; i < Vertex.i; i++) {
            if (startVert.getNumber() != i) {
                for (int cur = i; cur != -1; cur = p.get(cur)) {
                    path.add(cur);
                }
                reverse(path);
                for (int j = 0; j < path.size() - 1; j++){
                    edges.get(findEdge(path.get(j),path.get(j+1))).line.setStyle("-fx-stroke:red;-fx-stroke-width:4");
                    edges.get(findEdge(path.get(j),path.get(j+1))).line.toFront();
                    vertexesToFront();
                    edges.get(findEdge(path.get(j), path.get(j+1))).drawAllLines("red",2);
                }
                path.clear();
            }
        }
        path.clear();
        p.clear();
        nextStep = false;
        fordInWork = false;
        last = false;
        counter = 0;
        AddArmBut.setDisable(false);
        RandGenBut.setDisable(false);
        FixedEdgesBut.setDisable(false);
        ResultBut.setDisable(true);
        StepBut.setDisable(true);
        startVert.circle.setStyle("-fx-image:url('content/drt1.png');");
        startVert.isChecked = false;
        cycleCounter = 0;
        vec.clear();
    }

    public void fixedEdges(ActionEvent actionEvent) throws IOException{
        refreshBut(actionEvent);
        shouldBuild = true;
        if (DelBut.isDisable())
            DelBut.setDisable(false);
        if (ClearBut.isDisable())
            ClearBut.setDisable(false);
        if (!AddArmBut.isDisable())
            AddArmBut.setDisable(true);
        if (!RandGenBut.isDisable())
            RandGenBut.setDisable(true);
        if (!FixedEdgesBut.isDisable())
            FixedEdgesBut.setDisable(true);
        if (Vertex.i > 0) {
            if (GotovoBut.isDisable())
                GotovoBut.setDisable(false);
        }
        if (!ResultBut.isDisable())
            ResultBut.setDisable(true);
        if (!FatherBut.isDisable())
            FatherBut.setDisable(true);
        if (!StepBut.isDisable())
            StepBut.setDisable(true);
        for (int i = 0; i < edges.size(); i++) {
            edges.get(i).line.setStyle("-fx-stroke:black;-fx-stroke-width:2");
            edges.get(i).drawAllLines("black", 2);
        }
        if (generationType) {
            refreshBut(actionEvent);
            generationType = false;
            shouldBuild = true;
            if (!AddArmBut.isDisable())
                AddArmBut.setDisable(true);
            if (!RandGenBut.isDisable())
                RandGenBut.setDisable(true);
            if (!FixedEdgesBut.isDisable())
                FixedEdgesBut.setDisable(true);
        }
        fixedSizeType = true;
        generationType = true;
    }

    public void callEdgeSizeWindow (Event actionEvent)throws IOException{
        Stage stage = new Stage();
        Parent panel = FXMLLoader.load(getClass().getResource("../fxml/edgeSizeDialog.fxml"));
        stage.setTitle("¬вод веса ребра");
        stage.setMinWidth(200);
        stage.setMinHeight(150);
        stage.setResizable(false);
        stage.setScene(new Scene(panel));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                event.consume();

            }

        });
    }
}
