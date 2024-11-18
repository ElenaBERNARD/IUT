package izly;

public class Purse {
    private double solde;
    private double plafond;
    private  int nbOpMax;
    private  CodeSecret codeSecret;
    private int nbOperationDejaExecuteesDepuisLaCreationDuPurse;

    public Purse(double  plafond, int nbOpMax, CodeSecret codeSecret) {
        this.plafond = plafond;
        this.nbOpMax = nbOpMax;
        this.codeSecret = codeSecret;
    }

    public double getSolde() {
        return solde;
    }

    public void credit(double montant) throws MotifRejetTransactionException {
        controlePreOp(montant);
        if (solde+montant > plafond) throw new DepassementPlafondInterdit();
        solde += montant;
        nbOperationDejaExecuteesDepuisLaCreationDuPurse++;
    }

    private void controlePreOp(double montant) throws MotifRejetTransactionException {
        if(codeSecret.isBlocked()) throw new CodeBloqueException();
        if (nbOperationDejaExecuteesDepuisLaCreationDuPurse >= nbOpMax) throw new FinDeVieException();
        if (montant < 0) throw new MontantNegatifInterdit();
    }

    public void debit(double montant, String code) throws MotifRejetTransactionException {
        controlePreOp(montant);
        if(codeSecret.isBlocked()) throw new CodeFauxException();
        if (!codeSecret.verifierCode(code)) throw new CodeFauxException();
        if (solde < montant) throw new SoldeNegatifInterdit();
        solde -= montant;
        nbOperationDejaExecuteesDepuisLaCreationDuPurse++;
    }
}
