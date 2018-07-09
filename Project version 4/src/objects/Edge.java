package objects;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.awt.*;

import static java.lang.Math.sqrt;



public class Edge {
    public Vertex v1;
    public Vertex v2;
    private double weight;
    public Line line= new Line();
    public Line allLine[];
    public boolean isChecked;
    public Text txt;
    public Text txtWeight;

    public Edge(Vertex v1, Vertex v2, double weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
        this.isChecked = false;
        this.allLine = createLine (v1,v2);
        this.line = allLine[0];
    }
    public void drawAllLines (String color, int width) {
        allLine[1].setStyle("-fx-stroke:" + color +";-fx-stroke-width:" + width);
        allLine[2].setStyle("-fx-stroke:" + color + ";-fx-stroke-width:" + width);
        allLine[3].setStyle("-fx-stroke:" + color + ";-fx-stroke-width:" + width);
    }

    public Line[] createLine(Vertex v1, Vertex v2) {
        //Line line = new Line();
        //Polygon.
        this.line.setStartX(v1.circle.getX() + 25);
        this.line.setStartY(v1.circle.getY() + 25);
        this.line.setEndX(v2.circle.getX() + 25);
        this.line.setEndY(v2.circle.getY() + 25);
        this.line.setStyle("-fx-stroke-width:2");

        //Media sound = new Media(new File("").toURI().toString());
        //MediaPlayer mediaPlayer = new MediaPlayer(sound);
        //mediaPlayer.play();
        double arrowHeight=15, arrowWidth=15;
        double dx, dy,dx1,dy1;
        int m;
        if (v2.circle.getX() > v1.circle.getX()) {
            dx = v2.circle.getX() - v1.circle.getX();
            dy = v2.circle.getY() - v1.circle.getY();
            dx1=v1.circle.getX()+dx/2+15;
            m = 1;
        } else {
            dx = -v2.circle.getX() + v1.circle.getX();
            dy = -v2.circle.getY() + v1.circle.getY();
            dx1=v2.circle.getX()+dx/2+15;
            m = -1;
        }

        if(v2.circle.getY() > v1.circle.getY()){
            dy1= v1.circle.getY()+(v2.circle.getY() - v1.circle.getY())/2+15;
        }
        else {
            dy1 = v2.circle.getY()+(-v2.circle.getY() + v1.circle.getY())/2+15;
        }

        double angle = dy / dx;
        angle = Math.atan(angle);

        txtWeight = new Text((dx1),(dy1),""+(int)getWeight());
        txtWeight.setRotate(Math.toDegrees(angle));

        Point p5=new Point((int)(v2.circle.getX()-m*(15)*Math.cos(angle))+25,(int)(v2.circle.getY()-m*(15)*Math.sin(angle))+25);
        Point p2=new Point((int)(v2.circle.getX()-m*(arrowHeight+15)*Math.cos(angle)),(int)(v2.circle.getY()-m*(arrowHeight+15)*Math.sin(angle)));
        angle = Math.atan(((-1) * dx) / dy);
        Point p3=new Point((int)(p2.getX()-(arrowWidth / 2) * Math.cos(angle))+25, (int)(p2.getY()- (arrowWidth / 2) * Math.sin(angle))+25);
        Point p4=new Point((int)(p2.getX()+(arrowWidth / 2) * Math.cos(angle))+25, (int)(p2.getY()+ (arrowWidth / 2) * Math.sin(angle))+25);

        txt = new Text(v1.circle.getX(),v1.circle.getY(),""+weight);

        Line line1 = new Line();
        line1.setStartX(p5.getX());
        line1.setStartY(p5.getY());
        line1.setEndX(p3.getX());
        line1.setEndY(p3.getY());
        line1.setStyle("-fx-stroke-width:2");
        Line line2 = new Line();
        line2.setStartX(p3.getX());
        line2.setStartY(p3.getY());
        line2.setEndX(p4.getX());
        line2.setEndY(p4.getY());
        line2.setStyle("-fx-stroke-width:2");
        Line line3 = new Line();
        line3.setStartX(p4.getX());
        line3.setStartY(p4.getY());
        line3.setEndX(p5.getX());
        line3.setEndY(p5.getY());
        line3.setStyle("-fx-stroke-width:2");
        Line[] lines=new Line[4];
        lines[0]=line;
        lines[1]=line1;
        lines[2]=line2;
        lines[3]=line3;
        return lines;

    }

    public void syncronize (Vertex v1,Vertex v2, boolean weightChange) {
        this.v1 = v1;
        this.v2 = v2;
        changeLine(v1, v2, weightChange);
    }

    public void changeLine (Vertex v1, Vertex v2, boolean weightChange) {
        this.line.setStartX(v1.circle.getX() + 25);
        this.line.setStartY(v1.circle.getY() + 25);
        this.line.setEndX(v2.circle.getX() + 25);
        this.line.setEndY(v2.circle.getY() + 25);

        if (!weightChange) {
            this.weight = (int)sqrt(((v1.circle.getX() - v2.circle.getX()) * (v1.circle.getX() - v2.circle.getX()))
                    + ((v1.circle.getY() - v2.circle.getY()) * (v1.circle.getY() - v2.circle.getY())));
        }

        {
            this.weight = (int)this.weight;
        }
        double arrowHeight=15, arrowWidth=15;
        double dx, dy,dx1,dy1;
        int m;
        if (v2.circle.getX() > v1.circle.getX()) {
            dx = v2.circle.getX() - v1.circle.getX();
            dy = v2.circle.getY() - v1.circle.getY();
            dx1= v1.circle.getX()+dx/2+15;
            m = 1;
        } else {
            dx = -v2.circle.getX() + v1.circle.getX();
            dy = -v2.circle.getY() + v1.circle.getY();
            dx1=v2.circle.getX()+dx/2+15;
            m = -1;
        }

        if(v2.circle.getY() > v1.circle.getY()){
            dy1= v1.circle.getY()+(v2.circle.getY() - v1.circle.getY())/2+15;
        }
        else {
            dy1 = v2.circle.getY()+(-v2.circle.getY() + v1.circle.getY())/2+15;
        }

        double angle = dy / dx;
        angle = Math.atan(angle);

        txtWeight.setX(dx1);
        txtWeight.setY(dy1);
        txtWeight.setRotate(Math.toDegrees(angle));
        txtWeight.setText("" + weight);

        Point p5=new Point((int)(v2.circle.getX()-m*(15)*Math.cos(angle))+25,(int)(v2.circle.getY()-m*(15)*Math.sin(angle))+25);
        Point p2=new Point((int)(v2.circle.getX()-m*(arrowHeight+15)*Math.cos(angle)),(int)(v2.circle.getY()-m*(arrowHeight+15)*Math.sin(angle)));
        angle = Math.atan(((-1) * dx) / dy);
        Point p3=new Point((int)(p2.getX()-(arrowWidth / 2) * Math.cos(angle))+25, (int)(p2.getY()- (arrowWidth / 2) * Math.sin(angle))+25);
        Point p4=new Point((int)(p2.getX()+(arrowWidth / 2) * Math.cos(angle))+25, (int)(p2.getY()+ (arrowWidth / 2) * Math.sin(angle))+25);

        txt.setX(p3.getX());
        txt.setY(p3.getY());

        //Line line1 = new Line();
        allLine[1].setStartX(p5.getX());
        allLine[1].setStartY(p5.getY());
        allLine[1].setEndX(p3.getX());
        allLine[1].setEndY(p3.getY());
        //Line line2 = new Line();
        allLine[2].setStartX(p3.getX());
        allLine[2].setStartY(p3.getY());
        allLine[2].setEndX(p4.getX());
        allLine[2].setEndY(p4.getY());
        //Line line3 = new Line();
        allLine[3].setStartX(p4.getX());
        allLine[3].setStartY(p4.getY());
        allLine[3].setEndX(p5.getX());
        allLine[3].setEndY(p5.getY());
        // Line[] lines=new Line[4];

        //allLine[0]=line;
        //lines[1]=line1;
        //lines[2]=line2;
        //lines[3]=line3;
        //return lines;
    }

    public Vertex getV1() {
        return this.v1;
    }

    public Vertex getV2() {
        return this.v2;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight (double weight) {
        this.weight = weight;
        this.txtWeight.setText(""+weight);
    }
}
