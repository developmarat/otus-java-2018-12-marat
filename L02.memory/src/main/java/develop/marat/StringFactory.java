package develop.marat;

public class StringFactory implements ObjectFactory {
    private char[] chars;

    public StringFactory(){
    }

    public StringFactory(char[] chars){
        this.chars = chars;
    }

    public Object create(){
        if(chars != null){
            return new String(chars);
        }
        else {
            return new String();
        }
    }
}