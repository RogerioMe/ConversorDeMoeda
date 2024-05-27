import netscape.javascript.JSObject;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;

public class CurrencyConverter {
    public static void main(String[] args) throws IOException {
        Boolean running = true;
        do {
            HashMap<Integer, String> currencyCodes = new HashMap<Integer, String>();

            currencyCodes.put(1, "USD");
            currencyCodes.put(2, "CAD");
            currencyCodes.put(3, "EUR");
            currencyCodes.put(4, "HKD");
            currencyCodes.put(5, "INR");

            Integer from, to;
            String fromCode, toCode;
            double amount;

            Scanner sc = new Scanner(System.in);
            System.out.println("Bem-vindo ao conversor de moeda de conversão de moeda!!");

            System.out.println("Conversão de moeda? ");
            System.out.println("1:USD (US Dollar)\t 2: CAD (Canadian Dollar) \t 3:EUR (Euro)\t 4:HKD (Hong Kong Dollar)\t 5:INR (Indian Rupee)");
            from = sc.nextInt();
            while (from < 1 || from > 5) {
                System.out.println("Selecione uma moeda válida (1-5)");
                System.out.println("1:USD (US Dollar)\t 2: CAD (Canadian Dollar) \t 3:EUR (Euro)\t 4:HKD (Hong Kong Dollar)\t 5:INR (Indian Rupee)");
                from = sc.nextInt();
            }
            from = currencyCodes.get(from);


            System.out.println("Conversão de moeda para? ");
            System.out.println("1:USD (US Dollar)\t 2: CAD (Canadian Dollar) \t 3:EUR (Euro)\t 4:HKD (Hong Kong Dollar)\t 5:INR (Indian Rupee)");
            to = sc.nextInt();
            while (to < 1 || to > 5) {
                System.out.println("Selecione uma moeda válida (1-5)");
                System.out.println("1:USD (US Dollar)\t 2: CAD (Canadian Dollar) \t 3:EUR (Euro)\t 4:HKD (Hong Kong Dollar)\t 5:INR (Indian Rupee)");
                to = sc.nextInt();
            }
            to = currencyCodes.get(to);

            System.out.println("Valor que você deseja converter? ");
            amount = sc.nextFloat();

            sendHttpGetRequest(fromCode, toCode, amount);

            System.out.println("Gostaria de fazer outra conversão? ");


        }
        System.out.println("Obrigado por usar o conversor de moeda! ");
        System.out.println("1: Sim\t qualquer outro kay: não");
        if (sc.nextInt() != 1){
            running = false;
        }while (running);

    }
    private static void sendHttpGetRequest(String fromCode,String toCode,double amount) throws IOException {

        DecimalFormat f = new DecimalFormat(00.00);
        String GET_URL = "" + toCode + "" + fromCode;
        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK){
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }in.close();
        }
        Object response = null;
        JSONObject object = new JSONObject(response.toString());
        Double exchageRate = object.getJSONObject("rates").getDouble(fromCode);
        System.out.println(object.getJSONObject("rates"));
        System.out.println(exchageRate);
        System.out.println();
        System.out.println(f.format(amount) + fromCode + " = " + f.format(amount/exchageRate) + toCode);

    }
    else{
        System.out.println("GET Falha na solicitação!");
    }

}