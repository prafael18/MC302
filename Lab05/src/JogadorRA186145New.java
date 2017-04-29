import java.util.ArrayList;

public class JogadorRA186145New extends JogadorRA186145{
	
	public JogadorRA186145New (ArrayList<Carta> maoInicial, boolean primeiro){
		super(maoInicial, primeiro);
	}
	
	@Override
	public ArrayList<Jogada> processarTurno (Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente){
		int minhaMana, minhaVida, vidaInimigo;
		int numLacaios;
		if(cartaComprada != null)
			mao.add(cartaComprada);
		if(primeiroJogador) {
			minhaMana = mesa.getManaJog1();
			minhaVida = mesa.getVidaHeroi1();
			vidaInimigo = mesa.getVidaHeroi2();
			setLacaios(mesa.getLacaiosJog1());
			setLacaiosOponente(mesa.getLacaiosJog2());
			//System.out.println("--------------------------------- Começo de turno pro jogador1");
		}
		else{
			minhaMana = mesa.getManaJog2();
			minhaVida = mesa.getVidaHeroi2();
			vidaInimigo = mesa.getVidaHeroi1();
			setLacaios(mesa.getLacaiosJog2());
			setLacaiosOponente(mesa.getLacaiosJog1());
			//System.out.println("--------------------------------- Começo de turno pro jogador2");
		}
		
		ArrayList<Jogada> minhasJogadas = new ArrayList<Jogada>();
		numLacaios = getLacaios().size();
		// O laço abaixo cria jogas de baixar lacaios da mão para a mesa se houver mana disponível.		

		for (int i = 0; i < mao.size(); i++) {
			Carta card = (Carta) mao.get(i);
			if (card instanceof CartaMagia && minhaMana >= card.getMana()) {
				CartaMagia cartaMagia = (CartaMagia) card;
				if (cartaMagia.getMagiaTipo() == TipoMagia.AREA) {
					Jogada mag = new Jogada(TipoJogada.MAGIA, cartaMagia, null);
					minhasJogadas.add(mag);
					minhaMana-=cartaMagia.getMana();
					mao.remove(i);
					i--;
					for (int j = 0; j < getLacaiosOponente().size(); j++) {
						CartaLacaio lacaioOponente = getLacaiosOponente().get(j);
						if (lacaioOponente.getVidaAtual() - cartaMagia.getMagiaDano() > 0) {
							lacaioOponente.setVidaAtual(lacaioOponente.getVidaAtual() - cartaMagia.getMagiaDano());
						}
						else {
							getLacaiosOponente().remove(j);
							j--;
						}
					}
				}
			}
		}
		
		//Priorizar lacaios com maior ataque e menor custo de mana 
		
		for(int i = 0; i < mao.size(); i++) {
			Carta card = mao.get(i);
			if (card instanceof CartaLacaio && card.getMana() <= minhaMana && numLacaios < 7) {
				Jogada lac = new Jogada(TipoJogada.LACAIO, card, null);
				minhasJogadas.add(lac);
				minhaMana -= card.getMana();
				numLacaios++;
				mao.remove(i);
				i--;
			}
		}
		
		
		for (int i = 0; i < mao.size(); i++) {
			Carta card = (Carta) mao.get(i);
			if (card instanceof CartaMagia && minhaMana >= card.getMana()) {
				CartaMagia cartaMagia = (CartaMagia) card;
				if (cartaMagia.getMagiaTipo() == TipoMagia.BUFF && !getLacaios().isEmpty()) {
						int bestLacaioIndice = -1;
						int bestLacaioAtaque = 0;
						for(int j = 0; j < getLacaios().size(); j++) {
							CartaLacaio lacaio = getLacaios().get(j);
							if (lacaio.getAtaque() > bestLacaioAtaque) {
								bestLacaioAtaque = lacaio.getAtaque();
								bestLacaioIndice = j;
							}
						}
						Jogada mag = new Jogada(TipoJogada.MAGIA, cartaMagia, getLacaios().get(bestLacaioIndice));
						minhasJogadas.add(mag);
						minhaMana -= cartaMagia.getMana();
						mao.remove(i);
						i--;
				}
			}
		}
		
		for (int i = 0; i < mao.size(); i++) {
			Carta card = (Carta) mao.get(i);
			if (card instanceof CartaMagia && minhaMana >= card.getMana()) {
				CartaMagia cartaMagia = (CartaMagia) card;
				if (cartaMagia.getMagiaTipo() == TipoMagia.ALVO) {
					Jogada mag = new Jogada(TipoJogada.MAGIA, cartaMagia, null);
					minhasJogadas.add(mag);
					minhaMana-=cartaMagia.getMana();
					mao.remove(i);
					i--;
				}
			}
		}
	
			
		if (isKillingBlow(minhasJogadas, vidaInimigo)) {
			for (Carta currentLacaio : getLacaios()) {
				Jogada lac = new Jogada(TipoJogada.ATAQUE, currentLacaio, null);
				minhasJogadas.add(lac);
			}	
		}
		else {
			for (int i = 0; i < minhasJogadas.size(); i++) {
				Jogada jogada = minhasJogadas.get(i);
				if (jogada.getTipo() == TipoJogada.MAGIA) {
					CartaMagia cartaMagia = (CartaMagia) jogada.getCartaJogada();
					if (cartaMagia.getMagiaTipo() == TipoMagia.ALVO) {
						for (int j = 0; j < getLacaiosOponente().size(); j++) {
							CartaLacaio lacaioOponente = getLacaiosOponente().get(j);
							int ataqueDiff = lacaioOponente.getAtaque() - cartaMagia.getMagiaDano();
							boolean willKill = (cartaMagia.getMagiaDano() - lacaioOponente.getVidaAtual() >= 0);
							if (ataqueDiff > 0 && willKill) {
								jogada.setCartaAlvo(lacaioOponente);
								getLacaiosOponente().remove(j);
								break;
							}
						}
					}
				}
			}
			for (int i = 0; i < getLacaios().size(); i++) {
				CartaLacaio lacaioAtual = getLacaios().get(i);
				boolean usedLacaio = false;
				for (int j = 0; j < getLacaiosOponente().size(); j++) {
					CartaLacaio lacaioOponenteAtual = getLacaiosOponente().get(j);
					int ataqueDiff = lacaioOponenteAtual.getAtaque() - lacaioAtual.getAtaque();
					boolean willKill = (lacaioAtual.getAtaque() - lacaioOponenteAtual.getVidaAtual() >= 0);
					if (ataqueDiff > 0 && willKill) {
						usedLacaio = true;
						Jogada lac = new Jogada(TipoJogada.ATAQUE, lacaioAtual, lacaioOponenteAtual);
						minhasJogadas.add(lac);
						getLacaiosOponente().remove(j);
						break;
					}
				}
				if (!usedLacaio) {
					Jogada lac = new Jogada(TipoJogada.ATAQUE, lacaioAtual, null);
					minhasJogadas.add(lac);
				}
			}
		}
		
		if (minhaMana >= 2) {
			Jogada heroi = new Jogada(TipoJogada.PODER, null, null);
			minhasJogadas.add(heroi);
		}
		
		return minhasJogadas;
	}
	
	private boolean isKillingBlow(ArrayList<Jogada> minhasJogadas, int vidaInimigo) {
		int totalDamage = 0;
		for (CartaLacaio currentLacaio: getLacaios()) {
			totalDamage += currentLacaio.getAtaque();
		}
		for (Jogada jogada : minhasJogadas) {
			if (jogada.getTipo() == TipoJogada.MAGIA) {
				CartaMagia cartaMagia = (CartaMagia) jogada.getCartaJogada();
				totalDamage += cartaMagia.getMagiaDano();
			}
		}
		if (totalDamage >= vidaInimigo) {
			return true;
		}
		return false;
	}

}