public class Split {

    public static String[] split(String str, String delimiter) {
        // Vérifie si la chaîne ou le délimiteur est vide
        if (str == null || str.isEmpty() || delimiter == null || delimiter.isEmpty()) {
            return new String[]{str};
        }

        // Utilise la méthode split de la classe String
        return str.split(delimiter);
    }

    public static void main(String[] args) {
        String str = "1,2,3,4";
        String delimiter = ",";
        String[] result = split(str, delimiter);

        // Affiche les résultats
        for (String s : result) {
            System.out.println(Integer.parseInt(s));
        }
    }
}
