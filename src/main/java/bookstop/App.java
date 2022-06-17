package bookstop;



public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Present Project Directory : "+ System.getProperty("user.dir"));
        Menu app = new Menu();
         app.execute();
    }
}
