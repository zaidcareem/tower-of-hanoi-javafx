package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Rectangle block1, block2, block3, block0;
    @FXML
    private Button rulesBtn, tower1To2, tower1To3, tower2To1, tower2To3, tower3To1, tower3To2;
    @FXML
    private Label noOfMoveLabel;
    @FXML
    private VBox tower1, tower2, tower3;
    @FXML
    ComboBox<Integer> comboBox;

    private int noOfMoves;

    private Tower t1, t2, t3;
    private Block b1, b2, b3, b0;


    public void setNoOfMoves() {
        noOfMoves++;
        noOfMoveLabel.setText("No:of Moves: " + getNoOfMoveLabel());
    }
    public int getNoOfMoveLabel() { return (noOfMoves); }
    public void showRules() {
        Alerts rulesBox = new Alerts();
        rulesBox.showRules();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        comboBox.setValue(3);
        comboBox.getItems().add(3);
        comboBox.getItems().add(4);
        newGame();
    }

    public void newGame() {

        // create towers
        t1 = new Tower(tower1);
        t2 = new Tower(tower2);
        t3 = new Tower(tower3);
        // create Blocks for 1st tower
        b1 = new Block(block1, 1);
        b2 = new Block(block2, 2);
        b3 = new Block(block3, 3);
        // add Blocks into tower 1 for puzzle initiation initiation purpose
        t1.addBlock(b3);
        t1.addBlock(b2);
        t1.addBlock(b1);
        // finished initializing the 1st tower stack
    }

    public void comboBoxFunc() {

        noOfMoves = 0;
        noOfMoveLabel.setText("No:of Moves: " + getNoOfMoveLabel());

        t1.clearTower();
        t2.clearTower();
        t3.clearTower();
        newGame();  // fills up 3 blocks in the 1st tower
        if (comboBox.getValue() == 4) {
            block0.setVisible(true);
            b0 = new Block(block0, 0);
            t1.addBlock(b0);
        } else {
            block0.setVisible(false);
        }
    }

    public void moveFromTo(Tower from, Tower to) {

        if (from.equals(to)) {
            System.out.println("Block moved from current tower to same tower");
            return;
        }

        if (from.towerContent.isEmpty()) {
            System.out.println(from + " = Empty Tower, so nothing to move from tower");
            return;
        }
        from.moveToTower(to);
    }

    public void RealMove(MouseEvent e) {

        if (e.getSource().equals(tower1To2)) {
            if (Valid(t1, t2)) {
                moveFromTo(t1, t2);
            }
        }
        if (e.getSource().equals(tower1To3)) {
            if (Valid(t1, t3)) {
                moveFromTo(t1, t3);
            }
        }
        if (e.getSource().equals(tower2To1)) {
            if (Valid(t2, t1)) {
                moveFromTo(t2, t1);
            }
        }
        if (e.getSource().equals(tower2To3)) {
            if (Valid(t2, t3)) {
                moveFromTo(t2, t3);
            }
        }
        if (e.getSource().equals(tower3To1)) {
            if (Valid(t3, t1)) {
                moveFromTo(t3, t1);
            }
        }
        if (e.getSource().equals(tower3To2)) {
            if (Valid(t3, t2)) {
                moveFromTo(t3, t2);
            }
        }
        if ((Win(t2)) || (Win(t3))) {

            // restart game
            noOfMoves = 0;
            Alerts won = new Alerts();
            won.showWin();
            noOfMoveLabel.setText("No:of Moves: 0");
            t2.clearTower();
            t3.clearTower();
            newGame();

            if (comboBox.getValue() == 4) {
                t1.addBlock(b0);
            }
        }
    }

    public boolean Valid(Tower from, Tower to) {

        Alerts invalid = new Alerts();

        if ((to.towerContent.isEmpty()) && (from.towerContent.isEmpty())) {
            noOfMoves--;
            noOfMoveLabel.setText("No:of Moves: " + getNoOfMoveLabel());
            return false;
        }

        if (to.towerContent.isEmpty()) {
            return true;
        }
        if (from.towerContent.isEmpty()) {
            invalid.showInvalidMove();
            noOfMoves--;
            noOfMoveLabel.setText("No:of Moves: " + getNoOfMoveLabel());
            return false;
        }
        if (from.towerContent.peek().width < to.towerContent.peek().width) {
            return true;
        } else {
            invalid.showInvalidMove();
            noOfMoves--;
            noOfMoveLabel.setText("No:of Moves: " + getNoOfMoveLabel());
            return false;
        }
    }

    public boolean Win(Tower t) {

        if (comboBox.getValue() == 3) {
            if (t.towerContent.size() == 3) {
                return (t.towerContent.get(2).width == 1) && (t.towerContent.get(1).width == 2) && (t.towerContent.get(0).width == 3);
            } else {
                return false;
            }
        } else if (comboBox.getValue() == 4) {
            if (t.towerContent.size() == 4) {
                return (t.towerContent.get(3).width == 0) && (t.towerContent.get(2).width == 1) && (t.towerContent.get(1).width == 2) && (t.towerContent.get(0).width == 3);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
