package validation;

public class ProductValidator {
    private static final String ID_REGEX = "^PRD\\d{3}$";

    // Regex tên: chữ, số, khoảng trắng
    private static final String NAME_REGEX = "^[\\p{L}0-9 _-]{2,50}$";

    public  static boolean isValidId(String Id) {
        return Id != null && Id.matches(ID_REGEX);
    }

    public static boolean isValidName(String name) {
        return name != null && name.matches(NAME_REGEX);
    }

    public static boolean isValidPrice(double price) {
        return price >= 0;
    }

    public static boolean isValidQuantity(double quantity) {
        return quantity >= 0;
    }
}
