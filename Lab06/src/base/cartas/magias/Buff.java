package base.cartas.magias;

import java.util.UUID;

import base.Carta;
import base.cartas.Lacaio;

public class Buff extends Magia {

	private int aumentoEmAtaque;
	private int aumentoEmVida;
	
	
	
	public Buff(UUID id, String nome, int custoMana, int aumentoEmAtaque, int aumentoEmVida) {
		super(id, nome, custoMana);
		this.aumentoEmAtaque = aumentoEmAtaque;
		this.aumentoEmVida = aumentoEmVida;
	}
	
	public Buff(String nome, int custoMana, int aumentoEmAtaque, int aumentoEmVida) {
		super(nome, custoMana);
		this.aumentoEmAtaque = aumentoEmAtaque;
		this.aumentoEmVida = aumentoEmVida;
	}
	
	@Override
	public void usar(Carta alvo) {
		if (alvo instanceof Lacaio) {
			Lacaio cartaAlvo = (Lacaio) alvo;
			cartaAlvo.setAtaque(cartaAlvo.getAtaque()+getAumentoEmAtaque());
			cartaAlvo.setVidaMaxima(cartaAlvo.getVidaMaxima()+getAumentoEmVida());
			cartaAlvo.setVidaAtual(cartaAlvo.getVidaAtual()+getAumentoEmVida());
		}
	}
	
	public int getAumentoEmAtaque() {
		return aumentoEmAtaque;
	}
	public void setAumentoEmAtaque(int aumentoEmAtaque) {
		this.aumentoEmAtaque = aumentoEmAtaque;
	}
	public int getAumentoEmVida() {
		return aumentoEmVida;
	}
	public void setAumentoEmVida(int aumentoEmVida) {
		this.aumentoEmVida = aumentoEmVida;
	}
	
	public String toString() {
		return super.toString()
				+ "Aumento em vida = " + getAumentoEmVida() + "\n"
				+ "Aumento em ataque = " + getAumentoEmAtaque() + "\n";
	}
	
	
}
