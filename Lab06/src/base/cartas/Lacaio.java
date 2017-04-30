package base.cartas;

import java.util.UUID;

import base.Carta;

public class Lacaio extends Carta{
	private int ataque;
	private int vidaAtual;
	private int vidaMaxima;

	

	
	public Lacaio(UUID id, String nome, int custoMana, int ataque, int vidaAtual, int vidaMaxima) {
		super(id, nome, custoMana);
		this.ataque = ataque;
		this.vidaAtual = vidaAtual;
		this.vidaMaxima = vidaMaxima;
	}
	
	public Lacaio(String nome, int custoMana, int ataque, int vidaAtual, int vidaMaxima) {
		super(nome, custoMana);
		this.ataque = ataque;
		this.vidaAtual = vidaAtual;
		this.vidaMaxima = vidaMaxima;
	}


	public Lacaio (Lacaio source) {
		this(source.getID(),
				source.getNome(), 
				source.getCustoMana(), 
				source.getAtaque(), 
				source.getVidaAtual(), 
				source.getVidaMaxima());	
	}
	

	
	@Override
	public String toString() {
		return super.toString()
				+ "Ataque = " + getAtaque() + "\n"
				+ "Vida Atual = " + getVidaAtual() + "\n"
				+ "Vida Máxima = " + getVidaMaxima() + "\n";
	}
	
	@Override
	public void usar(Carta alvo) {
		if (alvo instanceof Lacaio) { 
			Lacaio cartaAlvo = (Lacaio) alvo;
			if (getAtaque() < cartaAlvo.getVidaAtual()) {
				cartaAlvo.setVidaAtual(cartaAlvo.getVidaAtual()-getAtaque());
			}
			else {
				cartaAlvo.setVidaAtual(0);
			}
		}
	}
	
	public UUID getID() {
		return super.getID();
	}

	public void setID(UUID id) {
		super.setID(id);
	}
	
	public String getNome() {
		return super.getNome();
	}
	
	public void setNome(String nome) {
		super.setNome(nome);
	}
	
	public int getCustoMana() {
		return super.getCustoMana();
	}
	
	public void setCustoMana(int custoMana) {
		super.setCustoMana(custoMana);
	}
	
	public int getAtaque() {
		return ataque;
	}
	
	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}
	
	public int getVidaAtual() {
		return vidaAtual;
	}
	
	public void setVidaAtual(int vidaAtual) {
		this.vidaAtual = vidaAtual;
	}
	
	public int getVidaMaxima() {
		return vidaMaxima;
	}
	
	public void setVidaMaxima(int vidaMaxima) {
		this.vidaMaxima = vidaMaxima;
	}
}