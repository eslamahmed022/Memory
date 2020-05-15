package sample;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.util.Pair;

import java.util.ArrayList;

public class algotithem {
    public static String[] colors = {"status-darkRed","status-green","status-blue","status-yellow","status-black",
            "status-brown","status-foshia","status-bate5y","status-smawy","status-nescafe","status-orange",
            "status-red","status-lamony","status-holoOrange","status-white","status-move","status-purple","status-black"};
    public static void merge_queue(ArrayList<Segment> queue) {
        for (int i = 0; i < queue.size(); i++) {
            for (int j = 0; j < queue.size(); j++) {
                if (queue.get(i).getBase() == queue.get(j).getBase() && i != j) {
                    Segment t=queue.get(j);
                    t.setLimit(queue.get(i).getLimit() + queue.get(j).getLimit());
                    t.setBase(queue.get(j).getBase());
                    queue.set(j,t);
                    queue.remove(i);
                    i = 0;
                    break;
                }
                if (queue.get(i).getBase() + queue.get(i).getLimit() == queue.get(j).getBase() && i != j) {
                    Segment t=queue.get(j);
                    t.setLimit(queue.get(i).getLimit() + queue.get(j).getLimit());
                    t.setBase(queue.get(i).getBase());
                    queue.set(j,t);

                    queue.remove(i);
                    i = 0;
                    break;
                }
            }
        }

    }

    public static boolean it_is_enough_firstfit(Memroy memroy, Process process) {
        int ok = 0;
        int k = 0;

        ArrayList<Segment> queue = new ArrayList<>();
        for (int i = 0; i < memroy.getNum_holes(); i++) {
            queue.add(new Segment(memroy.getHoles(i)));
        }

        for (int j = 0; j < process.getNum_Segments(); j++) {

            for (int i = 0; i < queue.size(); i++) {

                if (process.getSegment(j).getLimit() <= queue.get(i).getLimit()) {
                    queue.get(i).setLimit(queue.get(i).getLimit() - process.getSegment(j).getLimit());
                    queue.get(i).setBase(queue.get(i).getBase()+queue.get(i).getLimit());
                    ok++;
                    merge_queue(queue);
                    break;

                }

            }
        }
        if (ok == process.getNum_Segments()) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<Segment> Holes_sort_queue(ArrayList<Segment> queue) {
        int i, j;
        int num_holes = queue.size();
        for (i = 0; i < num_holes; i++) {

            for (j = i + 1; j < num_holes; j++) {
                if (queue.get(j).getLimit() < queue.get(i).getLimit()) {
                    Segment temp = queue.get(i);
                    queue.set(i, queue.get(j));
                    queue.set(j, temp);
                }
            }

        }
        return queue;
    }

    public static boolean it_is_enough_bestfit(Memroy memroy, Process process) {
        int ok = 0;
        int k = 0;
        ArrayList<Segment> queue = new ArrayList<>();

        for (int i = 0; i < memroy.getNum_holes(); i++) {
            queue.add(new Segment(memroy.getHoles(i)));
        }


        queue = Holes_sort_queue(queue);
        for (int j = 0; j < process.getNum_Segments(); j++) {

            for (int i = 0; i < queue.size(); i++) {

                if (process.getSegment(j).getLimit() <= queue.get(i).getLimit()) {
                    queue.get(i).setLimit(queue.get(i).getLimit() - process.getSegment(j).getLimit());
                    queue.get(i).setBase(queue.get(i).getBase()+queue.get(i).getLimit());
                    ok++;
                    merge_queue(queue);
                    queue = Holes_sort_queue(queue);
                    break;

                }

            }
        }
        if (ok == process.getNum_Segments()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean it_is_enough_worstfit(Memroy memroy, Process process) {
        int ok = 0;
        int k = 0;
        ArrayList<Segment> queue = new ArrayList<>();

        for (int i = 0; i < memroy.getNum_holes(); i++) {
            queue.add(new Segment(memroy.getHoles(i)));
        }


        queue = Holes_sort_queue(queue);
        for (int j = 0; j < process.getNum_Segments(); j++) {

            for (int i = queue.size() - 1; i >= 0; i--) {

                if (process.getSegment(j).getLimit() <= queue.get(i).getLimit()) {
                    queue.get(i).setLimit(queue.get(i).getLimit() - process.getSegment(j).getLimit());
                    queue.get(i).setBase(queue.get(i).getBase()+queue.get(i).getLimit());
                    ok++;
                    merge_queue(queue);
                    queue = Holes_sort_queue(queue);
                    break;

                }

            }
        }
        if (ok == process.getNum_Segments()) {
            return true;
        } else {
            return false;
        }
    }

    public static Pair bestfit(ArrayList<Process> processes, Memroy memroy) {
        String s = "0";
        Holes_sort(memroy);

        ArrayList<Process> waitings=new ArrayList<>();
        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < processes.size(); i++) {
            if (it_is_enough_bestfit(memroy, processes.get(i))) {

                for (int j = 0; j < processes.get(i).getNum_Segments(); j++) {
                    Process p = new Process();
                    p = processes.get(i);

                    for (int k = 0; k < memroy.getNum_holes(); k++) {
                        int hole_size = memroy.getHoles(k).getLimit();
                        int p_size = p.getSegment(j).getLimit();
                        if (p_size <= hole_size) {
                            Segment s1 = new Segment();
                            s1.setName(p.getSegment(j).getName());
                            s1.setLimit(p_size);
                            s1.setColor(p.getSegment(j).getColor());
                            s1.setProcessName(p.getSegment(j).getProcessName());
                            s1.setState("newprocess");
                            int index = memroy.is_hole_exist(memroy.getHoles(k));
                            if (index >= 0) {
                                memroy.remove_from_segments(index);
                            }
                            s1.setBase(memroy.getHoles(k).getBase());
                            memroy.setSegments(s1);
                            memroy.set_new_size_hole(k, hole_size - p_size, memroy.getHoles(k).getBase() + p_size);


                            break;


                        }
                    }
                }
            } else {


                   waitings.add(processes.get(i));


            }


        }
        Segments_sort(memroy);
        clear_oldprocess(memroy);
        int base = 0;
        int end = 0;
        for (int i = 0; i < memroy.getNum_segmant(); i++) {
            if (base == memroy.getSegment(i).getBase()) {
                end = base + memroy.getSegment(i).getLimit();
                s = s + " " + memroy.getSegment(i).getName() + " " + end;


                base = end;
                continue;

            }
            Segment old=new Segment();
            old.setColor(colors[17]);
            old.setBase(base);
            old.setLimit(memroy.getSegment(i).getBase()-base);
            old.setName("oldprocess");
            memroy.setOld_process(old);
            base = memroy.getSegment(i).getBase();
            end = base + memroy.getSegment(i).getLimit();

            s = s + " " + "oldprocess" + " " + memroy.getSegment(i).getBase() + " " + memroy.getSegment(i).getName() + " " + end;
                     base = end;

        }
        if (end != memroy.getMem_size()) {
            Segment m=new Segment();
            m.setLimit(memroy.getMem_size()-base);
            m.setBase(base);
            m.setName("oldprocess");
            m.setColor(colors[17]);
            memroy.setOld_process(m);
            s = s + " " + "oldprocess" + " " + memroy.getMem_size();

        }
        for(int i=0;i<memroy.getNum_old_proces();i++){
            memroy.setSegments(memroy.getOld_process(i));
        }
        Segments_sort(memroy);
        for(int i=0;i<memroy.getNum_segmant();i++){
            series.getData().add(new XYChart.Data(memroy.getSegment(i).getBase(),"", new GanttChart.ExtraData( memroy.getSegment(i).getLimit()-1,memroy.getSegment(i).getColor())));
        }
        Pair<XYChart.Series,ArrayList<Process>> out=new Pair<>(series,waitings);
        return out;
    }

    public static Pair worstfit(ArrayList<Process> processes, Memroy memroy) {
        String s = "0";
        XYChart.Series series = new XYChart.Series();
        ArrayList<Process> waitings=new ArrayList<>();
        Holes_sort(memroy);
        for (int i = 0; i < processes.size(); i++) {
            if (it_is_enough_worstfit(memroy, processes.get(i))) {
                for (int j = 0; j < processes.get(i).getNum_Segments(); j++) {
                    Process p = new Process();
                    p = processes.get(i);

                    for (int k = memroy.getNum_holes() - 1; k >= 0; k--) {
                        int hole_size = memroy.getHoles(k).getLimit();
                        int p_size = p.getSegment(j).getLimit();
                        if (p_size <= hole_size) {
                            Segment s1 = new Segment();
                            s1.setName(p.getSegment(j).getName());
                            s1.setLimit(p_size);
                            s1.setColor(p.getSegment(j).getColor());
                            s1.setProcessName(p.getSegment(j).getProcessName());
                            s1.setState("newprocess");
                            int index = memroy.is_hole_exist(memroy.getHoles(k));
                            if (index >= 0) {
                                memroy.remove_from_segments(index);
                            }
                            s1.setBase(memroy.getHoles(k).getBase());
                            memroy.setSegments(s1);
                            memroy.set_new_size_hole(k, hole_size - p_size, memroy.getHoles(k).getBase() + p_size);


                            break;


                        }
                    }
                }
            } else {
                waitings.add(processes.get(i));
            }


        }
        Segments_sort(memroy);
        clear_oldprocess(memroy);
        int base = 0;
        int end = 0;
        for (int i = 0; i < memroy.getNum_segmant(); i++) {
            if (base == memroy.getSegment(i).getBase()) {
                end = base + memroy.getSegment(i).getLimit();
                s = s + " " + memroy.getSegment(i).getName() + " " + end;


                base = end;
                continue;

            }
            Segment old=new Segment();
            old.setColor(colors[17]);
            old.setBase(base);
            old.setLimit(memroy.getSegment(i).getBase()-base);

            old.setName("oldprocess");
            memroy.setOld_process(old);
            base = memroy.getSegment(i).getBase();
            end = base + memroy.getSegment(i).getLimit();

            s = s + " " + "oldprocess" + " " + memroy.getSegment(i).getBase() + " " + memroy.getSegment(i).getName() + " " + end;
            base = end;

        }
        if (end != memroy.getMem_size()) {
            Segment m=new Segment();
            m.setLimit(memroy.getMem_size()-base);
            m.setBase(base);
            m.setName("oldprocess");
            m.setColor(colors[17]);
            memroy.setOld_process(m);
            s = s + " " + "oldprocess" + " " + memroy.getMem_size();

        }
        for(int i=0;i<memroy.getNum_old_proces();i++){
            memroy.setSegments(memroy.getOld_process(i));
        }
        Segments_sort(memroy);
        for(int i=0;i<memroy.getNum_segmant();i++){
            series.getData().add(new XYChart.Data(memroy.getSegment(i).getBase(),"", new GanttChart.ExtraData( memroy.getSegment(i).getLimit()-1,memroy.getSegment(i).getColor())));
        }

        Pair<XYChart.Series,ArrayList<Process>> out=new Pair<>(series,waitings);
        return out;

    }

    public static Pair firstfit(ArrayList<Process> processes, Memroy memroy) {
        String s = "0";
        XYChart.Series series = new XYChart.Series();
        ArrayList<Process> waitings=new ArrayList<>();


        for (int i = 0; i < processes.size(); i++) {
            if (it_is_enough_firstfit(memroy, processes.get(i))) {
                for (int j = 0; j < processes.get(i).getNum_Segments(); j++) {
                    Process p = new Process();
                    p = processes.get(i);

                    for (int k = 0; k < memroy.getNum_holes(); k++) {
                        int hole_size = memroy.getHoles(k).getLimit();
                        int p_size = p.getSegment(j).getLimit();
                        if (p_size <= hole_size) {
                            Segment s1 = new Segment();
                            s1.setName(p.getSegment(j).getName());
                            s1.setLimit(p_size);
                            s1.setColor(p.getSegment(j).getColor());
                            s1.setProcessName(p.getSegment(j).getProcessName());
                            s1.setState("newprocess");
                            int index = memroy.is_hole_exist(memroy.getHoles(k));
                            if (index >= 0) {
                                memroy.remove_from_segments(index);
                            }
                            s1.setBase(memroy.getHoles(k).getBase());
                            memroy.setSegments(s1);
                            memroy.set_new_size_hole(k, hole_size - p_size, memroy.getHoles(k).getBase() + p_size);


                            break;


                        }
                    }
                }
            } else {
                waitings.add(processes.get(i));
            }


        }
        Segments_sort(memroy);
        clear_oldprocess(memroy);
        int base = 0;
        int end = 0;
        for (int i = 0; i < memroy.getNum_segmant(); i++) {
            if (base == memroy.getSegment(i).getBase()) {
                end = base + memroy.getSegment(i).getLimit();
                s = s + " " + memroy.getSegment(i).getName() + " " + end;


                base = end;
                continue;

            }
            Segment old=new Segment();
            old.setColor(colors[17]);
            old.setBase(base);
            old.setLimit(memroy.getSegment(i).getBase()-base);

            old.setName("oldprocess");
            memroy.setOld_process(old);
            base = memroy.getSegment(i).getBase();
            end = base + memroy.getSegment(i).getLimit();

            s = s + " " + "oldprocess" + " " + memroy.getSegment(i).getBase() + " " + memroy.getSegment(i).getName() + " " + end;
            base = end;

        }
        if (end != memroy.getMem_size()) {
            Segment m=new Segment();
            m.setLimit(memroy.getMem_size()-base);
            m.setBase(base);
            m.setName("oldprocess");
            m.setColor(colors[17]);
            memroy.setOld_process(m);
            s = s + " " + "oldprocess" + " " + memroy.getMem_size();

        }
        for(int i=0;i<memroy.getNum_old_proces();i++){
            memroy.setSegments(memroy.getOld_process(i));
        }
        Segments_sort(memroy);
        for(int i=0;i<memroy.getNum_segmant();i++){
            series.getData().add(new XYChart.Data(memroy.getSegment(i).getBase(),"", new GanttChart.ExtraData( memroy.getSegment(i).getLimit()-1,memroy.getSegment(i).getColor())));
        }

        Pair<XYChart.Series,ArrayList<Process>> out=new Pair<>(series,waitings);
        return out;

    }
    public  static void clear_oldprocess(Memroy memroy){
        for(int i=0;i<memroy.getNum_segmant();i++){
            if(memroy.getSegment(i).getName().compareTo("oldprocess")==0){
                memroy.remove_from_segments(i);
                i--;
            }
        }
        for(int i=0;i<memroy.getNum_old_proces();i++){
            memroy.remove_from_oldProcess(i);
            i--;
        }
    }

    public static XYChart.Series dreaw(Memroy memroy) {
        String s = "0";
        XYChart.Series series = new XYChart.Series();
        Segments_sort(memroy);

      clear_oldprocess(memroy);
        int base = 0;
        int end = 0;
        for (int i = 0; i < memroy.getNum_segmant(); i++) {
            if (base == memroy.getSegment(i).getBase()) {
                end = base + memroy.getSegment(i).getLimit();
                s = s + " " + memroy.getSegment(i).getName() + " " + end;


                base = end;
                continue;

            }
            Segment old=new Segment();
            old.setColor(colors[17]);
            old.setBase(base);
            old.setLimit(memroy.getSegment(i).getBase()-base);
            old.setName("oldprocess");
            memroy.setOld_process(old);

            base = memroy.getSegment(i).getBase();
            end = base + memroy.getSegment(i).getLimit();

            s = s + " " + "oldprocess" + " " + memroy.getSegment(i).getBase() + " " + memroy.getSegment(i).getName() + " " + end;
            base = end;

        }
        if (end != memroy.getMem_size()) {
            Segment m=new Segment();
            m.setLimit(memroy.getMem_size()-base);
            m.setBase(base);
            m.setName("oldprocess");
            m.setColor(colors[17]);
            memroy.setOld_process(m);
            s = s + " " + "oldprocess" + " " + memroy.getMem_size();

        }

        for(int i=0;i<memroy.getNum_old_proces();i++){
            memroy.setSegments(memroy.getOld_process(i));
        }
        Segments_sort(memroy);
        for(int i=0;i<memroy.getNum_segmant();i++){
            series.getData().add(new XYChart.Data(memroy.getSegment(i).getBase(),"", new GanttChart.ExtraData( memroy.getSegment(i).getLimit()-1,memroy.getSegment(i).getColor())));
        }

        return series;
    }
    public static XYChart.Series drew_com(Memroy memroy) {
        String s = "0";
        XYChart.Series series = new XYChart.Series();

        Segments_sort(memroy);
        int end2=0;
        for(int i=0;i<memroy.getNum_segmant();i++){
            if(memroy.getSegment(i).getState().compareTo("Hole")==0){
                end2=memroy.getSegment(i).getLimit()+memroy.getSegment(i).getBase();
            }else if(end2!=0){
                memroy.getSegment(i).setBase(end2);
                end2=memroy.getSegment(i).getBase()+memroy.getSegment(i).getLimit();
            }

        }
        clear_oldprocess(memroy);

        int base = 0;
        int end = 0;
        for (int i = 0; i < memroy.getNum_segmant(); i++) {
            if (base == memroy.getSegment(i).getBase()) {
                end = base + memroy.getSegment(i).getLimit();
                s = s + " " + memroy.getSegment(i).getName() + " " + end;


                base = end;
                continue;

            }
            Segment old=new Segment();
            old.setColor(colors[17]);
            old.setBase(base);
            old.setLimit(memroy.getSegment(i).getBase()-base);
            old.setName("oldprocess");
            memroy.setOld_process(old);

            base = memroy.getSegment(i).getBase();
            end = base + memroy.getSegment(i).getLimit();

            s = s + " " + "oldprocess" + " " + memroy.getSegment(i).getBase() + " " + memroy.getSegment(i).getName() + " " + end;
            base = end;

        }
        if (end != memroy.getMem_size()) {
            Segment m=new Segment();
            m.setLimit(memroy.getMem_size()-base);
            m.setBase(base);
            m.setName("oldprocess");
            m.setColor(colors[17]);
            memroy.setOld_process(m);
            s = s + " " + "oldprocess" + " " + memroy.getMem_size();

        }

        for(int i=0;i<memroy.getNum_old_proces();i++){
            memroy.setSegments(memroy.getOld_process(i));
        }
        Segments_sort(memroy);
        for(int i=0;i<memroy.getNum_segmant();i++){
            series.getData().add(new XYChart.Data(memroy.getSegment(i).getBase(),"", new GanttChart.ExtraData( memroy.getSegment(i).getLimit()-1,memroy.getSegment(i).getColor())));
        }

        return series;
    }
    public static void dealocate_seg(int limit, int base, Memroy memroy, int ind) {
        Segment new_hole = new Segment();
        int flag = 0;
        if (ind >= 0) {
            memroy.remove_from_segments(ind);
        }
        new_hole.setName("Hole" + memroy.getNum_holes());
        new_hole.setBase(base);
        new_hole.setLimit(limit);
        new_hole.setState("Hole");
        new_hole.setColor(colors[16]);
        memroy.setHoles(new_hole);

        merge_holes(memroy);
    }

    public static void merge_holes(Memroy memroy) {
        for (int i = 0; i < memroy.getNum_holes(); i++) {
            for (int j = 0; j < memroy.getNum_holes(); j++) {
                if (memroy.getHoles(i).getBase() == memroy.getHoles(j).getBase() && i != j) {
                    memroy.set_new_size_hole(j, memroy.getHoles(i).getLimit() + memroy.getHoles(j).getLimit(), memroy.getHoles(j).getBase());
                    memroy.remove_hole(i);
                    i --;
                    break;
                }
                if (memroy.getHoles(i).getBase() + memroy.getHoles(i).getLimit() == memroy.getHoles(j).getBase() && i != j) {
                    memroy.set_new_size_hole(j, memroy.getHoles(i).getLimit() + memroy.getHoles(j).getLimit(), memroy.getHoles(i).getBase());
                    memroy.remove_hole(i);
                    i --;
                    break;
                }
            }
        }

    }
    public static void holes_sort_by_adress(Memroy memroy){
        int i, j;
        int num_holes = memroy.getNum_holes();
        for (i = 0; i < num_holes; i++) {

            for (j = i + 1; j < num_holes; j++) {
                if (memroy.getHoles(j).getBase() < memroy.getHoles(i).getBase()) {
                    Segment temp = memroy.getHoles(i);
                    memroy.Set_hole_index(memroy.getHoles(j), i);
                    memroy.Set_hole_index(temp, j);
                }
            }

        }
    }
    public static void Holes_sort(Memroy memroy) {
        int i, j;
        int num_holes = memroy.getNum_holes();
        for (i = 0; i < num_holes; i++) {

            for (j = i + 1; j < num_holes; j++) {
                if (memroy.getHoles(j).getLimit() < memroy.getHoles(i).getLimit()) {
                    Segment temp = memroy.getHoles(i);
                    memroy.Set_hole_index(memroy.getHoles(j), i);
                    memroy.Set_hole_index(temp, j);
                }
            }

        }
    }
    public static int is_name_process_exist(String name,ArrayList<Process> processes){
        int index=-1;

        for(int i=0;i<processes.size();i++){
            Process s=processes.get(i);
            if(s.getName().compareTo(name)==0){
                return i;
            }
        }
        return  index;
    }
    public static int is_name_process_exist1(String name, ObservableList<Process> processes){
        int index=-1;

        for(int i=0;i<processes.size();i++){
            Process s=processes.get(i);
            if(s.getName().compareTo(name)==0){
                return i;
            }
        }
        return  index;
    }
    public static void deallocate_process(Memroy memroy, Process p) {

        for (int i = 0; i < p.getNum_Segments(); i++) {
            int index = memroy.is_name_segment_exist(p.getSegment(i));
            if (index >= 0) {
                dealocate_seg(memroy.getSegment(index).getLimit(), memroy.getSegment(index).getBase(), memroy, index);
            }

        }
    }
    public static int is_adress_seg_exist(int address, ObservableList<Segment> segments){
        int index=-1;

        for(int i=0;i<segments.size();i++){
            Segment s=segments.get(i);
            if(s.getBase()==address){
                return i;
            }
        }
        return  index;
    }
    public static int is_name_seg_exist(String nameseg,String namepro,ArrayList<Process> processes){
        int index=-1;
       int index2=Controller.inIndexOf(namepro,processes);
        for(int i=0;i<processes.get(index2).getNum_Segments();i++){
            Segment s=processes.get(index2).getSegment(i);
            if(s.getName().compareTo(nameseg)==0){
                return i;
            }
        }
        return  index;
    }


    public static void Segments_sort(Memroy memroy) {
        int i, j;
        for (int t = 0; t < memroy.getNum_segmant(); t++) {
            if (memroy.getSegment(t).getState().compareTo( "Hole")==0) {
                memroy.remove_from_segments(t);
                t--;
            }

        }
        for (int k = 0; k < memroy.getNum_holes(); k++) {
            if (memroy.getHoles(k).getLimit() == 0) {
                memroy.remove_hole(k);
                k--;
                continue;
            }
        }

        for (int k = 0; k < memroy.getNum_holes(); k++) {
            Segment s = new Segment();
            s.setName(memroy.getHoles(k).getName());
            s.setBase(memroy.getHoles(k).getBase());
            s.setLimit(memroy.getHoles(k).getLimit());
            s.setColor(memroy.getHoles(k).getColor());
            s.setState("Hole");
            memroy.setSegments(s);


        }
        int num_segmant = memroy.getNum_segmant();
        for (i = 0; i < num_segmant; i++) {

            for (j = i + 1; j < num_segmant; j++) {
                if (memroy.getSegment(j).getBase() < memroy.getSegment(i).getBase()) {
                    Segment temp = memroy.getSegment(i);
                    memroy.Set_segment_index(memroy.getSegment(j), i);
                    memroy.Set_segment_index(temp, j);
                }
            }

        }
    }

    public static void External_compactio(Memroy memroy) {
        holes_sort_by_adress(memroy);
        Segment one_hole = new Segment();
        one_hole.setBase(memroy.getHoles(0).getBase());
        int sum = 0;
        for (int i = 0; i < memroy.getNum_holes(); i++) {
            sum = sum + memroy.getHoles(i).getLimit();
            memroy.remove_hole(i);
            i--;
        }
        one_hole.setLimit(sum);
        one_hole.setState("Hole");
        one_hole.setName("Hole" + memroy.getNum_holes());
        one_hole.setColor(colors[16]);
        memroy.setHoles(one_hole);
    }

    /*public static void main(String[] args) {

        ArrayList<Process> processes = new ArrayList<>();
        Memroy memroy = new Memroy();
        memroy.setMem_size(2000);
        //input enter
        Process p = new Process();


        int seg[]={212,416,112,426};
        for(int i=0;i<4;i++){
            Segment s = new Segment();
            s.setName("p1:s"+i);
            s.setLimit(seg[i]);
            p.setSegments(s);
        }



        processes.add(p);
        // memory enter
        int holes_limit[]={100,500,200,300,600};
        int holes_add[]={0,100,600,800,1200};

        for(int i=0;i<5;i++){
            Segment hole = new Segment();
            hole.setName("Hole"+i);
            hole.setState("Hole");
            hole.setBase(holes_add[i]);
            hole.setLimit(holes_limit[i]);
            memroy.setHoles(hole);
        }


        Memroy new_mem=new Memroy();
        String y=new String();
        merge_holes(memroy);
        Pair<Memroy,String>res;
        y= serial(memroy);
        res = firstfit(processes, memroy);
        res= bestfit(processes, memroy);
        deallocate_process(res.getKey(),p);

        External_compactio(memroy);

        res = bestfit(processes, memroy);
        //dealocate_seg(2000-300,300,memroy,-1);
        res= worstfit(processes, memroy);



    }
*/
}
