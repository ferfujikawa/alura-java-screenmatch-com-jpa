package br.com.alura.screenmatch.model;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import br.com.alura.screenmatch.service.ConsultaChatGPT;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "series")
public class Serie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String titulo;
	
	private Integer totalTemporadas;
	
	private Double avaliacao;
	
	@Enumerated(EnumType.STRING)
	private Categoria genero;
	
	private String atores;
	
	private String poster;
	
	private String sinopse;
	
	@OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
	private List<Episodio> episodios = new ArrayList<Episodio>();

	protected Serie() { }
	
	public Serie(DadosSerie dadosSerie) {
		this.titulo = dadosSerie.titulo();
		this.totalTemporadas = dadosSerie.totalTemporadas();
		this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0.0);
		this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
		this.atores= dadosSerie.atores();
		this.poster = dadosSerie.poster();
//		this.sinopse= ConsultaChatGPT.obterTraducao(dadosSerie.sinopse()).trim();
		this.sinopse= dadosSerie.sinopse();
	}
	
	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public Integer getTotalTemporadas() {
		return totalTemporadas;
	}

	public Double getAvaliacao() {
		return avaliacao;
	}

	public Categoria getGenero() {
		return genero;
	}

	public String getAtores() {
		return atores;
	}

	public String getPoster() {
		return poster;
	}

	public String getSinopse() {
		return sinopse;
	}

	public List<Episodio> getEpisodios() {
		return episodios;
	}
	
	public void setEpisodios(List<Episodio> episodios) {
		episodios.forEach(e -> e.setSerie(this));
		this.episodios = episodios;
	}

	@Override
	public String toString() {
		return "titulo=" + titulo +
				", totalTemporadas=" + totalTemporadas +
				", avaliacao=" + avaliacao +
				", genero=" + genero +
				", atores=" + atores +
				", poster=" + poster +
				", sinopse=" + sinopse;
	}
	
	
}
