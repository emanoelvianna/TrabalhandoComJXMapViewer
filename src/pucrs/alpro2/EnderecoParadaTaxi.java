package pucrs.alpro2;

public class EnderecoParadaTaxi {

	private Integer id;
	private String expesificacao;
	private String logradouro; // somente o nome
	private String telefone;
	private Double longitude;
	private Double latitude;
	private Integer grauDeCriminalidade;
	
	public void somaGrauDeCriminalidade() {
		this.grauDeCriminalidade++;  
	}

	public Integer getId() {
		return id;
	}

	public Integer getGrauDeCriminalidade() {
		return grauDeCriminalidade;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getExpesificacao() {
		return expesificacao;
	}

	public void setExpesificacao(String expesificacao) {
		this.expesificacao = expesificacao;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double l) {
		this.longitude = l;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

}
