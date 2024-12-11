package ro.ase.ie.dma11;

public interface Callback<R>{
    void runResultOnUiThread(R result);
}
