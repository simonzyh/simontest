public class BCryptDemo {
    public static void main(String[] args) {
        String password = "testpassword";
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(hashed+" "+BCrypt.gensalt());
        String hashed2 = BCrypt.hashpw(password, BCrypt.gensalt(12));

        String candidate = "testpassword";
        if (BCrypt.checkpw(candidate, hashed2))
            System.out.println("It matches");
        else
            System.out.println("It does not match");
    }
}