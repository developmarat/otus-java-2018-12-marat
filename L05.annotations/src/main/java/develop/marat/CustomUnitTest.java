package develop.marat;

import develop.marat.annotations.AfterEach;
import develop.marat.annotations.BeforeEach;
import develop.marat.annotations.Test;
import develop.marat.reflection.ReflectionHelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

public class CustomUnitTest {

    private Class<?> testType;
    private ArrayList<Method> testMethods = new ArrayList<>();
    private ArrayList<Method> beforeEachMethods = new ArrayList<>();
    private ArrayList<Method> afterEachMethods = new ArrayList<>();

    private CustomUnitTest(Class<?> testType){
        this.testType = testType;
        classificationMethods();
    }

    public static void run(Class<?> testType){
        CustomUnitTest test = new CustomUnitTest(testType);
        test.executeTests();
    }

    private void classificationMethods(){
        Method[] methods = ReflectionHelper.getDeclaredMethods(testType);

        for (Method method: methods){
            Annotation[] annotations = ReflectionHelper.getDeclaredAnnotations(method);
            for(Annotation annotation :annotations){
                if(annotation.annotationType().equals(Test.class)){
                    testMethods.add(method);
                }
                else if(annotation.annotationType().equals(BeforeEach.class)){
                    beforeEachMethods.add(method);
                }
                else if(annotation.annotationType().equals(AfterEach.class)){
                    afterEachMethods.add(method);
                }
            }
        }
    }

    private void executeTests(){
        shuffleMethodsLists();

        for (Method testMethod: testMethods){
            Object testObject = ReflectionHelper.instantiate(testType);

            invokeBeforeEachMethods(testObject);
            ReflectionHelper.callMethod(testObject, testMethod);
            invokeAfterEachMethods(testObject);
        }
    }

    private void shuffleMethodsLists(){
        Collections.shuffle(testMethods);
        Collections.shuffle(beforeEachMethods);
        Collections.shuffle(afterEachMethods);
    }

    private void invokeBeforeEachMethods(final Object testObject){
        beforeEachMethods.stream().forEach(m -> ReflectionHelper.callMethod(testObject, m));
    }

    private void invokeAfterEachMethods(final Object testObject){
        afterEachMethods.stream().forEach(m -> ReflectionHelper.callMethod(testObject, m));
    }
}
