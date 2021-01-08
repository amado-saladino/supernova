package models;

public class User {
    private String id;
    private String name;
    private int age;
    private String city;

    private User(UserBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.age = builder.age;
        this.city = builder.city;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public static class UserBuilder {
        private String id;
        private String name;
        private int age;
        private String city;

        public UserBuilder(String name, int age) {
            this.name = name;
            this.age = age;
        }
        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }
        public UserBuilder setAge(int age) {
            this.age = age;
            return this;
        }
        public UserBuilder setCity(String city) {
            this.city = city;
            return this;
        }
        public User build() {
            return new User(this);
        }
    }
}
