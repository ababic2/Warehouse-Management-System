package ba.unsa.etf.rpr.projekat.HelpModel;

//This class is made as a container to be possible passing by reference
//used in DAO interface

public class Reference<T> {
    private T referent;

    public Reference() {
    }

    public Reference(T initialValue) {
        referent = initialValue;
    }

    public void set(T newVal) {
        referent = newVal;
    }

    public T get() {
        return referent;
    }
}

