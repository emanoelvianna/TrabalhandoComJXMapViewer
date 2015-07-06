/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pucrs.alpro2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.util.GeoUtil;

import pucrs.alpro2.algoritmos.AlgoritmosGeograficos;

public class JanelaConsulta extends javax.swing.JFrame {

	private Dados dados;
	private LinkedList<Ponto> listaEnderecoParadaTaxis;
	private Consultas listaDeConsultas;

	private GerenciadorMapa gerenciador;
	private EventosMouse mouse;

	private JPanel painelMapa;
	private JPanel painelLateral;

	GeoUtil geoUtil;
	private JTextField textField;

	/**
	 * Creates new form JanelaConsulta
	 */
	public JanelaConsulta() {
		super();

		GeoPosition poa = new GeoPosition(-30.05, -51.18);
		gerenciador = new GerenciadorMapa(poa,
				GerenciadorMapa.FonteImagens.VirtualEarth);
		mouse = new EventosMouse();
		gerenciador.getMapKit().getMainMap().addMouseListener(mouse);
		gerenciador.getMapKit().getMainMap().addMouseMotionListener(mouse);

		painelMapa = new JPanel();
		painelMapa.setLayout(new BorderLayout());
		painelMapa.add(gerenciador.getMapKit(), BorderLayout.CENTER);

		getContentPane().add(painelMapa, BorderLayout.CENTER);

		painelLateral = new JPanel();
		painelMapa.add(painelLateral, BorderLayout.NORTH);
		painelLateral.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEADING);
		flowLayout_1.setVgap(3);
		flowLayout_1.setHgap(10);
		painelLateral.add(panel_1);

		JButton btnNewButton = new JButton("Consulta");
		panel_1.add(btnNewButton);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consulta(e);
			}
		});

		JButton btnNewButton3 = new JButton("Distancia");
		panel_1.add(btnNewButton3);

		btnNewButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				distancia(e);
			}
		});

		JButton btnNewButton2 = new JButton("Criminalidade");
		panel_1.add(btnNewButton2);

		btnNewButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				criminalidade(e);
			}
		});
		textField = new JTextField();

		JButton btnNewButton4 = new JButton("Rua");
		panel_1.add(btnNewButton4);

		btnNewButton4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar(e);
			}
		});

		painelLateral.add(textField);
		textField.setColumns(30);

		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Importante leitura de dados;
		dados = new Dados();
		dados.lerArquivoDeParadasDeTaxi();

	}

	private void consulta(java.awt.event.ActionEvent evt) {

		// Para obter o centro e o raio, usar como segue:
		GeoPosition centro = gerenciador.getSelecaoCentro();
		// System.out.println(centro.toString());
		int raio = gerenciador.getRaio();

		double distancia = 0;

		// Lista para armazenar o resultado da consulta
		List<MyWaypoint> lstPoints = new ArrayList<>();

		listaDeConsultas = new Consultas();

		listaEnderecoParadaTaxis = dados.getListaEnderecoParadaTaxis();

		for (Ponto ponto : listaEnderecoParadaTaxis) {

			GeoPosition geoPositionPontoTaxi = new GeoPosition(
					ponto.getLatitude(), ponto.getLongitude());

			distancia = AlgoritmosGeograficos.calcDistancia(
					geoPositionPontoTaxi, centro) * 1000;

			if (raio >= distancia) {
				// Adiciona na lista quad de consultas

				listaDeConsultas.adicionar(ponto, distancia);

				lstPoints.add(new MyWaypoint(Color.BLUE, distancia,
						geoPositionPontoTaxi));

			}

		}

		// Informa o resultado para o gerenciador
		gerenciador.setPontos(lstPoints);
		// Informa o intervalo de valores gerados, para calcular a cor de cada
		// ponto
		double menorValor = 15; // exemplo
		double maiorValor = 250; // exemplo
		gerenciador.setIntervaloValores(menorValor, maiorValor);

		this.repaint();

	}

	public void distancia(ActionEvent e) {
		GeoPosition centro = gerenciador.getSelecaoCentro();
		int raio = gerenciador.getRaio();

		double distancia = 0;

		List<MyWaypoint> lstPoints = new ArrayList<>();

		listaDeConsultas = new Consultas();

		listaEnderecoParadaTaxis = dados.getListaEnderecoParadaTaxis();

		for (Ponto ponto : listaEnderecoParadaTaxis) {

			GeoPosition geoPositionPontoTaxi = new GeoPosition(
					ponto.getLatitude(), ponto.getLongitude());

			distancia = AlgoritmosGeograficos.calcDistancia(
					geoPositionPontoTaxi, centro) * 1000;

			if (raio >= distancia) {
				// Adiciona na lista quad de consultas

				listaDeConsultas.adicionar(ponto, distancia);

				Color color = intervalosDeCoresDistancia(distancia);

				lstPoints.add(new MyWaypoint(color, distancia,
						geoPositionPontoTaxi));

			}

		}

		gerenciador.setPontos(lstPoints);

		this.repaint();
	}

	/*
	 * Realizar a consulta pelo grau de criminalidade
	 */
	public void criminalidade(ActionEvent e) {

		GeoPosition centro = gerenciador.getSelecaoCentro();
		int raio = gerenciador.getRaio();

		List<MyWaypoint> lstPoints = new ArrayList<>();

		listaEnderecoParadaTaxis = dados.getListaEnderecoParadaTaxis();

		listaDeConsultas = new Consultas();

		for (Ponto ponto : listaEnderecoParadaTaxis) {

			GeoPosition geoPositionPontoTaxi = new GeoPosition(
					ponto.getLatitude(), ponto.getLongitude());
			double distancia = AlgoritmosGeograficos.calcDistancia(
					geoPositionPontoTaxi, centro) * 1000;

			if (raio >= distancia) {
				listaDeConsultas.adicionar(ponto, distancia);

				Color color = intervaloDeCoresCriminalidade(ponto
						.getCriminalidade());

				lstPoints.add(new MyWaypoint(color, ponto.getCriminalidade(),
						geoPositionPontoTaxi));
			}
		}

		gerenciador.setPontos(lstPoints);
		this.repaint();

	}

	/*
	 * Consulta dois, buscar rua no jTextField e realiza a comparação entre as
	 * paradas para caso exister pintar no mapa.
	 */
	private void buscar(ActionEvent e) {
		List<MyWaypoint> lstPoints = new ArrayList<>();
		listaEnderecoParadaTaxis = dados.getListaEnderecoParadaTaxis();
		listaDeConsultas = new Consultas();

		String rua = textField.getText();

		for (Ponto ponto : listaEnderecoParadaTaxis) {

			if ((ponto.getExpesificacao() + ponto.getLogradouro())
					.contains(rua)) {

				GeoPosition geoPositionPontoTaxi = new GeoPosition(
						ponto.getLatitude(), ponto.getLongitude());

				listaDeConsultas.adicionar(ponto, 0);
				lstPoints.add(new MyWaypoint(Color.BLUE, ponto
						.getCriminalidade(), geoPositionPontoTaxi));
			}
		}

		gerenciador.setPontos(lstPoints);

		this.repaint();

	}

	private Color intervalosDeCoresDistancia(double distancia) {
		if (distancia <= 500)
			return Color.DARK_GRAY;
		else
			return Color.BLUE;
	}

	public Color intervaloDeCoresCriminalidade(int grauCriminalidad) {
		if (grauCriminalidad < 100) {
			return Color.BLUE;
		} else {
			if (grauCriminalidad > 200) {
				return Color.RED;
			}
		}
		return Color.BLACK;
	}

	private class EventosMouse extends MouseAdapter {
		private int lastButton = -1;

		@Override
		public void mousePressed(MouseEvent e) {
			JXMapViewer mapa = gerenciador.getMapKit().getMainMap();
			GeoPosition loc = mapa.convertPointToGeoPosition(e.getPoint());

			// System.out.println(loc.getLatitude()+", "+loc.getLongitude());
			lastButton = e.getButton();
			// Botão 3: seleciona localização
			if (lastButton == MouseEvent.BUTTON3) {
				gerenciador.setSelecaoCentro(loc);
				gerenciador.setSelecaoBorda(loc);
				// gerenciador.getMapKit().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				gerenciador.getMapKit().repaint();
			}
		}

		public void mouseDragged(MouseEvent e) {
			// Arrasta com o botão 3 para definir o raio
			if (lastButton == MouseEvent.BUTTON3) {
				JXMapViewer mapa = gerenciador.getMapKit().getMainMap();
				gerenciador.setSelecaoBorda(mapa.convertPointToGeoPosition(e
						.getPoint()));
				gerenciador.getMapKit().repaint();
			}
		}
	}
}
