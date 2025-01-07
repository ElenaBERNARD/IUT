import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ListOfWords {
    private ArrayList<String> words;

    // Cette methode lit le fichier liste_mot_francais.txt et remplit le tableau words
    public ListOfWords() {
        String fileName = "liste_mot_francais.txt";
        try {
            words = new ArrayList<>();
            String line;

            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
            reader.close();

        } catch (IOException e) {
            System.err.println("Error reading file");
        }
    }

    public ArrayList<String> randomSelect(int nbElements) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < nbElements; i++) {
            int index = (int) (Math.random() * words.size());
            list.add(words.get(index));
        }
        return list;
    }

    public ArrayList<String> find(ArrayList<String> l) {
        ArrayList<String> list = new ArrayList<>(l);
        ArrayList<String> found = new ArrayList<>();
        ArrayList<String> toRemove = new ArrayList<>();

        // Parcours de tous les mots
        for (String word : words) {
            toRemove.clear();
            // Parcours de la liste
            for (String s : list) {
                // Si on trouve un mot, on le retire de la liste
                if (s.equals(word)) {
                    toRemove.add(s);
                }
            }
            // Retirer les mots trouves
            for (String s : toRemove) {
                found.add(s + " yes");
                list.remove(s);
            }
        }

        // Les mots non trouves restent dans la liste
        for (String s : list) {
            found.add(s + " no");
        }

        return found;
    }
}