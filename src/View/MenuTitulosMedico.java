
package View;

import java.util.Scanner;


public class MenuTitulosMedico {
    
    Scanner scanner = new Scanner(System.in);
    
    public int menuMedico() {

        StringBuilder builder = new StringBuilder("");

        builder.append("\n============ MENU MEDICO  ===========");
        builder.append("\n0 - Para Sair..................: ");
        builder.append("\n1 - Ver Cadastro de Medico.....: ");
        builder.append("\n2 - Atualizar Dados de Medico..: ");
        builder.append("\n3 - Admnistrar Consultas.......: ");
        builder.append("\n4 - Admnistrar Procedimentos...: ");
        builder.append("\n5 - : ");
        builder.append("\n6 - : ");
        builder.append("\n\nInforme Opcao................: ");
        
        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
      public int menuAlteraDadosMedico() {

        StringBuilder builder = new StringBuilder("");

        builder.append("\n=== MENU ATUALIZACAO DE DADOS MEDICO ===");
        builder.append("\n0 - Para sair.........................: ");
        builder.append("\n1 - Atualizar Login Medico............: ");
        builder.append("\n2 - Atualizar Senha Medico............: ");
        builder.append("\n3 - Atualizar Telefone Medico.........: ");
        builder.append("\n\nInforme Opcao.........................: ");

        System.out.print(builder.toString());
        
        return Integer.parseInt(scanner.nextLine());
    }
      
      public int menuGerenciamentoConsultas() {

        StringBuilder builder = new StringBuilder("");

        builder.append("\n============= MENU GERENCIA CONSULTA ============");
        builder.append("\n0 - Para sair....................................: ");
        builder.append("\n1 - Atender Consultas............................: ");
        builder.append("\n2 - Ver consultas................................: ");
        builder.append("\n3 - Ver Informacoes De Consultas.................: ");
        builder.append("\n4 - Atualizar Descricao De Info De Consulta......: ");
        builder.append("\n\nInforme Opcao....................................: ");

        System.out.print(builder.toString());
        
        return Integer.parseInt(scanner.nextLine());
    }
      
    public int menuGerenciaProcedimentos() {

        StringBuilder builder = new StringBuilder("");

        builder.append("\n============ MENU PROCEDIMENTOS ===========");
        builder.append("\n0 - Para sair do programa.........: ");
        builder.append("\n1 - Marcar Procedimento...........: ");
        builder.append("\n2 - Ver Procedimentos.............: ");
        builder.append("\n3 - Cancelar Procedimento.........: ");
        builder.append("\n4 - Remarcar Procedimento.........: ");
        builder.append("\n\nInforme Opcao...................: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }  
}
