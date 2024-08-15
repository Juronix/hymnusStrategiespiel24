package city;

import java.util.Optional;

public class CityLvl2 extends City {

    private static double reputation = 100;
    private static double capacityNeeded = 100;

    
    public CityLvl2(String name, Optional<Bonus> optionalBonus) {
        super(name, 2, optionalBonus);
    }

    public CityLvl2(String name) {
        super(name, 2);
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
