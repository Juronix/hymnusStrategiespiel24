package city;

import java.util.Optional;

public class CityLvl1 extends City {

    private static double reputation = 100;
    private static double capacityNeeded = 100;

    
    public CityLvl1(String name, Optional<Bonus> optionalBonus) {
        super(name, 1, optionalBonus);
    }

    public CityLvl1(String name) {
        super(name, 1);
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
