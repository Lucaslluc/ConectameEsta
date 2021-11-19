package edu.epsevg.prop.lab.c4;

import static java.lang.Math.random;
import java.util.Random;

/**
 * Jugador aleatori
 * "Alea jacta est"
 * @author Profe
 */
public class Nostre
  implements Jugador, IAuto
{
  private String nom;
  
  private int colSeg=0;
  private int prof;
  private boolean primerMove = true;
          
  public Nostre(int pprof)
  {
    nom = "ConectameEsta";
    prof = pprof;
  }
  
  public int moviment(Tauler t, int color)
  {
//    int col = (int)(t.getMida() * Math.random());
//    while (!t.movpossible(col)) {
//      col = (int)(t.getMida() * Math.random());
//    }
    if(primerMove==true){
        int a = 0;
        int b = 5;
        primerMove = false;
        return new Random().nextBoolean()? a : b;
    }
    
    int nextCol = minimax(t,prof);
    return nextCol;
  }
  
  public String nom()
  {
    return nom;
  }
//funció Tria_Moviment(estat,joc,torns_max) retorna moviment
//Valor = -∞
//per cada s dins de Successor(estat) fer
//if(valor < Min-Valor(s,joc,alfa,beta,profunditat-1)) ){
//millorMoviment = s;
// fper;
// Retorna millorMoviment ;
//Ffunció

public int minimax(Tauler pt, int pprof){
    int valor = Integer.MIN_VALUE;
    int col = 0;
    for (int i = 0; i < 8; i++){
        if(pt.movpossible(i)){//Si podem moure l'analitezem, si no, la descartem
            Tauler move = new Tauler(pt);
            move.afegeix(i, 1);
            int valorNou = movMin(move, i ,pprof-1);
            if(valorNou > valor){
                valor = valorNou;
                col = i;
            }
        } 
    }  
    return col;
}

//funció Max-Valor(estat,joc, profunditat)
// si Terminal(estat) o profunditat==0 llavors retorna Eval(estat);
// Valor = -∞
//per cada s dins de Successor(estat) fer
//valor := Max(valor,Min-Valor(s,joc,alfa,beta,profunditat-1))
// fper;
// retorna valor;
//Ffunció
public boolean terminal(Tauler pt, int lastcol){
    return pt.solucio(lastcol,1) || pt.solucio(lastcol, -1) || !(pt.espotmoure()) ;
}
public int movMax(Tauler pt, int lastcol ,int pprof){
    
    if(terminal(pt, lastcol)){
        if(pt.solucio(lastcol,-1)){//Perdem
//            System.out.println("AAAAAAAAA"+lastcol+"-1");
            return -100000;
        }else if(pt.solucio(lastcol,1)){//perdem
           return 100000;
          //return Heuristica(pt);
        }else{//No queda moviments
            return 0;
        }
      
    }else if (pprof == 0){
        return Heuristica(pt);
    }
    
        int value = Integer.MIN_VALUE;
        for(int i = 0; i < 8; i++){
            if(pt.movpossible(i)){
                Tauler move = new Tauler(pt);
                move.afegeix(i,1);//ARA VOLEM MINIMITZAR EL MOVIMENT DEL RIVAL
                value = Math.max(value, movMin(move,i,pprof-1));
             }
        }
        return value;   
     
}


public int movMin(Tauler pt,int lastcol, int pprof){
    if(terminal(pt, lastcol)){
        if(pt.solucio(lastcol,-1)){//Guanyem
           return -100000;
//            return (Heuristica(pt));
        }else if(pt.solucio(lastcol,1)){//perdem
               return 100000;
//            return (Heuristica(pt));
        }else{//No queda moviments
           return 0;
        }
    }else if (pprof == 0){
        return Heuristica(pt);
    }
        int value = Integer.MAX_VALUE;
        for(int i = 0; i < 8; i++){
            if(pt.movpossible(i)){
                Tauler move = new Tauler(pt);
                move.afegeix(i,-1);//ARA VOLEM MINIMITZAR EL MOVIMENT DEL RIVAL
                value = Math.min(value, movMax(move,i,pprof -1));
             }
        }
//        System.out.println(value);
        return value;   
    
}


public int Heuristica(Tauler pt){
    int  Heur = 0;
    Heur = Valor(pt,1);
    return Heur;
}

public int Valor(Tauler pt, int color){
    int value = 0;
//    System.out.println("-----------------------------");
    for(int i = 0;i<8;i++){
        for(int j=0;j<8;j++){
            if(pt.solucio(j, color)){
//                 System.out.println("if0");
               return 10000;
            }
           //Casos de 3:
            if(j+2<8){//4 en H
                if(pt.getColor(i,j)==color && pt.getColor(i,j+1)==color &&
                        pt.getColor(i,j+2)==color ){
//                    System.out.println("if1");
                    value =  10000;
                }
            }
             if(i+2<8){//3 en V
                if(pt.getColor(i,j)==color && pt.getColor(i+1,j)==color &&
                        pt.getColor(i+2,j)==color){
//                     System.out.println("if2");
                    value = 10000;
                }
            }
            if(i+2<8 && j+2<8){//3 en Diagonal hacia derecha y arriba
                if(pt.getColor(i,j)==color && pt.getColor(i+1,j+1)==color &&
                        pt.getColor(i+2,j+2)==color){
//                     System.out.println("if3");
                    value = 12000;
                }
            }
            if(i-2>1 && j+2<8){//3 en Diagonal hacia derecha y abajo
                if(pt.getColor(i,j)==color && pt.getColor(i-1,j+1)==color &&
                        pt.getColor(i-2,j+2)==color ){
//                     System.out.println("if4");
                    value = 1200;
                }
            }
            if(i+2<8 && j-2>-1){//3en Diagonal hacia izq y arriba
                if(pt.getColor(i,j)==color && pt.getColor(i+1,j-1)==color &&
                        pt.getColor(i+2,j-2)==color){
//                     System.out.println("if5");
                    value = 1200;
                }
            }
//           //casos de 2:
            if(j+1<8){//2 en H
                if(pt.getColor(i,j)==color && pt.getColor(i,j+1)==color){
                    if(value<1000){
//                         System.out.println("if7");
                        value =  500;
                    }
                }
            }
             if(i+1<8){//2 en v
                if(pt.getColor(i,j)==color && pt.getColor(i+1,j)==color){
                    if(value<1000){
//                         System.out.println("if8");
                        value =  500;
                    }
                }
            }
            if(i+1<8 && j+1<8){//3 en Diagonal hacia derecha y arriba
                if(pt.getColor(i,j)==color && pt.getColor(i+1,j+1)==color){
                    if(value<1000){
//                         System.out.println("if9");
                        value =  700;
                    }
                }
            }
            if(i-1>1 && j+1<8){//3 en Diagonal hacia derecha y abajo
                if(pt.getColor(i,j)==color && pt.getColor(i-1,j+1)==color ){
//                     System.out.println("if11");
                    if(value<1000){
                        
                        value =  600;
                    }
                }
            }
            if(i+1<8 && j-1>-1){//2en Diagonal hacia izq y arriba
                if(pt.getColor(i,j)==color && pt.getColor(i+1,j-1)==color ){
//                     System.out.println("if12");
                    if(value<1000){
                        value =  600;
                    }
                }
            }
        }
    }
    return value;
}
}

