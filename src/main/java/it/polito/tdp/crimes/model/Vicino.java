package it.polito.tdp.crimes.model;

public class Vicino implements Comparable<Vicino> {
Integer v;
Double peso;
/**
 * @param v
 * @param peso
 */
public Vicino(Integer v, Double peso) {
	super();
	this.v = v;
	this.peso = peso;
}
public Integer getV() {
	return v;
}
public void setV(Integer v) {
	this.v = v;
}
public Double getPeso() {
	return peso;
}
public void setPeso(Double peso) {
	this.peso = peso;
}

@Override
public String toString() {
	return  "Distretto: "+ v +"--"+ "Distante: " + peso  ;
}
@Override
public int compareTo(Vicino o) {

	return (int) (this.peso-o.peso);
}


}
