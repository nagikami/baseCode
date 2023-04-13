package nagi.test;

public class str {
    public static void main(String[] args) {
        String a = "abcd/def";
        System.out.println(a.substring(a.lastIndexOf("/") + 1));
        System.out.println(a.substring(0, a.lastIndexOf("/def")));
    }
}
