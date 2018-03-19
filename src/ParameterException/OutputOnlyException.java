/*单独使用-o参数异常*/
package ParameterException;
public class OutputOnlyException extends Exception {
    public OutputOnlyException()
    {
        super("you can't use [-o] only!");
    }
}
