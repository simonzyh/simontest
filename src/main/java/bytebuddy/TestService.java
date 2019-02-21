package bytebuddy;

public class TestService {

    public void test1(String s) {
        System.out.println(t() + s);
    }

    ;

    public String test2(String s) {
        return "TestService test12" + s;
    }

    private String t() {
        return "TestService test1";
    }


}
