package br.com.springredis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Aula02 {

    static ConcurrentHashMap<String, Object> hash = new ConcurrentHashMap<>(){{
        put("estados", Arrays.asList("SP","MG","RJ"));
        put("mg", Arrays.asList("Juiz de fora","Belo horizonte","Contagem"));
    }};

    public static void main(String[] args) {
        Scanner tec = new Scanner(System.in);
        String c = tec.next();
        System.out.println(hash.get(c));
    }
}
