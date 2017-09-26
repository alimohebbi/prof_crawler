package gui;


import entities.Professor;
import ir.IrSystem;
import storage.Storage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author ali
 *this class start operations and initial instance of other classes
 */
public class Initial {


    public String doc_path;
    public IrSystem irSystem;
    public ArrayList<Professor> professors ;

    public Initial(String doc_path, String index_path, boolean readMode) throws IOException {

        irSystem = new IrSystem(index_path);
        professors = Storage.loadProfessors(doc_path);

        if (!readMode){
            irSystem.createIndexWriter();

            professors.forEach(v -> {
                try {
                    irSystem.addProfessorToIndex(v);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            irSystem.commit();
        }



    }
}
