package city;

public class Rome extends City {

    public Rome(String name, Province province) {
        super(name, province, 0);
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
