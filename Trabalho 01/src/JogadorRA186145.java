//Nome: Rafael Figueiredo Prudencio                             RA:186145
//Obs: Sempre que esteja indicado algum critério utilizado na escolha de uma carta para baixar ou alvo para atacar, pode-se assumir que foram testados inúmeros outros critérios e escolheu-se o que havia a melhor taxa de vitórias.
//Para o comportamento agressivo, não havia muito espaço para mudanças, dado que o único alvo possível era o herói inimigo. Após testar todas as permutações possíveis, verificou-se que a melhor priorização de cartas é, em ordem: carta magia do tipo AREA, carta magia do tipo BUFF, carta lacaio e carta magia do tipo ALVO. O poder heróico era utilizado somente se restasse mana após tentar baixar alguma carta. Para baixar a magia do tipo AREA, verificou-se se ela mataria ao menos um lacaio inimigo. Nas magias BUFF, foram utilizados dois critérios para determinar o melhor lacaio para buffar: ataque seguido de vidaAtual. Na hora de escolher os lacaios para baixar, priorizou-se baixar os que tinham maior ataque seguidos pela maior vidaMaxima. Na escolha da magia de ALVO, verificou-se que o desempenho do jogador não era afetado por um critério de priorizar a que tinha maior dano, por isso, somente verifica-se se há mana suficiente.
//Para o comportamento controle, seguiu-se as indicações do enunciado e priorizou-se, em ordem: cartas magia do tipo AREA, cartas magia do tipo ALVO com lacaio inimigo favorável, cartas lacaio, carta magia do tipo BUFF e carta ALVO sem lacaio favorável (ataca o herói). As cartas de magia do tipo AREA eram somente utilizadas caso houvesse 2 ou mais lacaios inimigos na mesa. A carta magia do tipo ALVO que tinham um lacaio oponente favorável (determinado usando os critérios do enunciado) para atacar foram priorizadas sobre baixar novos lacaios. Para baixar novos lacaios, preferiu-se baixar os que tinham maior vidaMaxima seguido por maior ataque, pois almejamos controle da mesa e lacaios com maior vida tendem a conseguir fazer trocas favoráveis com mais frequência. O critério para as magias BUFF foi o mesmo do comportamento agressivo, ataque seguido por vidaAtual. Finalmente, usa-se as magias de ALVO que irão atacar o herói. Nos casos de lacaios ou magias atacando lacaios inimigos, primeiro estabeleceu-se quais inimigos se encaixavam nas especificações de uma troca favorável dadas no enunciado. Em seguida, procurou-se eliminar primeiro os que tinham maior ataque seguido por maior vidaAtual. Para garantir que primeiro serão avaliados os lacaios mais fracos da mesa na hora de escolher um para atacar, ordenou-se a lista de lacaios de menor para maior usando os critérios de ataque e vidaAtual.
//Para o comportamento curva de mana, utilizou-se um algoritmo para encontrar todas as possíveis combinações de cartas. Primeiro, essas combinações foram filtradas para somente incluir as que usavam a maior quantia de mana possível para cada rodada. Também foram removidas as combinações com magias de AREA que não fariam um uso eficiente da mana do jogador e magias de ALVO que não tinham um oponente favorável para atacar. Das que restaram, priorizou-se lacaios sobre magias e das que tinham lacaios, preferiu-se baixar os lacaios de maior ataque. Em seguida, priorizou-se as combinações com maior dano total seguidas pelas que tinham maior vidaMaxima. Após determinar a melhor combinação de cartas, o critério para a escolha do alvo de cada carta é análogo ao do comportamento de controle, onde busca-se um oponente favorável e caso não haja, ataca-se o herói.
//Além dos comportamentos especificados pelo enunciado, implementou-se um comportamento de burst, cujo propósito era maximizar o dano causado sobre o herói. A diferença entre esse modo e o agressivo é que neste modo, prioriza-se o uso de cartas magia, já que o dano delas é de efeito imediato.
//Em todos os comportamentos, o poder heróico só é utilizado se após tentar baixar as cartas da mão sobrar 2 ou mais de mana. Além disso, ele é sempre usado no herói inimigo.
//A lógica para alternar entre os comportamentos é simples, porém eficaz. Primeiro verifica-se se é possível burstar o oponente no turno atual. Caso seja possível, utiliza-se o comportamento burstar e caso contrário verifica-se a mana atual do jogador. Se a mana for menor do que 6, deve-se usar o comportamento curva de mana, caso contrário verifica-se se a vida do inimigo é maior que a minha vida e se a minha mana é menor do que 10. Caso alguma dessas condições seja satisfeita, usa-se o comportamento agressivo e em último caso, usa-se o comportamento de controle.

import java.util.*;

/**
 * Created by rafael on 19/04/17.
 */
public class JogadorRA186145 extends Jogador {

    private final int QUANTIA_INIMAGINAVEL_DE_MANA = 1000;
    private ArrayList<CartaLacaio> lacaios;
    private ArrayList<CartaLacaio> lacaiosOponente;
    private int minhaMana, minhaVida, vidaInimigo, numLacaios, numCartasOponente;

    public JogadorRA186145(ArrayList<Carta> maoInicial, boolean primeiro) {
        primeiroJogador = primeiro;

        mao = maoInicial;
        lacaios = new ArrayList<CartaLacaio>();
        lacaiosOponente = new ArrayList<CartaLacaio>();

        // Mensagens de depuração:
        System.out.println("*Classe JogadorRA186145* Sou o " + (primeiro ? "primeiro" : "segundo") + " jogador (classe: JogadorAleatorio)");
        System.out.println("Mao inicial:");
        for (int i = 0; i < mao.size(); i++)
            System.out.println("ID " + mao.get(i).getID() + ": " + mao.get(i));
    }

    @Override
    @SuppressWarnings("Duplicates")
    public ArrayList<Jogada> processarTurno(Mesa mesa, Carta cartaComprada, ArrayList<Jogada> arrayList) {

        if (cartaComprada != null)
            mao.add(cartaComprada);
        if (primeiroJogador) {
            minhaMana = mesa.getManaJog1();
            minhaVida = mesa.getVidaHeroi1();
            vidaInimigo = mesa.getVidaHeroi2();
            lacaios = mesa.getLacaiosJog1();
            lacaiosOponente = mesa.getLacaiosJog2();
            numCartasOponente = mesa.getNumCartasJog2();
            //System.out.println("--------------------------------- Começo de turno pro jogador1");
        } else {
            minhaMana = mesa.getManaJog2();
            minhaVida = mesa.getVidaHeroi2();
            vidaInimigo = mesa.getVidaHeroi1();
            lacaios = mesa.getLacaiosJog2();
            lacaiosOponente = mesa.getLacaiosJog1();
            numCartasOponente = mesa.getNumCartasJog1();
            //System.out.println("--------------------------------- Começo de turno pro jogador2");
        }

        numLacaios = lacaios.size();

        if (canBurst()) {
            return jogarModoBurst(new ArrayList<>());
        } else if (minhaMana < 6) {
            return jogarModoCurvaDeMana(new ArrayList<>());
        } else if (vidaInimigo > minhaVida || minhaMana < 10){
            return jogarModoAgressivo(new ArrayList<>());
        } else {
            return jogarModoControle(new ArrayList<>());
        }
    }

    private ArrayList<Jogada> jogarModoAgressivo(ArrayList<Jogada> minhasJogadas) {

        //Verifica se há uma CartaMagia do tipo AREA para baixar e se ela irá matar pelo menos um lacaio inimigo.
        for (int i = 0; i < mao.size(); i++) {
            Carta card = mao.get(i);
            if (card instanceof CartaMagia && minhaMana >= card.getMana()) {
                CartaMagia cartaMagia = (CartaMagia) card;
                if (cartaMagia.getMagiaTipo() == TipoMagia.AREA) {
                    if (isMagiaAreaDamageEfficient(cartaMagia)) {
                        playMagiaArea(cartaMagia, minhasJogadas);
                        i--;
                    }
                }
            }
        }

        //Baixa uma carta magia do tipo BUFF se houver, usando-a no lacaio de maior ataque/vidaAtual.
        for (int i = 0; i < mao.size(); i++) {
            Carta card = mao.get(i);
            if (card instanceof CartaMagia && minhaMana >= card.getMana()) {
                CartaMagia cartaMagia = (CartaMagia) card;
                if (cartaMagia.getMagiaTipo() == TipoMagia.BUFF && !lacaios.isEmpty()) {
                    playMagiaBuff(cartaMagia, minhasJogadas);
                    i--;
                }
            }
        }

        //Baixa os lacaios em ordem de maior ataque e mana para menor.
        ArrayList<CartaLacaio> lacaiosList = new ArrayList<>();
        for (int i = 0; i < mao.size(); i++) {
            Carta carta = mao.get(i);
            if (carta instanceof CartaLacaio) {
                lacaiosList.add((CartaLacaio) carta);
            }
        }

        lacaiosList.sort(new AtaqueVidaAtualLacaioComparator());

        for (int i = 0; i < lacaiosList.size(); i++) {
            CartaLacaio cartaLacaio = lacaiosList.get(i);
            if (cartaLacaio.getMana() <= minhaMana && numLacaios < 7) {
                playLacaio(cartaLacaio, minhasJogadas);
                numLacaios++;
            }
        }

        //Ataca o herói inimigo com as magias de ALVO.
        for (int i = 0; i < mao.size(); i++) {
            Carta carta = mao.get(i);
            if (carta instanceof CartaMagia && carta.getMana() <= minhaMana) {
                CartaMagia cartaMagia = (CartaMagia) carta;
                if (cartaMagia.getMagiaTipo() == TipoMagia.ALVO) {
                    playMagiaAlvo(cartaMagia, minhasJogadas, true, false);
                    i--;
                }
            }
        }

        //Usa o poder de herói no herói inimigo se houver mana restante
        usarPoder(minhasJogadas);

        for (Carta currentLacaio : lacaios) {
            Jogada lac = new Jogada(TipoJogada.ATAQUE, currentLacaio, null);
            minhasJogadas.add(lac);
        }

        return minhasJogadas;
    }

    private ArrayList<Jogada> jogarModoControle(ArrayList<Jogada> minhasJogadas) {

        //Aplica magia de dano em AREA somente se houver 2 ou mais lacaios oponentes na mesa
        for (int i = 0; i < mao.size(); i++) {
            Carta card = mao.get(i);
            if (card instanceof CartaMagia && minhaMana >= card.getMana()) {
                CartaMagia cartaMagia = (CartaMagia) card;
                if (cartaMagia.getMagiaTipo() == TipoMagia.AREA && lacaiosOponente.size() >= 2) {
                    playMagiaArea(cartaMagia, minhasJogadas);
                    i--;
                }
            }
        }


        //Usa magias de dano em alvo caso haja um oponente favorável.
        for (int i = 0; i < mao.size(); i++) {
            Carta card = mao.get(i);
            if (card instanceof CartaMagia && minhaMana >= card.getMana()) {
                CartaMagia cartaMagia = (CartaMagia) card;
                if (cartaMagia.getMagiaTipo() == TipoMagia.ALVO) {
                    if (mostFavorableOpponent(cartaMagia) != null) {
                        playMagiaAlvo(cartaMagia, minhasJogadas, false, true);
                        i--;
                    }
                }
            }
        }


        //Prioriza baixar os lacaios que possuem uma vida mais alta.
        ArrayList<CartaLacaio> listaLacaiosParaBaixar = new ArrayList<>();
        for (Carta card : mao) {
            if (card instanceof CartaLacaio && card.getMana() <= minhaMana) {
                listaLacaiosParaBaixar.add((CartaLacaio) card);
            }
        }

        listaLacaiosParaBaixar.sort(new VidaMaximaAtaqueLacaioComparator());

        for (int i = 0; i < listaLacaiosParaBaixar.size(); i++) {
            CartaLacaio cartaLacaio = listaLacaiosParaBaixar.get(i);
            if (cartaLacaio.getMana() <= minhaMana && numLacaios < 7) {
                playLacaio(cartaLacaio, minhasJogadas);
                numLacaios++;
            }
        }


        //Baixa cartas do tiop BUFF caso haja mana restante e usa-as nos lacaios com maior ataque.
        for (int i = 0; i < mao.size(); i++) {
            Carta card = mao.get(i);
            if (card instanceof CartaMagia && minhaMana >= card.getMana()) {
                CartaMagia cartaMagia = (CartaMagia) card;
                if (cartaMagia.getMagiaTipo() == TipoMagia.BUFF && !lacaios.isEmpty()) {
                    playMagiaBuff(cartaMagia, minhasJogadas);
                    i--;
                }
            }
        }

        //Caso a carta magia de alvo nao tenha encontrado um oponente favoravel e ainda restar mana,
        //deve-se usá-la no herói inimigo.
        for (int i = 0; i < mao.size(); i++) {
            Carta card = mao.get(i);
            if (card instanceof CartaMagia && minhaMana >= card.getMana()) {
                CartaMagia cartaMagia = (CartaMagia) card;
                if (cartaMagia.getMagiaTipo() == TipoMagia.ALVO) {
                        playMagiaAlvo(cartaMagia, minhasJogadas, false, true);
                        i--;

                }
            }
        }

        //Usa o poder de herói no herói inimigo se houver mana restante
        usarPoder(minhasJogadas);

        //Procura um oponente favorável para atacar com os lacaios e caso não haja, ataca o herói.
        lacaios.sort(new AtaqueVidaAtualLacaioComparator());
        Collections.reverse(lacaios);
        for (CartaLacaio lacaio : lacaios) {
            CartaLacaio lacaioOponente = mostFavorableOpponent(lacaio);
            Jogada ataque = new Jogada(TipoJogada.ATAQUE, lacaio, lacaioOponente);
            minhasJogadas.add(ataque);
        }

        return minhasJogadas;
    }

    private ArrayList<Jogada> jogarModoCurvaDeMana(ArrayList<Jogada> minhasJogadas) {

        List<List<Carta>> allCardCombinations = new LinkedList<>();

        //Encontra todas as combinacoes possiveis de cartas.
        for (int i = 1; i <= mao.size(); i++)
            allCardCombinations.addAll(encontrarCombinacoes(mao, i));

        //Atribui a cada carta magia na mao, um boolean que diz se há ou não oponentes em que compensa usá-la
        HashMap<CartaMagia, Boolean> magiaBooleanHashMap = new HashMap<>();
        initializeHashMapCartas(magiaBooleanHashMap);

        removerCombinacoesInvalidas(allCardCombinations, magiaBooleanHashMap);

        //Usa as cartas de forma análoga ao comportamento controle.
        if (!allCardCombinations.isEmpty()) {
            List<List<Carta>> melhoresCombinacoesCartas;

            //Adiciona ao array "melhoresCombinacoesCartas", todas as combinacoes que têm
            //um custo de mana igual ao da combinação com o menor custo de mana.
            melhoresCombinacoesCartas = filtrarCombinacoesMana(allCardCombinations);

            //Ordena as combinações de melhor para pior de acordo com os critérios especificados pelo enunciado.
            melhoresCombinacoesCartas.sort(new CardListComparator());

            ArrayList<Carta> cardsToPlay = new ArrayList<>(melhoresCombinacoesCartas.get(0));

            for (Carta card : cardsToPlay) {
                if (card instanceof CartaLacaio) {
                    CartaLacaio lacaio = (CartaLacaio) card;
                    playLacaio(lacaio, minhasJogadas);
                } else if (card instanceof CartaMagia) {
                    CartaMagia cartaMagia = (CartaMagia) card;
                    switch (cartaMagia.getMagiaTipo()) {
                        case AREA:
                            playMagiaArea(cartaMagia, minhasJogadas);
                            break;
                        case ALVO:
                            playMagiaAlvo(cartaMagia, minhasJogadas, false, false);
                            break;
                        case BUFF:
                            playMagiaBuff(cartaMagia, minhasJogadas);
                            break;
                    }
                }
            }
        }

        //Usa o poder de herói no herói inimigo se houver mana restante
        usarPoder(minhasJogadas);

        //Usa os lacaios de forma análoga ao comportamento controle.
        lacaios.sort(new AtaqueVidaAtualLacaioComparator());
        Collections.reverse(lacaios);
        for (CartaLacaio lacaio : lacaios) {
            CartaLacaio lacaioOponente = mostFavorableOpponent(lacaio);
            Jogada ataque = new Jogada (TipoJogada.ATAQUE, lacaio, lacaioOponente);
            minhasJogadas.add(ataque);
        }

        return minhasJogadas;
    }

    private ArrayList<Jogada> jogarModoBurst (ArrayList<Jogada> minhasJogadas) {

        ArrayList<CartaMagia> magiaList = new ArrayList<>();
        for (int i = 0; i < mao.size(); i++) {
            Carta carta = mao.get(i);
            if (carta instanceof CartaMagia) {
                CartaMagia cartaMagia = (CartaMagia) carta;
                if (cartaMagia.getMagiaTipo() != TipoMagia.BUFF) {
                    magiaList.add((CartaMagia) carta);
                }
            }
        }

        magiaList.sort(new DanoMagiaComparator());

        for (int i = 0; i < magiaList.size(); i++) {
            CartaMagia cartaMagia = magiaList.get(i);
            if (cartaMagia.getMana() <= minhaMana) {
                minhaMana-=cartaMagia.getMana();
                mao.remove(cartaMagia);
                Jogada mag = new Jogada(TipoJogada.MAGIA, cartaMagia, null);
                minhasJogadas.add(mag);
            }
        }

        for (CartaLacaio lacaio : lacaios) {
            Jogada ataque = new Jogada (TipoJogada.ATAQUE, lacaio, null);
            minhasJogadas.add(ataque);
        }

        if (minhaMana >= 2) {
            Jogada poder = new Jogada(TipoJogada.PODER, null, null);
            minhasJogadas.add(poder);
        }

        return minhasJogadas;
    }


    /* Métodos de uso generalizado */

    //Adiciona um lacaio às jogadas.
    private void playLacaio (CartaLacaio lacaio, ArrayList<Jogada> minhasJogadas) {
        Jogada lac = new Jogada(TipoJogada.LACAIO, lacaio, null);
        minhasJogadas.add(lac);
        minhaMana-=lacaio.getMana();
        mao.remove(lacaio);
    }

    //Adiciona uma magia do tipo AREA às jogadas.
    private void playMagiaArea (CartaMagia cartaMagia, ArrayList<Jogada> minhasJogadas) {
        Jogada mag = new Jogada(TipoJogada.MAGIA, cartaMagia, null);
        minhasJogadas.add(mag);
        minhaMana-=cartaMagia.getMana();
        mao.remove(cartaMagia);
        aplicarMagiaAreaOponentes(cartaMagia);
    }

    //Adiciona uma magia do tipo ALVO às jogadas.
    private void playMagiaAlvo (CartaMagia cartaMagia, ArrayList<Jogada> minhasJogadas, boolean modoAgressivo, boolean modoControle) {
        Jogada mag;
        if (modoAgressivo) {
            mag = new Jogada(TipoJogada.MAGIA, cartaMagia, null);

        }
        else {
            if (modoControle) {
                CartaLacaio lacaioOponente = mostFavorableOpponent(cartaMagia);
                mag = new Jogada(TipoJogada.MAGIA, cartaMagia, lacaioOponente);
            }
            else {
                CartaLacaio lacaioOponente = mostManaFavorableOpponent(cartaMagia);
                mag = new Jogada(TipoJogada.MAGIA, cartaMagia, lacaioOponente);
            }
        }
        minhasJogadas.add(mag);
        minhaMana-=cartaMagia.getMana();
        mao.remove(cartaMagia);
    }

    //Adiciona uma magia do tipo BUFF às jogadas.
    private void playMagiaBuff (CartaMagia cartaMagia, ArrayList<Jogada> minhasJogadas) {
        lacaios.sort(new AtaqueVidaAtualLacaioComparator());
        CartaLacaio cartaLacaio = lacaios.get(0);
        Jogada mag = new Jogada(TipoJogada.MAGIA, cartaMagia, cartaLacaio);
        cartaLacaio.setAtaque(cartaLacaio.getAtaque() + cartaMagia.getMagiaDano());
        cartaLacaio.setVidaAtual(cartaLacaio.getVidaAtual() + cartaMagia.getMagiaDano());
        minhasJogadas.add(mag);
        minhaMana -= cartaMagia.getMana();
        mao.remove(cartaMagia);
    }

    //Aplica o dano da magia em área a todos os oponentes
    private void aplicarMagiaAreaOponentes (CartaMagia cartaMagia) {
        for (int j = 0; j < lacaiosOponente.size(); j++) {
            CartaLacaio lacOponente = lacaiosOponente.get(j);
            if (lacOponente.getVidaAtual() - cartaMagia.getMagiaDano() > 0) {
                lacOponente.setVidaAtual(lacOponente.getVidaAtual() - cartaMagia.getMagiaDano());
            }
            else {
                lacaiosOponente.remove(j);
                j--;
            }
        }
    }

    //Usa o poder heróico
    private void usarPoder(ArrayList<Jogada> minhasJogadas) {
        if (minhaMana >= 2) {
            Jogada poder = new Jogada (TipoJogada.PODER, null, null);
            minhasJogadas.add(poder);
        }
    }

    //Ordena os lacaios de maior para menor usando o critério de ataque e vidaAtual
    class AtaqueVidaAtualLacaioComparator implements Comparator<CartaLacaio> {
        public int compare(CartaLacaio c1, CartaLacaio c2)
        {
            if (c1.getAtaque() != c2.getAtaque()) return c2.getAtaque() - c1.getAtaque();
            else return c2.getVidaAtual() - c1.getVidaAtual();
        }
    }

    //Ordena os lacaios de maior para menor usando o critério de vidaMaxima e ataque
    class VidaMaximaAtaqueLacaioComparator implements  Comparator<CartaLacaio> {
        public int compare(CartaLacaio c1, CartaLacaio c2) {
            if (c1.getVidaMaxima() != c2.getVidaMaxima()) return c2.getVidaMaxima() - c1.getVidaMaxima();
            else return c2.getAtaque() - c1.getAtaque();
        }
    }

    /*Métodos de uso particular do comportamento AGRESSIVO */

    //Verifica se a magia de area faz um uso eficiente do seu dano.
    private boolean isMagiaAreaDamageEfficient(CartaMagia cartaMagia) {
        int lacaiosKilled = 0;
        for (int i = 0; i < lacaiosOponente.size(); i++) {
            CartaLacaio lacaio = lacaiosOponente.get(i);
            if (cartaMagia.getMagiaDano() > lacaio.getVidaAtual()) {
                lacaiosKilled++;
            }
        }

        if (lacaiosKilled > 0) return true;
        return false;
    }

    /*Métodos de uso particular do comportamento CONTROLE */

    //Retorna o lacaio oponente mais favoravel para atacar ou null se nao houver.
    private CartaLacaio mostFavorableOpponent (CartaMagia cartaMagia) {
        ArrayList<CartaLacaio> favorableOpponents = new ArrayList<>();
        for (CartaLacaio lacaioOponente: lacaiosOponente) {
            int willKillDiff = cartaMagia.getMagiaDano() - lacaioOponente.getVidaAtual();
            if (willKillDiff >= 0 && willKillDiff <= 1) {
                favorableOpponents.add(lacaioOponente);
            }
        }


        if (favorableOpponents.size() != 0) {
            favorableOpponents.sort(new AtaqueVidaAtualLacaioComparator());
            lacaiosOponente.remove(favorableOpponents.get(0));
            return favorableOpponents.get(0);

        }
        else {
            return null;
        }
    }

    //Retorna o lacaio oponente mais favoravel para atacar ou null se nao houver.
    private CartaLacaio mostFavorableOpponent (CartaLacaio lacaio) {
        ArrayList<CartaLacaio> favorableOpponents = new ArrayList<>();
        for (CartaLacaio lacaioOponente: lacaiosOponente) {
            //Lacaio x sobreviverá e lacaio y não
            //Ambos os lacaios morrerão mas o custo do lacaio y eh maior que o do x
            //ambos lacaios morrerão, porém lacaio x ja estava danificado e possui menos vida que o y
            if ((lacaio.getAtaque() > lacaioOponente.getVidaAtual()
                    && lacaio.getVidaAtual() > lacaioOponente.getAtaque())
                    || (lacaio.getAtaque() > lacaioOponente.getVidaAtual()
                    && lacaio.getMana() < lacaioOponente.getMana())
                    || (lacaio.getAtaque() > lacaioOponente.getVidaAtual()
                    && lacaio.getVidaAtual() < lacaio.getVidaMaxima()
                    && lacaio.getVidaAtual() < lacaioOponente.getVidaAtual()))
            {
                favorableOpponents.add(lacaioOponente);
            }
        }

        //Caso a lista de oponentes favoraveis tenha mais que um lacaio, deve-se escolher o mais forte para eliminar
        //usando o criterio de maior ataque seguido por maior mana.
        if (favorableOpponents.size() != 0) {
            favorableOpponents.sort(new AtaqueVidaAtualLacaioComparator());
            lacaiosOponente.remove(favorableOpponents.get(0));
            return favorableOpponents.get(0);
        }
        else {
            return null;
        }
    }

    /* Métodos exclusivos do comportamento CURVA DE MANA*/

    //Ordena as listas de cartas para ver qual combinaçao é a melhor, priorizando em ordem o número de lacaios, ataque e depois vida.
    static class CardListComparator implements  Comparator<List<Carta>> {
        public int compare(List<Carta> list1, List<Carta> list2)
        {
            int totalDano1 = 0, numLacaios1 = 0, totalVidaMaxima1 = 0, totalLacaioDano1 = 0;
            int totalDano2 = 0, numLacaios2 = 0, totalVidaMaxima2 = 0, totalLacaioDano2 = 0;

            for (Carta card: list1) {
                if (card instanceof  CartaLacaio) {
                    CartaLacaio lacaio = (CartaLacaio) card;
                    numLacaios1++;
                    totalLacaioDano1+=lacaio.getAtaque();
                    totalDano1+=lacaio.getAtaque();
                    totalVidaMaxima1+=lacaio.getVidaMaxima();
                }
                else if (card instanceof CartaMagia) {
                    CartaMagia magia = (CartaMagia) card;
                    totalDano1+=magia.getMagiaDano();
                }
            }

            for (Carta card: list2) {
                if (card instanceof  CartaLacaio) {
                    CartaLacaio lacaio = (CartaLacaio) card;
                    numLacaios2++;
                    totalLacaioDano2+=lacaio.getAtaque();
                    totalDano2+=lacaio.getAtaque();
                    totalVidaMaxima2+=lacaio.getVidaMaxima();
                }
                else if (card instanceof CartaMagia) {
                    CartaMagia magia = (CartaMagia) card;
                    totalDano2+=magia.getMagiaDano();
                }
            }

            if (numLacaios1 != numLacaios2) {
                if (numLacaios1 == 0 || numLacaios2 == 0) {
                    return numLacaios2 - numLacaios1;
                }
                return totalLacaioDano2-totalLacaioDano1;
            }
            else if (totalDano1 != totalDano2) return totalDano2 - totalDano1;
            else return totalVidaMaxima2 - totalVidaMaxima1;
        }
    }

    //Remove as combinações que:
    // 1.Excedem o número de lacaios permitidos na mesa
    // 2.Excedem a mana disponível para a jogada
    // 3.Têm uma magia de BUFF mas nao há lacaios na mesa
    // 4.Possuem uma magia de AREA cuja aplicação não faz um uso eficiente da mana do jogador.
    // 5.Possuem magias de ALVO sem lacaios favoráveis para atacar.
    private void removerCombinacoesInvalidas (List<List<Carta>> powerSet, HashMap<CartaMagia, Boolean> magiaBooleanHashMap) {
        int totalMana;
        int additionalLacaios;
        boolean hasBuff;
        boolean useMagia;
        for (int i = 0; i < powerSet.size(); i++) {
            List<Carta> jogada = powerSet.get(i);
            totalMana = 0;
            additionalLacaios = 0;
            hasBuff = false;
            useMagia = true;
            for (int j = 0; j < jogada.size(); j++) {
                if (jogada.get(j) instanceof CartaLacaio) {
                    additionalLacaios++;
                }
                else if (jogada.get(j) instanceof  CartaMagia) {
                    CartaMagia cartaMagia = (CartaMagia) jogada.get(j);
                    if (cartaMagia.getMagiaTipo() == TipoMagia.BUFF) hasBuff = true;
                    else {
                        useMagia = magiaBooleanHashMap.get(cartaMagia);
                        if (!useMagia) break;
                    }
                }
                totalMana += jogada.get(j).getMana();
            }
            if (totalMana > minhaMana ||
                    (numLacaios + additionalLacaios) > 7 ||
                    (hasBuff && lacaios.isEmpty()) ||
                    !useMagia) {
                powerSet.remove(i);
                i--;
            }
        }
    }

    //Retorna uma lista com todas as combinacoes de cartas que utilizam a maior quantia de mana possível
    private List<List<Carta>> filtrarCombinacoesMana (List<List<Carta>> powerSet) {

        ArrayList<Integer> manaDiffList = new ArrayList<>();
        int minManaDiff = QUANTIA_INIMAGINAVEL_DE_MANA;
        int totalMana, currentManaDiff;

        for (List<Carta> listCards: powerSet) {
            totalMana = 0;
            for (Carta card: listCards)
                totalMana += card.getMana();
            currentManaDiff = minhaMana - totalMana;
            if (currentManaDiff < minManaDiff)
                minManaDiff = currentManaDiff;
            manaDiffList.add(currentManaDiff);
        }

        List<List<Carta>> melhoresCombinacoesCartas = new ArrayList<>();
        for (int i = 0; i < manaDiffList.size(); i++) {
            if (manaDiffList.get(i) == minManaDiff) melhoresCombinacoesCartas.add(powerSet.get(i));
        }

        return melhoresCombinacoesCartas;
    }

    //Método que retorna todas as combinações possíveis de cartas da mao.
    private List<List<Carta>> encontrarCombinacoes (List<Carta> maoInicial, int size) {

        if (size == 0) {
            return Collections.singletonList(Collections.<Carta> emptyList());
        }

        if (maoInicial.isEmpty()) {
            return Collections.emptyList();
        }

        List<List<Carta>> combinacoes = new ArrayList<>();

        Carta primeiraCarta = maoInicial.iterator().next();

        List<Carta> subLista = new ArrayList<>(maoInicial);
        subLista.remove(primeiraCarta);

        List<List<Carta>> subCombinacao = encontrarCombinacoes(subLista, size - 1);

        if (subCombinacao != null) {
            for (List<Carta> listaAtual : subCombinacao) {
                ArrayList<Carta> novaLista = new ArrayList<>(listaAtual);
                novaLista.add(0, primeiraCarta);
                combinacoes.add(novaLista);
            }
        }

        combinacoes.addAll(encontrarCombinacoes(subLista, size));

        return combinacoes;
    }

    //Verifica se a magia de area faz um uso eficiente da mana do jogador.
    private boolean isMagiaAreaManaEfficient (CartaMagia cartaMagia) {
        int totalMana = 0;
        for (CartaLacaio lacaio: lacaiosOponente) {
            totalMana+=lacaio.getMana();
        }

        return cartaMagia.getMana() < totalMana;
    }

    //Retorna o oponente mais favorável de acordo com o critério de mana.
    private CartaLacaio mostManaFavorableOpponent(CartaMagia cartaMagia) {
        ArrayList<CartaLacaio> favorableOpponents = new ArrayList<>();
        for (CartaLacaio lacaioOponente : lacaiosOponente) {
            if (cartaMagia.getMagiaDano() > lacaioOponente.getVidaAtual()
                    && cartaMagia.getMana() < lacaioOponente.getMana()) {
                favorableOpponents.add(lacaioOponente);
            }
        }

        if (favorableOpponents.size() != 0) {
            favorableOpponents.sort(new AtaqueVidaAtualLacaioComparator());
            lacaiosOponente.remove(favorableOpponents.get(0));
            return favorableOpponents.get(0);
        }
        else {
            return null;
        }
    }

    //Verifica se a carta tem um oponente de mana favorável.
    private boolean hasManaFavorableOpponentMana (CartaMagia cartaMagia) {
        for (CartaLacaio lacaioOponente : lacaiosOponente) {
            if (cartaMagia.getMagiaDano() > lacaioOponente.getVidaAtual()
                    && cartaMagia.getMana() < lacaioOponente.getMana()) {
                return true;
            }
        }
        return false;
    }

    //Inicializa o hashMap que mapeia as cartas magia de ALVO com um boolean indicando se ha um oponente favoravel de mana.
    private void initializeHashMapCartas (HashMap<CartaMagia, Boolean> cartaMagiaBooleanHashMap) {
        for (Carta card: mao) {
            if (card instanceof CartaMagia) {
                boolean useMagia;
                CartaMagia cartaMagia = (CartaMagia) card;
                if (cartaMagia.getMagiaTipo() == TipoMagia.AREA) {
                    useMagia = isMagiaAreaManaEfficient(cartaMagia);
                    cartaMagiaBooleanHashMap.put(cartaMagia, new Boolean(useMagia));
                }
                else if (cartaMagia.getMagiaTipo() == TipoMagia.ALVO) {
                    useMagia = hasManaFavorableOpponentMana(cartaMagia);
                    cartaMagiaBooleanHashMap.put(cartaMagia, new Boolean(useMagia));
                }

            }
        }
    }

    /*Métodos exclusivos do comportamento BURST*/

    //Verifica se é possível burstar o herói inimigo nessa rodada.
    private boolean canBurst() {
        int totalDamage = 0;
        int minhaManaCopy = minhaMana;

        ArrayList<CartaMagia> magiaList = new ArrayList<>();
        for (int i = 0; i < mao.size(); i++) {
            Carta carta = mao.get(i);
            if (carta instanceof CartaMagia) {
                CartaMagia cartaMagia = (CartaMagia) carta;
                if (cartaMagia.getMagiaTipo() != TipoMagia.BUFF) {
                    magiaList.add((CartaMagia) carta);
                }
            }
        }

        magiaList.sort(new DanoMagiaComparator());

        for (int i = 0; i < magiaList.size(); i++) {
            CartaMagia cartaMagia = magiaList.get(i);
            if (cartaMagia.getMana() <= minhaManaCopy) {
                minhaManaCopy-=cartaMagia.getMana();
                totalDamage+=cartaMagia.getMagiaDano();
            }
        }

        for (CartaLacaio lacaio : lacaios) {
            totalDamage+=lacaio.getAtaque();
        }

        if (minhaManaCopy >= 2) totalDamage+=1;

        return (totalDamage >= minhaVida);
    }

    //Ordena de maior para menor as magias na mao do jogador em relacao ao dano e o custo de mana delas.
    class DanoMagiaComparator implements  Comparator<CartaMagia> {
        public int compare(CartaMagia c1, CartaMagia c2) {
            if (c1.getMagiaDano() != c2.getMagiaDano()) return c2.getMagiaDano() - c1.getMagiaDano();
            else return c1.getMana() - c2.getMana();
        }
    }
}
