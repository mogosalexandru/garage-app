package ro.arfin.garrageapp.Database;

import java.io.Serializable;

public class OperationByEngine implements Serializable{

    private String engineType;
    private String operationName;
    private String operationDuration;

    public OperationByEngine(String engineType, String operationName, String operationDuration) {
        this.engineType = engineType;
        this.operationName = operationName;
        this.operationDuration = operationDuration;
    }

    public String getEngineType() {
        return engineType;
    }

    public String getOperationName() {
        return operationName;
    }

    public String getOperationDuration() {
        return operationDuration;
    }
}
