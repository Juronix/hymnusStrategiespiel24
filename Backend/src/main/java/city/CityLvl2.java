package city;


public class CityLvl2 extends City {

    private static final double reputation = 100;
    private static final double capacityNeeded = 2.0/3.0;

    
    public CityLvl2(String name, int id, Province province, boolean hasTradeGood) {
        super(name, id, province, hasTradeGood);
    }

    @Override
    public int getCityLevel() {
        return 2;
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
