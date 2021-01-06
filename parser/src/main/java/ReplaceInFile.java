import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.util.regex.Pattern;

public class ReplaceInFile {

    private final Pattern pattern;
    private final String replaceMent;

    public ReplaceInFile(String pattern, String replaceMent) {
        this.pattern = Pattern.compile(pattern);
        this.replaceMent = replaceMent;
    }

    public ReplaceInFile(Pattern pattern, String replaceMent) {
        this.pattern = pattern;
        this.replaceMent = replaceMent;
    }

    public String matchAndReplace(String line) {
        return pattern.matcher(line).replaceAll(replaceMent);
    }

    public void matchAndReplace(File inFile, File outFile) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inFile));
        if (!outFile.exists()) {
            outFile.createNewFile();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String line;
            bufferedWriter.write("[");
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                JsonNode logStorageNode = objectMapper.readTree(line);
                ObjectNode object = (ObjectNode) logStorageNode;
                object.remove("text");
                if (i % 10000 == 0)
                    System.out.println(i);
                i++;
                bufferedWriter.write(matchAndReplace(object.toString()));
                bufferedWriter.newLine();
            }
            bufferedWriter.write("]");
        } finally {
            bufferedReader.close();
            bufferedWriter.flush();
            bufferedWriter.close();
        }
    }

    public void execute(File inFile, File outFile) {

    }
}
