package base;

import java.util.UUID;

public class Carta implements Comparable {

	private UUID id;
	private String nome;
	private int custoMana;
	
	public Carta(UUID id, String nome, int custoMana) {
		this.id = id;
		this.nome = nome;
		this.custoMana = custoMana;
	}
	
	public Carta(String nome, int custoMana) {
		this(UUID.randomUUID(), nome, custoMana);
	}

	public UUID getID() {
		return id;
	}

	public void setID(UUID id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCustoMana() {
		return custoMana;
	}

	public void setCustoMana(int custoMana) {
		this.custoMana = custoMana;
	}
	
	public void usar(Carta alvo) {}


	@Override
	public int compareTo(Object object) {
		if (object instanceof Carta) {
			Carta carta = (Carta) object;
			return getID().compareTo(carta.getID());
		}
		return -1;
	}

	@Override
	public String toString () {
		return getNome() + " (ID: " + getID() + ")\n"
				+ "Custo de Mana = " + getCustoMana() + "\n";
	}

	@Override
	public boolean equals (Object obj) {
		if (obj instanceof Carta) {
			return (this.getID().equals(((Carta) obj).getID()) &&
					this.getNome().equals(((Carta) obj).getNome()) &&
					this.getCustoMana() == ((Carta) obj).getCustoMana());
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 3;
		result = 13*result + (getNome() != null ? getNome().hashCode() : 0);
		result = 37*result + (getID() != null ? getID().hashCode() : 0);
		result = 61*result + getCustoMana();
		return result;
	}
}
