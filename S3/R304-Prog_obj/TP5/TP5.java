import java.util.*;

public class TP5 {
    protected static Random random = new Random(Calendar.getInstance().getTimeInMillis());

    public static void playMode0(Population population, int nbToursJeu) {
        int i, i1, i2, n, taille, bound, maxRegenerate;
        Humain h1, h2;
        List<Integer> listIdx = new ArrayList<Integer>();
        List<Humain> bebes = new ArrayList<Humain>();

        for (int tour = 0; tour < nbToursJeu; tour++) {
            bound = population.taille() / 2;
            n = 0;
            if (bound > 0)
                n = random.nextInt(bound);
            listIdx.clear();
            for (i = 0; i < n; i++) {
                taille = population.taille();
                i1 = random.nextInt(taille);
                maxRegenerate = 0;
                while (listIdx.contains(i1) && maxRegenerate < taille) {
                    i1 = random.nextInt(taille);
                    maxRegenerate++;
                }
                listIdx.add(i1);

                i2 = random.nextInt(taille);
                maxRegenerate = 0;
                while (listIdx.contains(i2) && maxRegenerate < taille) {
                    i2 = random.nextInt(taille);
                    maxRegenerate++;
                }
                listIdx.add(i2);

                h1 = population.getHumain(i1);
                h2 = population.getHumain(i2);
                Humain bebe = null;
                // Check if they are both oposite sexes
                if (h1.isFemme() != h2.isFemme()) {
                    bebe = h1.rencontre(h2);
                    System.out.println(bebe);
                }
                // Kill someone if parents make a baby
                if (bebe != null) {
                    bebes.add(bebe);
                    // Get a random person in both the population and the babies
                    int idMort = random.nextInt(taille + bebes.size());
                    // If id in the population
                    if (idMort < taille) {
                        population.removeHumain(idMort); // Removing the human from the population
                        if (idMort == i1) {
                            listIdx.remove(listIdx.indexOf(i1)); // Removing the id from the list containing it
                            // Shifting the id's by one because one was removed
                            for (i = 0; i < listIdx.size(); i++) {
                                if (listIdx.get(i) > i1) {
                                    int id = listIdx.get(i);
                                    listIdx.remove(i);
                                    listIdx.add(id - 1);
                                }
                            }
                        } else if (idMort == i2) {
                            listIdx.remove(listIdx.indexOf(i2)); // Removing the id from the list containing it
                            // Shifting the id's by one because one was removed
                            for (i = 0; i < listIdx.size(); i++) {
                                if (listIdx.get(i) > i2) {
                                    int id = listIdx.get(i);
                                    listIdx.remove(i);
                                    listIdx.add(id - 1);
                                }
                            }
                        }
                    }
                    // If id is a baby
                    else {
                        idMort -= taille;
                        bebes.remove(idMort);
                    }
                }
            }
            population.vieillir();

            for (Humain bebe : bebes) {
                population.addHumain(bebe);
            }
            bebes.clear();

            // Sorting population
            population.sortAsc();
        }
    }

    public static void playMode1(Population population, int nbToursJeu) {
        List<Humain> bebes = new ArrayList<Humain>();
        List<Homme> hommes = new ArrayList<Homme>();
        List<Femme> femmes = new ArrayList<Femme>();

        int i, n, taille;

        for (int tour = 0; tour < nbToursJeu; tour++) {
            taille = population.taille();
            // Get seperated list of men and women that can reproduce
            for (i = 0; i < taille; i++) {
                if (population.getHumain(i).isFemme() && population.getHumain(i).peutProcreer()) {
                    femmes.add((Femme) population.getHumain(i));
                } else if (population.getHumain(i).isHomme() && population.getHumain(i).peutProcreer()) {
                    hommes.add((Homme) population.getHumain(i));
                }
            }

            // While there is still at least one of each
            while (!femmes.isEmpty() && !hommes.isEmpty()) {
                taille = population.taille();
                Humain bebe;
                Femme f = femmes.remove(0);
                Homme h = hommes.remove(0);

                // Choosing at random between h meeting f or f meeting h
                n = random.nextInt(2);
                if (n == 0) {
                    bebe = h.rencontre(f);
                    if (bebe != null)
                        bebes.add(bebe);
                } else {
                    bebe = f.rencontre(h);
                    if (bebe != null)
                        bebes.add(bebe);
                }
                // Kill someone if parents make a baby
                if (bebe != null) {
                    // Get a random person in both the population and the babies
                    int idMort = random.nextInt(taille + bebes.size());
                    // If id in the population
                    if (idMort < taille) {
                        Humain mort = population.removeHumain(idMort); // Removing the human from the population
                        if (femmes.contains(mort)) {
                            femmes.remove(mort); // Removing the human from the list containing it
                        } else if (hommes.contains(mort)) {
                            hommes.remove(mort); // Removing the human from the list containing it
                        }
                    }
                    // If id is a baby
                    else {
                        idMort -= taille;
                        bebes.remove(idMort);
                    }
                }
            }
            population.vieillir();

            // Adding babies to the population
            for (Humain bebe : bebes) {
                population.addHumain(bebe);
            }
            // Clearing all list for the next generation
            bebes.clear();
            femmes.clear();
            hommes.clear();

            // Sorting population
            population.sortAsc();
        }
    }

    public static void playMode2(Population population, int nbToursJeu, int nbb) {
        List<Humain> bebes = new ArrayList<Humain>();
        List<Homme> hommes = new ArrayList<Homme>();
        List<Femme> femmes = new ArrayList<Femme>();

        int i, n, taille;

        for (int tour = 0; tour < nbToursJeu; tour++) {
            taille = population.taille();
            // Get seperated list of men and women that can reproduce
            for (i = 0; i < taille; i++) {
                if (population.getHumain(i).peutProcreer() && population.getHumain(i).isFemme()) {
                    femmes.add((Femme) population.getHumain(i));
                } else if (population.getHumain(i).peutProcreer() && population.getHumain(i).isHomme()) {
                    hommes.add((Homme) population.getHumain(i));
                }
            }

            // While the number of babies is below the threshold, we try to make new ones
            while (bebes.size() < nbb && !femmes.isEmpty() && !hommes.isEmpty()) {
                taille = population.taille();
                Humain bebe;
                // Choosing the parents at random
                Femme f = femmes.get(random.nextInt(femmes.size()));
                Homme h = hommes.get(random.nextInt(hommes.size()));

                if (!f.peutProcreer()) {
                    femmes.remove(f);
                }
                if (!h.peutProcreer()) {
                    hommes.remove(h);
                }

                // Choosing at random between h meeting f or f meeting h
                n = random.nextInt(2);
                if (n == 0) {
                    bebe = h.rencontre(f);
                    if (bebe != null)
                        bebes.add(bebe);
                } else {
                    bebe = f.rencontre(h);
                    if (bebe != null)
                        bebes.add(bebe);
                }
                // Kill someone if parents make a baby
                if (bebe != null) {
                    // Get a random person in both the population and the babies
                    int idMort = random.nextInt(taille + bebes.size());
                    // If id in the population
                    if (idMort < taille) {
                        Humain mort = population.removeHumain(idMort); // Removing the human from the population
                        if (femmes.contains(mort)) {
                            femmes.remove(mort); // Removing the human from the list containing it
                        } else if (hommes.contains(mort)) {
                            hommes.remove(mort); // Removing the human from the list containing it
                        }
                    }
                    // If id is a baby
                    else {
                        idMort -= taille;
                        bebes.remove(idMort);
                    }
                }
            }
            population.vieillir();

            // Adding babies to the population
            for (Humain bebe : bebes) {
                population.addHumain(bebe);
            }
            // Clearing all list for the next generation
            bebes.clear();
            femmes.clear();
            hommes.clear();

            // Sorting population
            population.sortAsc();
        }
    }

    public static void main(String[] args) {
        int nbToursJeu = Integer.parseInt(args[0]);
        int tailleInit = Integer.parseInt(args[1]);
        int mode = Integer.parseInt(args[2]);

        int i;

        Population population = new Population();

        for (i = 0; i < tailleInit / 2; i++) {
            population.addHumain(new Homme(20, 70, "homme" + i, random.nextInt(30) + 70, random.nextInt(10000) + 1000));
        }
        for (i = 0; i < tailleInit / 2; i++) {
            population.addHumain(new Femme(20, 55, "femme" + i, random.nextInt(99) + 1));
        }

        population.shufflePop();

        // Mode classique
        if (mode == 0) {
            playMode0(population, nbToursJeu);
        }

        // Mode croissance forcee
        else if (mode == 1) {
            playMode1(population, nbToursJeu);
        }

        // Mode croissance regulee
        else if (mode == 2) {
            int nbb = Integer.parseInt(args[3]);
            playMode2(population, nbToursJeu, nbb);
        }

        population.print();
        System.out.println("\nPopulation finale : " + population.taille() + "\n");
    }
}