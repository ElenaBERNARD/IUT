package izly;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Random;

public class CodeSecretUnitTest {

    @Test
    public void testRevelerCode(){
        CodeSecret code = CodeSecret.createCode(new Random());
        Assertions.assertTrue(isCode(code.revelerCode()));
        Assertions.assertEquals("xxxx", code.revelerCode());
    }

    private boolean isCode(String code) {
        if(code.length() != 4) return false;
        try {
            Integer.parseInt(code);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Test
    public void testCodeCreeAleatoirement(){
        Random rand = Mockito.mock(Random.class);
        Mockito.when(rand.nextInt(10)).thenReturn(1, 2, 3, 4);
        CodeSecret code = CodeSecret.createCode(rand);
        Assertions.assertEquals("1234", code.revelerCode());
        Mockito.verify(rand, Mockito.times(4)).nextInt(10);
        Assertions.assertEquals("xxxx", code.revelerCode());
    }

    @Test
    public void testVerifierCodeSurCodeJuste(){
        Random rand = Mockito.mock(Random.class);
        Mockito.when(rand.nextInt(10)).thenReturn(1, 2, 3, 4);
        CodeSecret code = CodeSecret.createCode(rand);
        Assertions.assertTrue(code.verifierCode("1234"));
    }

    @Test
    public void testVerifierCodeSurCodeFaux(){
        Random rand = Mockito.mock(Random.class);
        Mockito.when(rand.nextInt(10)).thenReturn(1, 2, 3, 4);
        CodeSecret code = CodeSecret.createCode(rand);
        Assertions.assertFalse(code.verifierCode("0000"));
    }

//    @Test
//    public void testCodeBloqueApres3essaisFauxSuccessifs(){
//        Random rand = Mockito.mock(Random.class);
//        Mockito.when(rand.nextInt(10)).thenReturn(1, 2, 3, 4);
//        CodeSecret code = CodeSecret.createCode(rand);
//        Assertions.assertFalse(code.isBlocked());
//        code.verifierCode("0000");
//        Assertions.assertFalse(code.isBlocked());
//        code.verifierCode("0000");
//        Assertions.assertFalse(code.isBlocked());
//        code.verifierCode("0000");
//        Assertions.assertTrue(code.isBlocked());
//    }

    @Test
    public void testCodeBloqueApres3essaisFaux(){
        Random rand = Mockito.mock(Random.class);
        Mockito.when(rand.nextInt(10)).thenReturn(1, 2, 3, 4);
        CodeSecret code = CodeSecret.createCode(rand);
        Assertions.assertFalse(code.isBlocked());
        code.verifierCode("0000");
        Assertions.assertFalse(code.isBlocked());
        code.verifierCode("0000");
        Assertions.assertFalse(code.isBlocked());
        code.verifierCode("1234");
        code.verifierCode("1234");
        Assertions.assertFalse(code.isBlocked());
        code.verifierCode("0000");
        Assertions.assertFalse  (code.isBlocked());
        code.verifierCode("0000");
        Assertions.assertFalse  (code.isBlocked());
        code.verifierCode("0000");
        Assertions.assertTrue  (code.isBlocked());
    }
}
