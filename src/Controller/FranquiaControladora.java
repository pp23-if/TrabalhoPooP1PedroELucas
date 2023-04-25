package Controller;

import Model.CalendarioSistema;
import Model.Franquia;
import Model.FranquiaDAO;
import Model.Medico;
import Model.MedicoDAO;
import Model.Pessoa;
import Model.PessoaDAO;
import Model.UnidadeFranquia;
import Model.UnidadeFranquiaDAO;
import View.MenuTitulosFranquia;
import java.time.LocalDateTime;
import java.util.Scanner;

public class FranquiaControladora {

    Scanner scanner = new Scanner(System.in);
    MenuTitulosFranquia telaFranquia = new MenuTitulosFranquia();

    public FranquiaControladora(Franquia franquia, FranquiaDAO franquiaDAO, PessoaDAO pessoaDAO,
            MedicoDAO medicoDAO, UnidadeFranquiaDAO unidadeFranquiaDAO, 
            ValidacaoEntradaDados vd,  CalendarioSistema calendarioSistema) {

        menuOpcoesFranquia(franquia, franquiaDAO, pessoaDAO, medicoDAO, unidadeFranquiaDAO, vd, calendarioSistema);
    }

    private void menuOpcoesFranquia(Franquia franquia, FranquiaDAO franquiaDAO, PessoaDAO pessoaDAO,
            MedicoDAO medicoDAO, UnidadeFranquiaDAO unidadeFranquiaDAO, ValidacaoEntradaDados vd, 
            CalendarioSistema calendarioSistema) {

        int opcao;

        do {
            opcao = telaFranquia.menuFranquia();

            switch (opcao) {
                case 1: {
                    System.out.println("\n" + franquiaDAO.buscaFranquia(franquia));
                    break;
                }
                case 2: {
                    System.out.println("\n" + franquia.getPessoa());
                    break;
                }
                case 3: {
                    cadastraNovaFranquia(franquiaDAO, pessoaDAO, vd);
                    break;
                }
                case 4: {
                    menuOpcoesAtualizarDadosFranquia(franquia, franquiaDAO, vd);
                    break;
                }
                case 5: {

                    break;
                }
                case 6: {
                    System.out.println("\n");
                    franquiaDAO.mostraTodasFranquias();
                    break;
                }
                case 7: {
                    cadastraMedico(pessoaDAO, medicoDAO, vd);
                    break;
                }
                case 8: {
                    System.out.println("\n");
                    medicoDAO.mostraTodosMedicos();
                    break;
                }
                case 9: {
                    cadastraUnidadeFranquia(pessoaDAO, unidadeFranquiaDAO, franquia, vd);
                    break;
                }
                case 10: {
                    System.out.println("\n");
                    unidadeFranquiaDAO.buscaUnidadeFranquiaAtravesDaFranquiaVinculada(franquia);
                    break;
                }
            }

        } while (opcao != 0);
    }

    private void cadastraNovaFranquia(FranquiaDAO franquiaDAO, PessoaDAO pessoaDAO, ValidacaoEntradaDados vd) {

        System.out.println("\n");
        pessoaDAO.filtraPessoasCandidatasADonoDeFranquia();

        System.out.println("\nInforme o Id da pessoa que Sera a Dona da Franquia: ");
        int idPessoa = Integer.parseInt(scanner.nextLine());
        idPessoa = vd.validarINT(idPessoa);

        Pessoa pessoa = pessoaDAO.buscaPessoaPorId(idPessoa);

        if (pessoa == null) {
            System.out.println("\nPessoa Nao Encontrada");
        } else if (franquiaDAO.verificaDonosDeFranquia(pessoa) == true) {
            System.out.println("\nPessoa ja Cadastrada Como Dono de Franquia\n");
        } else {
            System.out.println("\nInforme o Login de Dono de Franquia: ");
            String loginDonoFranquia = scanner.nextLine();
            loginDonoFranquia = vd.validaString(loginDonoFranquia);

            if (franquiaDAO.verificaSeLoginDonoFranquiaEstaSendoUsado(loginDonoFranquia) == true) {
                System.out.println("\nLogin Dono De Franquia Ja Esta Sendo Usado!");
            } else {

                System.out.println("\nInforme a Senha de Dono de Franquia: ");
                String senhaDonoFranquia = scanner.nextLine();
                senhaDonoFranquia = vd.validaString(senhaDonoFranquia);

                Pessoa pessoaDonoFranquia = new Pessoa(pessoa.getNomePessoa(), pessoa.getCpf(), pessoa.getEnderecoPessoa(),
                        pessoa.getTelefonePessoa(), loginDonoFranquia, senhaDonoFranquia, "DonodeFranquia", LocalDateTime.now());

                if (pessoaDAO.adicionaPessoa(pessoaDonoFranquia) == true) {
                    System.out.println("\nInforme o Nome da Franquia: ");
                    String nomeFranquia = scanner.nextLine();
                    nomeFranquia = vd.validaString(nomeFranquia);

                    System.out.println("\nInforme o Cnpj da Franquia: ");
                    String cnpjFranquia = scanner.nextLine();
                    cnpjFranquia = vd.validaString(cnpjFranquia);

                    System.out.println("\nInforme a Cidade da Franquia: ");
                    String cidadeFranquia = scanner.nextLine();
                    cidadeFranquia = vd.validaString(cidadeFranquia);

                    System.out.println("\nInforme o Endereco da Franquia: ");
                    String enderecoFranquia = scanner.nextLine();
                    enderecoFranquia = vd.validaString(enderecoFranquia);

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

                } else {
                    System.out.println("\nErro Ao Cadastrar Franquia.");
                }
            }

        }

    }

    private void menuOpcoesAtualizarDadosFranquia(Franquia franquia, FranquiaDAO franquiaDAO, ValidacaoEntradaDados vd) {
        int opcao;

        do {
            opcao = telaFranquia.menuAtualizacaoDeDadosFranquia();

            switch (opcao) {
                case 1: {
                    System.out.println("\nInforme o Novo Nome da Franquia: ");
                    String novoNomeFranquia = scanner.nextLine();
                    novoNomeFranquia = vd.validaString(novoNomeFranquia);

                    if (franquiaDAO.atualizarNomeFranquia(franquia, novoNomeFranquia) == true) {
                        System.out.println("\nNome Da Franquia Atualizado Com Sucesso!");
                    } else {
                        System.out.println("\nNao Foi Possivel Atualizar O Nome Da Franquia.");
                    }

                    break;
                }

                case 2: {
                    System.out.println("\nInforme a Nova Cidade da Franquia: ");
                    String novaCidadeFranquia = scanner.nextLine();
                    novaCidadeFranquia = vd.validaString(novaCidadeFranquia);

                    if (franquiaDAO.atualizarCidadeFranquia(franquia, novaCidadeFranquia) == true) {
                        System.out.println("\nCidade Da Franquia Atualizado Com Sucesso!");
                    } else {
                        System.out.println("\nNao Foi Possivel Atualizar A Cidade Da Franquia.");
                    }
                    break;
                }
                case 3: {
                    System.out.println("\nInforme o Novo Endereco Cidade da Franquia: ");
                    String novoEnderecoFranquia = scanner.nextLine();
                    novoEnderecoFranquia = vd.validaString(novoEnderecoFranquia);

                    if (franquiaDAO.atualizarEnderecoFranquia(franquia, novoEnderecoFranquia) == true) {
                        System.out.println("\nEndereco Da Franquia Atualizado Com Sucesso!");
                    } else {
                        System.out.println("\nNao Foi Possivel Atualizar O Endereco Da Franquia.");
                    }
                    break;
                }

                case 4: {
                    System.out.println("\nInforme o Novo Login De Dono De Franquia: ");
                    String novoLoginDonoFranquia = scanner.nextLine();
                    novoLoginDonoFranquia = vd.validaString(novoLoginDonoFranquia);

                    if (franquiaDAO.atualizaLoginDonoDeFranquia(franquia, novoLoginDonoFranquia) == true) {
                        System.out.println("\nLogin De Dono De Franquia Atualizado Com Sucesso!");
                    } else {
                        System.out.println("\nNao Foi Possivel Atualizar o Login De Dono De Franquia.");
                    }

                    break;
                }

                case 5: {
                    System.out.println("\nInforme a Nova Senha De Dono De Franquia: ");
                    String novaSenhaDonoFranquia = scanner.nextLine();
                    novaSenhaDonoFranquia = vd.validaString(novaSenhaDonoFranquia);

                    if (franquiaDAO.atualizaSenhaDonoDeFranquia(franquia, novaSenhaDonoFranquia) == true) {
                        System.out.println("\nSenha De Dono De Franquia Atualizado Com Sucesso!");
                    } else {
                        System.out.println("\nNao Foi Possivel Atualizar a Senha De Dono De Franquia.");
                    }
                    break;
                }

                case 6: {
                    System.out.println("\nInforme o Novo Telefone De Dono De Franquia: ");
                    String novoTelefoneDonoFranquia = scanner.nextLine();
                    novoTelefoneDonoFranquia = vd.validaString(novoTelefoneDonoFranquia);

                    if (franquiaDAO.atualizaTelefoneDonoDeFranquia(franquia, novoTelefoneDonoFranquia) == true) {
                        System.out.println("\nTelefone De Dono De Franquia Atualizado Com Sucesso!");
                    } else {
                        System.out.println("\nNao Foi Possivel Atualizar o Telefone De Dono De Franquia.");
                    }
                    break;
                }
            }

        } while (opcao != 0);
    }

    private void cadastraMedico(PessoaDAO pessoaDAO, MedicoDAO medicoDAO, ValidacaoEntradaDados vd) {

        System.out.println("\n");
        pessoaDAO.filtraPessoasCandidatasAMedico();

        System.out.println("\nInforme o Id da pessoa que Sera Medico: ");
        int idPessoa = Integer.parseInt(scanner.nextLine());
        idPessoa = vd.validarINT(idPessoa);

        Pessoa pessoaEncontrada = pessoaDAO.buscaPessoaPorId(idPessoa);

        if (pessoaEncontrada == null) {
            System.out.println("\nPessoa Nao Encontrada");
        } else {

            if (medicoDAO.verificaSeMedicoExiste(pessoaEncontrada) == true) {
                System.out.println("\nPessoa ja Cadastrada Como Medico.");
            } else {

                System.out.println("\nInforme O Crm do Medico: ");
                String crm = scanner.nextLine();
                crm = vd.validaString(crm);

                if (medicoDAO.verificaCrm(crm) == true) {
                    System.out.println("\nO Crm Informado Ja Esta Sendo Usado.");
                } else {
                    System.out.println("\nInforme A Especialidade do Medico: ");
                    String medicoEspecialidade = scanner.nextLine();
                    medicoEspecialidade = vd.validaString(medicoEspecialidade);

                    System.out.println("\nInforme O Login De Medico: ");
                    String LoginMedico = scanner.nextLine();
                    LoginMedico = vd.validaString(LoginMedico);

                    if (medicoDAO.verificaSeloginEstaSendoUsado(LoginMedico) == true) {
                        System.out.println("\nLogin De Medico Ja Esta Sendo Usado!");
                    } else {
                        System.out.println("\nInforme A Senha De Medico: ");
                        String senhaMedico = scanner.nextLine();
                        senhaMedico = vd.validaString(senhaMedico);

                        Pessoa pessoaMedico = new Pessoa(pessoaEncontrada.getNomePessoa(),
                                pessoaEncontrada.getCpf(), pessoaEncontrada.getEnderecoPessoa(),
                                pessoaEncontrada.getTelefonePessoa(), LoginMedico, senhaMedico, "Medico", LocalDateTime.now());

                        if (pessoaDAO.adicionaPessoa(pessoaMedico) == true) {

                            Medico medico = new Medico(crm, pessoaMedico, medicoEspecialidade, LocalDateTime.now());

                            if (medicoDAO.adicionaMedico(medico) == true) {
                                System.out.println("\nMedico Cadastrado Com Sucesso!");
                            } else {
                                System.out.println("\nNao Foi Possivel Cadastrar o Medico.");
                            }
                        } else {
                            System.out.println("\n Erro ao Cadastrar medico.");
                        }
                    }

                }

            }

        }
    }

    private void cadastraUnidadeFranquia(PessoaDAO pessoaDAO,
            UnidadeFranquiaDAO unidadeFranquiaDAO, Franquia franquia, ValidacaoEntradaDados vd) {

        System.out.println("\n");
        pessoaDAO.filtraPessoaCandidatasADonoUnidadeFranquia();

        System.out.println("\nInforme o Id da Pessoa Que Sera Dono De Unidade De Franquia: ");
        int idPessoa = Integer.parseInt(scanner.nextLine());
        idPessoa = vd.validarINT(idPessoa);

        Pessoa pessoaEncontrada = pessoaDAO.buscaPessoaPorId(idPessoa);

        if (pessoaEncontrada == null) {
            System.out.println("\nPessoa Nao Encontrada");
        } else {
            if (unidadeFranquiaDAO.verificaSeDonoUnidadeFranquiaExiste(pessoaEncontrada) == true) {
                System.out.println("\nPessoa ja Cadastrada Como Dono De Unidade de Franquia.");
            } else {
                System.out.println("\nInforme a Cidade da Unidade De Franquia: ");
                String cidadeUnidadeFranquia = scanner.nextLine();
                cidadeUnidadeFranquia = vd.validaString(cidadeUnidadeFranquia);

                System.out.println("\nInforme o Endereco da Unidade De Franquia: ");
                String enderecoUnidadeFranquia = scanner.nextLine();
                enderecoUnidadeFranquia = vd.validaString(enderecoUnidadeFranquia);

                System.out.println("\nInforme O Login Dono De Unidade De Franquia: ");
                String LoginDonoUnidadeFranquia = scanner.nextLine();
                LoginDonoUnidadeFranquia = vd.validaString(LoginDonoUnidadeFranquia);

                if (unidadeFranquiaDAO.verificaSeLoginDonoDeUnidadeFranquiaEstaEmUso(LoginDonoUnidadeFranquia) == true) {
                    System.out.println("\nLogin De Dono Unidade Franquia Ja Esta Sendo Usado!");
                } else {
                    System.out.println("\nInforme A Senha De Dono De Unidade De Franquia: ");
                    String senhaDonoUnidadeFranquia = scanner.nextLine();
                    senhaDonoUnidadeFranquia = vd.validaString(senhaDonoUnidadeFranquia);

                    Pessoa donoUnidadeFranquia = new Pessoa(pessoaEncontrada.getNomePessoa(),
                            pessoaEncontrada.getCpf(), pessoaEncontrada.getEnderecoPessoa(),
                            pessoaEncontrada.getTelefonePessoa(),
                            LoginDonoUnidadeFranquia, senhaDonoUnidadeFranquia,
                            "DonoDeUnidadeDeFranquia", LocalDateTime.now());

                    if (pessoaDAO.adicionaPessoa(donoUnidadeFranquia) == true) {
                        UnidadeFranquia unidadeFranquia = new UnidadeFranquia(franquia,
                                cidadeUnidadeFranquia, enderecoUnidadeFranquia, donoUnidadeFranquia, LocalDateTime.now());

                        if (unidadeFranquiaDAO.adicionaUnidadeFranquia(unidadeFranquia) == true) {
                            System.out.println("\nUnidade De Franquia Cadastrada Com Sucesso!");
                        } else {
                            System.out.println("\nNao Foi Possivel Cadastrar A Unidade De Franquia.");
                        }
                    } else {
                        System.out.println("\n Erro ao Cadastrar Dono De Unidade Franquia.");
                    }

                }

            }

        }

    }

}
