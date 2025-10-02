public class TestAnimal {
    static void cacophonie(Animal []tab){
        for (Animal animal : tab) {
            animal.crier();
        }
    }

    public static void main(String[] args) {
        Animal[] LA = new Animal[5];
        LA[0] = new Chat();
        LA[1] = new Chien();
        LA[2] = new Serpent();
        LA[3] = new Canard();
        LA[4] = new Lapin();
        
        cacophonie(LA);
    }
}

/*
 * A et A
 * A et A
 * B et A
 * B et A
 * B et B
 * B et A
 */