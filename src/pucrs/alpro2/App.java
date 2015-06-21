/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pucrs.alpro2;

import java.io.IOException;
import java.util.LinkedList;

import org.jxmapviewer.viewer.GeoPosition;

import pucrs.alpro2.algoritmos.AlgoritmosGeograficos;

public class App {
	public static void main(String[] args) throws IOException {

		JanelaConsulta janela = new JanelaConsulta();
		janela.setVisible(true);
		LinkedList<Ponto> paradasTaxi = new LinkedList<Ponto>();
		Dados leitorDados = new Dados();

		//paradasTaxi lista com informações das paradas e grau de criminalidade
		paradasTaxi = leitorDados.lerArquivoDeParadasDeTaxi();

		// Test simples com as informações na lista 
		//System.out.println("ID parada: " + paradasTaxi.get(0).getId());

		
		
		// teste para calculo
		// vou precisar mudar o Raio certo?! pq inicialmete ele esta 6372.8
		GeoPosition roubo = new GeoPosition(-30.0602646, -51.1520996);
		GeoPosition furto = new GeoPosition(-30.0329304, -51.2397118);
		GeoPosition parada = new GeoPosition(Double.valueOf(paradasTaxi.get(0).getLatitude()), Double.valueOf(paradasTaxi.get(0).getLongitude()));

		double vaiMeuGuri = AlgoritmosGeograficos.calcDistancia(parada, furto);
		System.out.println(vaiMeuGuri);
		
		
		/*
		 * IMPORTANTE preciso ignorar a primeira linha da tabela, pois é um cabeçalho
		 */
	}
}
