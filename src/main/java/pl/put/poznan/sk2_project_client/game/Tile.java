package pl.put.poznan.sk2_project_client.game;

public class Tile {
    private Unit unit;

    public Tile(Unit unit) {
        this.unit = unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Unit getUnit() {
        return unit;
    }
}
