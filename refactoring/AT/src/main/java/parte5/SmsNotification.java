package parte5;

public class SmsNotification implements NotificationChannel {
    
    @Override
    public void send(String message) {
        System.out.println("Sending SMS: " + message);
    }
    
    @Override
    public String getChannelName() {
        return "SMS";
    }
}
