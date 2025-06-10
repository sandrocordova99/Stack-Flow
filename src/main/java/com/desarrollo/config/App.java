package com.desarrollo.config;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.*;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) {

        /**Consumer**/

        //consumer-> recibe un valor y no retorna nada
        Consumer<String> consumer = (param)->{
            System.out.println("param = " + param);
        };
        //forma reducida
        Consumer<String> consumer1 = System.out::println;
        //ejercuar un consumer
        consumer.accept("Harol");
    
         
        
        /**Biconsumer***/

        //Recibe dos valores y no retornda nada
        BiConsumer<String,String> biConsumer = (a,b)->{
            System.out.println("p_a + p_b = " + a + b);
        };
        //llamar a la funcion
        biConsumer.accept("Harol","Ribyn");

        /**Supplier**/

        //no recibe valor, pero retorna un resultado(el valor entre "<>" es el valor que se retorna)
        Supplier<String> stringSupplier = ()->{
            return "Hola, soy un supplier";
        };
        
        String supplier = stringSupplier.get();

        System.out.println("supplier = " + supplier);

        /**Function**/
        //recibe un valor y retorna un valor
        Function<Integer,String> function = (num)->{
            return "El numero es " + num;
        };

        //llamar a la function
        String resultado = function.apply(250);

        /**BiFunction**/
        //recibe dos valores y retorna un resultado
        BiFunction<Integer,Integer,Integer> biFunction = (a,b)->{
            return a + b;
        };

        int resultado2 =  biFunction.apply(10,20);

        /**Predicate**/
        //recibe un valor y retorna un boleano

        Predicate<String> predicate = (str)->{
            return str.length()<5;
        };
        //ejecutar la funcion
       Boolean resultado3 =  predicate.test("Hola Mundo");


       /***BiPredicate**/
        //recibe dos valores y retorna un boleano
       BiPredicate<Integer, Integer> biPredicate = (a,b)->{
            return a>b;
       };
        //llamar a la funcion

        boolean resultado4 = biPredicate.test(5,10);

        /***BinaryOperator***/
        //recibe dos valores del mismo tipo y retorna el valor del mismo tipo

        BinaryOperator<Integer>binaryOperator =(a,b)->{
            return a+b;
        };

        Integer suma = binaryOperator.apply(10,50);



        /**Unary***/
        //recibe un solo valor, lo procesa y retorna un valor del mismo tipo

        UnaryOperator<Integer> unaryOperator = a -> a*2;

        /***Runnable**/
        //no recibe valores y no retorna nada, solo ejecuta ana tarea

        Runnable runnable = ()->{
            System.out.println("Ejecutando tarea");
        };

        runnable.run();

        /**Callable**/
        //no recibe valoes pero retorna un resultado y puede lanzar excepcion.
        Callable<String> callable = ()->{
          return "esto es un callable";
        };

        try {
            String resultCallabe =  callable.call();
            System.out.println("resultCallabe = " + resultCallabe);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        
        //ver desde el minuto 27:53
        List<String>  nombres = Arrays.asList("harol","pepe");

        List<String>nombreEnMay =  nombres.stream().map(String::toUpperCase).toList();
    }
}
