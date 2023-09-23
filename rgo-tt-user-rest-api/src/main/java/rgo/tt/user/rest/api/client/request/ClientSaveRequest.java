package rgo.tt.user.rest.api.client.request;

import static org.apache.commons.lang3.StringUtils.strip;

public class ClientSaveRequest {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = strip(email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = strip(password);
    }

    @Override
    public String toString() {
        return "ClientSaveRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
