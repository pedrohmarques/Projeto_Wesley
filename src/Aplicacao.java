public class Aplicacao {
    public static void main(String[] args) {

        ArvoreAVL arvoreAVL = new ArvoreAVL();
        arvoreAVL.insere(5);
        arvoreAVL.insere(6);
        arvoreAVL.insere(1);
        arvoreAVL.insere(8);
        arvoreAVL.insere(10);
        arvoreAVL.insere(4);
        arvoreAVL.insere(7);

        System.out.println(arvoreAVL.emLargura());

        if(arvoreAVL.gravou()){
            System.out.println("GRAVADO COM SUCESSO");
        }else{
            System.out.println("ERROR: Não foi possível gravar a árvore.");
        }
    }
}
