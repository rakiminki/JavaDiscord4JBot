package model;

import java.util.Objects;

public class ReturnResult {
    private boolean isPositive;
    private String message;
    private Object result;

    public ReturnResult(boolean isPositive, String message) {
        this.isPositive = isPositive;
        this.message = message;
    }

    public static ReturnResult good(){
        return new ReturnResult(true,null);
    }
    public static ReturnResult good(Object object){
        ReturnResult result = new ReturnResult(true,null);
        result.setResult(object);
        return result;
    }
    public static ReturnResult bad(String message){
        return new ReturnResult(false,message);
    }
    public boolean isGood() {
        return isPositive;
    }

    public boolean isBad(){
        return !isGood();
    }

    public String getMessage() {
        return message;
    }

    public Object getResult() {
        return result;
    }

    private void setResult(Object result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReturnResult that = (ReturnResult) o;
        return isGood() == that.isGood() && Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isGood(), getMessage());
    }

    @Override
    public String toString() {
        return "ReturnResult{" +
                "isPositive=" + isPositive +
                ", message='" + message + '\'' +
                '}';
    }
}
