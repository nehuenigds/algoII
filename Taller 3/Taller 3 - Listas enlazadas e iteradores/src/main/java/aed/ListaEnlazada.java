package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {
    private Nodo primero;
    private Nodo ultimo;
    private int longitud;

    private class Nodo {
        T elem;
        Nodo siguiente;
        Nodo anterior;

        Nodo(T elem) {
            this.elem = elem;
            this.siguiente = null;
            this.anterior = null;
        }
    }

    public ListaEnlazada() {
        primero = null;
        ultimo = null;
        longitud = 0;
    }

    public int longitud() {
        return longitud;
    }

    public void agregarAdelante(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (this.primero == null) {
            this.primero = nuevo;
        } else {
            this.primero.anterior = nuevo;
            nuevo.siguiente = this.primero;
            nuevo.anterior = null;
            this.primero = nuevo;
        }
        longitud++;
    }

    public void agregarAtras(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (this.primero == null) {
            this.primero = nuevo;
        } else {
            Nodo actual = this.primero;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;

            nuevo.anterior = actual;
            nuevo.siguiente = null;
            this.ultimo = nuevo;
        }
        longitud++;
    }

    public T obtener(int i) {
        int actual_i = 0;
        Nodo actual = this.primero;
        while (actual_i != i) {
            actual = actual.siguiente;
            actual_i ++;
        }
        return actual.elem;
    }

    private Nodo obtenerNodo(int i) {
        Nodo actual = this.primero;
        int actual_i = 0;
        while (actual_i != i) {
            actual = actual.siguiente;
            actual_i++;
        }
        return actual;
    }

    public void eliminar(int i) {
        if (i == 0) {
            if (primero.siguiente != null) {
                primero = primero.siguiente;
            } else {
                primero = null;
            }
        } else if (i == longitud - 1) {
            Nodo actual = obtenerNodo(i);
            ultimo = actual.anterior;
            ultimo.siguiente = null;
        } else {
            Nodo actual = obtenerNodo(i);
            actual.anterior.siguiente = actual.siguiente;
            actual.siguiente.anterior = actual.anterior;
        }
        longitud --;
    }

    public void modificarPosicion(int indice, T elem) {
        Nodo actual = obtenerNodo(indice);
        actual.elem = elem;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        primero = null;
        ultimo = null;
        longitud = 0;
        Nodo este_nodo = lista.primero;
        while (este_nodo != null) {
            this.agregarAtras(este_nodo.elem);
            este_nodo = este_nodo.siguiente;
        }

    }

    @Override
    public String toString() {
        Iterador<T> ite = this.iterador();
        String m = "[";
        m+=ite.siguiente();
        while (ite.haySiguiente()) {
            m +=", " + ite.siguiente();
        }m +="]";
        return m;
    }

    private class ListaIterador implements Iterador<T> {
        private int i;

        private ListaIterador() {
            i = 0;
        }

        public boolean haySiguiente() {
            return i != longitud;
        }

        public boolean hayAnterior() {
            return i > 0;
        }

        public T siguiente() {
            i++;
            return obtener(i - 1);
        }

        public T anterior() {
            i--;
            return obtener(i);
        }
    }

    public Iterador<T> iterador() {
        return new ListaIterador();
    }

}