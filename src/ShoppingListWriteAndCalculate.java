import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingListWriteAndCalculate {

    public static void main(String[] args) {

        try(ObjectOutputStream writer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("shopping.dat")))){

            List<Shopping> items = new ArrayList<>();
            items.add(new Shopping(new BigDecimal("20.00"), 3, "t-shirt"));
            items.add(new Shopping(new BigDecimal("4.00"), 5, "apple"));
            items.add(new Shopping(new BigDecimal("1.00"), 3, "notebook"));
            items.add(new Shopping(new BigDecimal("200.00"), 1, "chair"));

            for(Shopping s: items) {
                writer.writeObject(s);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // print out the shopping list
        List<Shopping> displayitems = new ArrayList<>();
        try(ObjectInputStream reader = new ObjectInputStream(new BufferedInputStream(new FileInputStream("shopping.dat")))){

            while(true) {
                Shopping myItem = (Shopping)reader.readObject();
                displayitems.add(myItem);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        double total =0;
        for(Shopping s: displayitems) {
           double price = s.getPrice().doubleValue();
           int unit = s.getUnit();
           total += price * unit;
        }
        System.out.println("total cost is " + total);
    }
}
