/*无参数异常*/
package ParameterException;
public class NoParameterException extends Exception {
    public NoParameterException()
    {
        super("please input enough parameter!");
    }


}
