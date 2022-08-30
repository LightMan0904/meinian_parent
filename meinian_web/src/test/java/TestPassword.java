import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author light
 * @create 2022-08-26 20:01
 */
public class TestPassword {
    @Test
    public void test1(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("222");
        System.out.println("encode = " + encode);
    }
}
