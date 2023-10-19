package functionalTests;
import org.junit.Test;
import br.com.ufcg.model.Fatura;
import br.com.ufcg.model.ProcessadorBoletos;
import br.com.ufcg.model.Boleto;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class ProcessadorTesteFuncionais {

    // Testes nos valores limites
    @Test
    public void test1Particao(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataString = "10/10/2023";       
        LocalDate data = LocalDate.parse(dataString, formatter);
        Fatura fatura = new Fatura(data,4,"Filipe");
        ProcessadorBoletos processadorBoletos = new ProcessadorBoletos(fatura);
        Boleto boleto1 = new Boleto(data, 1,1);
        Boleto boleto2 = new Boleto(data,1,2);
        Boleto boleto3 = new Boleto(data,1,3);
        List<Boleto> boletos = new ArrayList<>();
        boletos.add(boleto1);
        boletos.add(boleto2);
        boletos.add(boleto3);
        processadorBoletos.processarBoletos(boletos);
        assertTrue(fatura.getValorPago() == 3);
        assertFalse(fatura.isPago());
        assertTrue(processadorBoletos.getPagamentos().size() == 3);
    }

    @Test
    public void test2Particao(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataString = "10/10/2023";       
        LocalDate data = LocalDate.parse(dataString, formatter);
        Fatura fatura = new Fatura(data,4,"Filipe");
        ProcessadorBoletos processadorBoletos = new ProcessadorBoletos(fatura);
        Boleto boleto1 = new Boleto(data,1,1);
        Boleto boleto2 = new Boleto(data,1,2);
        Boleto boleto3 = new Boleto(data,2,3);
        List<Boleto> boletos = new ArrayList<>();
        boletos.add(boleto1);
        boletos.add(boleto2);
        boletos.add(boleto3);
        processadorBoletos.processarBoletos(boletos);
        assertTrue(fatura.getValorPago() == 4);
        assertTrue(fatura.isPago());
        assertTrue(processadorBoletos.getPagamentos().size() == 3);
    }

    @Test
    public void test3Particao(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataString = "10/10/2023";       
        LocalDate data = LocalDate.parse(dataString, formatter);
        Fatura fatura = new Fatura(data,4,"Filipe");
        ProcessadorBoletos processadorBoletos = new ProcessadorBoletos(fatura);
        Boleto boleto1 = new Boleto(data,1,1);
        Boleto boleto2 = new Boleto(data,2,2);
        Boleto boleto3 = new Boleto(data,2,3);
        List<Boleto> boletos = new ArrayList<>();
        boletos.add(boleto1);
        boletos.add(boleto2);
        boletos.add(boleto3);
        processadorBoletos.processarBoletos(boletos);
        assertTrue(fatura.getValorPago() == 5);
        assertTrue(fatura.isPago());
        assertTrue(processadorBoletos.getPagamentos().size() == 3);
    }

    // Testes de tabela de decisão

    @Test
    public void test1Tabela(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataString = "10/10/2023";       
        LocalDate data = LocalDate.parse(dataString, formatter);
        Fatura fatura = new Fatura(data,3,"Filipe");
        ProcessadorBoletos processadorBoletos = new ProcessadorBoletos(fatura);
        Boleto boleto1 = new Boleto(data,1,1);
        Boleto boleto2 = new Boleto(data,1,2);
        List<Boleto> boletos = new ArrayList<>();
        boletos.add(boleto1);
        boletos.add(boleto2);
        processadorBoletos.processarBoletos(boletos);
        assertTrue(fatura.getValorPago() == 2);
        assertFalse(fatura.isPago());
        assertTrue(processadorBoletos.getPagamentos().size() == 2);
    }

    @Test
    public void test2Tabela(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataString = "10/10/2023";       
        LocalDate data = LocalDate.parse(dataString, formatter);
        Fatura fatura = new Fatura(data,1,"Filipe");
        ProcessadorBoletos processadorBoletos = new ProcessadorBoletos(fatura);
        Boleto boleto1 = new Boleto(data,1,1);
        List<Boleto> boletos = new ArrayList<>();
        boletos.add(boleto1);
        processadorBoletos.processarBoletos(boletos);
        assertTrue(fatura.getValorPago() == 1);
        assertTrue(fatura.isPago());
        assertTrue(processadorBoletos.getPagamentos().size() == 1);
    }

}