package ro.arfin.garrageapp.Database;

import java.io.Serializable;
import java.util.ArrayList;

public class CarModelObject implements Serializable{

    private String modelName;
    private ArrayList<OperationByEngine> availableOperations = new ArrayList<>();

    public CarModelObject(String modelName, ArrayList<OperationByEngine> availableOperations) {
        this.modelName = modelName;
        populateOperationsVector(availableOperations);
    }

    public String getModelName() {
        return modelName;
    }

    public ArrayList<OperationByEngine> getAvailableOperations() {
        return availableOperations;
    }

    private void populateOperationsVector(ArrayList<OperationByEngine> availableOperations)
    {
        for (OperationByEngine value : availableOperations)
        {
            this.availableOperations.add(value);
        }
    }

}
