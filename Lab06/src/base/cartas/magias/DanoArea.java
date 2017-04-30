package base.cartas.magias;

import java.util.List;
import java.util.UUID;

import base.cartas.Lacaio;

public class DanoArea extends Dano {

	public DanoArea(UUID id, String nome, int custoMana, int dano) {
		super(id, nome, custoMana, dano);
	}

	public DanoArea(String nome, int custoMana, int dano) {
		super(nome, custoMana, dano);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	public void usar(List<Lacaio> alvos) {
		for (Lacaio cartaAtual: alvos) {
			super.usar(cartaAtual);
		}
	}
	
	
}
