package arvorebinaria;

import Item.*;

public class ArvoreBinariaPesquisa implements ArvoreBinaria {
    private static class No {
        Item registro;
        No esq, dir ;
    }

    private No raiz;

    public ArvoreBinariaPesquisa () {
        this.raiz = null;
    }

    public void insere (Item registro) {
        this.raiz = this.insere (registro, this.raiz);
    }

    private No insere (Item registro, No p) {
        if (p == null) {
            p = new No ();
            p.registro = registro;
            p.esq = null;
            p.dir = null;
        }
        else
        if (registro.compara (p.registro) < 0)
            p.esq = insere (registro, p.esq);
        else
        if (registro.compara (p.registro) > 0)
            p.dir = insere (registro, p.dir);
        else
            System.out.println (" - ERRO! J� existente: " + registro.toString () + ".");
        return p;
    }

    public Item pesquisa (Item registro) {
        return this.pesquisa (registro, this.raiz);
    }

    private Item pesquisa (Item registro, No p) {
        if (p == null)
            return null; //Registro n�o econtrado.
        else
        if (registro.compara (p.registro) < 0)
            return pesquisa (registro, p.esq);
        else
        if (registro.compara (p.registro) > 0)
            return pesquisa (registro, p.dir);
        else
            return p.registro;
    }

    public void retira (Item registro) {
        this.raiz = this.retira (registro, this.raiz);
    }

    private No retira (Item registro, No p) {
        if (p == null)
            System.out.println (" - ERRO! N�o existente: " + registro.toString ());
        else
        if (registro.compara (p.registro) < 0)
            p.esq = retira (registro, p.esq);
        else
        if (registro.compara (p.registro) > 0)
            p.dir = retira (registro, p.dir);
        else {
            if (p.dir == null)
                p = p.esq;
            else
            if (p.esq == null)
                p = p.dir;
            else
                p.esq = encontraNovaRaiz (p, p.esq);
        }
        return p;
    }

    private No encontraNovaRaiz (No raiz, No descendente) {
        if (descendente.dir != null)
            descendente.dir = encontraNovaRaiz (raiz, descendente.dir);
        else {
            raiz.registro = descendente.registro;
            descendente = descendente.esq;
        }
        return descendente;
    }

    public void imprime () {
        this.em_ordem (this.raiz);
    }

    private void em_ordem (No p) {
        if (p != null) {
            em_ordem (p.esq);
            System.out.print (p.registro.toString () + "  ");
            em_ordem (p.dir);
        }
    }

    public void testa () {
        this.testa (this.raiz);
    }

    private void testa (No p) {
        if (p == null)
            return;
        if (p.esq != null) {
            if (p.registro.compara (p.esq.registro) < 0) {
                System.out.println ("Erro: Pai " + p.registro.toString() + " menor que filho a esquerda " + p.esq.registro.toString());
                System.exit (1);
            }
        }
        if (p.dir != null) {
            if (p.registro.compara (p.dir.registro) > 0 ) {
                System.out.println ("Erro: Pai " + p.registro.toString() + " maior que filho a direita " + p.dir.registro.toString());
                System.exit (1);
            }
        }
        testa(p.esq);
        testa(p.dir);
    }
}
