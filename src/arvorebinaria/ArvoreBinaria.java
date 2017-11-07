package arvorebinaria;

import Item.*;

public interface ArvoreBinaria {
    public void insere (Item reg);
    public Item pesquisa (Item reg);
    public void retira (Item reg);
    public void imprime ();
    public void testa ();
}
