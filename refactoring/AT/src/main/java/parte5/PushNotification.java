package parte5;

public class PushNotification implements NotificationChannel {
    
    @Override
    public void send(String message) {
        System.out.println("Sending PUSH: " + message);
    }
    
    @Override
    public String getChannelName() {
        return "PUSH";
    }
}
