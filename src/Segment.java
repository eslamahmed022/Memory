public class Segment {
    private String name;
    private int limit;
    private int base;
    private String state="oldprocess";

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
