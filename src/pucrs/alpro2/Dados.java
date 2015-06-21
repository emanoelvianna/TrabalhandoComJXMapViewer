package pucrs.alpro2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import org.jxmapviewer.viewer.GeoPosition;

import pucrs.alpro2.algoritmos.AlgoritmosGeograficos;

public class Dados {

	private String linha;
	private String csvDivisor1;
	private String csvDivisor2;

	/*
	 * Metodo para ler os arquivos de furtos, roubos e paradas de taxi
	 * 
	 * @param linkedList para guardar informações das paradas de táxi
	 */
	public LinkedList<Ponto> lerArquivoDeParadasDeTaxi() {

		Ponto enderecoParadaTaxi;
		LinkedList<Ponto> listaEnderecoParadaTaxis = null;

		BufferedReader br = null;

		linha = "";
		csvDivisor1 = ";"; // divisor de colunas
		csvDivisor2 = " ";

		try {

			listaEnderecoParadaTaxis = new LinkedList<Ponto>();
			br = new BufferedReader(new FileReader("taxis.csv"));

			while ((linha = br.readLine()) != null) {
				String[] infoParada = linha.split(csvDivisor1);

				enderecoParadaTaxi = new Ponto();

				enderecoParadaTaxi.setId(Integer.valueOf(infoParada[0]));

				// para guardar os a expesificação e logradouro separadamente
				String[] aux = infoParada[1].split(csvDivisor2);
				enderecoParadaTaxi.setExpesificacao(aux[0]);

				String logradouro = "";
				for (int i = 1; i < aux.length; i++) {
					logradouro += " " + aux[i];
				}
				enderecoParadaTaxi.setLogradouro(logradouro);

				enderecoParadaTaxi.setTelefone(infoParada[2]);
				enderecoParadaTaxi.setLongitude(Double.valueOf(infoParada[3]));
				enderecoParadaTaxi.setLatitude(Double.valueOf(infoParada[4]));

				// Chama os metodos para calcular o grau de criminalidade
				calculaGrauCriminalidadeFurtos(enderecoParadaTaxi);
				//calcularGrauCriminalidadeRubos(enderecoParadaTaxi);

				listaEnderecoParadaTaxis.add(enderecoParadaTaxi);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {

			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return listaEnderecoParadaTaxis;

	}

	/*
	 * Metodo para ler os dados de furtos e realizar o calculo guardando no nodo
	 * o grau de criminalidade.
	 * 
	 * @param Nodo EnderecoParadaTaxi
	 * 
	 * @returns lista EnderecoParadaTaxi com o grau calculado
	 */

	public Ponto calculaGrauCriminalidadeFurtos(Ponto enderecoParadaTaxi) {

		linha = "";
		csvDivisor1 = ";";

		BufferedReader br = null;

		try {

			br = new BufferedReader(new FileReader("furtos.csv"));

			int cont = 0;
			while ((linha = br.readLine()) != null) {

				// ignorando a primeira linha :D
				if (cont >= 1) {
					String[] infoFurtos = linha.split(csvDivisor1);

					// try para tratar caso o campo esteja vazio ou nullo
					try {
						if (infoFurtos[11] != null && infoFurtos[11] != "") {
							if (infoFurtos[12] != null && infoFurtos[12] != "") {

								GeoPosition geoPositionParada = new GeoPosition(enderecoParadaTaxi.getLatitude(), enderecoParadaTaxi.getLongitude());
								GeoPosition geoPositionFurto = new GeoPosition(Double.valueOf(infoFurtos[11]), Double.valueOf(infoFurtos[12]));

								// não tenho certeza do que ele me retorna, se é metros ou km
								double diferenca = AlgoritmosGeograficos.calcDistancia(geoPositionParada, geoPositionFurto);

								// acho que a comparacao não esta correta, não sei se o valor deveria ser este
								if (diferenca < 1.5) {
									enderecoParadaTaxi.somaCriminalidade();
									System.out.println("somaCriminalidade: " + enderecoParadaTaxi.getCriminalidade());
								}

							}
						}

					} catch (NumberFormatException numberFormatException) {
						System.out.println("Número fora de padrão");
					}
				}
				cont++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {

			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return enderecoParadaTaxi;

	}

	public Ponto calcularGrauCriminalidadeRubos(Ponto enderecoParadaTaxi) {

		String linha = "";
		String csvDivisor = ";";

		BufferedReader br = null;

		try {

			br = new BufferedReader(new FileReader("roubos.csv"));
			while ((linha = br.readLine()) != null) {
				String[] infoRoubos = linha.split(csvDivisor);

				// try para tratar caso o campo esteja vazio ou nullo
				try {
					if (infoRoubos[11] != null && infoRoubos[11] != "") {
						if (infoRoubos[12] != null && infoRoubos[12] != "") {

							GeoPosition geoPositionParada = new GeoPosition(enderecoParadaTaxi.getLatitude(), enderecoParadaTaxi.getLongitude());
							GeoPosition geoPositionRoubo = new GeoPosition(Double.valueOf(infoRoubos[11]), Double.valueOf(infoRoubos[12]));

							// não tenho certeza do que ele me retorna, se é metros ou km
							double diferenca = AlgoritmosGeograficos.calcDistancia(geoPositionParada, geoPositionRoubo);

							// acho que a comparacao não esta correta, não sei se o valor deveria ser este
							if (diferenca < 1.5) {
								enderecoParadaTaxi.somaCriminalidade();
								System.out.println("somaCriminalidade: " + enderecoParadaTaxi.getCriminalidade());
							}

						}
					}

				} catch (NumberFormatException numberFormatException) {
					System.out.println("Número fora de padrão");
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {

			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return enderecoParadaTaxi;
	}

}
