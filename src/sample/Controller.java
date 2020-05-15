package sample;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    public TextField processInput;

    public TableView<Segment> table;
    public TableView<Segment> table1;

    public ComboBox comboBox;
    public VBox chartVBox;
    public Label comboErrorLabel;
    public Label nameLabel;
    public Label burstLabel;
    public Label priorityLabel;
    public TextField quantumInput;
    public Label quantumError;
    public HBox buttonsBox;
    public BorderPane root;
    public VBox legendVBox;
    public Label numberError;
    public TextField numberInput;
    public MenuItem play;
    public TextField addInput;
    public TextField sizeInput;
    public TableColumn startColumn;
    public TableColumn sizeColumn;
    public TableColumn nameColumn;
    public TableColumn processColumn;
    public TableColumn segmentColumn;
    public TableColumn sizeColumn1;
    public TextField processInput1;
    public TextField segmentName;
    public TextField segmentSize;
    public TextField memorySizeInput;
    public TextField ProcessName;
    public TextField sizeProcess;
    public TextField adressProcess;
    public TextField segegmentName;
    public TableView<Process> WaitingTable;
    public TableColumn waitingProcess;
    public  Label memoryError;
    public Label holeaddrLabel;
    public Label holesizeLabel;
    public Label proSizeLabel;
    public Label segNameLabel;
    public Label proNameLabel;
    public Label deSegAdd;
    public Label deProName;
    public Label deOldSize;
    public Label deOldAdd;
    public HBox startBox;
    GanttChart<Number,String> chart;
    public ObservableList<Segment> segments ;
    public ObservableList<Process> Waitings ;
    public Memroy memroy=new Memroy();
   public   ArrayList<Process>processes=new ArrayList<>();
    public ObservableList<Segment> holes;
   public int count=0;
    public static String[] colors = {"status-darkRed","status-green","status-blue","status-yellow","status-black",
            "status-brown","status-foshia","status-bate5y","status-smawy","status-nescafe","status-orange",
            "status-red","status-lamony","status-holoOrange","status-white","status-move","status-purple","status-black"};
    public static int inIndexOf(String name, ArrayList<Process> processes){
        int index=-1;
        for(int i=0;i<processes.size();i++){
            if(name.compareTo(processes.get(i).getName())==0){
                return i;
            }
        }
        return index;
    }

    public int in_Index_Of(Segment s,ObservableList<Segment> segments){
        int index=-1;
        for(int i=0;i<segments.size();i++){
            if(s.getName().compareTo(segments.get(i).getName())==0&&s.getProcessName().compareTo(segments.get(i).getProcessName())==0){
                return i;
            }
        }
        return index;
    }



    //called as soon as the layout loads
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root.getStylesheets().add(getClass().getResource("Viper.css").toExternalForm());
        startColumn.setCellValueFactory(new PropertyValueFactory<>("base"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("limit"));
        processColumn.setCellValueFactory(new PropertyValueFactory("processName"));
        sizeColumn1.setCellValueFactory(new PropertyValueFactory<>("limit"));
        segmentColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        waitingProcess.setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        comboBox.getItems().add("Best Fit");
        comboBox.getItems().add("First Fit");
        comboBox.getItems().add("Worst Fit");

        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

        chart = new GanttChart<>(xAxis,yAxis);
        xAxis.setLabel("");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        xAxis.setMinorTickCount(4);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);

    /*    chart.setTitle("Gantt Chart");
        chart.setTitleSide(Side.LEFT);*/
        chart.setLegendVisible(false);
        chart.setBlockHeight(400);
        chart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());
        chartVBox.getChildren().add(chart);
        chart.rotateProperty().setValue(90);



        Label label = new Label("hole");
        label.setTextFill(Color.web("#FFF"));
        label.setStyle("-fx-font-size:10pt; -fx-font-weight: bold;");
        TextField textField = new TextField();
        textField.setEditable(false);
        textField.getStyleClass().add("status-white");
        textField.setPrefWidth(10);
        textField.setPrefHeight(5);

        startBox.getChildren().addAll(textField, label, new Label(""));

         label = new Label("old process");
        label.setTextFill(Color.web("#FFF"));
        label.setStyle("-fx-font-size:10pt; -fx-font-weight: bold;");
     TextField   textField1 = new TextField();
        textField1.setEditable(false);
        textField1.getStyleClass().add("status-black");
        textField1.setPrefWidth(10);
        textField1.setPrefHeight(5);
        startBox.getChildren().addAll(textField1, label);




        memorySizeInput.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(memorySizeInput.getText()==null || memorySizeInput.getText().isEmpty()) {
                memoryError.setText("");
                return;
            }
            validateMemory(memorySizeInput.getText());

        }));

        addInput.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(addInput.getText()==null || addInput.getText().isEmpty()) {
                holeaddrLabel.setText("");
                return;
            }
            validateAddress(addInput.getText());

        }));


        sizeInput.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(sizeInput.getText()==null || sizeInput.getText().isEmpty()) {
                holesizeLabel.setText("");
                return;
            }
            validateHoleSize(sizeInput.getText());

        }));


        segmentSize.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(segmentSize.getText()==null || segmentSize.getText().isEmpty()) {
                proSizeLabel.setText("");
                return;
            }
            validateProSize(segmentSize.getText());

        }));

        processInput1.textProperty().addListener(((observable, oldValue, newValue) -> {

            validateProName(processInput1.getText());

        }));

        segmentName.textProperty().addListener(((observable, oldValue, newValue) -> {

            validateSegName(segmentName.getText());

        }));
    }






    public void addHoleButtonClicked() {

        String address = addInput.getText();
        String size = sizeInput.getText();
        if(!validateAddress(address) | !validateHoleSize(size))
            return;

        Segment node = new Segment(Integer.parseInt(address),Integer.parseInt(size),"");
        node.setState("Hole");
        node.setColor(colors[16]);
        table.getItems().add(node);
        memroy.setHoles(new Segment(node));
        addInput.clear();
        sizeInput.clear();

        holes =  table.getItems();
        /*for(int i=0;i<memroy.getNum_holes();i++){
            memroy.remove_hole(i);
            i--;
        }

        for(int i=0;i<holes.size();i++){
            holes.get(i).setState("Hole");
            holes.get(i).setColor(colors[16]);
            memroy.setHoles(new Segment(holes.get(i)));
        }
        for(int i=0;i<memroy.getNum_segmant();i++){
            memroy.remove_from_segments(i);
            i--;
        }
        chart.getData().clear();

        XYChart.Series  series=algotithem.dreaw(memroy);
        chart.getData().addAll(series);
*/



    }

    public void addSegmentButtonClicked() {

        String size = segmentSize.getText();
        String proName = processInput1.getText();
        String segName = segmentName.getText();
        if(!validateProSize(size)| !validateProName(proName) | !validateSegName(segName))
            return;

Process p=new Process();
p.setName(proName);
Segment s=new Segment();
s.setName(segName);
s.setProcessName(processInput1.getText());
s.setLimit(Integer.parseInt(size));
s.setBase(0);
s.setColor(colors[count]);
count++;

        int index=inIndexOf(p.getName(),processes);
        if(index>=0){
            processes.get(index).setSegments(s);
        }else{
            p.setSegments(s);
            processes.add(p);
        }

        Segment node = new Segment(0,Integer.parseInt(size),segmentName.getText(),processInput1.getText());


        table1.getItems().add(node);
        segmentSize.clear();
        segmentName.clear();
        processInput1.clear();
startOnAction();

    }


    public void startOnAction() {
        if(memroy.getMem_size()==0){
            chart.getData().clear();
            comboErrorLabel.setText("*Please entre memory size ");
        }

        else if (comboBox.getSelectionModel().getSelectedItem() == null) {
            XYChart.Series  series=algotithem.dreaw(memroy);
            chart.getData().clear();
            chart.getData().addAll(series);
            comboErrorLabel.setText("*Please Choose Algorithm First ");
        /*else if(table.getItems().size()==0)
            comboErrorLabel.setText("*Please Enter at least one Process ");*/
        }else {
            chart.getData().clear();
            legendVBox.getChildren().clear();
            setLegend(processes);
            XYChart.Series  series;
            algotithem.holes_sort_by_adress(memroy);


            for(int i=0;i<memroy.getNum_segmant();i++){
                if(memroy.getSegment(i).getState().compareTo("newprocess")==0){
                    Segment hole = new Segment();
                    hole.setName("hole" + memroy.getNum_holes());
                    hole.setColor(colors[16]);
                    hole.setBase(memroy.getSegment(i).getBase());
                    hole.setLimit(memroy.getSegment(i).getLimit());
                    hole.setState("Hole");

                    memroy.setHoles(hole);
                    memroy.remove_from_segments(i);
                    i--;
                }else{
                    memroy.remove_from_segments(i);
                    i--;
                }

            }
            algotithem.merge_holes(memroy);


            comboErrorLabel.setText("");
            switch ((String) comboBox.getValue()) {
                case "Best Fit": // Priority(Non-Preemptive)
                {
                    Pair<XYChart.Series,ArrayList<Process>> out= algotithem.bestfit(processes, memroy);

                    series=out.getKey();

                    chart.getData().addAll(series);
                    ArrayList<Process> wait=out.getValue();
                    for(int i=0;i<wait.size();i++){
                        int idex= algotithem.is_name_process_exist1(processes.get(i).getName(),  Waitings);
                        if(idex<0) {
                            Waitings.add(wait.get(i));
                        }
                    }
                    if(wait.size()==0){
                        Waitings.clear();
                    }
                    break;

                }
                case "First Fit": // Priority(Non-Preemptive)
                {
                    Pair<XYChart.Series,ArrayList<Process>> out= algotithem.firstfit(processes, memroy);

                    series=out.getKey();

                    chart.getData().addAll(series);
                    ArrayList<Process> wait=out.getValue();
                    for(int i=0;i<wait.size();i++){
                        int idex= algotithem.is_name_process_exist1(processes.get(i).getName(),  Waitings);
                        if(idex<0) {
                            Waitings.add(wait.get(i));
                        }                    }
                    if(wait.size()==0){
                        Waitings.clear();
                    }

                    break;

                }
                case "Worst Fit": // Priority(Non-Preemptive)
                {
                    Pair<XYChart.Series,ArrayList<Process>> out= algotithem.worstfit(processes, memroy);

                    series=out.getKey();

                    chart.getData().addAll(series);
                    ArrayList<Process> wait=out.getValue();
                    for(int i=0;i<wait.size();i++){
                        int idex= algotithem.is_name_process_exist1(processes.get(i).getName(),  Waitings);
                        if(idex<0) {
                            Waitings.add(wait.get(i));
                        }                    }
                    if(wait.size()==0){
                        Waitings.clear();
                    }
                    break;
                }

            }
        }
    }

    public void deallocateProcessOnAction() {
        String name=ProcessName.getText();


        if(name==null | name.equals(""))
            return;

        int index=algotithem.is_name_process_exist(name,processes);
        if(index>=0){
            ProcessName.clear();
            deProName.setText("");
            algotithem.deallocate_process(memroy,processes.get(index));
            algotithem.merge_holes(memroy);
            XYChart.Series  series=new XYChart.Series();
            chart.getData().clear();
            chart.getData().addAll(series);
             series = algotithem.dreaw(memroy);
            chart.getData().addAll(series);

            processes=new ArrayList<>();



            for(int i=0;i<segments.size();i++) {
                Process p=new Process();
                p.setName(segments.get(i).getProcessName());
                if(p.getName().compareTo(name)==0) {
                    segments.remove(i);
                    i--;
                }
            }
            for(int i=0;i<memroy.getNum_segmant();i++){

                    if (memroy.getSegment(i).getProcessName().compareTo(name) == 0) {
                        memroy.remove_from_segments(i);
                        i--;
                    } else if(memroy.getSegment(i).getState().compareTo("newprocess")==0){
                        Segment hole = new Segment();
                        hole.setName("hole" + memroy.getNum_holes());
                        hole.setColor(colors[16]);
                        hole.setBase(memroy.getSegment(i).getBase());
                        hole.setLimit(memroy.getSegment(i).getLimit());
                        hole.setState("Hole");

                        memroy.setHoles(hole);
                        memroy.remove_from_segments(i);
                        i--;
                    }else{
                        memroy.remove_from_segments(i);
                        i--;
                    }

            }
            for(int i=0;i<segments.size();i++){
                Process p=new Process();
                p.setName(segments.get(i).getProcessName());
                 int index2 = inIndexOf(p.getName(), processes);
                    if (index2 >= 0) {
                        p.setSegments(segments.get(i));
                        processes.get(index2).setSegments(segments.get(i));
                    } else {
                        p.setSegments(segments.get(i));
                        processes.add(p);
                    }

            }
            count=0;
            for (int i=0;i<processes.size();i++)
            {
                for(int j=0;j<processes.get(i).getNum_Segments();j++){
                    processes.get(i).getSegment(j).setColor(colors[count]);
                    count++;
                }
            }
            algotithem.merge_holes(memroy);


            startOnAction();
            table.getItems().clear();
            for(int i=0;i<memroy.getNum_holes();i++){
                if(memroy.getHoles(i).getLimit()!=0){
                    holes.add(memroy.getHoles(i));
                }

            }
        }else {

            deProName.setText("*Process doesn't exist");
        }

    }

    public void DeallocateOldProcessOnAction() {
        if(memroy.getMem_size()==0)
        {
            return;
        }

        if(!validateDeOldSize(sizeProcess.getText()) | ! validateDeOldAdd(adressProcess.getText()))
            return;

        int size=Integer.parseInt(sizeProcess.getText());
        int address=Integer.parseInt(adressProcess.getText());
        sizeProcess.clear();
        adressProcess.clear();
        for(int i=0;i<memroy.getNum_segmant();i++){
            if(memroy.getSegment(i).getState().compareTo("newprocess")==0&&memroy.getSegment(i).getBase()==address){
                //error
                return;
            }
        }
        for(int i=0;i<memroy.getNum_holes();i++){
            if(memroy.getHoles(i).getBase()==address){
                //error
                return;
            }
        } int flag=0;
        for(int i=0;i<memroy.getNum_old_proces();i++){
            if(memroy.getOld_process(i).getBase()==address){
                flag=1;
            }
        }
        if(flag==0){
            return;
        }


        algotithem.dealocate_seg(size,address,memroy,-1);
        XYChart.Series  series=new XYChart.Series();
        chart.getData().clear();
        chart.getData().addAll(series);
        series=algotithem.dreaw(memroy);
        algotithem.merge_holes(memroy);

        chart.getData().addAll(series);




        for(int i=0;i<memroy.getNum_segmant();i++){


            if(memroy.getSegment(i).getState().compareTo("newprocess")==0){
                Segment hole = new Segment();
                hole.setName("hole" + memroy.getNum_holes());
                hole.setColor(colors[16]);
                hole.setBase(memroy.getSegment(i).getBase());
                hole.setLimit(memroy.getSegment(i).getLimit());
                hole.setState("Hole");


                memroy.setHoles(hole);
                memroy.remove_from_segments(i);
                i--;
            }else{
                memroy.remove_from_segments(i);
                i--;
            }

        }
        legendVBox.getChildren().clear();
        setLegend(processes);

        algotithem.merge_holes(memroy);
        startOnAction();
        table.getItems().clear();
        for(int i=0;i<memroy.getNum_holes();i++){
            if(memroy.getHoles(i).getLimit()!=0){
                holes.add(memroy.getHoles(i));
            }

        }


    }

    public void makeOnAction() {
        memroy=new Memroy();
        processes=new ArrayList<>();
        Waitings=WaitingTable.getItems();

            Waitings.clear();


        segments=table1.getItems();

       holes =  table.getItems();

        for(int i=0;i<holes.size();i++){
            holes.get(i).setState("Hole");
            holes.get(i).setColor(colors[16]);
            memroy.setHoles(new Segment(holes.get(i)));
        }
        if(validateMemory(memorySizeInput.getText())) {
            memroy.setMem_size(Integer.parseInt(memorySizeInput.getText()));


            for (int i = 0; i < segments.size(); i++) {
                Process p = new Process();
                p.setName(segments.get(i).getProcessName());
                int index = inIndexOf(p.getName(), processes);
                if (index >= 0) {
                    p.setSegments(segments.get(i));
                    processes.get(index).setSegments(segments.get(i));
                } else {
                    p.setSegments(segments.get(i));
                    processes.add(p);
                }
            }
            count = 0;
            for (int i = 0; i < processes.size(); i++) {
                for (int j = 0; j < processes.get(i).getNum_Segments(); j++) {
                    processes.get(i).getSegment(j).setColor(colors[count]);
                    count++;
                }
            }
            algotithem.merge_holes(memroy);
        }
    }

    public void DeallocateSegmentOnAction() {
        if(!validateDeSegAdd(segegmentName.getText()))
            return;


        int address=Integer.parseInt(segegmentName.getText());
        for(int i=0;i<memroy.getNum_holes();i++){
            if(memroy.getHoles(i).getBase()==address){
                //error
                return;
            }
        }

        int index=memroy.is_base_segment_exist(address);
        if(index>=0){
            deSegAdd.setText("");
            segegmentName.clear();
            Segment s=memroy.getSegment(index);
            int index2=in_Index_Of(s,segments);

            int size=memroy.getSegment(index).getLimit();

            XYChart.Series  series=new XYChart.Series();
            chart.getData().clear();
            chart.getData().addAll(series);
            algotithem.dealocate_seg(size,address,memroy,index);
            series=algotithem.dreaw(memroy);
            algotithem.merge_holes(memroy);



            chart.getData().addAll(series);

            if(index2>=0) {
                segments.remove(index2);
            }
            processes=new ArrayList<>();
            for(int i=0;i<memroy.getNum_segmant();i++){


                if(memroy.getSegment(i).getState().compareTo("newprocess")==0){
                    Segment hole = new Segment();
                    hole.setName("hole" + memroy.getNum_holes());
                    hole.setColor(colors[16]);
                    hole.setBase(memroy.getSegment(i).getBase());
                    hole.setLimit(memroy.getSegment(i).getLimit());
                    hole.setState("Hole");

                    memroy.setHoles(hole);
                    memroy.remove_from_segments(i);
                    i--;
                }else{
                    memroy.remove_from_segments(i);
                    i--;
                }

            }
            for(int i=0;i<segments.size();i++){
                Process p=new Process();
                p.setName(segments.get(i).getProcessName());
                int index3 = inIndexOf(p.getName(), processes);
                if (index3 >= 0) {
                    p.setSegments(segments.get(i));
                    processes.get(index3).setSegments(segments.get(i));
                } else {
                    p.setSegments(segments.get(i));
                    processes.add(p);
                }

            }
            count=0;
            for (int i=0;i<processes.size();i++)
            {
                for(int j=0;j<processes.get(i).getNum_Segments();j++){
                    processes.get(i).getSegment(j).setColor(colors[count]);
                    count++;
                }
            }

            algotithem.merge_holes(memroy);
            startOnAction();

            table.getItems().clear();
            for(int i=0;i<memroy.getNum_holes();i++){
                if(memroy.getHoles(i).getLimit()!=0){
                    holes.add(memroy.getHoles(i));
                }

            }

        }else{
            deSegAdd.setText("*Segment doesn't exist");
        }

    }

    public void ExternalCompactionOnAction() {
        algotithem.External_compactio(memroy);
        XYChart.Series  series=new XYChart.Series();
        chart.getData().clear();

        chart.getData().addAll(series);
        series=algotithem.drew_com(memroy);
        algotithem.merge_holes(memroy);
        chart.getData().addAll(series);
        for(int i=0;i<memroy.getNum_segmant();i++){
            if(memroy.getSegment(i).getState().compareTo("newprocess")==0){
                Segment hole = new Segment();
                hole.setName("hole" + memroy.getNum_holes());
                hole.setColor(colors[16]);
                hole.setBase(memroy.getSegment(i).getBase());
                hole.setLimit(memroy.getSegment(i).getLimit());
                hole.setState("Hole");

                memroy.setHoles(hole);
                memroy.remove_from_segments(i);
                i--;
            }else{
                memroy.remove_from_segments(i);
                i--;
            }
        }
        algotithem.merge_holes(memroy);
        startOnAction();
        table.getItems().clear();
        for(int i=0;i<memroy.getNum_holes();i++){
            if(memroy.getHoles(i).getLimit()!=0) {
                holes.add(memroy.getHoles(i));
            }
        }
    }

    public void deleteHoleButtonClicked() {
        ObservableList<Segment> productSelected, allProducts;
        allProducts = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();
        productSelected.forEach(allProducts::remove);
        holes =  table.getItems();
        for(int i=0;i<memroy.getNum_holes();i++){
            memroy.remove_hole(i);
            i--;
        }


        for(int i=0;i<holes.size();i++){
            holes.get(i).setState("Hole");
            holes.get(i).setColor(colors[16]);
            memroy.setHoles(new Segment(holes.get(i)));
        }



    }

    public void resetHoleButtonClicked( ) {
        table.getItems().clear();
        chart.getData().clear();
    }

    public void deletSegmenteButtonClicked( ) {
        ObservableList<Segment> productSelected, allProducts;
        allProducts = table1.getItems();
        productSelected = table1.getSelectionModel().getSelectedItems();
        productSelected.forEach(allProducts::remove);
        processes=new ArrayList<>();
        segments=table1.getItems();
count=0;
        for(int i=0;i<segments.size();i++){
            Process p=new Process();
            p.setName(segments.get(i).getProcessName());
            int index=inIndexOf(p.getName(),processes);
            if(index>=0) {
                p.setSegments(segments.get(i));
                processes.get(index).setSegments(segments.get(i));
            }else{
                p.setSegments(segments.get(i));
                processes.add(p);
            }
        }

        for (int i=0;i<processes.size();i++)
        {
            for(int j=0;j<processes.get(i).getNum_Segments();j++){
                processes.get(i).getSegment(j).setColor(colors[count]);
                count++;
            }
        }
        algotithem.merge_holes(memroy);


    }

    public void resetSegmentButtonClicked( ) {
        table1.getItems().clear();
        chart.getData().clear();
        Waitings.clear();
        processes=new ArrayList<>();
        segments.clear();
        count=0;
    }


    private void setLegend( List<Process> processes)
    {
        HBox[] hBoxes = new HBox[3];
        hBoxes[0] = new HBox(10);
        legendVBox.getChildren().add(hBoxes[0]);
        int i=0;
        for(Process process:processes) {


            for (int j = 0; j < process.getNum_Segments(); j++) {



                Label label = new Label(process.getName()+":"+process.getSegment(j).getName());
                label.setTextFill(Color.web("#FFF"));
                label.setStyle("-fx-font-size:10pt; -fx-font-weight: bold;");
                TextField textField = new TextField();
                textField.setEditable(false);
                textField.getStyleClass().add(process.getSegment(j).getColor());
                textField.setPrefWidth(10);
                textField.setPrefHeight(5);

                hBoxes[i].getChildren().addAll(textField, label, new Label(""));


                if (hBoxes[i].getChildren().size() == 15) {
                    i++;
                    hBoxes[i] = new HBox(10);
                    legendVBox.getChildren().add(hBoxes[i]);

                }
            }

        }
    }

    boolean validateMemory(String no)
    {
        try {
            int number = Integer.parseInt(no);
            if(number<=0)
            {
                memoryError.setText("*Enter +ve No.");
                return false;
            }
            memoryError.setText("");
            return true;
        }catch (NumberFormatException e)
        {
            memoryError.setText("*Enter integer No.");
            return false;
        }
    }

    boolean validateAddress(String no)
    {
        try {
            int number = Integer.parseInt(no);
            if(number<0)
            {
                holeaddrLabel.setText("*Enter +ve No.");
                return false;
            }
            holeaddrLabel.setText("");
            return true;
        }catch (NumberFormatException e)
        {
            holeaddrLabel.setText("*Enter integer No.");
            return false;
        }
    }

    boolean validateHoleSize(String no)
    {
        try {
            int number = Integer.parseInt(no);
            if(number<=0)
            {
                holesizeLabel.setText("*Enter +ve No.");
                return false;
            }
            holesizeLabel.setText("");
            return true;
        }catch (NumberFormatException e)
        {
            holesizeLabel.setText("*Enter integer No.");
            return false;
        }
    }

    boolean validateProSize(String no)
    {
        try {
            int number = Integer.parseInt(no);
            if(number<=0)
            {
                proSizeLabel.setText("*Enter +ve No.");
                return false;
            }
            proSizeLabel.setText("");
            return true;
        }catch (NumberFormatException e)
        {
            proSizeLabel.setText("*Enter integer No.");
            return false;
        }
    }
    boolean validateSegName(String no)
    {


            if(no==null || no.equals(""))
            {
                segNameLabel.setText("*Enter Seg Name");
                return false;
            }
            segNameLabel.setText("");
            return true;

    }

    boolean validateProName(String no)
    {


        if(no==null || no.equals(""))
        {
            proNameLabel.setText("*Enter Process Name");
            return false;
        }
        proNameLabel.setText("");
        return true;

    }

    boolean validateDeSegAdd(String no)
    {

        try {
            int number = Integer.parseInt(no);
            if(number<0)
            {
                deSegAdd.setText("*Enter +ve No.");
                return false;
            }
            deSegAdd.setText("");
            return true;
        }catch (NumberFormatException e)
        {
            deSegAdd.setText("*Enter integer No.");
            return false;
        }

    }

    boolean validateDeProName(String no)
    {

        try {
            int number = Integer.parseInt(no);
            if(number<=0)
            {
                deProName.setText("*Enter +ve No.");
                return false;
            }
            deProName.setText("");
            return true;
        }catch (NumberFormatException e)
        {
            deProName.setText("*Enter integer No.");
            return false;
        }

    }

    boolean validateDeOldSize(String no)
    {

        try {
            int number = Integer.parseInt(no);
            if(number<=0)
            {
                deOldSize.setText("*Enter +ve No.");
                return false;
            }
            deOldSize.setText("");
            return true;
        }catch (NumberFormatException e)
        {
            deOldSize.setText("*Enter integer No.");
            return false;
        }

    }

    boolean validateDeOldAdd(String no)
    {

        try {
            int number = Integer.parseInt(no);
            if(number<=0)
            {
                deOldAdd.setText("*Enter +ve No.");
                return false;
            }
            deOldAdd.setText("");
            return true;
        }catch (NumberFormatException e)
        {
            deOldAdd.setText("*Enter integer No.");
            return false;
        }

    }




}
