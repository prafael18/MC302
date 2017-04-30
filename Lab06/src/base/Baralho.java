package base;

import java.util.ArrayList;
import java.util.Collections;

public class Baralho {
	private ArrayList<Carta> vetorCartas;
	private final int MAX_CARDS = 30;
	
	public Baralho() {
		vetorCartas = new ArrayList<Carta>();
	}

	public void adicionarCarta (Carta carta) {
		if (vetorCartas.size() < MAX_CARDS) {
			vetorCartas.add(carta);
		}
	}
		
	public Carta comprarCarta () {
		return vetorCartas.remove(vetorCartas.size()-1);
	}
		
	public void embaralhar () {

		Collections.shuffle(vetorCartas);
		Collections.reverse(vetorCartas);
		
		for (Carta cartaAtual: vetorCartas) {
			System.out.println(cartaAtual);
		}
		
		Collections.reverse(vetorCartas);
		
	}

	public ArrayList<Carta> getVetorCartas() {
		return vetorCartas;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Baralho)
			return this.vetorCartas.equals(((Baralho) obj).getVetorCartas());
		return false;
	}

	@Override
	public int hashCode() {
		return (vetorCartas != null ? vetorCartas.hashCode() : 0);
	}
}
