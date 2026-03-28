package parte5;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class NotificationService {
    
    private static final Map<String, Supplier<NotificationChannel>> channelFactory = new HashMap<>();
    
    static {
        channelFactory.put("EMAIL", EmailNotification::new);
        channelFactory.put("SMS", SmsNotification::new);
        channelFactory.put("PUSH", PushNotification::new);
    }
    
    public void notifyUser(String channel, String message) {
        NotificationChannel notificationChannel = getChannel(channel);
        if (notificationChannel != null) {
            notificationChannel.send(message);
        } else {
            System.out.println("Canal desconhecido: " + channel);
        }
    }
    
    public NotificationChannel getChannel(String channel) {
        Supplier<NotificationChannel> supplier = channelFactory.get(channel.toUpperCase());
        if (supplier != null) {
            return supplier.get();
        }
        return null;
    }
    
    public void registerChannel(String channelName, Supplier<NotificationChannel> channelSupplier) {
        channelFactory.put(channelName.toUpperCase(), channelSupplier);
    }
    
    public static void main(String[] args) {
        NotificationService service = new NotificationService();
        
        System.out.println("=== Sistema de Notificações Refatorado ===\n");
        
        System.out.println("--- Enviando por EMAIL ---");
        service.notifyUser("EMAIL", "Olá, este é um teste!");
        
        System.out.println("\n--- Enviando por SMS ---");
        service.notifyUser("SMS", "Seu código é 123456");
        
        System.out.println("\n--- Enviando por PUSH ---");
        service.notifyUser("PUSH", "Você tem uma nova mensagem");
        
        System.out.println("\n--- Canal desconhecido ---");
        service.notifyUser("WHATSAPP", "Teste");
        
        System.out.println("\n--- Adicionando novo canal (WhatsApp) ---");
        service.registerChannel("WHATSAPP", () -> new NotificationChannel() {
            @Override
            public void send(String message) {
                System.out.println("Enviando WHATSAPP: " + message);
            }
            
            @Override
            public String getChannelName() {
                return "WHATSAPP";
            }
        });
        
        service.notifyUser("WHATSAPP", "Nova funcionalidade!");
    }
}
