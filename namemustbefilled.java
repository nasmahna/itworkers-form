public class N196_NamaMustBeFilledException extends Exception {
     @Override
    public String getMessage() {
        return " 'Nama' must be filled with valid data!";
    }
}
