
package Model;

import java.time.LocalDateTime;


public class MedicoDAO {
    
    private Medico[] vetorMedico = new Medico[20];

    public MedicoDAO(PessoaDAO pessoaDAO) {
        
        
        Pessoa pessoaMedico = pessoaDAO.mostraDadosPessoaLogada("lm23", "456");
        
        if(pessoaMedico != null)
        {
            Medico medico = new Medico("ABC-123", pessoaMedico, "Ortopedista", LocalDateTime.now());
            adicionaMedico(medico);
        }
        
        
    }

    
    
     private int proximaPosilivreMedico() {
        for (int i = 0; i < vetorMedico.length; i++) {
            if (vetorMedico[i] == null) {
                return i;
            }

        }
        return -1;
    }
    
     public boolean adicionaMedico(Medico medico) {
        int proxima = proximaPosilivreMedico();
        if (proxima != -1) {
            vetorMedico[proxima] = medico;
            return true;
        } else {
            return false;
        }

    }
     
     
      public void mostraTodosMedicos() {
          for (Medico medico : vetorMedico) {
              if(medico != null)
              {
                  System.out.println(medico);
              }
          }
    }
     
     
     
     
     
     
     
     
}
