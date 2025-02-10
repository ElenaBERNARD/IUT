import java.util.Random;

public class
CodeSecret {
    public boolean codeRevele;
    private String code = "0000";
    private final int nbEssaiMax = 3;
    private int nbEssaisFaux;

    public boolean verifierCode(String code) {
        if(this.code.equals(code)){
            nbEssaisFaux = 0;
            return true;
        }
        nbEssaisFaux++;
        return false;
    }

    public boolean isBlocked() {
        return nbEssaisFaux >= nbEssaiMax;
    }

    public static CodeSecret createCode(Random random) {
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            codeBuilder.append(random.nextInt(10));
        }
        String code = codeBuilder.toString();
        CodeSecret codeSecret = new CodeSecret(code);
        return codeSecret;
    }

    public CodeSecret(String code) {
        this.nbEssaisFaux = 0;
        this.code = code;
    }

    public String revelerCode() {
        if(!codeRevele){
            codeRevele = true;
            return code;
        }
        return "xxxx";
    }
}
