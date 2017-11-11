package Main;
import Item.*;
import arvorebinaria.ArvoreBinariaPesquisa;

public class Principal {
    public static void main(String[] args) {
        ArvoreBinariaPesquisa arvore = new ArvoreBinariaPesquisa();

        MeuItem item;
        item = new MeuItem(50);
        arvore.insere(item);
        item = new MeuItem(33);
        arvore.insere(item);
        item = new MeuItem(40);
        arvore.insere(item);
        item = new MeuItem(15);
        arvore.insere(item);
        item = new MeuItem(8);
        arvore.insere(item);
        item = new MeuItem(17);
        arvore.insere(item);
        item = new MeuItem(5);
        arvore.insere(item);
        item = new MeuItem(3);
        arvore.insere(item);
        item = new MeuItem(44);
        arvore.insere(item);
        item = new MeuItem(70);
        arvore.insere(item);
        item = new MeuItem(62);
        arvore.insere(item);



        arvore.imprime_em_ordem();

    }
}
