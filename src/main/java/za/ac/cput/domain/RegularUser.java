package za.ac.cput.domain;

public class RegularUser extends User {
    private String membershipID;

    public RegularUser() {
    }
    public RegularUser(RegularUserBuilder regularUserBuilder) {
        super(new UserBuilder()
                .setUserID(regularUserBuilder.userID)
                .setUserName(regularUserBuilder.userName)
                .setEmail(regularUserBuilder.email)
                .setPassword(regularUserBuilder.password));
        this.membershipID = regularUserBuilder.membershipID;
    }
    public String getMembershipID() {
        return membershipID;
    }
    @Override
    public String toString() {
        return "RegularUser{" +
                "membershipID='" + membershipID + '\'' +
                ", userID=" + getUserID() +
                ", userName='" + getUserName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }
    public static class RegularUserBuilder {
        private Long userID;
        private String userName;
        private String email;
        private String password;
        private String membershipID;

        public RegularUserBuilder() {
        }

        public RegularUserBuilder setUserID(Long userID) {
            this.userID = userID;
            return this;
        }

        public RegularUserBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public RegularUserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public RegularUserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public RegularUserBuilder setMembershipID(String membershipID) {
            this.membershipID = membershipID;
            return this;
        }
    }
}
