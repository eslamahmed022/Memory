package sample;

public class Segment {
    private String name;
    private int limit;
    private int base;
    private String state="oldprocess";
    private String processName="";
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Segment() {
        name="";
        limit=0;
        base=0;
    }
    public Segment(Segment another ) {
        this.name=another.name;
        this.limit=another.limit;
        this.base=another.base;
        this.color=another.color;
        this.state=another.state;

    }
    public Segment(int address,int size,String name ,String process_name) {
        this.name=name;
        this.limit=size;
        this.base=address;
        processName=process_name;
    }

    public Segment(int address,int size,String name ) {
        this.name=name;
        this.limit=size;
        this.base=address;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }
}
