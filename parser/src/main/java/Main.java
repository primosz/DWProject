import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String args[]) throws IOException {

        String review = "C:\\piotr\\Studia\\Magisterka\\Data Warehouses\\Project\\databases\\yelp\\yelp_academic_dataset_review.json";
        String checkin = "C:\\piotr\\Studia\\Magisterka\\Data Warehouses\\Project\\databases\\yelp\\yelp_academic_dataset_checkin.json";
        String user = "C:\\piotr\\Studia\\Magisterka\\Data Warehouses\\Project\\databases\\yelp\\yelp_academic_dataset_user.json";
        String business = "C:\\piotr\\Studia\\Magisterka\\Data Warehouses\\Project\\databases\\yelp\\yelp_academic_dataset_business.json";

//        analyse(new File(review), "Sentiment.json");

//        Properties props = new Properties();
//        props.setProperty("annotators", "tokenize, ssplit, pos, parse, sentiment");
//        StanfordCoreNLP stanfordCoreNLP = new StanfordCoreNLP(props);
//        String text = "I will burn this place, because I do not like you.";
//        CoreDocument coreDocument = new CoreDocument(text);
//        stanfordCoreNLP.annotate(coreDocument);
//        List<CoreSentence> sentences = coreDocument.sentences();
//        for (CoreSentence sentence : sentences) {
//            String sentiment = sentence.sentiment();
//            System.out.println(sentiment + "\t" + sentence);
//        }

//        Properties props = new Properties();
//        // set the list of annotators to run
//        props.setProperty("annotators", "tokenize, ssplit, pos, parse");
//        // build pipeline
//        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
//        // create a document object
//        CoreDocument document = pipeline.processToCoreDocument("I love you");

        ReplaceInFile replaceInFile = new ReplaceInFile("}", "},");
        replaceInFile.matchAndReplace(new File(user), new File("User.json"));


//        readReview(new File(review), "Review.json");

//        read(new File(user), "User.json");
//
//        FileWriter writer2 = new FileWriter("Checkin.json");
//        writer2.close();
//
//        FileWriter writer3 = new FileWriter("User.json");
//        writer3.close();
//
//
//        FileWriter writer4 = new FileWriter("Business.json");
//
//        writer4.close();
    }

    public static void readReview(File MyFile, String outFile) throws FileNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();

        try (FileReader reader = new FileReader(MyFile);
             BufferedReader bufferedReader = new BufferedReader(reader);
        ) {
            String currentLine;
            int i = 0;
            while ((currentLine = bufferedReader.readLine()) != null && i < 6000000) {
                JsonNode logStorageNode = mapper.readTree(currentLine);
                ObjectNode object = (ObjectNode) logStorageNode;
                object.remove("text");
                arrayNode.add(logStorageNode);
                if (i % 10000 == 0)
                    System.out.println(MyFile.getName() + " " + i);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(new File(outFile), arrayNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(File MyFile, String outFile) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();

        try (FileReader reader = new FileReader(MyFile);
             BufferedReader bufferedReader = new BufferedReader(reader);
        ) {
            String currentLine;
            int i = 0;
            while ((currentLine = bufferedReader.readLine()) != null) {
                JsonNode logStorageNode = mapper.readTree(currentLine);
                arrayNode.add(logStorageNode);
                if (i % 10000 == 0)
                    System.out.println(MyFile.getName() + " " + i);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(new File(outFile), arrayNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int analyse(File MyFile, String outFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        try (FileReader reader = new FileReader(MyFile);
             BufferedReader bufferedReader = new BufferedReader(reader);
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(outFile)));
        ) {
            String currentLine;
            int i = 0;
            bufferedWriter.write("[");
            while ((currentLine = bufferedReader.readLine()) != null) {
                JsonNode logStorageNode = objectMapper.readTree(currentLine);
                ObjectNode object = (ObjectNode) logStorageNode;
                String text = object.get("text").textValue();
                Annotation annotation = pipeline.process(text);
                ObjectNode childNode1 = objectMapper.createObjectNode();
                childNode1.put("review_id", object.get("review_id").textValue());
//                childNode1.put("stars", object.get("stars").doubleValue());
                childNode1.put("text", getSentiment(annotation).toString());
                bufferedWriter.write(childNode1.toString() + ",");
                bufferedWriter.newLine();

                if (i % 10000 == 0)
                    System.out.println(MyFile.getName() + " " + i);
                i++;

            }
            bufferedWriter.write("]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static SentimentType getSentiment(Annotation annotation) {
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            return SentimentType.fromValue(RNNCoreAnnotations.getPredictedClass(tree));
        }
        return SentimentType.NEUTRAL;
    }


}

