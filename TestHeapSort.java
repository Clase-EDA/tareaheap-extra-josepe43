/*
 * Estructuras de datos
 * Autor: Jose Luis Gutierrez Espinosa
 */
package Heaps;

import Tree.Prefix.Trie;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author jlgut
 */
public class TestHeapSort {
       
    public static void cargaPalabrasArreglo(String[] arr, String nomArch, int n){
        try{
            File f = new File(nomArch);
            Scanner sc = new Scanner(f);
            int i = 0;
            while(sc.hasNext() && i < arr.length && i <= n){
                arr[i] = sc.next();
                i++;
            }//while
        }//try
        catch(Exception e){
            System.out.println(e);
        }//catch
    }//method
    
    public static char[] generaLlaves(String nomArch){
        ArrayList<Character> arr = new ArrayList();        
        try{
            File f = new File(nomArch);
            Scanner sc = new Scanner(f);
            String linea;
            while (sc.hasNext()){
                linea = sc.nextLine();
                if (!linea.contains("#")){
                    for (int i = 0; i < linea.length(); i++){
                        if (!arr.contains(linea.charAt(i))){
                            arr.add(linea.charAt(i));
                        }//if
                    }//for                    
                }//if
            }//while
        }//try
        catch(Exception e){
            System.out.println(e);
        }//catch
        char [] ar = new char [arr.size()];
        for (int i = 0; i < arr.size(); i++)
            ar [i] = arr.get(i);
        return ar;
    }//method
    
    public static void cargaPalabras(String nomArch, Trie t){
        try{
            File f = new File(nomArch);
            Scanner sc = new Scanner(f);
            String s;
            while(sc.hasNext()){
                s = sc.nextLine();
                if (!s.contains("#"))
                    t.add(s);                
            }//while
        }//try
        catch(Exception e){
            System.out.println(e);            
        }//catch
    }//method
    
    
    public static void imprimeArr(String [] arr){
        for (int i = 0; i < arr.length; i++)
            System.out.println(arr[i]);
    }//method
    
    //copia del codigo para el algoritmo merge sort proporcionado en la tarea de ordenamientos
    public static <T extends Comparable <T>> void ordenaMergeSort(T [] datos){
        int [] ar = {0};
        ordenaMergeSort(datos, 0, datos.length - 1, ar);
        System.out.println("mergeSort reviso " + ar[0] + " datos conentrada de " + datos.length);
    }//method
    //Metodo que ordena el arreglo con el merge sort
    private static <T extends Comparable <T>> void ordenaMergeSort(T [] datos, int min, int max, int[] sum){
        T [] temp;
        int indice, izq, der;
        if (min >= max - 1)
            return;
        int tam = max - min + 1, mitad = (max + min)/2;
        temp = (T[])(new Comparable [tam]);
        ordenaMergeSort(datos, min, mitad, sum);
        ordenaMergeSort(datos, mitad + 1, max, sum);

        izq = min; der = mitad + 1;
        for(int i = 0; i < tam; i++){
            sum[0]++;
            if(izq <= mitad && der <= max){
                if(datos[izq].compareTo(datos[der]) < 0)
                    temp[i] = datos[izq++];
                else
                    temp[i] = datos[der++];
            }//if
            else{
                if(izq <= mitad)
                    temp[i] = datos[izq++];
                else   
                    temp[i] = datos[der++];
            }//else
        }//for


        for(int i = 0; i < temp.length; i++){
            sum[0]++;
            datos[min + i] = temp[i];
        }//for
        
    }//method

    public static void imprimeMat(long[][] mat){
        System.out.println("Número de entradas|MergeSort|TrieSort|HeapSort  -->en nanosegundos ");
        for(int i = 0; i < mat[0].length; i ++){
            System.out.print("| ");
            for(int j = 0; j < mat.length; j++){
                System.out.print(mat[j][i] + "| ");
            }//for
            System.out.print("\n");
        }//for
    }//method
    
    public static void main(String args[]){
        long st, ord, imp, end;
        long[][] res = new long[4][10];//#entradas|MergeSort|TrieSort|HeapSort
        int o = 0;
        for(int i = 10000; i<=100000; i+=10000){
            res[0][o] = i;
            
            //====================================Pruebas ordenamiento mergeSort===================================
            String [] arreglo = new String[i];
            String nombreArch = "wiki-100k.txt";
            st = System.nanoTime();
            cargaPalabrasArreglo(arreglo, nombreArch, i);
            ord = System.nanoTime();
            ordenaMergeSort(arreglo);
            end = System.nanoTime();
            //imprimeArr(arreglo);
            imp = System.nanoTime();
            res[1][o] = (end - st);
            
            //================================Pruebas trie==================================
            char keys [] = generaLlaves(nombreArch);
            Trie t = new Trie(keys);
            st = System.nanoTime();
            cargaPalabras(nombreArch, t);
            end = System.nanoTime();
            String [] s = t.toStringArray();
            long ar = System.nanoTime();
            res[2][o] = (end - st);

            //==================================Pruebas heap==================================

            MinHeap<String> h = new MinHeap(i);
            st = System.nanoTime();
            for(int j = 0; j<arreglo.length; j++){
                h.add(arreglo[j]);
            }//for
            String[] ordenado = new String[i];
            for(int k = 0; k < i; k++){
                ordenado[k] = h.delete().toString();
            }//for
            end = System.nanoTime();
            res[3][o] = (end - st);
            o++;
        }//for

        imprimeMat(res);
    }//main
}//class
