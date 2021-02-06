package pl.put.poznan.sk2_project_client.game;

import pl.put.poznan.sk2_project_client.logic.Marker;

import java.util.Collection;
import java.util.Vector;

public class UnitSelector {
    private final Vector<Unit> selectedUnits = new Vector<>();

    public void clear() {
        for (Unit unit : selectedUnits) unit.setSelected(false);
        selectedUnits.clear();
    }

    public boolean isEmpty() {
        return selectedUnits.isEmpty();
    }

    public void select(Marker marker, Collection<Unit> units) {
        for (Unit unit : units) {
            if (marker.checkUnitSelection(unit)) {
                selectedUnits.add(unit);
            }
        }
    }

    public void select(Collection<Unit> units) {
        for (Unit unit : units) {
            unit.setSelected(true);
            selectedUnits.add(unit);
        }
    }

    public void unselect(Unit unit) {
        selectedUnits.remove(unit);
    }

    public Vector<Unit> getSelectedUnits() {
        return selectedUnits;
    }
}
