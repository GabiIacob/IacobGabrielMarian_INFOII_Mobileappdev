public abstract class Notification {
    private String message="Salutare";

    public String getMessage(){
        return message;
    }
    public void setMessage(String text){
        this.message=text;
    }
    public void displayNotification(){
        System.out.println("Notification:" +getMessage());
    }
}

