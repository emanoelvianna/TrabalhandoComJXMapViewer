package pucrs.alpro2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.viewer.WaypointRenderer;

import pucrs.alpro2.algoritmos.AlgoritmosGeograficos;

/**
 * Classe para gerenciar um mapa
 *
 * @author Marcelo Cohen
 */
public class GerenciadorMapa {

	final JXMapKit jXMapKit;
	private WaypointPainter<MyWaypoint> pontosPainter;
	private GeoPosition centro;

	private GeoPosition selCentro;
	private GeoPosition selBorda;

	private Color corMenor;
	private Color corMaior;

	private double valorMenor;
	private double valorMaior;

	public enum FonteImagens {

		OpenStreetMap, VirtualEarth
	};

	/*
	 * Cria um gerenciador de mapas, a partir de uma posição e uma fonte de
	 * imagens
	 * 
	 * @param centro centro do mapa
	 * 
	 * @param fonte fonte das imagens (FonteImagens.OpenStreetMap ou
	 * FonteImagens.VirtualEarth)
	 */
	public GerenciadorMapa(GeoPosition centro, FonteImagens fonte) {
		jXMapKit = new JXMapKit();
		TileFactoryInfo info = null;
		if (fonte == FonteImagens.OpenStreetMap) {
			info = new OSMTileFactoryInfo();
		} else {
			info = new VirtualEarthTileFactoryInfo(
					VirtualEarthTileFactoryInfo.MAP);
		}
		
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		jXMapKit.setTileFactory(tileFactory);

		// Ajustando a opacidade do mapa (50%)
		jXMapKit.getMainMap().setAlpha(0.5f);

		// Ajustando o nível de zoom do mapa
		jXMapKit.setZoom(4);
		// Informando o centro do mapa
		jXMapKit.setAddressLocation(centro);
		// Indicando que não desejamos ver um marcador nessa posição
		jXMapKit.setAddressLocationShown(false);
	
		// Criando um objeto para "pintar" os pontos
		pontosPainter = new WaypointPainter<MyWaypoint>();

		// Criando um objeto para desenhar os pontos
		pontosPainter.setRenderer(new WaypointRenderer<MyWaypoint>() {

			@Override
			public void paintWaypoint(Graphics2D g, JXMapViewer viewer,
					MyWaypoint wp) {

				// Desenha cada waypoint como um pequeno círculo
				Point2D point = viewer.getTileFactory().geoToPixel(
						wp.getPosition(), viewer.getZoom());
				int x = (int) point.getX();
				int y = (int) point.getY();
				// g = (Graphics2D) g.create();

				// Obtém a cor do waypoint
				Color cor = wp.getColor();
				// Normaliza os valores entre 0 (mínimo) e 1 (máximo)
				float fator = (float) ((wp.getValue() - valorMenor) / (valorMaior - valorMenor));
				// Seta a opacidade da cor usando o fator de importância
				// calculado (0=mínimo,1=máximo)

				/*
				 * Minha modifica��o do trecho de c�digo!
				 */
				g.setColor(new Color(cor.getRed(), cor.getGreen(), cor
						.getBlue(), cor.getAlpha() / 2));

				g.fill(new Ellipse2D.Float(x - 3, y - 3, 6, 6));
			}
		});

		// Criando um objeto para desenhar os elementos de interface
		// (círculo de seleção, etc)
		Painter<JXMapViewer> guiPainter = new Painter<JXMapViewer>() {
			public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
				if (selCentro == null || selBorda == null)
					return;
				Point2D point = map.convertGeoPositionToPoint(selCentro);
				Point2D pont2 = map.convertGeoPositionToPoint(selBorda);
				int x = (int) point.getX();
				int y = (int) point.getY();
				int raio = (int) Math.sqrt(Math.pow(
						point.getX() - pont2.getX(), 2)
						+ Math.pow(point.getY() - pont2.getY(), 2));
				int r = raio / 2;
				g.setColor(Color.RED);
				g.setStroke(new BasicStroke(2));
				g.draw(new Ellipse2D.Float(x - r, y - r, raio, raio));
				g.drawString(getRaio() + " metros", x + r, y + r);
				g.fill(new Ellipse2D.Float(x - 3, y - 3, 6, 6));
			}
		};

		// Um CompoundPainter permite combinar vários painters ao mesmo
		// tempo...
		CompoundPainter cp = new CompoundPainter();
		cp.setPainters(pontosPainter, guiPainter);

		jXMapKit.getMainMap().setOverlayPainter(cp);

		selCentro = null;
		selBorda = null;
	}

	/*
	 * Informa a localização do ponto central da região
	 * 
	 * @param ponto central
	 */
	public void setSelecaoCentro(GeoPosition sel) {
		this.selCentro = sel;
	}

	public GeoPosition getSelecaoCentro() {
		return selCentro;
	}

	/*
	 * Informa a localização de um ponto da borda da região Utilizamos isso
	 * para definir o raio da região e desenhar o círculo
	 * 
	 * @param ponto da borda
	 */
	public void setSelecaoBorda(GeoPosition sb) {
		this.selBorda = sb;
	}

	// Retorna o raio da região selecionada (em metros)
	public int getRaio() {
		return (int) (AlgoritmosGeograficos.calcDistancia(selBorda, selCentro) * 500);
	}

	public void setIntervaloValores(double valMenor, double valMaior) {
		this.valorMenor = valMenor;
		this.valorMaior = valMaior;
		System.out.println(valMenor + "->" + valMaior);
	}

	/*
	 * Informa os pontos a serem desenhados (precisa ser chamado a cada vez que
	 * mudar)
	 * 
	 * @param lista lista de pontos (objetos MyWaypoint)
	 */
	public void setPontos(List<MyWaypoint> lista) {
		// Criando um conjunto de pontos
		Set<MyWaypoint> pontos = new HashSet<MyWaypoint>(lista);
		// Informando o conjunto ao painter
		pontosPainter.setWaypoints(pontos);
	}

	/*
	 * Retorna a referência ao objeto JXMapKit, para ajuste de parâmetros (se
	 * for o caso)
	 * 
	 * @returns referência para objeto JXMapKit em uso
	 */
	public JXMapKit getMapKit() {

		return jXMapKit;
	}

}
