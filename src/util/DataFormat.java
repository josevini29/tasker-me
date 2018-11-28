package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.TextField;

public class DataFormat {

    public static Date getAgora() {
        Date agora = new Date();
        return agora;
    }

    public static void autoComplete(TextField campo) {
        if (campo.getText().length() == 10) {
            return;
        }
        String data = AmericaToBrasilSemHora(getAgora());
        if (campo.getText().length() == 2) {
            campo.setText(campo.getText() + data.substring(2, 10));
        }
        if (campo.getText().length() == 5) {
            campo.setText(campo.getText() + data.substring(5, 10));
        }
    }

    public Date StringToDate(String data) throws Exception {
        if (data == null) {
            return null;
        } else {
            if (data.equals("")) {
                return null;
            }
        }        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        sdf.setLenient(false);
        try {
            return sdf.parse(data);
        } catch (ParseException ex) {
            throw new Exception("Data inválida!");
        }
    }

    public String getId() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(new Date());
    }

    public static String BrasilToAmericaSemHora(Date data) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(data);
    }

    public static String AmericaToBrasilSemHora(Date data) {
        if (data != null) {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            return df.format(data);
        } else {
            return "";
        }
    }

    public String AmericaToBrasil(Date data) {
        if (data != null) {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return df.format(data);
        } else {
            return "";
        }
    }
    
    public static String AmericaToBrasilMesAno(Date data) {
        if (data != null) {
            SimpleDateFormat df = new SimpleDateFormat("MM-yyyy");
            return df.format(data);
        } else {
            return "";
        }
    }
    
    public static String AmericaToBrasilMes(Date data) {
        if (data != null) {
            SimpleDateFormat df = new SimpleDateFormat("MM");
            return df.format(data);
        } else {
            return "";
        }
    }
    
    public static String AmericaToBrasilAno(Date data) {
        if (data != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            return df.format(data);
        } else {
            return "";
        }
    }

    public static String getDiaSemana(int dia) {
        switch (dia) {
            case 1:
                return "Domingo";
            case 2:
                return "Segunda-Feira";
            case 3:
                return "Terça-Feira";
            case 4:
                return "Quarta-Feira";
            case 5:
                return "Quinta-Feira";
            case 6:
                return "Sexta-Feira";
            case 7:
                return "Sábado-Feira";
            default:
                return "Não definido";
        }
    }

}