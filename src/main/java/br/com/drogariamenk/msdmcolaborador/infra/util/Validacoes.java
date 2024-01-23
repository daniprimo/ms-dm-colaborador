package br.com.drogariamenk.msdmcolaborador.infra.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
public class Validacoes {

    public static boolean isCPF(String cpf) {

        String rg = "^\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}$";

        Pattern pattern = Pattern.compile(rg);
        Matcher matcher = pattern.matcher(cpf);

        if (matcher.find()) {
            return true;
        } else {
            return false;
        }

    }



}
