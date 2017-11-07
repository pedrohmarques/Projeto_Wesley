package Main;
import Item.*;
import arvorebinaria.ArvoreBinariaPesquisa;

public class Principal {
    public static void main(String[] args) {
        ArvoreBinariaPesquisa arvore = new ArvoreBinariaPesquisa();
        for(int i= 0;i<10;i+=2){
            MeuItem item = new MeuItem(i+1);
            arvore.insere(item);
        }

        arvore.imprime_em_largura();

    }
}
