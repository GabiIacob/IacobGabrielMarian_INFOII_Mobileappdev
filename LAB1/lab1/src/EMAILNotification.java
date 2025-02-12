public class EMAILNotification extends  Notification implements  Notifiable{
    @Override
    public void sendNotification() {
        System.out.println("Email: "+ getMessage());
    }
}
