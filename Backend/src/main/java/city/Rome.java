package city;

public class Rome extends City {

    public Rome(String name, int id, Province province) {
        super(name, id, province, 0);
    }

    @Override
    public double getReputation() {
        return 0;
    }

    @Override
    public double getCapacityNeeded() {
        return 0;
    }

}
