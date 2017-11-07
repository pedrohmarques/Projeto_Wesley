package arvorebinaria;

import Item.*;

public interface ArvoreBinaria {
    public void insere (Item reg);
    public Item pesquisa (Item reg);
    public void retira (Item reg);
    public void imprime_em_ordem ();
    public void imprime_pre_ordem ();
    public void imprime_pos_ordem ();
    public void imprime_em_largura ();
    public void testa ();
}
