package group;

import java.util.ArrayList;
import java.util.List;

public class Senate {

    private List<String> nameList = new ArrayList<String>();
    private Family familyOfPolitician1;
    private Family familyOfPolitician2;
    private Family familyOfPolitician3;
    private Family familyOfPolitician4;
    private Family familyOfPolitician5;
    private Family familyOfPolitician6;
    private Family familyOfPolitician7;
    private Family familyOfPolitician8;

    public Senate() {
        nameList.add("Marcus Licinius Crassus");
        nameList.add("Augustus Aerocus");
        nameList.add("Gnaeus Pompeius Magnus");
        nameList.add("Lucius Cornelius Sulla");
        nameList.add("Marcus Agrippa");
        nameList.add("Gaius Maecenas");
        nameList.add("Marecus Aemilius Lepidus");
        nameList.add("Tiberius Gracchus");
    }

    public void setFamilyOfPolitician1(Family familyOfPolitician1) {  // Marcus Licinius Crassus, +20% Hymnen
        if(familyOfPolitician1 != null) {
            familyOfPolitician1.somethingChanged();
        }
        this.familyOfPolitician1 = familyOfPolitician1;
    }

    public void setFamilyOfPolitician2(Family familyOfPolitician2) {  // Augustus Aerocus, +6% famliyReputation at the end
        this.familyOfPolitician2 = familyOfPolitician2;
    }

    public void setFamilyOfPolitician3(Family familyOfPolitician3) {  // Gnaeus Pompeius Magnus, +10% more reputation
        this.familyOfPolitician3 = familyOfPolitician3;
    }

    public void setFamilyOfPolitician4(Family familyOfPolitician4) {  // Lucius Cornelius Sulla, +6% famliyReputation at the end
        this.familyOfPolitician4 = familyOfPolitician4;
    }

    public void setFamilyOfPolitician5(Family familyOfPolitician5) {  // Marcus Agrippa, + 20% more transport capacity
        this.familyOfPolitician5 = familyOfPolitician5;
    }

    public void setFamilyOfPolitician6(Family familyOfPolitician6) {  // Gaius Maecenas, +6% famliyReputation at the end
        this.familyOfPolitician6 = familyOfPolitician6;
    }

    public void setFamilyOfPolitician7(Family familyOfPolitician7) {  // Marecus Aemilius Lepidus, +20% more production in all cities
        this.familyOfPolitician7 = familyOfPolitician7;
    }

    public void setFamilyOfPolitician8(Family familyOfPolitician8) {  // Tiberius Gracchus, +6% famliyReputation at the end
        this.familyOfPolitician8 = familyOfPolitician8;
    }

    public Family getFamilyOfPolitician1() {
        return familyOfPolitician1;
    }

    public Family getFamilyOfPolitician2() {
        return familyOfPolitician2;
    }

    public Family getFamilyOfPolitician3() {
        return familyOfPolitician3;
    }

    public Family getFamilyOfPolitician4() {
        return familyOfPolitician4;
    }

    public Family getFamilyOfPolitician5() {
        return familyOfPolitician5;
    }

    public Family getFamilyOfPolitician6() {
        return familyOfPolitician6;
    }

    public Family getFamilyOfPolitician7() {
        return familyOfPolitician7;
    }

    public Family getFamilyOfPolitician8() {
        return familyOfPolitician8;
    }

    public List<String> getNameList() {
        return nameList;
    }

}
