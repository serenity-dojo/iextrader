package iextrader.model;

public class Client {
    String id;
    String email;
    String firstName;
    String lastName;

    public Client(String id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public static final class Builder {
        String id;
        String email;
        String firstName;
        String lastName;

        private Builder() {
        }

        public static Builder aClient() {
            return new Builder();
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Client build() {
            return new Client(id, email, firstName, lastName);
        }
    }
}
