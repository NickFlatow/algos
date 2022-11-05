import java.util.*;



public class HelloWorld {
    public static void main(String[] args) {  

        System.out.println("Please enter input");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        System.out.println("Hello, World" + name);
    }
}