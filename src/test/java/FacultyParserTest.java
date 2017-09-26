

/**
 * Created by ali on 9/22/17.
 */
import crawler.FacultyParser;
import crawler.ProfilePageParser;
import entities.Professor;
import ir.IrSystem;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.junit.Test;
import storage.Storage;

import java.io.IOException;
import java.util.ArrayList;

public class FacultyParserTest {

//   @Test
   /*public void getFacultyTest() throws IOException {
       FacultyParser facultyParser = new FacultyParser("https://cs.uwaterloo.ca/about/people");
       HashMap<String, Professor> professors = facultyParser.getProfessors();
       professors.forEach((k,v) -> System.out.println(v));
   }*/

  @Test
    public void testGetInterests() throws IOException {
        FacultyParser facultyParser = new FacultyParser("https://cs.uwaterloo.ca/about/people");

        ArrayList<Professor> professors = new ArrayList<Professor>(facultyParser.getProfessors().values());


        for (Professor professor: professors) {
            ProfilePageParser profilePageParser = new ProfilePageParser(professor);
            String interests = profilePageParser.getInterests();
            professor.setInterests(interests);
        }


        for (int i = 0 ; i<10; i++) {
            Professor professor = professors.get(i);
            System.out.println(professor);
        }
    }


//    @Test
    public void testIrSystem () throws IOException, ParseException {
        FacultyParser facultyParser = new FacultyParser("https://cs.uwaterloo.ca/about/people");

        ArrayList<Professor> professors = new ArrayList<Professor>(facultyParser.getProfessors().values());


        for (Professor professor: professors) {
            ProfilePageParser profilePageParser = new ProfilePageParser(professor);
            String interests = profilePageParser.getInterests();
            professor.setInterests(interests);
        }

        IrSystem irSystem = new IrSystem("index");
        irSystem.createIndexWriter();

        for (Professor professor: professors) {
            irSystem.addProfessorToIndex(professor);
        }
        irSystem.commit();

        ScoreDoc[] hits = irSystem.search("Software Engineering testing data mining evolution prediction");
        ArrayList<Professor> result = irSystem.getProfessors(hits,facultyParser.getProfessors());

        result.forEach(v ->  System.out.println(v));

    }

    @Test
    public void testSave() throws IOException {
        FacultyParser facultyParser = new FacultyParser("https://cs.uwaterloo.ca/about/people");

        ArrayList<Professor> professors = new ArrayList<Professor>(facultyParser.getProfessors().values());


        for (Professor professor: professors) {
            ProfilePageParser profilePageParser = new ProfilePageParser(professor);
            String interests = profilePageParser.getInterests();
            professor.setInterests(interests);
        }
        Storage storage = new Storage();
        storage.saveProfessors(professors,"");
    }

    @Test
    public void load() throws IOException {

        ArrayList<Professor> professors  = Storage.loadProfessors("");

        professors.forEach(v ->  System.out.println(v));
    }


    @Test
    public void testIndexWriter() throws IOException, ParseException {
        ArrayList<Professor> professors  = Storage.loadProfessors("");

        IrSystem irSystem = new IrSystem("index");



//        irSystem.createIndexWriter();
        for (Professor professor: professors) {
//            irSystem.addProfessorToIndex(professor);

        }
//        irSystem.commit();




        ScoreDoc[] hits = irSystem.search("Software Engineering testing data mining evolution prediction");
        ArrayList<Professor> result = irSystem.getProfessors(hits,professors);
        irSystem.close();
        result.forEach(v ->  System.out.println(v));

    }
}