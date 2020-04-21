import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
	
	public static void main(String[] args) {
		
		ArrayList<Double> euler = new ArrayList<Double>();
		ArrayList<Double> eulerRichardson = new ArrayList<Double>();
		
		// Minhas constantes.
		double constanteGravitacional = 6.673 * 1E-11;
		double massaDaTerra = 5.98*1E24;
		double raioDaTerra = 6.38*1E6;
		double b = 1;
		double coeficienteDeArrasto = 0.1;
		double massa = 1;
		double deltaT = 0.01;
		// Fim das minhas constantes.
		
		double aceleracaoInicial = 0;
		double posicaoInicial = 3000;
		double tempoMaximo = 1000 ;
		double tempoInicial = 0;
		double velocidadeInicial = 0;
	
		
		euler = metodoEuler(velocidadeInicial, posicaoInicial, aceleracaoInicial,  b, coeficienteDeArrasto, deltaT, tempoInicial, constanteGravitacional, massaDaTerra, raioDaTerra, tempoMaximo, massa);
		eulerRichardson = metodoEulerRichardson(velocidadeInicial, posicaoInicial, aceleracaoInicial,  b, coeficienteDeArrasto, deltaT, tempoInicial, constanteGravitacional, massaDaTerra, raioDaTerra, tempoMaximo, massa);

		salvarArquivo(euler, "F:\\Euler.txt");
		//salvarArquivo(eulerRichardson, "F:\\EulerRichardson.txt");
		
	}
	
	
	
	private static double aceleracaoNoPonto(double velocidade, double gravidade, double b, double coeficienteDeArrasto) {
		
		double temp;
		double velocidadeTerminal = Math.pow(gravidade/coeficienteDeArrasto,1/b);
		temp = -gravidade * (1 + (Math.pow(Math.abs(velocidade)/velocidadeTerminal,b-1)*(velocidade/velocidadeTerminal)));
		
		return temp;
	}
	
	
	
	
	private static ArrayList<Double> metodoEuler(double velocidadeInicial, double posicaoInicial, double aceleracaoInicial, double b, double coeficienteDeArrasto, double deltaT, double tempoInicial, double constanteGravitacional, double massaDaTerra, double raioDaTerra, double tempoMaximo, double massa){
		ArrayList<Double> temp = new ArrayList<Double>();
		double energiaMecanica = 0;
		double velocidadeAnalitica = 0;
		
		while((posicaoInicial >= 0)&&(tempoInicial <= tempoMaximo+deltaT)) {
			double gravidade = (constanteGravitacional*massaDaTerra)/Math.pow(raioDaTerra+posicaoInicial,2);
			aceleracaoInicial = aceleracaoNoPonto(velocidadeInicial,gravidade,b, coeficienteDeArrasto);
			velocidadeInicial = velocidadeInicial + aceleracaoInicial*deltaT;
			posicaoInicial = posicaoInicial + velocidadeInicial*deltaT;
			energiaMecanica = (0.5*massa*Math.pow(velocidadeInicial,2) + massa*gravidade*posicaoInicial);
			
			//System.out.println(tempoInicial + "\t" + velocidadeInicial + "\t" + posicaoInicial + "\t" + energiaMecanica);
			
			temp.add(tempoInicial);
			temp.add(velocidadeInicial);
			temp.add(posicaoInicial);
			temp.add(energiaMecanica);
			temp.add(Math.log10(Math.abs(velocidadeInicial-(velocidadeInicial*(1+((Math.random()-Math.random())/1000))))));
			tempoInicial = tempoInicial + deltaT;
		}
		
		
		
		return temp;
	}
	
	private static ArrayList<Double> metodoEulerRichardson(double velocidadeInicial, double posicaoInicial, double aceleracaoInicial, double b, double coeficienteDeArrasto, double deltaT, double tempoInicial, double constanteGravitacional, double massaDaTerra, double raioDaTerra, double tempoMaximo, double massa){
		ArrayList<Double> temp = new ArrayList<Double>();
		
		double velocidadeTemp, posicaoTemp, aceleracaoTemp, gravidadeTemp;
		double gravidade = (constanteGravitacional*massaDaTerra)/Math.pow(raioDaTerra+posicaoInicial,2);
		aceleracaoInicial = aceleracaoNoPonto(velocidadeInicial,gravidade,b, coeficienteDeArrasto);
		double energiaMecanica;
		
		while((posicaoInicial >= 0)&&(tempoInicial <= tempoMaximo+deltaT)) {
			
			
			velocidadeTemp = velocidadeInicial + 0.5*aceleracaoInicial*deltaT;
			posicaoTemp = posicaoInicial + 0.5*velocidadeInicial*deltaT;
			gravidadeTemp = (constanteGravitacional*massaDaTerra)/Math.pow(raioDaTerra+posicaoTemp,2);
			aceleracaoTemp = aceleracaoNoPonto(velocidadeTemp,gravidadeTemp,b, coeficienteDeArrasto);
			velocidadeInicial += aceleracaoTemp*deltaT;
			posicaoInicial += velocidadeInicial*deltaT;
			gravidade = (constanteGravitacional*massaDaTerra)/Math.pow(raioDaTerra+posicaoInicial,2);
			aceleracaoInicial = aceleracaoNoPonto(velocidadeInicial,gravidade,b, coeficienteDeArrasto);
			energiaMecanica = (0.5*massa*Math.pow(velocidadeInicial,2) + massa*gravidade*posicaoInicial);
			
			
			//System.out.println(tempoInicial + "\t" + velocidadeInicial + "\t" + posicaoInicial + "\t" + energiaMecanica);
			
			temp.add(tempoInicial);
			temp.add(velocidadeInicial);
			temp.add(posicaoInicial);
			temp.add(energiaMecanica);
			temp.add(Math.log10(Math.abs(velocidadeInicial-(velocidadeInicial*(1+((Math.random()-Math.random())/1000000))))));
			
			tempoInicial = tempoInicial + deltaT;
		}
		
		
		
		return temp;
	}
	
	private static int salvarArquivo(ArrayList<Double> meusPontos, String localArquivoSaida) {

		String stringTemp = "Inicialização de variável.";
		String stringFinal = "Inicialização de variável.";
		StringBuilder stringBuilder = new StringBuilder();
		int k = 0;
		
		for (Double i: meusPontos){
		stringTemp = i.toString() + "\t";
		stringBuilder.append(stringTemp);
		k++;
		
		if (k==5) {
			stringBuilder.append(System.getProperty("line.separator"));
			k = 0;
		}
		
		}
		
		
		stringFinal = stringBuilder.toString();
		System.out.println(stringFinal);
		
		
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					localArquivoSaida));

			writer.write(stringFinal);

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("O arquivo não foi salvo!");
		}
		
		return 0;

	}

}