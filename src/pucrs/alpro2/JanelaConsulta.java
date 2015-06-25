/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pucrs.alpro2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import pucrs.alpro2.algoritmos.AlgoritmosGeograficos;

public class JanelaConsulta extends javax.swing.JFrame {

	private Dados dados;
	private LinkedList<Ponto> listaEnderecoParadaTaxis;
	private Consultas consultas;

	private GerenciadorMapa gerenciador;
	private EventosMouse mouse;

	private JPanel painelMapa;
	private JPanel painelLateral;

	/**
	 * Creates new form JanelaConsulta
	 */
	public JanelaConsulta() {
		super();
		// initComponents();

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
		getContentPane().add(painelLateral, BorderLayout.WEST);

		JButton btnNewButton = new JButton("Consulta");

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consulta(e);
			}
		});

		// botão para teste
		JButton btnNewButton2 = new JButton("Grau Criminalidade");

		painelLateral.add(btnNewButton);
		painelLateral.add(btnNewButton2);

		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Importante leitura de dados;
		dados = new Dados();
		dados.lerArquivoDeParadasDeTaxi();

	}

	private void consulta(java.awt.event.ActionEvent evt) {

		// Para obter o centro e o raio, usar como segue:
		GeoPosition centro = gerenciador.getSelecaoCentro();
		int raio = gerenciador.getRaio();

		// Lista para armazenar o resultado da consulta
		List<MyWaypoint> lstPoints = new ArrayList<>();

		listaEnderecoParadaTaxis = dados.getListaEnderecoParadaTaxis();

		for (Ponto ponto : listaEnderecoParadaTaxis) {
			GeoPosition geoPositionPontoTaxi = new GeoPosition(
					ponto.getLatitude(), ponto.getLongitude());
			double distancia = AlgoritmosGeograficos.calcDistancia(
					geoPositionPontoTaxi, centro) * 1000;
			if (raio >= distancia) {
				if (ponto.getCriminalidade() >= 250) {
					lstPoints.add(new MyWaypoint(Color.BLUE, ponto
							.getCriminalidade(), geoPositionPontoTaxi));
				} else {
					lstPoints.add(new MyWaypoint(Color.GREEN, ponto
							.getCriminalidade(), geoPositionPontoTaxi));

				}
			}
		}

		/*
		 * // Exemplo:
		 * 
		 * // ex: valor da consulta (criminalidade ou distancia) double valor =
		 * 250;
		 * 
		 * // ex: localiza��o da parada GeoPosition loc = new
		 * GeoPosition(-30.05, -51.18);
		 * 
		 * double distancia1 = AlgoritmosGeograficos.calcDistancia(loc, centro)
		 * * 1000;
		 * 
		 * System.out.println("Distancia: " + distancia1);
		 * System.out.println("Raio: " + raio);
		 * 
		 * if (raio >= distancia1) { lstPoints.add(new MyWaypoint(Color.BLUE,
		 * valor, loc)); }
		 */
		// Informa o resultado para o gerenciador
		gerenciador.setPontos(lstPoints);
		// Informa o intervalo de valores gerados, para calcular a cor de cada
		// ponto
		double menorValor = 15; // exemplo
		double maiorValor = 250; // exemplo
		gerenciador.setIntervaloValores(menorValor, maiorValor);

		this.repaint();

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
