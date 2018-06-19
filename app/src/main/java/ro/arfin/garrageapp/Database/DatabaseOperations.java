package ro.arfin.garrageapp.Database;

import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import ro.arfin.garrageapp.R;

public class DatabaseOperations implements Serializable {
    private DatabaseReference m_database;
    private ArrayList<CarModelObject> m_modelList = new ArrayList<>();
    private ArrayList<OperationByEngine> m_modelOperations = new ArrayList<>();

    public DatabaseOperations() {
        m_database = FirebaseDatabase.getInstance().getReference();
    }

    public ArrayList<CarModelObject> getModelList() {
        return m_modelList;
    }

    public ArrayList<OperationByEngine> getModelOperations() {
        return m_modelOperations;
    }

    public void setModelList(ArrayList<CarModelObject> modelList) {
        this.m_modelList = modelList;
    }

    public void getDatabaseUpdate() {
        DatabaseReference ref = m_database.child("operatiuni");

        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (Map.Entry<String, Object> modelEntry : ((Map<String, Object>) dataSnapshot.getValue()).entrySet()) {

                            String modelName = modelEntry.getKey();
                            Map modelContent = (Map) modelEntry.getValue();

                            for (Map.Entry<String, Object> engineEntry : ((Map<String, Object>) modelContent).entrySet()) {
                                String engineName = engineEntry.getKey();
                                Map operationsContent = (Map) engineEntry.getValue();

                                for (Map.Entry<String, String> operationsEntry : ((Map<String, String>) operationsContent).entrySet()) {
                                    String operationName = operationsEntry.getKey();
                                    String operationTime = operationsEntry.getValue();
                                    m_modelOperations.add(new OperationByEngine(engineName, operationName, operationTime));
                                }
                            }
                            m_modelList.add(new CarModelObject(modelName, m_modelOperations));

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    public void writeNewModel(String model, String motorizare) {
        DatabaseReference ref = m_database.child("operatiuni");

        System.out.println("motorizare: " + motorizare);

        if (motorizare.equals("Benzina & Diesel")) {
            ref.child(model).setValue("Benzina");
            ref.child(model).setValue("Diesel");
            return;
        }

        ref.child(model).child(motorizare).child("operatiune_nume").setValue("test");
    }
}
