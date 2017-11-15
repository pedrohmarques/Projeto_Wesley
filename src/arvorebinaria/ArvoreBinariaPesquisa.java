package arvorebinaria;

import item.*;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ArvoreBinariaPesquisa implements ArvoreBinaria {
    private static class No {
        Item registro;
        No esq, dir ;
        int fatorBalanceamento = 0;
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
            System.out.println (" - ERROR! Já existente: " + registro.toString () + ".");

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

    private static int altura( No t ) {
        return t == null ? -1 : t.fatorBalanceamento;
    }

    private int pegaDesbalanceado (No t) {
        return altura( t.esq ) - altura( t.dir );
    }

    public No balancear(No treeBalancear){
        if(pegaDesbalanceado(treeBalancear) == 2){
            //se estiver desbalanceada pra direita
            if(pegaDesbalanceado(treeBalancear.esq)>0){
                treeBalancear =simplesDir(treeBalancear);
            }else{
                treeBalancear = duplaDir(treeBalancear);
            }
        }else if(pegaDesbalanceado(treeBalancear) == -2){
            //se estiver desbalanceada pra direita
            if(pegaDesbalanceado(treeBalancear.dir)<0){
                treeBalancear = simplesEsq(treeBalancear);
            }else{
                treeBalancear = duplaEsq(treeBalancear);
            }

        }
        treeBalancear.fatorBalanceamento = max(altura(treeBalancear.esq),
                altura(treeBalancear.dir)) +1;

        return treeBalancear;
    }

    private static int max( int lhs, int rhs ) {
        return lhs > rhs ? lhs : rhs;
    }

    private static No duplaDir(No k3 ) {
        k3.esq = simplesEsq(k3.esq );
        return simplesDir(k3);
    }

    private static No duplaEsq(No k1 ) {
        k1.dir = simplesDir(k1.dir);
        return simplesEsq(k1);
    }

    private static No simplesDir(No k2 ) {
        No k1 = k2.esq;
        k2.esq = k1.dir;
        k1.dir = k2;
        k2.fatorBalanceamento = max(altura(k2.esq), altura(k2.dir)) + 1;
        k1.fatorBalanceamento = max(altura(k1.esq), k2.fatorBalanceamento) + 1;
        return k1;
    }

    private static No simplesEsq(No k1 ) {
        No k2 = k1.dir;
        k1.dir = k2.esq;
        k2.esq = k1;
        k1.fatorBalanceamento = max(altura(k1.esq), altura(k1.dir)) + 1;
        k2.fatorBalanceamento = max(altura(k2.dir), k1.fatorBalanceamento) + 1;
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
        System.out.println(this.em_largura (this.raiz));
    }

    private void em_ordem (No p) {
        if (p != null) {
            em_ordem (p.esq);
            System.out.print (p.registro.toString () + " ");
            em_ordem (p.dir);
        }
    }

    private void pre_ordem(No p){
        if(p != null){
            System.out.print(p.registro.toString() + " ");
            pre_ordem(p.esq);
            pre_ordem(p.dir);
        }
    }

    private void pos_ordem(No p){
        if(p != null){
            pos_ordem(p.esq);
            pos_ordem(p.dir);
            System.out.print(p.registro.toString() + " ");
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
                sb.append(no.registro.toString() + ", ");
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
                System.out.println("ERROR: Lista vazia.");
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

    public ArvoreBinariaPesquisa recuperarArvore(int iteracao){
        String aux = "";
        ArvoreBinariaPesquisa arvore = new ArvoreBinariaPesquisa();

        File arquivo;
        FileInputStream entrada = null;
        InputStreamReader leitor = null;
        BufferedReader buffer_entrada = null;

        try{
            arquivo = new File("arvoreAVL.txt");
            entrada = new FileInputStream(arquivo);
            leitor = new InputStreamReader(entrada);
            buffer_entrada = new BufferedReader(leitor);

            for(int i=0; i<iteracao; i++) {
                if ((aux = buffer_entrada.readLine()) == null) {
                    break;
                }
            }

            if(aux != null) {
                String[] itens = aux.split(", ");

                for (int i = 0; i < itens.length; i++) {
                    int item = Integer.parseInt(itens[i]);
                    arvore.insere(new MeuItem(item));
                }
            }
            else{
                System.out.println("ERROR: Não foi possível encontrar nenhuma árvore na linha solicitada");
            }

        }catch(Exception e){
            System.out.println("Não foi possível abrir o arquivo arvoreAVL.txt");
        }
        finally {
            try {
                if (buffer_entrada != null)
                    buffer_entrada.close ();
                if (leitor != null)
                    leitor.close ();
                if (entrada != null)
                    entrada.close ();
            } catch (Exception e) {
                System.out.println ("ERROR: Não foi possível fechar os manipuladores de entrada do arvoreAVL.txt");
                e.printStackTrace ();
            }
        }
        return arvore;
    }

}
