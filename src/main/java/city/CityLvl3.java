package city;

import java.util.Optional;

public class CityLvl3 extends City {

    private static double reputation = 100;
    private static double capacityNeeded = 100;

    
    public CityLvl3(String name, Optional<Bonus> optionalBonus) {
        super(name, 3, optionalBonus);
    }

    public CityLvl3(String name) {
        super(name, 3);
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
