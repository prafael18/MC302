//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Motor {
    private Jogador jogador1;
    private Jogador jogador2;
    private Baralho deck1;
    private Baralho deck2;
    private ArrayList<Carta> Mao1;
    private ArrayList<Carta> Mao2;
    private ArrayList<CartaLacaio> Lacaios1;
    private ArrayList<CartaLacaio> Lacaios2;
    private Baralho deck;
    private ArrayList<Carta> mao;
    private ArrayList<CartaLacaio> lacaios;
    private ArrayList<CartaLacaio> lacaiosOponente;
    static final int cartasIniJogador1 = 3;
    static final int cartasIniJogador2 = 4;
    private Mesa mesa;
    private int vidaHeroi1;
    private int vidaHeroi2;
    private int manaHeroi1;
    private int manaHeroi2;
    private int maxManaHeroi1;
    private int maxManaHeroi2;
    private int nCartasHeroi1;
    private int nCartasHeroi2;
    private int turno;
    private int verbose;
    private int timeLimit;
    public int erroJogador1;
    public int erroJogador2;
    public boolean limiteTempoJogador1;
    public boolean limiteTempoJogador2;
    private String nameclasse1;
    private String nameclasse2;
    private boolean poderHeroicoUsado;
    private HashSet<Integer> lacaiosAtacaramID;
    private InterfaceGrafica gui;
    ArrayList<Object> listaAcoesGUI;

    public Motor(Baralho deck1, Baralho deck2, ArrayList<Carta> mao1, ArrayList<Carta> mao2, Jogador jogador1, Jogador jogador2, int verbose, int tempoLimite, String classe1, String classe2) {
        this.deck1 = deck1;
        this.deck2 = deck2;
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.Mao1 = mao1;
        this.Mao2 = mao2;
        this.verbose = verbose;
        this.timeLimit = tempoLimite;
        this.erroJogador1 = 0;
        this.erroJogador2 = 0;
        this.limiteTempoJogador1 = false;
        this.limiteTempoJogador2 = false;
        this.nameclasse1 = classe1;
        this.nameclasse2 = classe2;
        this.gui = null;
        this.listaAcoesGUI = new ArrayList();
        if(verbose != 0) {
            if(this.gui != null) {
                System.out.println("Motor Construtor: A gui está ativa.");
            } else {
                System.out.println("Motor Construtor: A gui está inativa.");
            }

            this.imprimir("*** Iniciando partida LaMa ***");
        }

    }

    public Motor(Baralho deck1, Baralho deck2, ArrayList<Carta> mao1, ArrayList<Carta> mao2, Jogador jogador1, Jogador jogador2, int verbose, int tempoLimite, String classe1, String classe2, InterfaceGrafica gui) {
        this.deck1 = deck1;
        this.deck2 = deck2;
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.Mao1 = mao1;
        this.Mao2 = mao2;
        this.verbose = verbose;
        this.timeLimit = tempoLimite;
        this.erroJogador1 = 0;
        this.erroJogador2 = 0;
        this.limiteTempoJogador1 = false;
        this.limiteTempoJogador2 = false;
        this.nameclasse1 = classe1;
        this.nameclasse2 = classe2;
        this.gui = gui;
        if(verbose != 0) {
            if(gui != null) {
                System.out.println("Motor Construtor: A gui está ativa.");
            } else {
                System.out.println("Motor Construtor: A gui está inativa.");
            }
        }

        this.listaAcoesGUI = new ArrayList();
        this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.INICIO_PARTIDA, (Carta)null, 0, true));
        this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_HP_HEROI, (Carta)null, 30, true));
        this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, (Carta)null, 0, true));
        this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MAXMANA_HEROI, (Carta)null, 0, true));
        this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_NUM_BARALHO, (Carta)null, 27, true));
        this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_HP_HEROI, (Carta)null, 30, false));
        this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, (Carta)null, 0, false));
        this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MAXMANA_HEROI, (Carta)null, 0, false));
        this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_NUM_BARALHO, (Carta)null, 26, false));
        Iterator var13 = mao1.iterator();

        Carta card;
        while(var13.hasNext()) {
            card = (Carta)var13.next();
            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.ADICIONAR_MAO, (Carta)UnoptimizedDeepCopy.copy(card), 0, true));
        }

        var13 = mao2.iterator();

        while(var13.hasNext()) {
            card = (Carta)var13.next();
            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.ADICIONAR_MAO, (Carta)UnoptimizedDeepCopy.copy(card), 0, false));
        }

        this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_TURNO, (Carta)null, 0, true));
        if(verbose != 0) {
            this.imprimir("*** Iniciando partida LaMa ***");
        }

        if(gui != null) {
            gui.processarAcoesGUI(this.listaAcoesGUI);
        }

    }

    public int getVerbose() {
        return this.verbose;
    }

    public void setVerbose(int verbose) {
        this.verbose = verbose;
    }

    void imprimir(String texto) {
        System.out.println(texto);
        if(this.gui != null) {
            this.listaAcoesGUI.add(new String(texto));
        }

    }

    public boolean executarPartida() {
        this.vidaHeroi1 = this.vidaHeroi2 = 30;
        this.maxManaHeroi1 = this.maxManaHeroi2 = 0;
        this.nCartasHeroi1 = 3;
        this.nCartasHeroi2 = 4;
        this.Lacaios1 = new ArrayList();
        this.Lacaios2 = new ArrayList();
        ArrayList<Jogada> movimentos = new ArrayList();
        int noCardDmgCounter1 = 1;
        int noCardDmgCounter2 = 1;
        this.turno = 1;
        this.erroJogador1 = 0;
        this.erroJogador2 = 0;
        this.limiteTempoJogador1 = false;
        this.limiteTempoJogador2 = false;
        this.manaHeroi1 = 0;
        this.manaHeroi2 = 0;

        for(int k = 0; k < 60; ++k) {
            if(this.verbose != 0) {
                this.imprimir("\n=== TURNO " + this.turno + " ===\n");
            }

            this.deck = this.deck1;
            this.mao = this.Mao1;
            this.lacaios = this.Lacaios1;
            this.lacaiosOponente = this.Lacaios2;
            if(this.maxManaHeroi1 < 10) {
                ++this.maxManaHeroi1;
            }

            this.manaHeroi1 = this.maxManaHeroi1;
            ArrayList<CartaLacaio> lacaios1clone = (ArrayList)UnoptimizedDeepCopy.copy(this.Lacaios1);
            ArrayList<CartaLacaio> lacaios2clone = (ArrayList)UnoptimizedDeepCopy.copy(this.Lacaios2);
            this.mesa = new Mesa(lacaios1clone, lacaios2clone, this.vidaHeroi1, this.vidaHeroi2, this.nCartasHeroi1 + 1, this.nCartasHeroi2, this.maxManaHeroi1, this.maxManaHeroi2);
            this.listaAcoesGUI.clear();
            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.INICIO_TURNO, (Carta)null, k + 1, true));
            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, (Carta)null, this.manaHeroi1, true));
            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MAXMANA_HEROI, (Carta)null, this.mesa.getManaJog1(), true));
            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_NUM_BARALHO, (Carta)null, 27 - k > 0?27 - k:0, true));
            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, (Carta)null, this.manaHeroi2, false));
            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MAXMANA_HEROI, (Carta)null, this.mesa.getManaJog2(), false));
            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_NUM_BARALHO, (Carta)null, 26 - k > 0?26 - k:0, false));
            if(this.verbose != 0) {
                this.imprimir("\n----------------------- Começo de turno " + (k + 1) + " para Jogador 1:");
            }

            ArrayList<Jogada> cloneMovimentos1 = (ArrayList)UnoptimizedDeepCopy.copy(movimentos);
            long startTime = System.nanoTime();

            try {
                if(this.deck.getCartas().size() > 0) {
                    if(this.nCartasHeroi1 >= 7) {
                        movimentos = this.jogador1.processarTurno(this.mesa, (Carta)null, cloneMovimentos1);
                    } else {
                        Carta cartaComprada = this.comprarCarta(true);
                        Carta cartaComprada2 = (Carta)UnoptimizedDeepCopy.copy(cartaComprada);
                        movimentos = this.jogador1.processarTurno(this.mesa, cartaComprada, cloneMovimentos1);
                        this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.ADICIONAR_MAO, cartaComprada2, 0, true));
                    }

                    this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_NUM_BARALHO, (Carta)null, this.deck.getCartas().size(), true));
                } else {
                    this.vidaHeroi1 -= noCardDmgCounter1++;
                    this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_HP_HEROI, (Carta)null, this.vidaHeroi1, true));
                    if(this.vidaHeroi1 <= 0) {
                        if(this.verbose != 0) {
                            this.imprimir("\n\n*** ACABOU no TURNO " + this.turno + "***\n");
                        }

                        if(this.verbose != 0) {
                            this.imprimir("VidaFinal\nHeroi1: " + this.vidaHeroi1 + ". Heroi2:" + this.vidaHeroi2);
                        }

                        if(this.verbose != 0) {
                            this.imprimir("Vitoria do Jogador2 ! (classe: " + this.nameclasse2 + ")");
                        }

                        this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_PARTIDA, (Carta)null, 0, false));
                        if(this.gui != null) {
                            this.gui.processarAcoesGUI(this.listaAcoesGUI);
                        }

                        return false;
                    }

                    movimentos = this.jogador1.processarTurno(this.mesa, (Carta)null, cloneMovimentos1);
                }
            } catch (Exception var20) {
                this.imprimir("(Motor) Erro durante o turno do jogador1:\n:" + var20.getMessage());
                this.imprimir("Vitoria do Jogador2 por erros do outro jogador ! (classe: " + this.nameclasse2 + ")");
                this.imprimir("Erro print: " + var20);
                var20.printStackTrace();
                this.erroJogador1 = 1;
                this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_PARTIDA, (Carta)null, 0, false));
                if(this.gui != null) {
                    this.gui.processarAcoesGUI(this.listaAcoesGUI);
                }
                System.exit(1);

                return false;
            }

            long endTime = System.nanoTime();
            long totalTime = endTime - startTime;
            if(this.timeLimit > 0 && (double)totalTime > 3.0E8D) {
                this.imprimir("(Motor) Tempo excedido pelo Primeiro Jogador no metodo processarTurno(): Usou tempo de " + (double)totalTime / 1000000.0D + "ms. Limite: 100ms");
                this.imprimir("Vitoria do Jogador2 por erros do outro jogador ! (classe: " + this.nameclasse2 + ")");
                this.limiteTempoJogador1 = true;
                this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_PARTIDA, (Carta)null, 0, false));
                if(this.gui != null) {
                    this.gui.processarAcoesGUI(this.listaAcoesGUI);
                }

                return false;
            }

            if(this.verbose != 0) {
                this.imprimir("Tempo usado em processarTurno(): " + (double)totalTime / 1000000.0D + "ms");
            }

            try {
                this.poderHeroicoUsado = false;
                this.lacaiosAtacaramID = new HashSet();

                for(int i = 0; i < movimentos.size(); ++i) {
                    this.listaAcoesGUI.add(new Jogada((Jogada)movimentos.get(i)));
                    this.processarJogada((Jogada)movimentos.get(i), true);
                }

                if(this.manaHeroi1 < 0) {
                    throw new RuntimeException("Jogadas excederam a mana. Mana após jogadas:" + this.manaHeroi1);
                }
            } catch (Exception var22) {
                this.imprimir("(Motor) Erro durante jogadas do jogador1:\n:" + var22.getMessage());
                this.imprimir("Vitoria do Jogador2 por erros do outro jogador ! (classe: " + this.nameclasse2 + ")");
                this.imprimir("Erro print: " + var22);
                var22.printStackTrace();
                this.erroJogador1 = 2;
                this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_PARTIDA, (Carta)null, 0, false));
                if(this.gui != null) {
                    this.gui.processarAcoesGUI(this.listaAcoesGUI);
                }
                System.exit(1);

                return false;
            }

            if(this.vidaHeroi2 <= 0) {
                if(this.verbose != 0) {
                    this.imprimir("\n\n*** ACABOU no TURNO " + this.turno + "***\n");
                }

                if(this.verbose != 0) {
                    this.imprimir("VidaFinal\nHeroi1: " + this.vidaHeroi1 + ". Heroi2:" + this.vidaHeroi2);
                }

                if(this.verbose != 0) {
                    this.imprimir("Vitoria do Jogador1 ! (classe: " + this.nameclasse1 + ")");
                }

                this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_PARTIDA, (Carta)null, 0, true));
                if(this.gui != null) {
                    this.gui.processarAcoesGUI(this.listaAcoesGUI);
                }

                return true;
            }

            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_TURNO, (Carta)null, 0, true));
            if(this.gui != null) {
                this.gui.processarAcoesGUI(this.listaAcoesGUI);
            }

            this.listaAcoesGUI.clear();
            this.deck = this.deck2;
            this.mao = this.Mao2;
            this.lacaios = this.Lacaios2;
            this.lacaiosOponente = this.Lacaios1;
            if(this.turno == 1) {
                this.maxManaHeroi2 = 2;
            } else if(this.turno == 2) {
                this.maxManaHeroi2 = 2;
            } else if(this.maxManaHeroi2 < 10) {
                ++this.maxManaHeroi2;
            }

            this.manaHeroi2 = this.maxManaHeroi2;
            ArrayList<CartaLacaio> lacaios1clone2 = (ArrayList)UnoptimizedDeepCopy.copy(this.Lacaios1);
            ArrayList<CartaLacaio> lacaios2clone2 = (ArrayList)UnoptimizedDeepCopy.copy(this.Lacaios2);
            this.mesa = new Mesa(lacaios1clone2, lacaios2clone2, this.vidaHeroi1, this.vidaHeroi2, this.nCartasHeroi1, this.nCartasHeroi2 + 1, this.maxManaHeroi1, this.maxManaHeroi2);
            this.listaAcoesGUI.clear();
            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.INICIO_TURNO, (Carta)null, k + 1, false));
            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, (Carta)null, this.manaHeroi1, true));
            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MAXMANA_HEROI, (Carta)null, this.mesa.getManaJog1(), true));
            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_NUM_BARALHO, (Carta)null, 27 - k - 1 > 0?27 - k - 1:0, true));
            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, (Carta)null, this.manaHeroi2, false));
            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MAXMANA_HEROI, (Carta)null, this.mesa.getManaJog2(), false));
            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_NUM_BARALHO, (Carta)null, 26 - k > 0?26 - k:0, false));
            if(this.verbose != 0) {
                this.imprimir("\n\n----------------------- Começo de turno " + (k + 1) + " para Jogador 2:");
            }

            ArrayList<Jogada> cloneMovimentos2 = (ArrayList)UnoptimizedDeepCopy.copy(movimentos);
            startTime = System.nanoTime();

            try {
                if(this.deck.getCartas().size() > 0) {
                    if(this.nCartasHeroi2 >= 7) {
                        movimentos = this.jogador2.processarTurno(this.mesa, (Carta)null, cloneMovimentos2);
                    } else {
                        Carta cartaComprada = this.comprarCarta(false);
                        Carta cartaComprada2 = (Carta)UnoptimizedDeepCopy.copy(cartaComprada);
                        movimentos = this.jogador2.processarTurno(this.mesa, cartaComprada, cloneMovimentos2);
                        this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.ADICIONAR_MAO, cartaComprada2, 0, false));
                    }

                    this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_NUM_BARALHO, (Carta)null, this.deck.getCartas().size(), false));
                } else {
                    this.vidaHeroi2 -= noCardDmgCounter2++;
                    this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_HP_HEROI, (Carta)null, this.vidaHeroi2, false));
                    if(this.vidaHeroi2 <= 0) {
                        if(this.verbose != 0) {
                            this.imprimir("\n\n*** ACABOU no TURNO " + this.turno + "***\n");
                        }

                        if(this.verbose != 0) {
                            this.imprimir("VidaFinal\nHeroi1: " + this.vidaHeroi1 + ". Heroi2:" + this.vidaHeroi2);
                        }

                        this.imprimir("Vitoria do Jogador1 ! (classe: " + this.nameclasse1 + ")");
                        this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_PARTIDA, (Carta)null, 0, true));
                        if(this.gui != null) {
                            this.gui.processarAcoesGUI(this.listaAcoesGUI);
                        }

                        return true;
                    }

                    movimentos = this.jogador2.processarTurno(this.mesa, (Carta)null, cloneMovimentos2);
                }
            } catch (Exception var19) {
                this.imprimir("(Motor) Erro durante o turno do jogador2:\n:" + var19.getMessage());
                this.imprimir("Vitoria do Jogador1 por erros do outro jogador ! (classe: " + this.nameclasse1 + ")");
                this.imprimir("Erro print: " + var19);
                var19.printStackTrace();
                this.erroJogador2 = 1;
                this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_PARTIDA, (Carta)null, 0, true));
                if(this.gui != null) {
                    this.gui.processarAcoesGUI(this.listaAcoesGUI);
                }
                System.exit(1);
                return true;
            }

            endTime = System.nanoTime();
            totalTime = endTime - startTime;
            if(this.timeLimit > 0 && (double)totalTime > 3.0E8D) {
                this.imprimir("(Motor) Tempo excedido pelo Segundo Jogador no metodo processarTurno(): Usou tempo de " + (double)totalTime / 1000000.0D + "ms. Limite: 100ms");
                this.imprimir("Vitoria do Jogador1 por erros do outro jogador ! (classe: " + this.nameclasse1 + ")");
                this.limiteTempoJogador2 = true;
                this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_PARTIDA, (Carta)null, 0, true));
                if(this.gui != null) {
                    this.gui.processarAcoesGUI(this.listaAcoesGUI);
                }

                return true;
            }

            if(this.verbose != 0) {
                this.imprimir("Tempo usado em processarTurno(): " + (double)totalTime / 1000000.0D + "ms");
            }

            try {
                this.poderHeroicoUsado = false;
                this.lacaiosAtacaramID = new HashSet();

                for(int i = 0; i < movimentos.size(); ++i) {
                    this.listaAcoesGUI.add(new Jogada((Jogada)movimentos.get(i)));
                    this.processarJogada((Jogada)movimentos.get(i), false);
                }

                if(this.manaHeroi2 < 0) {
                    throw new RuntimeException("Jogadas excederam a mana. Mana após jogadas:" + this.manaHeroi2);
                }
            } catch (Exception var21) {
                this.imprimir("(Motor) Erro durante jogadas do jogador2:\n:" + var21.getMessage());
                this.imprimir("Vitoria do Jogador1 por erros do outro jogador ! (classe: " + this.nameclasse1 + ")");
                this.imprimir("Erro print: " + var21);
                var21.printStackTrace();
                this.erroJogador2 = 2;
                this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_PARTIDA, (Carta)null, 0, true));
                if(this.gui != null) {
                    this.gui.processarAcoesGUI(this.listaAcoesGUI);
                }
                System.exit(1);

                return true;
            }

            if(this.vidaHeroi1 <= 0) {
                if(this.verbose != 0) {
                    this.imprimir("\n\n*** ACABOU no TURNO " + this.turno + "***\n");
                }

                if(this.verbose != 0) {
                    this.imprimir("VidaFinal\nHeroi1: " + this.vidaHeroi1 + ". Heroi2:" + this.vidaHeroi2);
                }

                if(this.verbose != 0) {
                    this.imprimir("Vitoria do Jogador2 ! (classe: " + this.nameclasse2 + ")");
                }

                this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_PARTIDA, (Carta)null, 0, false));
                if(this.gui != null) {
                    this.gui.processarAcoesGUI(this.listaAcoesGUI);
                }

                return false;
            }

            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.FIM_TURNO, (Carta)null, 0, false));
            if(this.gui != null) {
                this.gui.processarAcoesGUI(this.listaAcoesGUI);
            }

            this.listaAcoesGUI.clear();
            ++this.turno;
        }

        if(this.verbose != 0) {
            this.imprimir("VidaFinal\nHeroi1: " + this.vidaHeroi1 + ". Heroi2:" + this.vidaHeroi2);
        }

        if(this.vidaHeroi1 > this.vidaHeroi2) {
            this.imprimir("Vitoria do Jogador1 ! (classe: " + this.nameclasse1 + ")");
            return true;
        } else {
            this.imprimir("Vitoria do Jogador2 ! (classe: " + this.nameclasse2 + ")");
            return false;
        }
    }

    private Carta comprarCarta(boolean jogador) {
        if(this.deck.getCartas().size() <= 0) {
            throw new RuntimeException("Não há mais cartas no baralho para serem compradas.");
        } else {
            Carta nova = (Carta)this.deck.getCartas().get(0);
            this.deck.getCartas().remove(0);
            this.mao.add(nova);
            if(jogador) {
                ++this.nCartasHeroi1;
            } else {
                ++this.nCartasHeroi2;
            }

            return (Carta)UnoptimizedDeepCopy.copy(nova);
        }
    }

    private Carta usarCartaMao(int idCarta, boolean jogador) {
        String dbg = "";

        for(int i = 0; i < this.mao.size(); ++i) {
            if(((Carta)this.mao.get(i)).getID() == idCarta) {
                Carta ret = (Carta)this.mao.get(i);
                this.mao.remove(i);
                if(jogador) {
                    --this.nCartasHeroi1;
                } else {
                    --this.nCartasHeroi2;
                }

                return ret;
            }

            dbg = dbg + ((Carta)this.mao.get(i)).getID() + " ";
        }

        throw new RuntimeException("Tentou usar carta que nao possui na mao. ID carta=:" + idCarta + ". IDs de cartas na mao= " + dbg);
    }

    private void processarJogada(Jogada umaJogada, boolean jogador) {
        switch(umaJogada.getTipo()) {
            case LACAIO:
                if(!(umaJogada.getCartaJogada() instanceof CartaLacaio)) {
                    throw new RuntimeException("Tentou usar uma jogada LACAIO, mas a cartaJogada nao era lacaio.");
                }

                if(this.verbose != 0) {
                    this.imprimir("JOGADA: Um lacaio entrou na mesa, lacaio_id=" + umaJogada.getCartaJogada().getID() + " (" + umaJogada.getCartaJogada().getNome() + ")");
                }

                this.baixarCartaLacaio(umaJogada, jogador);
                break;
            case MAGIA:
                if(!(umaJogada.getCartaJogada() instanceof CartaMagia)) {
                    throw new RuntimeException("Tentou usar uma jogada MAGIA, mas a cartaJogada nao era magia.");
                }

                if(((CartaMagia)umaJogada.getCartaJogada()).getMagiaTipo() == TipoMagia.ALVO) {
                    if(this.verbose != 0) {
                        this.imprimir("JOGADA: Uma magia usada de id=" + umaJogada.getCartaJogada().getID() + " mirando no lacaio_id= " + (umaJogada.getCartaAlvo() == null?"Heroi":Integer.valueOf(umaJogada.getCartaAlvo().getID())));
                    }
                } else if(((CartaMagia)umaJogada.getCartaJogada()).getMagiaTipo() == TipoMagia.AREA) {
                    if(this.verbose != 0) {
                        this.imprimir("JOGADA: Uma magia usada de id=" + umaJogada.getCartaJogada().getID() + " de dano em area.");
                    }
                } else if(this.verbose != 0) {
                    this.imprimir("JOGADA: Uma magia usada de id=" + umaJogada.getCartaJogada().getID() + " de buff.");
                }

                this.usoCartaMagia(umaJogada, jogador);
                break;
            case ATAQUE:
                if(!(umaJogada.getCartaJogada() instanceof CartaLacaio)) {
                    throw new RuntimeException("Tentou usar uma jogada ATAQUE, mas a cartaJogada nao era lacaio.");
                }

                if(this.verbose != 0) {
                    this.imprimir("JOGADA: Um ataque do lacaio_id =" + umaJogada.getCartaJogada().getID() + " (" + umaJogada.getCartaJogada().getNome() + ") no lacaio_id =" + (umaJogada.getCartaAlvo() == null?"Heroi":Integer.valueOf(umaJogada.getCartaAlvo().getID())));
                }

                if(this.lacaiosAtacaramID.contains(new Integer(umaJogada.getCartaJogada().getID()))) {
                    throw new RuntimeException("Nao se pode atacar com um lacaio mais de uma vez por turno.");
                }

                this.lacaiosAtacaramID.add(new Integer(umaJogada.getCartaJogada().getID()));
                this.ataqueLacaio(umaJogada, jogador);
                break;
            case PODER:
                if(this.verbose != 0) {
                    this.imprimir("JOGADA: Um poder heroico usado no lacaio_id=" + (umaJogada.getCartaAlvo() == null?"Heroi":Integer.valueOf(umaJogada.getCartaAlvo().getID())));
                }

                if(this.poderHeroicoUsado) {
                    throw new RuntimeException("Nao pode utilizar mais de um poder heroico por turno.");
                }

                this.poderHeroicoUsado = true;
                this.poderHeroico(umaJogada, jogador);
                break;
            default:
                throw new RuntimeException("JOGADA de tipo invalido: tipo=" + umaJogada.getTipo());
        }

    }

    private void poderHeroico(Jogada jogPoder, boolean jogador) {
        int alvo = jogPoder.getCartaAlvo() == null?-1:jogPoder.getCartaAlvo().getID();
        if(alvo == -1) {
            this.danoHeroi(1, jogador);
        } else {
            int atkLacaio = this.danoLacaio(alvo, this.lacaiosOponente, 1, !jogador);
            this.danoHeroi(atkLacaio, !jogador);
        }

        if(jogador) {
            this.manaHeroi1 -= 2;
            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, (Carta)null, this.manaHeroi1, jogador));
        } else {
            this.manaHeroi2 -= 2;
            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, (Carta)null, this.manaHeroi2, jogador));
        }

    }

    private void ataqueLacaio(Jogada jogAtaque, boolean jogador) {
        int lacaioOrigem = jogAtaque.getCartaJogada().getID();
        int lacaioDestino = jogAtaque.getCartaAlvo() == null?-1:jogAtaque.getCartaAlvo().getID();

        int i;
        for(i = 0; i < this.lacaios.size() && ((CartaLacaio)this.lacaios.get(i)).getID() != lacaioOrigem; ++i) {
            ;
        }

        if(i >= this.lacaios.size()) {
            throw new RuntimeException("Lacaio invalido de origem do ataque. ID Lacaio=" + lacaioOrigem);
        } else if(((CartaLacaio)this.lacaios.get(i)).getTurno() == this.turno) {
            throw new RuntimeException("Lacaio nao pode atacar no mesmo turno que entrou na mesa. ID Lacaio=" + lacaioOrigem);
        } else {
            if(lacaioDestino == -1) {
                this.danoHeroi(((CartaLacaio)this.lacaios.get(i)).getAtaque(), jogador);
            } else {
                int j = 0;

                while(true) {
                    if(j >= this.lacaiosOponente.size() || ((CartaLacaio)this.lacaiosOponente.get(j)).getID() == lacaioDestino) {
                        if(j >= this.lacaiosOponente.size() || ((CartaLacaio)this.lacaiosOponente.get(j)).getID() != lacaioDestino) {
                            throw new RuntimeException("Lacaio invalido de alvo do ataque. ID Alvo=" + lacaioDestino + ". ID Origem=" + lacaioOrigem);
                        }

                        int atkLacaio1 = ((CartaLacaio)this.lacaios.get(i)).getAtaque();
                        int atkLacaio2 = ((CartaLacaio)this.lacaiosOponente.get(j)).getAtaque();
                        this.danoLacaio(lacaioDestino, this.lacaiosOponente, atkLacaio1, !jogador);
                        this.danoLacaio(lacaioOrigem, this.lacaios, atkLacaio2, jogador);
                        break;
                    }

                    ++j;
                }
            }

        }
    }

    private void baixarCartaLacaio(Jogada jogLacaio, boolean jogador) {
        if(!(jogLacaio.getCartaJogada() instanceof CartaLacaio)) {
            throw new RuntimeException("Erro em baixarCartaLacaio(): Tentou baixar uma carta que nao e lacaio. ID: " + jogLacaio.getCartaJogada().getID());
        } else {
            CartaLacaio lacaio = (CartaLacaio)this.usarCartaMao(jogLacaio.getCartaJogada().getID(), jogador);
            lacaio.setTurno(this.turno);
            if(this.lacaios.size() >= 7) {
                throw new RuntimeException("Erro em baixarCartaLacaio(): Nao se pode ter mais de sete lacaios na mesa !");
            } else {
                this.lacaios.add(lacaio);
                this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.RETIRAR_MAO, jogLacaio.getCartaJogada(), 0, jogador));
                this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.ADICIONAR_MESA, jogLacaio.getCartaJogada(), 0, jogador));
                if(jogador) {
                    this.manaHeroi1 -= lacaio.getMana();
                    this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, (Carta)null, this.manaHeroi1, jogador));
                } else {
                    this.manaHeroi2 -= lacaio.getMana();
                    this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, (Carta)null, this.manaHeroi2, jogador));
                }

            }
        }
    }

    private void usoCartaMagia(Jogada jogMagia, boolean jogador) {
        if(!(jogMagia.getCartaJogada() instanceof CartaMagia)) {
            throw new RuntimeException("Erro em usarCartaMagia(): Tentou usar uma carta como magia, mas nao e magia. ID: " + jogMagia.getCartaJogada().getID());
        } else {
            CartaMagia magia = (CartaMagia)this.usarCartaMao(jogMagia.getCartaJogada().getID(), jogador);
            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.RETIRAR_MAO, jogMagia.getCartaJogada(), 0, jogador));
            if(magia.getMagiaTipo() == TipoMagia.ALVO) {
                int alvo = jogMagia.getCartaAlvo() == null?-1:jogMagia.getCartaAlvo().getID();
                if(alvo == -1) {
                    this.danoHeroi(magia.getMagiaDano(), jogador);
                } else {
                    this.danoLacaio(alvo, this.lacaiosOponente, magia.getMagiaDano(), !jogador);
                }
            } else if(magia.getMagiaTipo() == TipoMagia.AREA) {
                if(this.verbose != 0) {
                    this.imprimir("Usando a magia: " + magia.getNome() + " em area ...");
                }

                ArrayList<Integer> alvos = new ArrayList();
                Iterator var6 = this.lacaiosOponente.iterator();

                while(var6.hasNext()) {
                    Carta card = (Carta)var6.next();
                    alvos.add(new Integer(card.getID()));
                }

                var6 = alvos.iterator();

                while(var6.hasNext()) {
                    Integer alvo = (Integer)var6.next();
                    this.danoLacaio(alvo.intValue(), this.lacaiosOponente, magia.getMagiaDano(), !jogador);
                }

                this.danoHeroi(magia.getMagiaDano(), jogador);
            } else {
                if(jogMagia.getCartaAlvo() == null) {
                    throw new RuntimeException("Erro em usarCartaMagia(): Tentou usar carta de buff, mas sem alvo! ID buff: " + jogMagia.getCartaJogada().getID());
                }

                this.buffarLacaio(jogMagia.getCartaAlvo().getID(), this.lacaios, magia.getMagiaDano(), jogador);
            }

            if(jogador) {
                this.manaHeroi1 -= magia.getMana();
                this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, (Carta)null, this.manaHeroi1, jogador));
            } else {
                this.manaHeroi2 -= magia.getMana();
                this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_MANA_HEROI, (Carta)null, this.manaHeroi2, jogador));
            }

        }
    }

    private int danoLacaio(int idAlvo, ArrayList<CartaLacaio> grupoLacaios, int dano, boolean jogador) {
        String dbg = "";

        for(int i = 0; i < grupoLacaios.size(); ++i) {
            if(((CartaLacaio)grupoLacaios.get(i)).getID() == idAlvo) {
                int vidaLacaio = ((CartaLacaio)grupoLacaios.get(i)).getVidaAtual();
                int atkLacaio = ((CartaLacaio)grupoLacaios.get(i)).getAtaque();
                if(vidaLacaio - dano > 0) {
                    ((CartaLacaio)grupoLacaios.get(i)).setVidaAtual(vidaLacaio - dano);
                    if(this.verbose != 0) {
                        this.imprimir("Lacaio id=" + idAlvo + " (" + ((CartaLacaio)grupoLacaios.get(i)).getNome() + ") sofreu dano mas esta vivo (Vida antes=" + vidaLacaio + ". dano=" + dano + ". Vida agora=" + (vidaLacaio - dano) + ").");
                    }

                    this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_LACAIO_HP, (Carta)grupoLacaios.get(i), ((CartaLacaio)grupoLacaios.get(i)).getVidaAtual(), jogador));
                } else {
                    if(this.verbose != 0) {
                        this.imprimir("Lacaio id=" + idAlvo + " (" + ((CartaLacaio)grupoLacaios.get(i)).getNome() + ") sofreu dano e morreu. (Vida antes=" + vidaLacaio + ". dano=" + dano + ").");
                    }

                    this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.RETIRAR_MESA, (Carta)grupoLacaios.get(i), 0, jogador));
                    grupoLacaios.remove(i);
                }

                return atkLacaio;
            }

            dbg = dbg + ((CartaLacaio)grupoLacaios.get(i)).getID() + " ";
        }

        throw new RuntimeException("Tentativa de causar dano em lacaio invalido. ID Lacaio alvo=" + idAlvo + ". Alvos validos: " + dbg);
    }

    private void danoHeroi(int dano, boolean jogador) {
        if(jogador) {
            this.vidaHeroi2 -= dano;
            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_HP_HEROI, (Carta)null, this.vidaHeroi2, false));
            if(this.verbose != 0) {
                this.imprimir("O heroi 2 tomou " + dano + " de dano (vida restante: " + this.vidaHeroi2 + ").");
            }
        } else {
            this.vidaHeroi1 -= dano;
            this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_HP_HEROI, (Carta)null, this.vidaHeroi1, true));
            if(this.verbose != 0) {
                this.imprimir("O heroi 1 tomou " + dano + " de dano (vida restante: " + this.vidaHeroi1 + ").");
            }
        }

    }

    private int buffarLacaio(int idAlvo, ArrayList<CartaLacaio> grupoLacaios, int valorBuff, boolean jogador) {
        String dbg = "";

        for(int i = 0; i < grupoLacaios.size(); ++i) {
            if(((CartaLacaio)grupoLacaios.get(i)).getID() == idAlvo) {
                int vidaLacaio = ((CartaLacaio)grupoLacaios.get(i)).getVidaAtual() + valorBuff;
                int atkLacaio = ((CartaLacaio)grupoLacaios.get(i)).getAtaque() + valorBuff;
                ((CartaLacaio)grupoLacaios.get(i)).setVidaAtual(vidaLacaio);
                ((CartaLacaio)grupoLacaios.get(i)).setAtaque(atkLacaio);
                int vidaMax = ((CartaLacaio)grupoLacaios.get(i)).getVidaMaxima() + valorBuff;
                ((CartaLacaio)grupoLacaios.get(i)).setVidaMaxima(vidaMax);
                if(this.verbose != 0) {
                    this.imprimir("Lacaio id=" + idAlvo + " (" + ((CartaLacaio)grupoLacaios.get(i)).getNome() + ") buffado +" + valorBuff + "/+" + valorBuff + " (antes=" + (atkLacaio - valorBuff) + "/" + (vidaLacaio - valorBuff) + ". buff=+" + valorBuff + "+/" + valorBuff + ". Agora=" + atkLacaio + "/" + vidaLacaio + ").");
                }

                this.listaAcoesGUI.add(new AcaoGUI(TipoAcao.SET_LACAIO_BUFFATTACK, (Carta)grupoLacaios.get(i), ((CartaLacaio)grupoLacaios.get(i)).getVidaAtual(), jogador));
                return atkLacaio;
            }

            dbg = dbg + ((CartaLacaio)grupoLacaios.get(i)).getID() + " ";
        }

        throw new RuntimeException("Tentativa de buffar em lacaio invalido. ID Lacaio alvo=" + idAlvo + ". Alvos validos: " + dbg);
    }

    public static ArrayList<Carta> gerarListaCartasPadrao(int player) {
        ArrayList<Carta> retorno = new ArrayList();
        Carta c1 = new CartaLacaio(player * 100 + 0, "Gnomo", 1, 2, 1, 1, TipoEfeito.NADA, -1);
        Carta c2 = new CartaLacaio(player * 100 + 2, "Guerreiro Orc", 2, 3, 2, 2, TipoEfeito.NADA, -1);
        Carta c3 = new CartaLacaio(player * 100 + 4, "Guerreiro Espadachim", 2, 2, 3, 3, TipoEfeito.NADA, -1);
        Carta c4 = new CartaLacaio(player * 100 + 6, "Mestre Orc", 3, 4, 2, 2, TipoEfeito.NADA, -1);
        Carta c5 = new CartaLacaio(player * 100 + 8, "Filhote de Dragão", 3, 3, 4, 4, TipoEfeito.NADA, -1);
        Carta c6 = new CartaLacaio(player * 100 + 10, "Cavaleiro", 3, 3, 1, 1, TipoEfeito.NADA, -1);
        Carta c7 = new CartaLacaio(player * 100 + 12, "Gigante de Pedra", 4, 4, 5, 5, TipoEfeito.NADA, -1);
        Carta c8 = new CartaLacaio(player * 100 + 14, "Arqueira Experiente", 4, 3, 5, 5, TipoEfeito.NADA, -1);
        Carta c9 = new CartaLacaio(player * 100 + 16, "Mestre Espadachim", 5, 3, 9, 9, TipoEfeito.NADA, -1);
        Carta c10 = new CartaLacaio(player * 100 + 18, "Mestre Mago", 5, 7, 3, 3, TipoEfeito.NADA, -1);
        Carta c11 = new CartaLacaio(player * 100 + 20, "Dragão", 7, 7, 7, 7, TipoEfeito.NADA, -1);
        Carta z1 = new CartaMagia(player * 100 + 22, "Rajada Congelante", 3, TipoMagia.ALVO, 3);
        Carta z2 = new CartaMagia(player * 100 + 24, "Raio", 5, TipoMagia.ALVO, 7);
        Carta z3 = new CartaMagia(player * 100 + 26, "Benção dos Deuses", 2, TipoMagia.BUFF, 2);
        Carta z4 = new CartaMagia(player * 100 + 28, "Mininova", 7, TipoMagia.AREA, 4);
        Carta c1_2 = new CartaLacaio(player * 100 + 1, "Gnomo", 1, 2, 1, 1, TipoEfeito.NADA, -1);
        Carta c2_2 = new CartaLacaio(player * 100 + 3, "Guerreiro Orc", 2, 3, 2, 2, TipoEfeito.NADA, -1);
        Carta c3_2 = new CartaLacaio(player * 100 + 5, "Guerreiro Espadachim", 2, 2, 3, 3, TipoEfeito.NADA, -1);
        Carta c4_2 = new CartaLacaio(player * 100 + 7, "Mestre Orc", 3, 4, 2, 2, TipoEfeito.NADA, -1);
        Carta c5_2 = new CartaLacaio(player * 100 + 9, "Filhote de Dragão", 3, 3, 4, 4, TipoEfeito.NADA, -1);
        Carta c6_2 = new CartaLacaio(player * 100 + 11, "Cavaleiro", 3, 3, 1, 1, TipoEfeito.NADA, -1);
        Carta c7_2 = new CartaLacaio(player * 100 + 13, "Gigante de Pedra", 4, 4, 5, 5, TipoEfeito.NADA, -1);
        Carta c8_2 = new CartaLacaio(player * 100 + 15, "Arqueira Experiente", 4, 3, 5, 5, TipoEfeito.NADA, -1);
        Carta c9_2 = new CartaLacaio(player * 100 + 17, "Mestre Espadachim", 5, 3, 9, 9, TipoEfeito.NADA, -1);
        Carta c10_2 = new CartaLacaio(player * 100 + 19, "Mestre Mago", 5, 7, 3, 3, TipoEfeito.NADA, -1);
        Carta c11_2 = new CartaLacaio(player * 100 + 21, "Dragão", 7, 7, 7, 7, TipoEfeito.NADA, -1);
        Carta z1_2 = new CartaMagia(player * 100 + 23, "Rajada Congelante", 3, TipoMagia.ALVO, 3);
        Carta z2_2 = new CartaMagia(player * 100 + 25, "Raio", 5, TipoMagia.ALVO, 7);
        Carta z3_2 = new CartaMagia(player * 100 + 27, "Benção dos Deuses", 2, TipoMagia.BUFF, 2);
        Carta z4_2 = new CartaMagia(player * 100 + 29, "Mininova", 7, TipoMagia.AREA, 4);
        retorno.add(c1);
        retorno.add(c2);
        retorno.add(c3);
        retorno.add(c4);
        retorno.add(c5);
        retorno.add(c6);
        retorno.add(c7);
        retorno.add(c8);
        retorno.add(c9);
        retorno.add(c10);
        retorno.add(c11);
        retorno.add(z1);
        retorno.add(z2);
        retorno.add(z3);
        retorno.add(z4);
        retorno.add(c1_2);
        retorno.add(c2_2);
        retorno.add(c3_2);
        retorno.add(c4_2);
        retorno.add(c5_2);
        retorno.add(c6_2);
        retorno.add(c7_2);
        retorno.add(c8_2);
        retorno.add(c9_2);
        retorno.add(c10_2);
        retorno.add(c11_2);
        retorno.add(z1_2);
        retorno.add(z2_2);
        retorno.add(z3_2);
        retorno.add(z4_2);
        return retorno;
    }
}
