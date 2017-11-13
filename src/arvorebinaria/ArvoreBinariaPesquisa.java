package arvorebinaria;

import Item.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

public class ArvoreBinariaPesquisa implements ArvoreBinaria {
    private static class No {
        Item registro;
        No esq, dir ;
        private int fatorBalanceamento;
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

        p = balancear(p);
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
        p = balancear(p);
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
    private static int altura(No raiz){
        int tamAltura = 0;
        if(raiz == null)
            tamAltura = -1;
        else{
            int he = altura(raiz.esq);
            int hd = altura(raiz.dir);
            if(he<hd)
                tamAltura = hd+1;
            else
                tamAltura = he+1;
        }

        return tamAltura;
    }

    private int pegaDesbalanceado(No arvore){
        int alturaEsq = altura(arvore.esq);
        int alturaDir = altura(arvore.dir);
        alturaEsq = alturaEsq * -1;
        int desbalanceado =  alturaEsq + alturaDir;
        return desbalanceado;
    }

    public No balancear(No treeBalancear){
        if(pegaDesbalanceado(treeBalancear) == 2){
            //se estiver desbalanceada pra direita
            if(treeBalancear.esq == null)
                treeBalancear = simplesDirNull(treeBalancear);
            else if(pegaDesbalanceado(treeBalancear.esq)>0){
                treeBalancear =simplesDir(treeBalancear);
            }else{
                treeBalancear = duplaDir(treeBalancear);
            }
        }else if(pegaDesbalanceado(treeBalancear) == -2){
            //se estiver desbalanceada pra direita
            if(treeBalancear.esq == null)
                treeBalancear = simplesEsqNull(treeBalancear);
            if(pegaDesbalanceado(treeBalancear.esq)<0){
                treeBalancear = simplesEsq(treeBalancear);
            }else{
                treeBalancear = duplaEsq(treeBalancear);
            }

        }
        treeBalancear.fatorBalanceamento = max(altura(treeBalancear.esq),
                altura(treeBalancear.dir));

        return treeBalancear;
    }

    private static int max( int lhs, int rhs ) {
        return lhs > rhs ? lhs : rhs;
    }

    private No simplesDirNull(No raiz){
        No aux = raiz.dir;
        Item aux2 = raiz.registro;
        raiz = aux;
        this.insere(aux2, raiz);
        return raiz;
    }

    private  No simplesEsqNull(No raiz){
        No aux = raiz.esq;
        Item aux2 = raiz.registro;
        raiz = aux;
        this.insere(aux2,raiz);
        return raiz;
    }

    private No duplaDir(No k3 ) {
        k3.dir = simplesEsq(k3.dir );
        return simplesDir(k3);
    }

    private  No duplaEsq(No k1 ) {
        k1.esq = simplesDir(k1.esq);
        return simplesEsq(k1);
    }

    private  No simplesDir(No k2 ) {
        No k1 = k2.esq;
        if(k2.esq == null)
           k1 = simplesDirNull(k2);
        else {
            k2.esq = k1.dir;
            k1.esq = k2;
            k2.fatorBalanceamento = max(altura(k2.esq), altura(k2.esq)) + 1;
            k1.fatorBalanceamento = max(altura(k1.esq), k2.fatorBalanceamento) + 1;
        }
        return k1;
    }

    private No simplesEsq(No k1 ) {
        No k2 = k1.dir;
        if(k1.dir == null)
            k2 = simplesEsqNull(k1);
        else {
            k1.dir = k2.esq;
            k2.esq = k1;
            k1.fatorBalanceamento = max(altura(k1.esq), altura(k1.dir)) + 1;
            k2.fatorBalanceamento = max(altura(k2.dir), k1.fatorBalanceamento) + 1;
        }
        return k2;
    }


    public void imprime_em_ordem () {
        this.em_ordem (this.raiz);
    }
    public void imprime_pre_ordem () {
        this.pre_ordem (this.raiz);
    }
    public void imprime_pos_ordem () {
        this.pos_ordem (this.raiz);
    }
    public void imprime_em_largura () {
        this.em_largura (this.raiz);
    }

    private void em_ordem (No p) {
        if (p != null) {
            em_ordem (p.esq);
            System.out.print (p.registro.toString () + "  ");
            em_ordem (p.dir);
        }
    }

    private void pre_ordem(No p){
        if(p != null){
            System.out.println(p.registro.toString() + " ");
            pre_ordem(p.esq);
            pre_ordem(p.dir);
        }
    }

    private void pos_ordem(No p){
        if(p != null){
            pos_ordem(p.esq);
            pos_ordem(p.dir);
            System.out.println(p.registro.toString() + " ");
        }
    }

    private String em_largura(No p){
        Queue<No> fila = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        fila.add(this.raiz);
        while(!fila.isEmpty()){
            No no = fila.poll();
            if(no.registro == null){
                sb.append("");
            }else{
                sb.append(no.registro.toString() + " | ");
            }
            if(no.esq != null) fila.add(no.esq);
            if(no.dir != null) fila.add(no.dir);
        }
        return sb.toString();
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


    public boolean gravou() {
        boolean gravado = false;
        File arquivo;
        FileOutputStream saida = null;
        OutputStreamWriter gravador = null;
        BufferedWriter buffer_saida = null;
        try {
            arquivo = new File("arvoreAVL.txt");
            saida = new FileOutputStream(arquivo, true);
            gravador = new OutputStreamWriter(saida);
            buffer_saida = new BufferedWriter(gravador);

            String linha = System.getProperty("line.separator");
            if (raiz != null) {
                buffer_saida.write(this.em_largura(raiz) + linha);
            } else {
                throw new Exception("ERROR: Lista vazia.");
            }
            gravado = true;
        } catch (Exception e) {
            gravado = false;
            System.err.println("ERROR: Não foi possível registrar no arquivo.");
        } finally {
            try {
                if (buffer_saida != null)
                    buffer_saida.close();
                if (gravador != null)
                    gravador.close();
                if (saida != null)
                    saida.close();
            } catch (Exception e) {
                System.out.println("ERRO ao fechar os manipuladores de escrita do arquivo arvoreAVL.txt");
                e.printStackTrace();
            }
            return gravado;
        }
    }
    
}
