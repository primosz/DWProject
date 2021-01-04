import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;

public class Main {
    public static void main(String x[]) throws IOException {

        String review = "C:\\piotr\\Studia\\Magisterka\\Data Warehouses\\Project\\databases\\yelp\\yelp_academic_dataset_review.json";
        String checkin = "C:\\piotr\\Studia\\Magisterka\\Data Warehouses\\Project\\databases\\yelp\\yelp_academic_dataset_checkin.json";
        String user = "C:\\piotr\\Studia\\Magisterka\\Data Warehouses\\Project\\databases\\yelp\\yelp_academic_dataset_user.json";
        String business = "C:\\piotr\\Studia\\Magisterka\\Data Warehouses\\Project\\databases\\yelp\\yelp_academic_dataset_business.json";


//        ReplaceInFile replaceInFile = new ReplaceInFile("}", "},");
//        replaceInFile.matchAndReplace(new File(business), new File("Business.json"));


        readReview(new File(review), "Review.json");

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

}
