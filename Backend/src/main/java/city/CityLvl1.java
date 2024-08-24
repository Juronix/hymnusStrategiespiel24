package city;


public class CityLvl1 extends City {

    private static final double reputation = 100;
    private static final double capacityNeeded = 100;

    
    public CityLvl1(String name, int id, Province province, boolean hasTradeGood) {
        super(name, id, province, hasTradeGood);
    }

    @Override
    public int getCityLevel() {
        return 1;
    }

    @Override
    public double getReputation() {
        return reputation;
    }

    @Override
    public double getCapacityNeeded() {
        return capacityNeeded;
    }

}
