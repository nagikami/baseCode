package nagi.java.trick;

import java.util.Comparator;
import java.util.TreeSet;

public class ComparatorChain {
    public static void main(String[] args) {
        Comparator<User> userComparator = Comparator.comparingInt(User::getId)
                .thenComparing(User::getName)
                .thenComparing(User::getGroup);
        TreeSet<User> users = new TreeSet<>(userComparator);
        users.add(new User(1, "nagi", "b"));
        users.add(new User(2, "hello", "a"));
        users.add(new User(1,"nagi", "a"));
        users.forEach(System.out::println);
    }

    static class User {
        private int id;
        private String name;
        private String group;

        public User(int id, String name, String group) {
            this.id = id;
            this.name = name;
            this.group = group;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getGroup() {
            return group;
        }

        @Override
        public String toString() {
            return "id: " + id
                    + "\tname: " + name
                    + "\tgroup: " + group;
        }
    }
}
