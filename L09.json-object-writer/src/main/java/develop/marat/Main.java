package develop.marat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static class Inner{
        private int innerClassValue;

        public Inner() {
        }

        public Inner(int value) {
            innerClassValue = value;
        }

        @Override
        public String toString() {
            return "Class " + Inner.class + "\n Fields: \nint innerClassValue = " + innerClassValue;
        }
    }

    public abstract static class BaseTest {
        private char charValue = 's';
        private Boolean booleanValue = true;
        private byte byteValue = 10;

        private Integer integerValue = 20;
        private Double doubleValue = 22.0;

        @Override
        public String toString() {
            return "char charValue = " + charValue + "\n" +
                    "Boolean booleanValue = " + booleanValue + "\n" +
                    "byte byteValue = " + byteValue + "\n" +
                    "Integer integerValue = " + integerValue + "\n" +
                    "Double doubleValue = " + doubleValue + "\n";
        }
    }

    public static class Test extends BaseTest{
        private int notInitIntValue;
        private Long longValue;
        private Inner object = new Inner(1000);
        private List<Integer> list = Arrays.asList(10, 11);
        private double[] doubleArray = {100.0, 200.0};
        private Inner[] inners = new Inner[3];
        @SuppressWarnings("unchecked")
        protected Map<Integer, Integer> map = new HashMap();

        {
            inners[0] = new Inner(2000);
            inners[1] = new Inner(3000);

            map.put(1,1);
            map.put(1001, 2002);
        }

        public void setLongValue(Long longValue) {
            this.longValue = longValue;
        }

        public String toString() {
            return "Class " + Inner.class + "\n Fields: \n" +
                    "int notInitIntValue = " + notInitIntValue + "\n" +
                    "Long longValue = " + longValue + "\n" +

                    "Inner object = " + object + "\n" +
                    "List<Integer> list = " + list + "\n" +
                    "double[] doubleArray = " + Arrays.toString(doubleArray) + "\n" +
                    "Inner[] inners = " + Arrays.toString(inners) + "\n" +
                    "Map<Integer, Integer> map = " + map + "\n" + super.toString();
        }

    }

    public static void main(String[] args){

        Test testObj = new Test();
        testObj.setLongValue(999999L);
        System.out.println(testObj);

        System.out.println("----------------------------");

        MyJson jsonObjectWriter = new MyJson();
        String json = jsonObjectWriter.toJson(testObj);
        System.out.println(json);

        System.out.println("----------------------------");

        Test parsObject = jsonObjectWriter.fromJson(json, Test.class);
        System.out.println(parsObject);
    }
}
