package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    // Agregar atributos privados del Conjunto
    Nodo raiz;
    int card;

    private class Nodo {
        // Agregar atributos privados del Nodo
        T val;
        Nodo izq;
        Nodo der;

        // Crear Constructor del nodo
        Nodo(T v) {
            this.val = v;
            this.der = null;
            this.der = null;
        }
    }

    public ABB() {
        this.raiz = null;
        this.card = 0;
    }

    public int cardinal() {
        return this.card;
    }

    public T maximo() {
        if (raiz == null) {
            return null;
        }
        return minimoAux(raiz);
    }

    private T minimoAux(Nodo nodo) {
        if (nodo.izq != null) {
            return minimoAux(nodo.izq);
        } else {
            return nodo.val;
        }
    }

    public T minimo() {
        if (raiz == null) {
            return null;
        }
        return maximoAux(raiz);
    }

    private T maximoAux(Nodo nodo) {
        if (nodo.der != null) {
            return maximoAux(nodo.der);
        } else {
            return nodo.val;
        }
    }

    public void insertar(T elem) {
        if (raiz == null) {
            raiz = new Nodo(elem);
            this.card++;
        } else {
            insertarAux(raiz, elem);
        }
    }

    private void insertarAux(Nodo nodo, T elem) {
        if (nodo.val.compareTo(elem) == 0) {
            return;
        }
        if (nodo.val.compareTo(elem) > 0) {
            if (nodo.der == null) {
                nodo.der = new Nodo(elem);
                this.card++;
            } else {
                insertarAux(nodo.der, elem);
            }
        } else {
            if (nodo.izq == null) {
                nodo.izq = new Nodo(elem);
                this.card++;
            } else {
                insertarAux(nodo.izq, elem);
            }
        }
    }

    public boolean pertenece(T elem) {
        if (raiz == null) {
            return false;
        } else {
            return perteneceAux(raiz, elem);
        }
    }

    private boolean perteneceAux(Nodo nodo, T elem) {
        if (nodo.val.compareTo(elem) == 0) {
            return true;
        }
        if (nodo.val.compareTo(elem) > 0) {
            if (nodo.der == null) {
                return false;
            }
            return perteneceAux(nodo.der, elem);
        } else {
            if (nodo.izq == null) {
                return false;
            }
            return this.perteneceAux(nodo.izq, elem);
        }
    }

    public void eliminar(T elem) {
        raiz = eliminarAux(raiz, elem);
    }

    private Nodo eliminarAux(Nodo nodo, T elem) {
        if (nodo == null)
            return null;

        if (elem.compareTo(nodo.val) > 0) {
            nodo.izq = eliminarAux(nodo.izq, elem);
        } else if (elem.compareTo(nodo.val) < 0) {
            nodo.der = eliminarAux(nodo.der, elem);
        } else {
            if (nodo.izq == null && nodo.der == null) {
                nodo = null;
                card--;
            } else if (nodo.izq == null) {
                nodo = nodo.der;
                card--;
            } else if (nodo.der == null) {
                nodo = nodo.izq;
                card--;
            } else {
                T sucesor = minimoAux(nodo.der);
                nodo.val = sucesor;
                nodo.der = eliminarAux(nodo.der, sucesor);
            }
        }
        return nodo;
    }

    public String toString() {
        return "{" + toStringAux(raiz).trim() + "}";
    }

    private String toStringAux(Nodo nodo) {
        if (nodo == null) {
            return "";
        }
        String actual = nodo.val.toString();
        String izquierda = toStringAux(nodo.der);
        String derecha = toStringAux(nodo.izq);

        return izquierda + (izquierda.isEmpty() ? "" : ",") + actual + (derecha.isEmpty() ? "" : ",") + derecha;
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual;

        ABB_Iterador() {
            this._actual = raiz;
        }

        public boolean haySiguiente() {
            if (_actual == null || (_actual.izq == null && _actual.der == null)) {
                return false;
            } else {
                return true;
            }
        }

        public T siguiente() {
            if (!haySiguiente()) {
                return null;
            }
            T elemA = minimo();
            eliminar(elemA);
            return elemA;
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}
