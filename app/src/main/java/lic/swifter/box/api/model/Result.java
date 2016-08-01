package lic.swifter.box.api.model;

/**
 * Created by lic on 16-8-1.
 */
public class Result<T> {
    public int resultcode;
    public String reason;
    public T result;

    @Override
    public String toString() {
        return "Result{" +
                "resultcode=" + resultcode +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }
}
