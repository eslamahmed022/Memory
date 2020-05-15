package sample;

import java.util.ArrayList;

public class Memroy {
    private int mem_size;
    private ArrayList<Segment>holes=new ArrayList<>();
    private  int num_holes;
    private ArrayList<Segment>segments=new ArrayList<>();
    private ArrayList<Segment>old_process=new ArrayList<>();
    private int num_old_proces=0;

    public int getNum_old_proces() {
        return num_old_proces;
    }

    public void setNum_old_proces(int num_old_proces) {
        this.num_old_proces = num_old_proces;
    }

    private int num_segmant=0;
    public void setSegments(Segment S){
        segments.add(S);
        num_segmant++;
    }
    public void setOld_process(Segment S){
        old_process.add(S);
        num_old_proces++;
    }
    public Segment getOld_process(int index){

       return old_process.get(index);

    }

    public int getNum_segmant(){
        return num_segmant;
    }
    public Segment getSegment(int index){
        return segments.get(index);
    }
    public Memroy(Memroy another) {
        this.mem_size = another.mem_size;
        this.num_holes =another.num_holes;


        for(int i=0;i<another.holes.size();i++){
            holes.add(new Segment(another.getHoles(i)));
        }
    }
    public Memroy() {
        this.mem_size = 0;
        this.num_holes = 0;
    }

    public int getMem_size() {
        return mem_size;
    }

    public void setMem_size(int mem_size) {
        this.mem_size = mem_size;
    }

    public Segment getHoles(int index) {
        return holes.get(index);
    }

    public void setHoles(Segment hole) {
        this.holes.add(hole);
        num_holes++;
    }
    public void Set_hole_index(Segment hole,int index) {
        this.holes.set(index,hole);

    }
    public void Set_segment_index(Segment s,int index) {
        this.segments.set(index,s);

    }

    public int getNum_holes() {
        return num_holes;
    }


    public void set_new_size_hole(int index,int size,int base) {
      holes.get(index).setBase(base);
        holes.get(index).setLimit(size);

    }
    public int is_hole_exist(Segment hole){
        int index=-1;

        for(int i=0;i<segments.size();i++){
            Segment s=segments.get(i);
            if(s.getLimit()==hole.getLimit()&&s.getName().compareTo(hole.getName())==0&&s.getBase()==hole.getBase()){
                return i;
            }
        }
        return  index;
    }
    public int is_name_segment_exist(Segment hole){
        int index=-1;

        for(int i=0;i<segments.size();i++){
            Segment s=segments.get(i);
            if(s.getLimit()==hole.getLimit()&&s.getName().compareTo(hole.getName())==0&&s.getProcessName().compareTo(hole.getProcessName())==0){
                return i;
            }
        }
        return  index;
    }


    public int is_base_hole_exist(int base){
        int index=-1;

        for(int i=0;i<holes.size();i++){
            Segment s=holes.get(i);
            if(s.getBase()==base){
                return i;
            }
        }
        return  index;
    }
    public int is_base_segment_exist(int base){
        int index=-1;

        for(int i=0;i<segments.size();i++){
            Segment s=segments.get(i);
            if(s.getBase()==base){
                return i;
            }
        }
        return  index;
    }
    public void remove_from_segments(int index){
        segments.remove(index);
        num_segmant--;
    }
    public void remove_from_oldProcess(int index){
        old_process.remove(index);
        num_old_proces--;
    }
    public void remove_hole(int index){
        holes.remove(index);
        num_holes--;
        for(int i=0;i<holes.size();i++){
            holes.get(i).setName("Hole"+i);
        }
    }

}
