package Controller;

import Model.Franquia;
import Model.FranquiaDAO;
import Model.Pessoa;
import Model.PessoaDAO;
import View.MenuTitulosFranquia;
import java.time.LocalDateTime;
import java.util.Scanner;

public class FranquiaControladora {

    Scanner scanner = new Scanner(System.in);
    MenuTitulosFranquia telaFranquia = new MenuTitulosFranquia();

    public FranquiaControladora(Franquia franquia, FranquiaDAO franquiaDAO, PessoaDAO pessoaDAO) {

        menuOpcoesFranquia(franquia, franquiaDAO, pessoaDAO);
    }

    private void menuOpcoesFranquia(Franquia franquia, FranquiaDAO franquiaDAO, PessoaDAO pessoaDAO) {

        int opcao;

        do {
            opcao = telaFranquia.menuFranquia();

            switch (opcao) {
                case 1: {
                    System.out.println("\n" + franquiaDAO.buscaFranquia(franquia));
                    break;
                }
                case 2: {
                    cadastraNovaFranquia(franquiaDAO, pessoaDAO);
                    break;
                }
                case 3: {
                    menuOpcoesAtualizarDadosFranquia(franquia, franquiaDAO);
                    break;
                }
                case 4: {
                    break;
                }
                case 5: {
                    System.out.println("======== VETOR DE FRANQUIAS ===========\n");
                    franquiaDAO.mostraTodasFranquias();
                }
            }

        } while (opcao != 0);
    }

    private void cadastraNovaFranquia(FranquiaDAO franquiaDAO, PessoaDAO pessoaDAO) {

        pessoaDAO.filtraPessoasQueNaoSaoDonosDeFranquia();

        System.out.println("\nInforme o Id da pessoa que Sera a Dona da Franquia: ");
        int idPessoa = Integer.parseInt(scanner.nextLine());

        Pessoa pessoa = pessoaDAO.buscaPessoaPorId(idPessoa);

        if (pessoa == null) {
            System.out.println("\nPessoa Nao Encontrada");
        } else {
            System.out.println("\nInforme o Login de Dono de Franquia: ");
            String loginDonoFranquia = scanner.nextLine();

            System.out.println("\nInforme a Senha de Dono de Franquia: ");
            String senhaDonoFranquia = scanner.nextLine();

            if (franquiaDAO.verificaDonosDeFranquia(pessoa) == true) {
                System.out.println("\nPessoa ja Cadastrada Como Dono de Franquia\n");
            } else {
                Pessoa pessoaDonoFranquia = new Pessoa(pessoa.getNomePessoa(), pessoa.getCpf(), pessoa.getEnderecoPessoa(),
                        pessoa.getTelefonePessoa(), loginDonoFranquia, senhaDonoFranquia, "DonodeFranquia", LocalDateTime.now());

                pessoaDAO.adicionaPessoa(pessoaDonoFranquia);

                System.out.println("\nInforme o Nome da Franquia: ");
                String nomeFranquia = scanner.nextLine();

                System.out.println("\nInforme o Cnpj da Franquia: ");
                String cnpjFranquia = scanner.nextLine();

                System.out.println("\nInforme a Cidade da Franquia: ");
                String cidadeFranquia = scanner.nextLine();

                System.out.println("\nInforme o Endereco da Franquia: ");
                String enderecoFranquia = scanner.nextLine();

                if (franquiaDAO.verificaSeFranquiaExiste(nomeFranquia, cnpjFranquia) == true) {
                    System.out.println("\nA Franquia Ja Existe");
                } else {
                    Franquia franquia = new Franquia(nomeFranquia.toUpperCase(), cnpjFranquia,
                            cidadeFranquia, enderecoFranquia, pessoaDonoFranquia, LocalDateTime.now());

                    boolean franquiaAdicionada = franquiaDAO.adicionaFranquia(franquia);

                    if (franquiaAdicionada == true) {
                        System.out.println("\nFranquia Cadastrada Com Sucesso!");
                    } else {
                        System.out.println("\nNao Foi Possivel Cadastrar a Franquia.");
                    }

                }

            }

        }
    }

    private void menuOpcoesAtualizarDadosFranquia(Franquia franquia, FranquiaDAO franquiaDAO) {
        int opcao;

        do {
            opcao = telaFranquia.menuAtualizacaoDeDadosFranquia();

            switch (opcao) {
                case 1: {
                    System.out.println("Informe o Novo Nome da Franquia: ");
                    String novoNomeFranquia = scanner.nextLine();

                    if (franquiaDAO.atualizarNomeFranquia(franquia, novoNomeFranquia) == true) {
                        System.out.println("\nNome Da Franquia Atualizado Com Sucesso!");
                    } else {
                        System.out.println("\nNao Foi Possivel Atualizar O Nome Da Franquia.");
                    }

                    break;
                }
                case 2: {
                    System.out.println("Informe o Novo Cnpj da Franquia: ");
                    String novoCnpjFranquia = scanner.nextLine();

                    if (franquiaDAO.atualizarCnpjFranquia(franquia, novoCnpjFranquia) == true) {
                        System.out.println("\nCnpj Da Franquia Atualizado Com Sucesso!");
                    } else {
                        System.out.println("\nNao Foi Possivel Atualizar O Cnpj Da Franquia.");
                    }

                    break;
                }
                case 3: {
                     System.out.println("Informe a Nova Cidade da Franquia: ");
                     String novaCidadeFranquia = scanner.nextLine();
                     
                     if(franquiaDAO.atualizarCidadeFranquia(franquia, novaCidadeFranquia) == true)
                     {
                       System.out.println("\nCidade Da Franquia Atualizado Com Sucesso!");  
                     }
                     else
                     {
                        System.out.println("\nNao Foi Possivel Atualizar A Cidade Da Franquia.");  
                     }
                    break;
                }
                case 4: 
                {
                     System.out.println("Informe o Novo Endereco Cidade da Franquia: ");
                     String novoEnderecoFranquia = scanner.nextLine();
                     
                     if(franquiaDAO.atualizarEnderecoFranquia(franquia, novoEnderecoFranquia) == true)
                     {
                       System.out.println("\nEndereco Da Franquia Atualizado Com Sucesso!");  
                     }
                     else
                     {
                        System.out.println("\nNao Foi Possivel Atualizar O Endereco Da Franquia.");  
                     }
                    break;
                }
                
                case 5:
                {
                    System.out.println("Informe o Novo Login De Dono De Franquia: ");
                    String novoLoginDonoFranquia = scanner.nextLine();
                    
                    if(franquiaDAO.atualizaLoginDonoDeFranquia(franquia, novoLoginDonoFranquia) == true)
                    {
                      System.out.println("\nLogin De Dono De Franquia Atualizado Com Sucesso!");    
                    }
                    else
                    {
                       System.out.println("\nNao Foi Possivel Atualizar o Login De Dono De Franquia.");   
                    }
                    
                    
                    break;
                }
                
                case 6:
                {
                    System.out.println("Informe a Nova Senha De Dono De Franquia: ");
                    String novaSenhaDonoFranquia = scanner.nextLine();
                    
                    if(franquiaDAO.atualizaSenhaDonoDeFranquia(franquia, novaSenhaDonoFranquia) == true)
                    {
                      System.out.println("\nSenha De Dono De Franquia Atualizado Com Sucesso!");      
                    }
                    else
                    {
                      System.out.println("\nNao Foi Possivel Atualizar a Senha De Dono De Franquia.");    
                    }
                    break;
                }
                
                case 7:
                {
                    System.out.println("Informe o Novo Telefone De Dono De Franquia: ");
                    String novoTelefoneDonoFranquia = scanner.nextLine();
                    
                    if(franquiaDAO.atualizaTelefoneDonoDeFranquia(franquia, novoTelefoneDonoFranquia) == true)
                    {
                      System.out.println("\nTelefone De Dono De Franquia Atualizado Com Sucesso!");   
                    }
                    else
                    {
                      System.out.println("\nNao Foi Possivel Atualizar o Telefone De Dono De Franquia.");  
                    }
                    break;
                }
            }

        } while (opcao != 0);
    }

}
