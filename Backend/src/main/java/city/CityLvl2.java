package city;


public class CityLvl2 extends City {

    private static double reputation = 100;
    private static double capacityNeeded = 100;

    
    public CityLvl2(String name, int id, Province province, boolean hasTradeGood) {
        super(name, id, 2, province, hasTradeGood);
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
