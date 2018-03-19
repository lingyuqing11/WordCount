/*参数重复异常*/
package ParameterException;
public class ParameterDuplicateException extends Exception {
    public ParameterDuplicateException()
    {
        super("you can't use duplicate parameter!");
    }
}
