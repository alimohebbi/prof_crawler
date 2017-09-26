package ir;


import entities.Professor;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ali on 9/22/17.
 */
public class IrSystem {

    EnglishAnalyzer analyzer;
    Directory indexDirectory;
    IndexWriter indexWriter;
    IndexSearcher searcher;

    public IrSystem (String indexDir) throws IOException {


        File indexFolder = new File(indexDir);
        indexDirectory = FSDirectory.open(Paths.get(indexDir));
        analyzer = new EnglishAnalyzer();
    }

    public void createIndexWriter() throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        indexWriter = new IndexWriter(indexDirectory, config);
    }

    public void addProfessorToIndex(Professor professor) throws IOException {
        String interests = professor.getInterests();
        String name = professor.getName();

        Document doc = new Document();
        doc.add(new TextField("interests", interests, Field.Store.YES));
        doc.add(new StringField("name", name, Field.Store.YES));
        indexWriter.addDocument(doc);

    }
    public void commit() throws IOException {
        indexWriter.commit();
    }


    public ScoreDoc[] search (String q) throws IOException, ParseException {
        int hitsPerPage = 15;
        analyzer = new EnglishAnalyzer();
        Query query = new QueryParser("interests", analyzer).parse(q);
        IndexReader reader = DirectoryReader.open(indexDirectory);
        searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(query, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;
        return hits;

    }

    public ArrayList <Professor> getProfessors(ScoreDoc[] hits, HashMap<String, Professor> professors) throws IOException {
        ArrayList <Professor> resultProfessors = new ArrayList<>();
        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            String name = d.get("name");
            Professor p = professors.get(name);
            resultProfessors.add(p);
        }
        return resultProfessors;
    }

    public ArrayList <Professor> getProfessors(ScoreDoc[] hits, ArrayList< Professor> professors) throws IOException {
        HashMap<String , Professor> hashP = new HashMap<>();
        professors.forEach(v-> hashP.put(v.getName(), v));
        return getProfessors(hits, hashP);
    }

    public void close () throws IOException {
        analyzer.close();
        indexDirectory.close();
    }



}
