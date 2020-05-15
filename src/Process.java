import java.util.ArrayList;

public class Process {
    private String name;

    public Process(String name, int num_Segments) {
        this.name = name;
        this.num_Segments = num_Segments;
    }
    public Process() {
        this.name = "";
        this.num_Segments = 0;

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum_Segments() {
        return num_Segments;
    }



    public Segment getSegment(int index) {
        return Segments.get(index);
    }

    public void setSegments(Segment segment) {
        Segments.add(segment);
        num_Segments++;
    }

    private int num_Segments;
    private ArrayList<Segment>
            Segments=new ArrayList<>();

}
