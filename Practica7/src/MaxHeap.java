
import java.util.NoSuchElementException;

public class MaxHeap <T extends Comparable<T>> extends Heap<T>{

    public MaxHeap(){
        super();
    }
    
    public MaxHeap(Lista<T> c){
	super(c);
    }
    
    public MaxHeap(Coleccionable <T> c){
	super(c);
    }
    
    @Override
    protected void reordena(int indice){
	super.reordena(indice,false);
    }

    @Override
    public T elimina() {
        if(siguiente==0)
            throw new NoSuchElementException("No hay elementos en el Max-Heap");
        T max = arreglo[0];
        arreglo[0] = arreglo[siguiente-1];
        arreglo[--siguiente] = null;
        super.reordenaParaAbajo(0, false);
        return max;
    }
    

}
