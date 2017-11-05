import java.util.LinkedList;
import java.util.Queue;

public class ArvoreAVL {
    private int fatorBalanceamento;
    private int chave;
    private ArvoreAVL esq;
    private ArvoreAVL dir;

    public ArvoreAVL(int chave, ArvoreAVL esq, ArvoreAVL dir){
        setChave(chave);
        setDir(dir);
        setEsq(esq);
    }

    public ArvoreAVL(){
       this(-1,null,null);
    }

    public ArvoreAVL(int chave){
        this(chave,new ArvoreAVL(), new ArvoreAVL());
    }

    public ArvoreAVL getDir() {
        return dir;
    }

    public ArvoreAVL getEsq() {
        return esq;
    }

    public int getFatorBalanceamento() {
        return fatorBalanceamento;
    }

    public void setFatorBalanceamento(int fatorBalanceamento) {
        this.fatorBalanceamento = fatorBalanceamento;
    }

    public int getChave() {
        return chave;
    }

    public void setChave(int chave) {
        this.chave = chave;
    }

    public void setDir(ArvoreAVL dir) {
        this.dir = dir;
    }

    public void setEsq(ArvoreAVL esq) {
        this.esq = esq;
    }

    //Pega a altura da folha
    private static int altura(ArvoreAVL raiz){
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

    //verifica se esta vazia
    public boolean estahVazia(){
        boolean vazia = false;
        if(getEsq() == null && getDir() == null){
            vazia = true;
        }
        return vazia;
    }

    public void anexa(int chave){
        setChave(chave);
        setEsq(new ArvoreAVL());
        setDir(new ArvoreAVL());
    }

    public boolean insere(int chave){
        boolean inserido = true;

        if(estahVazia())anexa(chave);
        else{
            if(chave<getChave()){esq.insere(chave);}
            else if(chave>this.getChave()){dir.insere(chave);}
            else inserido = false;
        }
        //Esta balanceando so falta arrumar esta parte
        // this = balancear(this);
        balancear(this);
        return inserido;
    }
    public boolean remove(ArvoreAVL remover){
        ArvoreAVL r;
        if (remover.getEsq() == null || remover.getDir() == null) {

            if (remover.getPai() == null) {
                this.raiz = null;
                remover = null;
                return;
            }
            r = remover;

        } else {
            r = sucessor(remover);
            remover.setChave(r.getChave());
        }

        ArvoreAVL p = null;
        if (r.getEsq() != null) {
            p = r.getEsq();
        } else {
            p = r.getDir();
        }

        if (p != null) {
            p.setPai(r.getPai());
        }

        if (r.getPai() == null) {
            this.raiz = p;
        } else {
            if (r == r.getPai().getEsq()) {
                r.getPai().setEsq(p);;
            } else {
                r.getPai().setDir(p);
            }
            balancear(r.getPai());
        }
        r = null;
    }

    }

    //pega o desbalanceado
    private int pegaDesbalanceado(ArvoreAVL arvore){
        int alturaEsq = altura(arvore.esq);
        int alturaDir = altura(arvore.dir);
        alturaEsq = alturaEsq * -1;
        int desbalanceado =  alturaEsq + alturaDir;
        return desbalanceado;
    }

    //balanceia a arvore
    public ArvoreAVL balancear(ArvoreAVL treeBalancear){
        if(pegaDesbalanceado(treeBalancear) == 2){
            //se estiver desbalanceada pra direita
            if(pegaDesbalanceado(treeBalancear.esq)>0){
                treeBalancear =simplesDir(treeBalancear);
            }else{
                treeBalancear = duplaDir(treeBalancear);
            }
        }else if(pegaDesbalanceado(treeBalancear) == -2){
            //se estiver desbalanceada pra direita
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

    public void preOrdem(){
        if(estahVazia())return;
        System.out.println(this.getChave() + ", ");
        esq.preOrdem();
        dir.preOrdem();
    }

    public void posOrdem(){

        if(estahVazia())return;
        esq.posOrdem();
        dir.posOrdem();
        System.out.println(this.getChave() + ", ");
    }

    public void emOrdem(){
        if(estahVazia())return;
        esq.emOrdem();
        System.out.println(this.getChave()+ ", ");
        dir.emOrdem();
    }

    public void emLargura(){
        Queue<ArvoreAVL> fila = new LinkedList<>();
        fila.add(this);
        while(!fila.isEmpty()){
            ArvoreAVL no = fila.poll();
            if(no.chave == -1){
                System.out.print("");
            }else{
                System.out.print(no.chave + " | ");
            }
            if(no.esq != null) fila.add(no.esq);
            if(no.dir != null) fila.add(no.dir);
        }

    }

    private static int max( int lhs, int rhs ) {
        return lhs > rhs ? lhs : rhs;
    }

    private static ArvoreAVL duplaDir( ArvoreAVL k3 ) {
        k3.esq = simplesEsq( k3.dir );
        return simplesDir( k3 );
    }

    private static ArvoreAVL duplaEsq( ArvoreAVL k1 ) {
        k1.dir = simplesDir(k1.esq);
        return simplesEsq(k1);
    }

    private static ArvoreAVL simplesDir( ArvoreAVL k2 ) {
        ArvoreAVL k1 = k2.esq;
        k2.esq = k1.dir;
        k1.esq = k2;
        k2.fatorBalanceamento = max( altura( k2.esq ), altura( k2.esq ) ) + 1;
        k1.fatorBalanceamento = max( altura( k1.esq ), k2.fatorBalanceamento ) + 1;
        return k1;
    }

    private static ArvoreAVL simplesEsq( ArvoreAVL k1 ) {
        ArvoreAVL k2 = k1.dir;
        k1.dir = k2.esq;
        k2.esq = k1;
        k1.fatorBalanceamento = max( altura( k1.esq ), altura(k1.dir) ) + 1;
        k2.fatorBalanceamento = max( altura( k2.dir ), k1.fatorBalanceamento ) + 1;
        return k2;
    }
}
