import java.util.ArrayList;

public class JogadorRA186145New extends JogadorRA186145{
	
	public JogadorRA186145New (ArrayList<Carta> maoInicial, boolean primeiro){
		super(maoInicial, primeiro);
	}
	
	@Override
	public ArrayList<Jogada> processarTurno (Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente){
		int minhaMana, minhaVida;
		if(cartaComprada != null)
			mao.add(cartaComprada);
		if(primeiroJogador){
			minhaMana = mesa.getManaJog1();
			minhaVida = mesa.getVidaHeroi1();
			setLacaios(mesa.getLacaiosJog1());
			setLacaiosOponente(mesa.getLacaiosJog2());
			//System.out.println("--------------------------------- Começo de turno pro jogador1");
		}
		else{
			minhaMana = mesa.getManaJog2();
			minhaVida = mesa.getVidaHeroi2();
			setLacaios(mesa.getLacaiosJog2());
			setLacaiosOponente(mesa.getLacaiosJog1());
			//System.out.println("--------------------------------- Começo de turno pro jogador2");
		}
		
		ArrayList<Jogada> minhasJogadas = new ArrayList<Jogada>();
		
		// O laço abaixo cria jogas de baixar lacaios da mão para a mesa se houver mana disponível.
//		boolean usedMagia = false;
		
		
//		boolean usedLacaio = false;
		if (minhaMana >= 2) {
			if (getLacaiosOponente().size() != 0) {
				Jogada pod = new Jogada(TipoJogada.PODER, null, getLacaiosOponente().get(0));
				minhasJogadas.add(pod);
				minhaMana-=2;
			}
		}
		
		for(int i = 0; i < mao.size(); i++) {
			Carta card = mao.get(i);
			if (card instanceof CartaLacaio && card.getMana() <= minhaMana) {
				Jogada lac = new Jogada(TipoJogada.LACAIO, card, null);
//				usedLacaio = true;
				minhasJogadas.add(lac);
				minhaMana -= card.getMana();
				mao.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < mao.size(); i++){
			Carta card = mao.get(i);
			if (card instanceof CartaMagia && card.getMana() <= minhaMana) {
				CartaMagia cartaMagia = (CartaMagia) card;
				Jogada lac;
				switch (cartaMagia.getMagiaTipo()) {
					case BUFF:
						if (getLacaios().size() != 0) {
							lac = new Jogada(TipoJogada.MAGIA, card, getLacaios().get(0));
//							usedMagia = true;
							minhasJogadas.add(lac);
							minhaMana -= card.getMana();
							mao.remove(i);
							i--;
						}
						break;
	
					case AREA:
						lac = new Jogada(TipoJogada.MAGIA, card, null);
//						usedMagia = true;
						minhasJogadas.add(lac);
						minhaMana -= card.getMana();
						mao.remove(i);
						i--;
						break;
					case ALVO:
						if (getLacaiosOponente().size() != 0) {
							lac = new Jogada(TipoJogada.MAGIA, card, getLacaiosOponente().get(0));
//							usedMagia = true;
							minhasJogadas.add(lac);
							minhaMana -= card.getMana();
							mao.remove(i);
							i--;
						}
						break;
				}

			}
		}
		
			
		for (Carta currentLacaio : getLacaios()) {
			Jogada lac = new Jogada(TipoJogada.ATAQUE, currentLacaio, null);
			minhasJogadas.add(lac);
		}
		
		return minhasJogadas;
	}

}
