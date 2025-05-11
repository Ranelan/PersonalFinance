package za.ac.cput.domain;

public class User {
    private Long userID;
    private String userName;
    private String email;
    private String password;

    public User(){
    }

    public User(UserBuilder userBuilder){
        this.userID = userBuilder.userID;
        this.userName = userBuilder.userName;
        this.email = userBuilder.email;
        this.password = userBuilder.password;

    }

    public Long getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" + "userID=" + userID + ", userName=" + userName + ", email=" + email + ", password=" + password + '}';
    }



    public static class UserBuilder{
        private Long userID;
        private String userName;
        private String email;
        private String password;


        public UserBuilder(){
        }

        public UserBuilder setUserID(Long userID) {
            this.userID = userID;
            return this;
        }

        public UserBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }


}