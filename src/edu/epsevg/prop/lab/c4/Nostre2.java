package edu.epsevg.prop.lab.c4;

import static java.lang.Math.random;
import java.util.Random;

/**
 * Jugador aleatori
 * "Alea jacta est"
 * @author Profe
 */
public class Nostre2
  implements Jugador, IAuto
{
  private String nom;
  
  private int colorNostre=1;
  private int prof;
  private boolean primerMove = true;
          
  public Nostre2(int pprof)
  {
    nom = "ConectameEsta2";
    prof = pprof;
  }
  
  public int moviment(Tauler t, int color)
  {
//    int col = (int)(t.getMida() * Math.random());
//    while (!t.movpossible(col)) {
//      col = (int)(t.getMida() * Math.random());
//    }
      if(primerMove==true){
        for(int i = 0; i<8;i++){
              if(t.getColor(0, i)!=0){
                  colorNostre=colorNostre*-1;
                  break;
              }
        }
      }
      
//    if(primerMove==true){
//
//        primerMove = false;
//        return 3;
//    }
//    
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
    int alpha = Integer.MIN_VALUE;
    int beta = Integer.MAX_VALUE;
    for (int i = 0; i < 8; i++){
        if(pt.movpossible(i)){//Si podem moure l'analitezem, si no, la descartem
            Tauler move = new Tauler(pt);
            move.afegeix(i, 1);
            int valorNou = movMin(move, i ,pprof-1,alpha,beta);
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
public int movMax(Tauler pt, int lastcol ,int pprof,int alpha,int beta){
    
    if(terminal(pt, lastcol)){
        if(pt.solucio(lastcol,-1)){//Perdem
            return -100000;
        }else if(pt.solucio(lastcol,1)){//perdem
           return 100000;
        }else{//No queda moviments
            return 0;
        }
      
    }else if (pprof == 0){
        return Heuristica(pt,1);
    }
        int value = Integer.MIN_VALUE;
        for(int i = 0; i < 8; i++){
            if(pt.movpossible(i)){
                Tauler move = new Tauler(pt);
                move.afegeix(i,1);//ARA VOLEM MINIMITZAR EL MOVIMENT DEL RIVAL
                value = Math.max(value, movMin(move,i,pprof-1,alpha ,beta));
                alpha = Math.max(value,alpha);
                if(alpha>=beta)
                    break;
             }
        }
        return value;   
     
}


public int movMin(Tauler pt,int lastcol, int pprof,int alpha, int beta){
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
        return Heuristica(pt,-1);
    }
        int value = Integer.MAX_VALUE;
        for(int i = 0; i < 8; i++){
            if(pt.movpossible(i)){
                Tauler move = new Tauler(pt);
                move.afegeix(i,-1);//ARA VOLEM MINIMITZAR EL MOVIMENT DEL RIVAL
                value = Math.min(value, movMax(move,i,pprof -1,alpha,beta));
                beta = Math.min(value,beta);
                if(alpha>=beta)
                    break;
             }
        }
//        System.out.println(value);
        return value;   
    
}


public int Heuristica(Tauler pt,int color){
    int  Heur = 0;
    Heur = Valor(pt,color);
    return Heur*color;
}

public int Valor(Tauler pt, int color){
    int value = 0;
//    System.out.println("-----------------------------");
    for(int i = 0;i<8;i++){
        for(int j=0;j<8;j++){
           //Si es columna central le sumamos un plus.
           if(pt.solucio(j, color)){
               return 100000;
           }else{
                /***************
                 Coneccio de 2:
                ***************/
                if(j+1<8){//Der
                    if(pt.getColor(i, j)==color && pt.getColor(i, j+1)==color){
                        value +=10;
                    }
                }
                if(j-1>-1){//Izq
                    if(pt.getColor(i, j)==color && pt.getColor(i, j-1)==color){
                        value +=10;
                    }
                }
                if(i+1<8){//UP
                    if(pt.getColor(i, j)==color && pt.getColor(i+1, j)==color){
                        value +=10;
                    }
                }
                if(i-1>-1){//Down
                    if(pt.getColor(i, j)==color && pt.getColor(i-1, j)==color){
                        value +=10;
                    }
                }
                if(j+1<8 && i+1<8){//UP-DER-Diago
                    if(pt.getColor(i, j)==color && pt.getColor(i+1, j+1)==color) {
                        value +=10;
                    }
                }
                if(j-1>-1 && i+1 < 8){//UP-Izq-Diago
                    if(pt.getColor(i, j-1)==color && pt.getColor(i+1, j-1)==color){
                        value +=10;
                    }
                }
                if(i-1>-1 && j+1<8){//Down-Der-Diago
                    if(pt.getColor(i-1, j)==color && pt.getColor(i-1, j+1)==color){
                        value +=10;
                    }
                }
                if(i-1>-1 && j-1>-1){//Down-Izq-Diago
                    if(pt.getColor(i-1, j)==color && pt.getColor(i-1, j-1)==color){
                        value +=10;
                    }
                }
                
                
                /**************
                 Coneccion de 3
                 **************/
                if(j+2<8){//Der
                    if(pt.getColor(i, j)==color && pt.getColor(i, j+1)==color && pt.getColor(i, j+2)==color){
                        value +=100;
                    }
                }
                if(j-2>-1){//Izq
                    if(pt.getColor(i, j)==color && pt.getColor(i, j-1)==color && pt.getColor(i, j-2)==color){
                        value +=100;
                    }
                }
                if(i+2<8){//UP
                    if(pt.getColor(i, j)==color && pt.getColor(i+1, j)==color && pt.getColor(i+1, j)==color){
                        value +=100;
                    }
                }
                if(i-2>-1){//Down
                    if(pt.getColor(i, j)==color && pt.getColor(i-1, j)==color && pt.getColor(i-2, j)==color){
                        value +=100;
                    }
                }
                if(j+2<8 && i+2<8){//UP-DER-Diago
                    if(pt.getColor(i, j)==color && pt.getColor(i+1, j+1)==color && pt.getColor(i+2, j+2)==color) {
                        value +=100;
                    }
                }
                if(j-2>-1 && i+2 < 8){//UP-Izq-Diago
                    if(pt.getColor(i, j-1)==color && pt.getColor(i+1, j-1)==color  && pt.getColor(i+2, j-2)==color){
                        value +=100;
                    }
                }
                if(i-2>-1 && j+2<8){//Down-Der-Diago
                    if(pt.getColor(i-1, j)==color && pt.getColor(i-1, j+1)==color  && pt.getColor(i-2, j+2)==color){
                        value +=100;
                    }
                }
                if(i-2>-1 && j-2>-1){//Down-Izq-Diago
                    if(pt.getColor(i-1, j)==color && pt.getColor(i-1, j-1)==color && pt.getColor(i-2, j-2)==color){
                        value +=100;
                    }
                }
                
                
                
            }
           
        }
    }
    return value;
}
}

