/*单独使用-o参数异常*/
package ParameterException;
public class NoFileNameException extends Exception {
    public NoFileNameException()
    {
        super("[-o/-e] must be followed by file name!");
    }
}
