package edu.epsevg.prop.lab.c4;

import static java.lang.Math.random;
import java.util.Random;

/**
 * Jugador aleatori
 * "Alea jacta est"
 * @author Profe
 */
public class Nostre3
  implements Jugador, IAuto
{
  private String nom;
  
  private int colorNostre=1;
  private int prof;
  private boolean primerMove;
  private int contaNodes;
  
public int[][] tablaPuntuacio = {
    {3, 4, 5, 7, 7, 5, 4, 3},
    {4, 6, 8,10,10, 8, 6, 4},
    {5, 8,11,13,13,11, 8, 5},
    {7,10,13,16,16,13,10, 7},
    {7,10,13,16,16,13,10, 7},
    {5, 8,11,13,13,11, 8, 5},
    {4, 6, 8,10,10, 8, 6, 4},
    {3, 4, 5, 7, 7, 5, 4, 3}
}
                                                            ;
  
  
  public Nostre3(int pprof)
  {
    nom = "ConectameEsta3";
    prof = pprof;
    contaNodes = 0;
    primerMove =true;
  }
  
  public int moviment(Tauler t, int color)
  {
//    int col = (int)(t.getMida() * Math.random());
//    while (!t.movpossible(col)) {
//      col = (int)(t.getMida() * Math.random());
//    }
      if(primerMove==true){
        primerMove = false;
        for(int i = 0; i<8;i++){
              if(t.getColor(0, i)!=0){
                  colorNostre=colorNostre*-1;
                  System.out.println("COLOR"+colorNostre);
                  break;
              }
        }
//        if(t.getColor(0,3)==0)
//            return 3;
//        if(t.getColor(0,4)==0)
//            return 4;
            //return new Random().nextBoolean()? 3 : 5;
        }
      
   
    int nextCol = minimax(t,prof);
    System.out.println("Nodes total = "+ contaNodes);
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
            move.afegeix(i, colorNostre);
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
        if(pt.solucio(lastcol,colorNostre*-1)){//Perdem
            return -1000000;
        }else if(pt.solucio(lastcol,colorNostre)){//perdem
           return 1000000;
        }else{//No queda moviments
            return Heuristica(pt,colorNostre);
        }
      
    }else if (pprof == 0){
        return Heuristica(pt,colorNostre);
    }
        int value = Integer.MIN_VALUE;
        for(int i = 0; i < 8; i++){
            if(pt.movpossible(i)){
                
                Tauler move = new Tauler(pt);
                move.afegeix(i,colorNostre);//ARA VOLEM MINIMITZAR EL MOVIMENT DEL RIVAL
                value = Math.max(value, movMin(move,i,pprof-1,alpha ,beta));
                alpha = Math.max(value,alpha);
                if(alpha>=beta)
                {
                    break;
                }
             }
        }
        return value;   
     
}


public int movMin(Tauler pt,int lastcol, int pprof,int alpha, int beta){
    if(terminal(pt, lastcol)){
        if(pt.solucio(lastcol,colorNostre*-1)){//Guanyem
           return -100000;
//            return (Heuristica(pt));
        }else if(pt.solucio(lastcol,colorNostre)){//perdem
               return 100000;
//            return (Heuristica(pt));
        }else{//No queda moviments
           return Heuristica(pt,colorNostre);
        }
    }else if (pprof == 0){
        return Heuristica(pt,colorNostre);
    }
        int value = Integer.MAX_VALUE;
        for(int i = 0; i < 8; i++){
            if(pt.movpossible(i)){
                Tauler move = new Tauler(pt);
                move.afegeix(i, colorNostre*-1);//ARA VOLEM MINIMITZAR EL MOVIMENT DEL RIVAL
                value = Math.min(value, movMax(move,i,pprof -1,alpha,beta));
                beta = Math.min(value,beta);
                if(alpha>=beta)
                {
                    break;
                }
             }
        }
//        System.out.println(value);
        return value;   
    
}


public int Heuristica(Tauler pt,int color){
//    int  Heur = 0;
  contaNodes = contaNodes + 1;
//    Heur = Valor(pt,colorNostre)-(Valor(pt,colorNostre*-1)*2);
//    return Heur;


    int sum = 0;
    for (int i = 0; i < 8; i++) {
        if(pt.getColor(i, 0)==0 && pt.getColor(i,1)==0  &&
            pt.getColor(i, 2)==0 && pt.getColor(i,3)==0 &&
             pt.getColor(i, 4)==0 && pt.getColor(i,5)==0 &&
                 pt.getColor(i, 6)==0 && pt.getColor(i,7)==0){
            break;
        }
        for (int j = 0; j < 8; j++) {
            if (pt.getColor(i, j) == colorNostre) {
                sum += tablaPuntuacio[i][j];
            } else if (pt.getColor(i, j) == colorNostre*-1) {
                sum -= tablaPuntuacio[i][j];
            }
        }
    }
    return sum;

}

public int Valor(Tauler pt, int color){
    int value = 0;
//    System.out.println("-----------------------------");
    for(int i = 0;i<8;i++){
        if(pt.getColor(i, 0)==0 && pt.getColor(i,1)==0  &&
            pt.getColor(i, 2)==0 && pt.getColor(i,3)==0 &&
             pt.getColor(i, 4)==0 && pt.getColor(i,5)==0 &&
                 pt.getColor(i, 6)==0 && pt.getColor(i,7)==0){
            break;
        }
        for(int j=0;j<8;j++){
            
           if(pt.solucio(j, color)){
               return 100000;
           }
           else{
//               if((j==3 || j == 4) && color == colorNostre){
//                   value = value + 200;
//               }
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
                        value +=20;
                    }
                }
                if(i-1>-1){//Down
                    if(pt.getColor(i, j)==color && pt.getColor(i-1, j)==color){
                        value +=20;
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
                        value +=110;
                    }
                }
                if(i-2>-1){//Down
                    if(pt.getColor(i, j)==color && pt.getColor(i-1, j)==color && pt.getColor(i-2, j)==color){
                        value +=110;
                    }
                }
                if(j+2<8 && i+2<8){//UP-DER-Diago
                    if(pt.getColor(i, j)==color && pt.getColor(i+1, j+1)==color && pt.getColor(i+2, j+2)==color) {
                        value +=120;
                    }
                }
                if(j-2>-1 && i+2 < 8){//UP-Izq-Diago
                    if(pt.getColor(i, j-1)==color && pt.getColor(i+1, j-1)==color  && pt.getColor(i+2, j-2)==color){
                        value +=120;
                    }
                }
                if(i-2>-1 && j+2<8){//Down-Der-Diago
                    if(pt.getColor(i-1, j)==color && pt.getColor(i-1, j+1)==color  && pt.getColor(i-2, j+2)==color){
                        value +=120;
                    }
                }
                if(i-2>-1 && j-2>-1){//Down-Izq-Diago
                    if(pt.getColor(i-1, j)==color && pt.getColor(i-1, j-1)==color && pt.getColor(i-2, j-2)==color){
                        value +=120;
                    }
                }
                
                
                
            }
           
        }
    }
    return value;
}





}

