package util;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JOptionPane;

import org.json.JSONObject;

import exception.ValidarException;

public class Validar {
	
	public static Map<String, String> validarCEP(String cep) {
		try {
			
			if (cep == null) {
				throw new NullPointerException("Operação cancelada!");
			}
			
			cep = cep.replaceAll("[^0-9]", "");
			
			if (cep.length() != 8) {
				throw new ValidarException("CEP inválido!");
			}
			
			URL url = new URL("https://viacep.com.br/ws/%s/json/".formatted(cep));
			
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			int status = con.getResponseCode();
			
			if (status != 200) {
				throw new RuntimeException("Internal server error, please try again later");
			}
			else {
				
				StringBuffer bf = new StringBuffer();
				
				Scanner scanner = new Scanner(url.openStream());
				
				while (scanner.hasNext()) {
					bf.append(scanner.nextLine() + "\n");
				}
				
				scanner.close();
				
				if (bf.toString().contains("erro: true")) {
					throw new ValidarException("CEP inválido!");
				}
				
				JSONObject json = new JSONObject(bf.toString());
				
				Map<String, String> endereco = new HashMap<>();
				
				for (String key : json.keySet()) {
					endereco.put(key, json.getString(key));
				}
				
				endereco.put("pais", "Brasil");
				endereco.replace("cep", endereco.get("cep"), endereco.get("cep").replaceAll("[^0-9]", ""));
				return endereco;
			}
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return null;
	}

	public static String validarCPF(String cpf) {
		try {
			
			if (cpf == null) {
				throw new NullPointerException("Operação cancelada!");
			}
			
			cpf = cpf.replaceAll("[^0-9]", "");

			if (cpf.length() != 11) {
				throw new ValidarException("CPF inválido!");
			}

			int result1 = 0, result2 = 0;

			for (int i = 0, aux = 10; i < cpf.length() - 2; i++, aux--) {
				result1 += aux * Integer.parseInt(String.valueOf(cpf.charAt(i)));
			}

			for (int i = 0, aux = 11; i < cpf.length() - 1; i++, aux--) {
				result2 += aux * Integer.parseInt(String.valueOf(cpf.charAt(i)));
			}

			result1 = ((result1 * 10) % 11) == 10 ? 0 : ((result1 * 10) % 11);
			result2 = ((result2 * 10) % 11) == 10 ? 0 : ((result2 * 10) % 11);

			if (result1 == Integer.parseInt(String.valueOf(cpf.charAt(9)))
					&& result2 == Integer.parseInt(String.valueOf(cpf.charAt(10))))
				return cpf;
			else
				throw new ValidarException("CPF inválido!");
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return null;
	}
	
	public static String validarCNPJ(String cnpj) {
		try {
			if (cnpj == null) {
				throw new NullPointerException("Operação cancelada!");
			}
			
			cnpj = cnpj.replaceAll("[^0-9]", "");

			if (cnpj.length() != 14) {
				throw new ValidarException("CNPJ inválido!");
			}
			
			int result1 = 0, result2 = 0;
			int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2}, pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
			
			for (int i = 0; i < pesos1.length; i++) {
				result1 += pesos1[i] * Integer.parseInt(String.valueOf(cnpj.charAt(i)));
			}
			
			result1 = (result1 % 11) < 2 ? 0 : (11 - (result1 % 11));
			
			for (int i = 0; i < pesos2.length; i++) {
				result2 += pesos2[i] * Integer.parseInt(String.valueOf(cnpj.charAt(i)));
			}
			
			result2 = (result2 % 11) < 2 ? 0 : (11 - (result2 % 11));
			
			if (result1 == Integer.parseInt(String.valueOf(cnpj.charAt(12))) && result2 == Integer.parseInt(String.valueOf(cnpj.charAt(13))))
				return cnpj;
			else
				throw new ValidarException("CNPJ inválido!");
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return null;
	}
	
	public static String  validarIE(String ie) {
		try {
			if (ie == null) {
				throw new NullPointerException("Operação cancelada!");
			}
			
			ie = ie.replaceAll("[^0-9Pp]", "");

			if (ie.contains("P") || ie.contains("p")) {
				
				if (ie.length() != 14) {
					throw new ValidarException("Inscrição estadual inválida!");
				}
				
				int result = 0;
				int[] pesos = {1, 3, 4, 5, 6, 7, 8, 10};
				
				for (int i = 0; i < pesos.length; i++) {
					result += pesos[i] * Integer.parseInt(String.valueOf(ie.charAt(i+1)));
				}
				
				result = (result % 11) == 10 ? 0 : (result % 11);
				
				if (result == Integer.parseInt(String.valueOf(ie.charAt(9))))
					return ie;
				else
					throw new ValidarException("Inscrição estadual inválida!");
			}
			else {
				
				if (ie.length() != 12) {
					throw new ValidarException("O valor de entrada não é válido!");
				}
				
				int result1 = 0, result2 = 0;
				
				int[] pesos1 = {1, 3, 4, 5, 6, 7, 8, 10}, pesos2 = {3, 2, 10, 9, 8, 7, 6, 5, 4, 3, 2};
				
				for (int i = 0; i < pesos1.length; i++) {
					result1 += pesos1[i] * Integer.parseInt(String.valueOf(ie.charAt(i)));
				}
				
				result1 = (result1 % 11) == 10 ? 0 : (result1 % 11);
				
				for (int i = 0; i < pesos2.length; i++) {
					result2 += pesos2[i] * Integer.parseInt(String.valueOf(ie.charAt(i)));
				}
				
				result2 = (result2 % 11) == 10 ? 0 : (result2 % 11);
				
				if (result1 == Integer.parseInt(String.valueOf(ie.charAt(8))) && result2 == Integer.parseInt(String.valueOf(ie.charAt(11))))
					return ie;
				else
					throw new ValidarException("Inscrição estadual inválida!");
			}
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return null;
	}
	
	public static String validarRG(String rg) {
		try {
			if (rg == null) {
				throw new NullPointerException("Operação cancelada!");
			}
			
			rg = rg.replaceAll("[^0-9X]", "");
			
			if (rg.length() != 9) {
				throw new ValidarException("RG inválido!");
			}
			
			int result = 0;
			int[] pesos = {2, 3, 4, 5, 6, 7, 8, 9};
			
			for (int i = 0; i < pesos.length; i++) {
				result += pesos[i] * Integer.parseInt(String.valueOf(rg.charAt(i)));
			}
			
			result = (11 - (result % 11)) == 11 ? 0 : (11 - (result % 11));
			
			if (rg.contains("X")) {
				if (result == 10)
					return rg;
				else
					throw new ValidarException("RG inválido!");
			}
			else {
				if (result == Integer.parseInt(String.valueOf(rg.charAt(8))))
					return rg;
				else
					throw new ValidarException("RG inválido!");
			}
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return null;
	}
	
	public static boolean confirmarSenha(String senha, String confirmar) {
		return senha.equals(confirmar);
	}
}
