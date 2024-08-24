package city;

public class Rome extends City {

    public Rome(String name, int id, Province province) {
        super(name, id, province, false);
    }

    @Override
    public int getCityLevel() {
        return 0;
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
