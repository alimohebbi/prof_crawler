package storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import entities.Professor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ali on 9/22/17.
 */
public class Storage {

    public static void saveProfessors(ArrayList<Professor> professors, String path) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(path+"objects"), professors);
    }

    public static ArrayList<Professor>  loadProfessors(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CollectionType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Professor.class);
        ArrayList<Professor> professors = mapper.readValue(new File(path+"objects"), type);
        return professors;
    }


}
