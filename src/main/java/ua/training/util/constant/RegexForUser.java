package ua.training.util.constant;

public interface RegexForUser {
    String PHONE = "^\\+38\\(0[\\d]{2}\\)[\\d]{7}$";
    String LOGIN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    String PASSWORD = "^[a-zA-Z0-9!@#$%^&*()_+|~\\-=\\/‘\\{\\}\\[\\]:\";’<>?,./]{8,20}$";
    String MONEY = "^\\d+$";
}
