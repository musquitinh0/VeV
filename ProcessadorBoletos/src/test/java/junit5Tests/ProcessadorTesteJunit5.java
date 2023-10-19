package junit5Tests;
import br.com.ufcg.model.TipoPagamento;
import org.junit.Test;
import br.com.ufcg.model.Fatura;
import br.com.ufcg.model.ProcessadorBoletos;
import br.com.ufcg.model.Boleto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class ProcessadorTesteJunit5 {

    LocalDate data;
    
    @BeforeAll
    public void setUp(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataString = "10/10/2023";
        this.data = LocalDate.parse(dataString, formatter);
    }

    // Testes de partição por equivalência
    @Test
    @DisplayName("Caso de teste 1 da suíte de testes de partição por equivalência")
    @Tag("TestParticao")
    public void testCT1Particao(){
        Fatura fatura = new Fatura(data,1000,"Filipe");
        ProcessadorBoletos processadorBoletos = new ProcessadorBoletos(fatura);
        Boleto boleto1 = new Boleto(data,333,1);
        Boleto boleto2 = new Boleto(data,333,2);
        Boleto boleto3 = new Boleto(data,333,3);
        List<Boleto> boletos = new ArrayList<>();
        boletos.add(boleto1);
        boletos.add(boleto2);
        boletos.add(boleto3);
        processadorBoletos.processarBoletos(boletos);
        assertTrue(fatura.getValorPago() == 999);
        assertFalse(fatura.isPago());
        assertTrue(processadorBoletos.getPagamentos().size() == 3);
    }

    @Test
    @DisplayName("teste 1 da suíte de testes de particao por equivalencia (Agora verificando todos os dados da fatura)")
    @Tag("TestParticao")
    public void test1Extendido(){
        Fatura fatura = new Fatura(data,4,"Filipe");
        assertEquals(fatura.getData(),this.data);
        assertEquals(fatura.getNomeCliente(),"Filipe");
        ProcessadorBoletos processadorBoletos = new ProcessadorBoletos(fatura);
        Boleto boleto1 = new Boleto(data,1,1);
        assertEquals(boleto1.getCodigo(),1);
        assertEquals(boleto1.getData(),this.data);
        Boleto boleto2 = new Boleto(data,1,2);
        Boleto boleto3 = new Boleto(data,1,3);
        List<Boleto> boletos = new ArrayList<>();
        boletos.add(boleto1);
        boletos.add(boleto2);
        boletos.add(boleto3);
        processadorBoletos.processarBoletos(boletos);
        assertEquals(boleto1.getTipoPagamento(), TipoPagamento.BOLETO);
        assertTrue(fatura.getValorPago() == 3);
        assertFalse(fatura.isPago());
        assertTrue(processadorBoletos.getPagamentos().size() == 3);
    }

    @Test
    @DisplayName("teste 2 da suíte de testes(valor pago igual ao valor da fatura)")
    @Tag("TestParticao")
    public void test2Particao(){
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
    @DisplayName("teste 3 da suíte de testes(valor pago superior ao valor da fatura)")
    @Tag("TestParticao")
    public void testCT3Particao(){
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
    @DisplayName("teste 1 da suíte de testes de tabela de decisão(1+1 < 3)")
    @Tag("TestTabela")
    public void testCT1Tabela(){
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
    @DisplayName("teste 2 da suíte de testes de tabela de decisão")
    @Tag("TestTabela")
    public void testCT2Tabela(){
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

    @Test
    @DisplayName("Verificação de estado inválido do status do boleto antes de processamento")
    @Tag("TestException")
    public void testInvalidStatusBoleto(){
        Boleto boleto1 = new Boleto(data,333,1);
        assertThrows(NullPointerException.class,
                     ()->{
                        TipoPagamento tipo = boleto1.getTipoPagamento();
                        System.out.println("O tipo de pagamento é " + tipo.toString());
                     });

    }

}