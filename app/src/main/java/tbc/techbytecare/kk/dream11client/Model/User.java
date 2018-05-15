package tbc.techbytecare.kk.dream11client.Model;

public class User {

    private String inviteCode;
    private String mobile;
    private String email;
    private String password;

    public User() {
    }

    public User(String inviteCode, String mobile, String email, String password) {
        this.inviteCode = inviteCode;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
