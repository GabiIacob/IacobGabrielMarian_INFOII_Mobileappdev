public class PUSHNotification extends Notification implements Notifiable{
    @Override
    public void sendNotification() {
        System.out.println("PushNotification: "+getMessage());
    }
}
    