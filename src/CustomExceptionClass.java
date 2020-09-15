public class CustomExceptionClass {
    public static void main(String[] args) {
        try{
            int result = divideInt(10,5);
            System.out.format("10 divided by 5 is %d%n", result);   //The %n is a platform-independent newline character.
            result = divideInt(10,0);
            System.out.println("10 divided by 0 is %d%n" +result);
        }catch(DivideByZeroException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int divideInt(int a, int b) throws DivideByZeroException{ // this method has to throw the exception
       if (b == 0) {
          throw new DivideByZeroException("Divisor can not be zero.");
       }
       
        return a/b;
    }


}

// when a class extends Exception, you are defining a "checked" exception
// if extends RuntimeException, you are defining a "unchecked" exception
class DivideByZeroException extends Exception{
    public DivideByZeroException(String message) {
        super(message);
    }
}
