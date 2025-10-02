import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class HashOfWords {
    Map<Integer, String> words;

    public HashOfWords(int i) {
        switch (i) {
            case 0: {
                words = new HashMap<>();
                break;
            }
            case 1: {
                words = new TreeMap<Integer, String>();
                break;
            }
            case 2: {
                words = new LinkedHashMap<>();
                break;
            }
            default:
                break;
        }
        loadWords();
    }

    public void loadWords(){
        String fileName = "liste_mot_francais.txt";
        try {
            String line;

            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            while ((line = reader.readLine()) != null) {
                words.put(line.hashCode(), line);
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }

    public ArrayList<String> findValuesList(ArrayList<String> values) {
        ArrayList<String> result = new ArrayList<>();
        for (String value : values) {
            if (words.containsValue(value)) {
                result.add(value + " yes");
            }
            else result.add(value + " no");
        }
        return result;
    }

    public ArrayList<String> findValuesToSet(ArrayList<String> values) {
        HashSet<String> valuesSet = new HashSet<>(words.values());
        ArrayList<String> result = new ArrayList<>();
        for (String value : values) {
            if (valuesSet.contains(value)) {
                result.add(value + " yes");
            }
            else result.add(value + " no");
        }
        return result;
    }

    public ArrayList<String> findKeys(ArrayList<String> values) {
        ArrayList<String> result = new ArrayList<>();
        int key;
        for (String value : values) {
            key = value.hashCode();
            if (words.containsKey(key)) {
                result.add(value + " yes");
            }
            else result.add(value + " no");
        }
        return result;
    }
}
