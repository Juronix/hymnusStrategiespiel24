package city;

import java.io.Serializable;

public class Province implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String name;

    public Province(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
