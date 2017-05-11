package com.mhrimaz.knapsackfx;

/*
 * The MIT License
 *
 * Copyright 2017 Hossein Rimaz.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
import com.mhrimaz.model.Item;
import com.mhrimaz.model.KnapsackData;
import com.mhrimaz.model.Solution;
import com.mhrimaz.model.Util;
import com.mhrimaz.solver.KnapsackSolver;
import com.mhrimaz.solver.KnapsackStrategy;
import com.mhrimaz.solver.strategy.DPStrategy;
import com.mhrimaz.solver.strategy.GAStrategy;
import com.mhrimaz.solver.strategy.HCBestFirstStrategy;
import com.mhrimaz.solver.strategy.HCSidewaysStrategy;
import com.mhrimaz.solver.strategy.HCStandardStrategy;
import com.mhrimaz.solver.strategy.HCStochasticStrategy;
import com.mhrimaz.solver.strategy.RandomRestartStrategy;
import com.mhrimaz.solver.strategy.SAStrategy;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author hossein
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TableView<Item> itemTable;

    @FXML
    private TableColumn<Item, Item> itemVColumn;

    @FXML
    private TableColumn<Item, Item> itemWColumn;

    @FXML
    private TextField itemVTextField;

    @FXML
    private TextField itemWTextField;

    @FXML
    private TextField itemSizeField;

    @FXML
    private TextField knapsackWField;

    @FXML
    private TableView<Item> DPItemTable;

    @FXML
    private TableColumn<Item, Integer> DPitemVColumn;

    @FXML
    private TableColumn<Item, Integer> DPitemWColumn;

    @FXML
    private Label DPTimeLabel;

    @FXML
    private Label DPValueLabel;

    @FXML
    private Label DPWeightLabel;

    @FXML
    private Tab geneticItemTable;

    @FXML
    private TableView<Item> GAItemTable;

    @FXML
    private TableColumn<Item, Integer> GAitemVColumn;

    @FXML
    private TableColumn<Item, Integer> GAitemWColumn;

    @FXML
    private Label GATimeLabel;

    @FXML
    private Label GAValueLabel;

    @FXML
    private Label GAWeightLabel;

    @FXML
    private TextField populationSizeField;

    @FXML
    private TextField crossoverRateField;

    @FXML
    private TextField mutationRateField;

    @FXML
    private TextField crossoverPointsField;

    @FXML
    private TableView<Item> SAItemTable;

    @FXML
    private TableColumn<Item, Integer> SAitemVColumn;

    @FXML
    private TableColumn<Item, Integer> SAitemWColumn;

    @FXML
    private Label SATimeLabel;

    @FXML
    private Label SAValueLabel;

    @FXML
    private Label SAWeightLabel;

    @FXML
    private TextField coolingFactorField;

    @FXML
    private TextField terminationTempretureField;

    @FXML
    private TextField initialTempretureField;

    @FXML
    private TextField neighborSamplingSizeField;

    @FXML
    private TableView<Item> TSItemTable;

    @FXML
    private TableColumn<Item, Integer> TSitemVColumn;

    @FXML
    private TableColumn<Item, Integer> TSitemWColumn;

    @FXML
    private TableView<Item> HCItemTable;

    @FXML
    private TableColumn<Item, Integer> HCitemVColumn;

    @FXML
    private TableColumn<Item, Integer> HCitemWColumn;

    @FXML
    private Label HCTimeLabel;

    @FXML
    private Label HCValueLabel;

    @FXML
    private Label HCWeightLabel;

    @FXML
    private TextField iterationLimitField;

    @FXML
    private TextField restartLimitField;

    @FXML
    private TextField sidewaysMoveField;

    @FXML
    private ComboBox<String> HCVersionBox;

    @FXML
    private void handleGenerateItemButton() {
        int itemSize = Integer.valueOf(itemSizeField.getText());
        itemTable.getItems().addAll(Util.generateRandomData(itemSize));
    }

    @FXML
    private void handleAddItemButton() {
        int itemWeight = Integer.valueOf(itemWTextField.getText());
        int itemValue = Integer.valueOf(itemVTextField.getText());
        itemTable.getItems().add(new Item(itemWeight, itemValue));
    }

    @FXML
    void handleClearAlllButton(ActionEvent event) {
        itemTable.getItems().clear();
    }

    @FXML
    void handleSolveDPButton() {
        KnapsackData knapsackData = getKnapsackData();
        DPStrategy knapsackDP = new DPStrategy();
        KnapsackSolver knapsackSolver = new KnapsackSolver(knapsackData, knapsackDP);
        Solution DPBestSolution = knapsackSolver.getSolution();
        DPValueLabel.setText("Total Value: " + DPBestSolution.getGainedValue());
        DPWeightLabel.setText("Total Weight: " + DPBestSolution.getGainedWeight());
        DPTimeLabel.setText("DP Time: " + DPBestSolution.getTakenTime() + " ms");
        DPItemTable.getItems().clear();
        DPItemTable.getItems().addAll(DPBestSolution.getPickedItem());
    }

    @FXML
    private void handleSolveGAButton() {
        KnapsackData knapsackData = getKnapsackData();
        KnapsackSolver solver = new KnapsackSolver(knapsackData,
                new GAStrategy(Double.parseDouble(mutationRateField.getText()),
                        Double.parseDouble(crossoverRateField.getText()),
                        Integer.parseInt(populationSizeField.getText())));
        Solution solution = solver.getSolution();
        GAValueLabel.setText("Total Value: " + solution.getGainedValue());
        GAWeightLabel.setText("Total Weight: " + solution.getGainedWeight());
        GATimeLabel.setText("GA Time: " + solution.getTakenTime() + " ms");
        GAItemTable.getItems().clear();
        GAItemTable.getItems().addAll(solution.getPickedItem());
    }

    @FXML
    private void handleSolveSAButton() {
        KnapsackData knapsackData = getKnapsackData();
        double coolingFactor = Double.parseDouble(coolingFactorField.getText());
        double endingTempereture = Double.parseDouble(terminationTempretureField.getText());
        double initTempereture = Double.parseDouble(initialTempretureField.getText());
        int samplingSize = Integer.parseInt(neighborSamplingSizeField.getText());
        KnapsackSolver solver = new KnapsackSolver(knapsackData,
                new SAStrategy(samplingSize, initTempereture, endingTempereture, coolingFactor));
        Solution solution = solver.getSolution();

        SAValueLabel.setText("Total Value: " + solution.getGainedValue());
        SAWeightLabel.setText("Total Weight: " + solution.getGainedWeight());
        SATimeLabel.setText("SA Time: " + solution.getTakenTime() + " ms");
        SAItemTable.getItems().clear();
        SAItemTable.getItems().addAll(solution.getPickedItem());
    }

    @FXML
    private void handleSolveHCButton() {
        KnapsackData knapsackData = getKnapsackData();
        KnapsackStrategy strategy = null;
        String selectedStrategy = HCVersionBox.getSelectionModel().getSelectedItem();
        switch (selectedStrategy) {
            case "Standard":
                strategy = new RandomRestartStrategy(new HCStandardStrategy(),
                        Integer.parseInt(restartLimitField.getText()));
                break;
            case "Stochastic":
                strategy = new RandomRestartStrategy(new HCStochasticStrategy(Integer.parseInt(iterationLimitField.getText())),
                        Integer.parseInt(restartLimitField.getText()));
                break;
            case "Best First":
                strategy = new RandomRestartStrategy(new HCBestFirstStrategy(),
                        Integer.parseInt(restartLimitField.getText()));
                break;
            case "HC with Sideways Moves":
                strategy = new RandomRestartStrategy(new HCSidewaysStrategy(Integer.parseInt(sidewaysMoveField.getText())),
                        Integer.parseInt(restartLimitField.getText()));
                break;
            default:
                break;
        }
        KnapsackSolver solver = new KnapsackSolver(knapsackData, strategy);
        Solution solution = solver.getSolution();
        HCValueLabel.setText("Total Value: " + solution.getGainedValue());
        HCWeightLabel.setText("Total Weight: " + solution.getGainedWeight());
        HCTimeLabel.setText("HC Time: " + solution.getTakenTime() + " ms");
        HCItemTable.getItems().clear();
        HCItemTable.getItems().addAll(solution.getPickedItem());
    }

    private KnapsackData getKnapsackData() throws NumberFormatException {
        int knapsackWeight = Integer.parseInt(knapsackWField.getText());
        KnapsackData knapsackData = new KnapsackData(knapsackWeight);
        knapsackData.addItem(new ArrayList<>(itemTable.getItems()));
        return knapsackData;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        itemVColumn.setCellValueFactory(new PropertyValueFactory("value"));
        itemWColumn.setCellValueFactory(new PropertyValueFactory("weight"));
        GAitemVColumn.setCellValueFactory(new PropertyValueFactory("value"));
        GAitemWColumn.setCellValueFactory(new PropertyValueFactory("weight"));
        SAitemVColumn.setCellValueFactory(new PropertyValueFactory("value"));
        SAitemWColumn.setCellValueFactory(new PropertyValueFactory("weight"));
        DPitemVColumn.setCellValueFactory(new PropertyValueFactory("value"));
        DPitemWColumn.setCellValueFactory(new PropertyValueFactory("weight"));
        HCitemVColumn.setCellValueFactory(new PropertyValueFactory("value"));
        HCitemWColumn.setCellValueFactory(new PropertyValueFactory("weight"));
        TSitemVColumn.setCellValueFactory(new PropertyValueFactory("value"));
        TSitemWColumn.setCellValueFactory(new PropertyValueFactory("weight"));
        HCVersionBox.getItems().addAll("Standard", "Stochastic", "Best First", "HC with Sideways Moves");
        HCVersionBox.getSelectionModel().select(0);
    }

}
