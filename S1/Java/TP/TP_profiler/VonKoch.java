/******************************************************************************
 * Compilation: javac VonKoch.java
 * Execution: java VonKoch N [1|3]
 * Dependencies: Turtle.java
 *
 * Dessine un flocon de Von Koch de niveu N.
 * Si Le deuxième argument vaut 3, on trace un flocon complet (3 tiers)
 *
 * % java VoonKoch 5 3
 *
 ******************************************************************************/

class VonKoch {

  static void vonKoch(int n, // niveau de récursion demandé
      float xa, float ya, // coordonnées du point A
      float xb, float yb) // coordonnées du point B
  {
    if (n == 0) {
      StdDraw.line(Math.round(xa), Math.round(ya),
          Math.round(xb), Math.round(yb));
    } else {
      float xc = (2 * xa + xb) / 3;
      float yc = (2 * ya + yb) / 3;
      float xd = (xa + 2 * xb) / 3;
      float yd = (ya + 2 * yb) / 3;
      /*
       * coordonnées de E en calculant la droite perpendiculaire à
       * AB passant par le milieu M
       * Le produit des coefs directeurs est -1 et si on change de repère
       * pour le centrer en M, on a comme eq de la droite perp a AB
       * passant par M : y = -(xb-xa)/(yb-ya)*x
       * Dans ce repère, la distance ME est telle que xe^2 + ye^2 = AB^2/12
       * En remplaçant, dans l'eq ci-dessus, ye par son expression, on obtient : xe^2
       * = (yb-ya)^2/12
       * soit xe = (yb-ya)/2V3 ou xe = -(yb-ya)/2V3
       * Ce sont les points symmétriques par rapport à AB. Le point E étant celui
       * d'angle PI/2, c'est donc xe = -(yb-ya)/2V3
       * Il suffit ensuite de remplacer xe dans l'eq de droite pour avoir ye =
       * (xb-xa)/2V3
       * dans le repère d'origine, il faut ajouter OM, soit finalement
       * xe = xm - (yb-ya)/2V3
       * ye = ym + (xb-xa)/2V3
       */
      float xm = (xa + xb) / 2;
      float ym = (ya + yb) / 2;
      float xe = xm - (yb - ya) / (2 * (float) Math.sqrt(3));
      float ye = ym + (xb - xa) / (2 * (float) Math.sqrt(3));
      /*
       * coordonnées de E en utilisant matrice de rotation
       * float x = xd - xc;
       * float y = yd - yc;
       * float xe = xc + (x - y * (float)Math.sqrt(3)) / 2;
       * float ye = yc + (x * (float)Math.sqrt(3) + y) / 2;
       */
      Profiler.analyse(VonKoch::vonKoch, n-1, xe, ye, xd, yd);
      Profiler.analyse(VonKoch::vonKoch, n - 1, xd, yd, xb, yb);
      Profiler.analyse(VonKoch::vonKoch, n-1, xa, ya, xc, yc);
      Profiler.analyse(VonKoch::vonKoch, n-1, xc, yc, xe, ye);
    }
  }

  public static void main(String[] args) {
    StdDraw.enableDoubleBuffering();

    Profiler.initGlobalTime();

    int rec = Integer.parseInt(args[0]); // niveau de récursion passé en paramètre de la ligne de commande
    int flocon = Integer.parseInt(args[1]);
    final int width = 600;
    final int height = 300;

    float xa, ya, xb, yb;

    StdDraw.setCanvasSize(width, height);
    StdDraw.setXscale(0.0, (double) width);
    StdDraw.setYscale(0.0, (double) height);

    switch (flocon) {
      case 3:
        // coordonnées de départ : même si ce n'est pas nécessaire, on souhaite
        // un triangle équilatéral. La longuer du côté est 'c'.
        float c = (float) Math.min(width - 4,
            (height - 4) * Math.sqrt(3) / 2);
        xa = width / 2f;
        ya = (float) (height - 2 * c / Math.sqrt(3)) / 2f;
        xb = (width - c) / 2f;
        yb = (float) (ya + c * Math.sqrt(3) / 2);
        float xc = xb + c;
        float yc = yb;
        Profiler.analyse(VonKoch::vonKoch,rec, xa, ya, xb, yb);
        Profiler.analyse(VonKoch::vonKoch,rec, xb, yb, xc, yc);
        Profiler.analyse(VonKoch::vonKoch,rec, xc, yc, xa, ya);
        break;
      default:
        xa = 0.0f;
        ya = 10.0f;
        xb = (float) width;
        yb = 10.0f;
        Profiler.analyse(VonKoch::vonKoch,rec, xa, ya, xb, yb);
        break;
    }
    System.out.println(Profiler.getGlobalTime());
    StdDraw.show();
  }
}
