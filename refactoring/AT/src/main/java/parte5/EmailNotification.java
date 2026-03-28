package parte5;

public class EmailNotification implements NotificationChannel {
    
    @Override
    public void send(String message) {
        System.out.println("Sending EMAIL: " + message);
    }
    
    @Override
    public String getChannelName() {
        return "EMAIL";
    }
}
