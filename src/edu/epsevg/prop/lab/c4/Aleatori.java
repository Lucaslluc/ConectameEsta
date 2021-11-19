package edu.epsevg.prop.lab.c4;

/**
 * Jugador aleatori
 * "Alea jacta est"
 * @author Profe
 */
public class Aleatori
  implements Jugador, IAuto
{
  private String nom;
  
  public Aleatori()
  {
    nom = "RandomBanzai";
  }
  
  public int moviment(Tauler t, int color)
  {
    int col = (int)(t.getMida() * Math.random());
    while (!t.movpossible(col)) {
      col = (int)(t.getMida() * Math.random());
    }
    return col;
  }
  
  public String nom()
  {
    return nom;
  }
}
  
//  public int minMax(Tauler pt, int col, int pprof, boolean max){
//    if (!pt.espotmoure()){//No hi ha cap moviment possible
//       return 0;
//    }else if(pt.solucio(col, 1)){//Guanyem nosaltres
//        return 10000;
//    }else if (pt.solucio(col, -1)){//Perdem nosaltres
//        return -10000;
//    }else if(pprof == 0){//Hem arrivat a la profunditat màxima(fem heuristica)
//        System.out.println("avaluaaaamax  " + evaluaTauler(pt,col,1)+ "ccol" + col);
//        return evaluaTauler(pt,col,1);
//    }else{
//         if(max == true){
//             int value = -1000000;
//             for(int i = 0; i<8;i++){
//                 if(pt.movpossible(i)){
//                    Tauler aux = new Tauler(pt);
//                    aux.afegeix(i,1);
//                    int valueNou = minMax(aux,i,pprof-1,false);
//                    if(valueNou>value){
//                        value = valueNou;
//                        colSeg = i;
//                    }
//                 
//                 }
//             }
//             return value;
//        
//         }else{
//            int value = 1000000;
//             for(int i = 0; i<8;i++){
//                 if(pt.movpossible(i)){
//                    Tauler aux = new Tauler(pt);
//                    aux.afegeix(i,-1);
//                    int valueNou = minMax(aux,i,pprof-1,true);
//                    if(valueNou<value){
//                        value = valueNou;
//                        colSeg = i;
//                    }
//                 }
//             }
//             return value;
//         
//         }
//        
//    }
//  
//  
//  }
  
  
  
  




