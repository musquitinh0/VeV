import org.junit.Test;

import br.com.ufcg.model.Boleto;
import br.com.ufcg.model.Fatura;
import br.com.ufcg.model.ProcessadorBoletos;
import br.com.ufcg.model.Boleto;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class ProcessadorBoletosTest {

    @Test
    public void testCriarProcessadorBoletos(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataString = "23/02/2000";       
        LocalDate data = LocalDate.parse(dataString, formatter);
        Fatura fatura = new Fatura(data,12341.23,"Filipe");
        ProcessadorBoletos processadorBoletos = new ProcessadorBoletos(fatura);
    }
    
    @Test
    public void testAdicionaBoletosPagouFatura(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataString = "01/01/2023";       
        LocalDate data = LocalDate.parse(dataString, formatter);
        Fatura fatura = new Fatura(data,2500,"Everton");
        ProcessadorBoletos processadorBoletos = new ProcessadorBoletos(fatura);
        Boleto boleto1 = new Boleto(data,500,1);
        Boleto boleto2 = new Boleto(data,500,1);
        Boleto boleto3 = new Boleto(data,1500,1);
        List<Boleto> boletos = new ArrayList<>();
        boletos.add(boleto1);
        boletos.add(boleto2);
        boletos.add(boleto3);
        processadorBoletos.processarBoletos(boletos);
        assertTrue(fatura.isPago());
    }

    @Test
    public void testAdicionaBoletosNaoPagouFatura(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataString = "01/01/2021";       
        LocalDate data = LocalDate.parse(dataString, formatter);
        Fatura fatura = new Fatura(data,12345,"Adrian");
        ProcessadorBoletos processadorBoletos = new ProcessadorBoletos(fatura);
        Boleto boleto1 = new Boleto(data,2,1);
        Boleto boleto2 = new Boleto(data,3,2);
        Boleto boleto3 = new Boleto(data,40,3);
        List<Boleto> boletos = new ArrayList<>();
        boletos.add(boleto1);
        boletos.add(boleto2);
        boletos.add(boleto3);
        processadorBoletos.processarBoletos(boletos);
        assertFalse(fatura.isPago());
    }


}
