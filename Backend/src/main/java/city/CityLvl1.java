package city;

import java.util.Optional;

public class CityLvl1 extends City {

    private static double reputation = 100;
    private static double capacityNeeded = 100;

    
    public CityLvl1(String name, Province province, boolean hasTradeGood) {
        super(name, 1, province, hasTradeGood);
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
