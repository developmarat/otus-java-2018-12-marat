package develop.marat;

import develop.marat.annotations.AfterEach;
import develop.marat.annotations.BeforeEach;
import develop.marat.annotations.Test;

public class TestClass {
    public TestClass(){

    }

    @BeforeEach
    public void beforeEach1(){
        System.out.println("BeforeEach 1");
    }
    @BeforeEach
    public void beforeEach2(){
        System.out.println("BeforeEach 2");
    }

    @Test
    public void test1(){
        System.out.println("Test 1");
    }

    @Test
    public void test2(){
        System.out.println("Test 2");
    }

    @Test
    public void test3(){
        System.out.println("Test 3");
    }

    @AfterEach
    public void afterEach1(){
        System.out.println("AfterEach 1");
    }
    @AfterEach
    public void afterEach2(){
        System.out.println("AfterEach 2");
    }

}
