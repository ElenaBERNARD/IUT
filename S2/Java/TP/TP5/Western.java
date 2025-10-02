public class Western {
    public static void main(String[] args) {
        Dame d = new Dame("Madeline", "eau", "rouge");
        Bringand b = new Bringand("Gilderoy", "tord-boyaux", "vilain", 4, 250);
        Cowboy c = new Cowboy("Sherif", "lait", "gentil", 5);

        d.sePresenter();

        b.sePresenter();

        c.sePresenter();

        System.out.println("");

        b.kidnapper(d);

        System.out.println("");
        c.tirer(b);

        System.out.println("");
        c.liberer(d, b);       

        System.out.println("");
        d.changeRobe("verte");
    }
}
