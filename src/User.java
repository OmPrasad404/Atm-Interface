public class User {
    private String userId;
    private String pin;
    private String name;

    public User(String userId, String pin, String name) {
        this.userId = userId;
        this.pin = pin;
        this.name = name;
    }

    public boolean authenticate(String inputUserId, String inputPin) {
        return this.userId.equals(inputUserId) && this.pin.equals(inputPin);
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}

